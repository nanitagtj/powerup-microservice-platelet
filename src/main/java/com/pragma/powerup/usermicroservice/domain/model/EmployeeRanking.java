package com.pragma.powerup.usermicroservice.domain.model;

public class EmployeeRanking {
    private Long id;
    private Long employeeId;
    private String averageElapsedTime;

    public EmployeeRanking() {
    }

    public EmployeeRanking(Long id, Long employeeId, String averageElapsedTime) {
        this.id = id;
        this.employeeId = employeeId;
        this.averageElapsedTime = averageElapsedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getAverageElapsedTime() {
        return averageElapsedTime;
    }

    public void setAverageElapsedTime(String averageElapsedTime) {
        this.averageElapsedTime = averageElapsedTime;
    }
}
