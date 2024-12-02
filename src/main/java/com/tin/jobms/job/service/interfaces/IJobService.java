package com.tin.jobms.job.service.interfaces;

import com.tin.jobms.job.dto.JobDTO;
import com.tin.jobms.job.model.Job;

import java.util.List;

public interface IJobService {
    List<JobDTO> findAll();
    JobDTO findJobById(Long id);
    Job createJob(Job job);
    Boolean deleteJobById(Long id);

    Job updateJob(Long id, Job job);


}
