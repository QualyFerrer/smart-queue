package com.smartqueue.smartqueue.domain.organization;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService service;


    public OrganizationController(OrganizationService service) {
        this.service = service;
    }
}
