package com.nexops.dto;

import jakarta.validation.constraints.NotBlank;
import com.nexops.entity.ApplicationStatus;

public class ApplicationRequest {

    private ApplicationStatus status;

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    @NotBlank(message = "Application name is required")
    private String name;

    private String description;

    @NotBlank(message = "Team is required")
    private String team;

    public ApplicationRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}