package domain.servicepoint;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServicePointRepository extends JpaRepository<ServicePoint, UUID> {

    List<ServicePoint> findByOrganizationId(UUID OrganizationId);

    List<ServicePoint> findByOrganizationIdAndActive(UUID organizationId, Boolean active);

}
