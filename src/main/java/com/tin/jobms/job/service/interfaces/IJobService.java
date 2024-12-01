package com.tin.jobms.job.service.interfaces;

import com.tin.jobms.job.dto.JobWithCompanyDTO;
import com.tin.jobms.job.model.Job;

import java.util.List;

public interface IJobService {
    List<JobWithCompanyDTO> findAll();
    Job findJobById(Long id);
    Job createJob(Job job);
    Boolean deleteJobById(Long id);

    Job updateJob(Long id, Job job);


}
