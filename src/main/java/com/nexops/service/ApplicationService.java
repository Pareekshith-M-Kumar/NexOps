package com.nexops.service;

import com.nexops.dto.ApplicationRequest;
import com.nexops.dto.ApplicationResponse;
import com.nexops.entity.Application;
import com.nexops.entity.ApplicationStatus;
import com.nexops.exception.ResourceNotFoundException;
import com.nexops.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    // CREATE
    public ApplicationResponse createApplication(
            ApplicationRequest request) {

        ApplicationStatus status = request.getStatus();

        if (status == null) {
            status = ApplicationStatus.ACTIVE;
        }

        Application application = new Application(
                request.getName(),
                request.getDescription(),
                request.getTeam(),
                status
        );

        Application savedApplication =
                applicationRepository.save(application);

        return toResponse(savedApplication);
    }

    // READ ALL
    public List<ApplicationResponse> getAllApplications() {

        return applicationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // READ BY ID
    public ApplicationResponse getApplicationById(Long id) {

        Application application =
                applicationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Application with id "
                                                + id
                                                + " not found"
                                ));

        return toResponse(application);
    }

    // UPDATE
    public ApplicationResponse updateApplication(
            Long id,
            ApplicationRequest request) {

        Application application =
                applicationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Application with id "
                                                + id
                                                + " not found"
                                ));

        application.setName(request.getName());
        application.setDescription(request.getDescription());
        application.setTeam(request.getTeam());

        // Update status only if a status was provided
        if (request.getStatus() != null) {
            application.setStatus(request.getStatus());
        }

        Application updatedApplication =
                applicationRepository.save(application);

        return toResponse(updatedApplication);
    }

    // DELETE
    public void deleteApplication(Long id) {

        Application application =
                applicationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Application with id "
                                                + id
                                                + " not found"
                                ));

        applicationRepository.delete(application);
    }

    // ENTITY → RESPONSE DTO
    private ApplicationResponse toResponse(
            Application application) {

        return new ApplicationResponse(
                application.getId(),
                application.getName(),
                application.getDescription(),
                application.getTeam(),
                application.getStatus(),
                application.getCreatedAt(),
                application.getUpdatedAt()
        );
    }
}