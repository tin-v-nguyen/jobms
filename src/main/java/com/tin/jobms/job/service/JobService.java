package com.tin.jobms.job.service;

import com.tin.jobms.job.dto.JobWithCompanyDTO;
import com.tin.jobms.job.external.Company;
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

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        // TODO: might be more efficient to handle responding with a list of companies in company microservice
        return jobs.stream().map(this::convertToJobWithCompanyDTO).collect(Collectors.toList());
    }

    private JobWithCompanyDTO convertToJobWithCompanyDTO(Job job) {
            JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
            jobWithCompanyDTO.setJob(job);
            RestTemplate restTemplate = new RestTemplate();
            Company company = restTemplate.getForObject("http://localhost:8082/companies/" + job.getCompanyId(), Company.class);
            jobWithCompanyDTO.setCompany(company);
        return jobWithCompanyDTO;
    }

    @Override
    public Job findJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
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
