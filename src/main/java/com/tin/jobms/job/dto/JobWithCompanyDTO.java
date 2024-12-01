package com.tin.jobms.job.dto;

import com.tin.jobms.job.external.Company;
import com.tin.jobms.job.model.Job;

public class JobWithCompanyDTO {
   private Job job;
   private Company company;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
