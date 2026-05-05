package com.smartqueue.smartqueue.domain.servicepoint;

import com.smartqueue.smartqueue.domain.organization.Organization;
import com.smartqueue.smartqueue.domain.organization.OrganizationService;
import com.smartqueue.smartqueue.exception.BusinessException;
import com.smartqueue.smartqueue.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ServicePointService {

    public ServicePointRepository servicePointRepository;
    public OrganizationService organizationService;

    public ServicePointService(ServicePointRepository servicePointRepository, OrganizationService organizationService) {
        this.servicePointRepository = servicePointRepository;
        this.organizationService = organizationService;
    }

    public ServicePoint create (UUID organizationID, String name){
        Organization organization = organizationService.findById(organizationID);


        if(!organization.isActive()){
            throw new BusinessException(
                    "Não é possível criar um guichê em uma organização inativa"
            );
        }

        ServicePoint servicePoint = new ServicePoint(organization, name);
        return servicePointRepository.save(servicePoint);
    }

    @Transactional(readOnly = true)
    public ServicePoint findById(UUID id){
        return servicePointRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ServicePoint" + id));
    }
    @Transactional(readOnly = true)
    public List<ServicePoint> findByOrganizationId (UUID organizationId){
        organizationService.findById(organizationId);
        return servicePointRepository.findByOrganizationIdAndActive(organizationId, true);
    }

    public void deactivate(UUID id){
        ServicePoint servicePoint = findById(id);
        servicePoint.setActive(false);
        servicePointRepository.save(servicePoint);
    }

}
