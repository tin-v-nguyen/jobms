package com.tin.jobms.job.service;

import com.tin.jobms.job.dto.JobWithCompanyDTO;
import com.tin.jobms.job.external.Company;
import com.tin.jobms.job.mapper.JobMapper;
import com.tin.jobms.job.model.Job;
import com.tin.jobms.job.repository.JobRepository;
import com.tin.jobms.job.service.interfaces.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService implements IJobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs = new ArrayList<>();
        // TODO: might be more efficient to handle responding with a list of companies in company microservice
        return jobs.stream().map(this::convertToJobWithCompanyDTO).collect(Collectors.toList());
    }

    private JobWithCompanyDTO convertToJobWithCompanyDTO(Job job) {
        if (job == null) return null;
        // RestTemplate restTemplate = new RestTemplate();
        Company company = restTemplate.getForObject("http://COMPANYMS:8082/companies/" + job.getCompanyId(), Company.class);
        JobWithCompanyDTO jobWithCompanyDTO = JobMapper.mapToJobWithCompanyDTO(job, company);
        return jobWithCompanyDTO;
    }

    @Override
    public JobWithCompanyDTO findJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        JobWithCompanyDTO jobWithCompanyDTO = convertToJobWithCompanyDTO(job);
        return jobWithCompanyDTO;
    }

    @Override
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public Boolean deleteJobById(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Job updateJob(Long id, Job job) {
        Optional<Job> jobO = jobRepository.findById(id);
        if (jobO.isPresent()) {
            Job newJob = jobO.get();
            newJob.setDescription(job.getDescription());
            newJob.setLocation(job.getLocation());
            newJob.setTitle(job.getTitle());
            newJob.setMaxSalary(job.getMaxSalary());
            newJob.setMinSalary(job.getMinSalary());
            return jobRepository.save(newJob);
        }
        return null;
    }
}
