package com.tin.jobms.job.external;

import java.util.List;

public class Company {

    private Long id;
    private String name;
    private String description;
    private List<Long> jobIds;
    private List<Long> reviewIds;

    public Company() {

    }

    public Company(Long id, String name, String description, List<Long> jobIds, List<Long> reviewIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.jobIds = jobIds;
        this.reviewIds = reviewIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Long> getJobIds() {
        return jobIds;
    }

    public void setJobIds(List<Long> jobIds) {
        this.jobIds = jobIds;
    }

    public List<Long> getReviewIds() {
        return reviewIds;
    }

    public void setReviewIds(List<Long> reviewIds) {
        this.reviewIds = reviewIds;
    }
}
