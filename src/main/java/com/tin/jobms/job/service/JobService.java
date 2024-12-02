package com.tin.jobms.job.service;

import com.tin.jobms.job.clients.CompanyClient;
import com.tin.jobms.job.clients.ReviewClient;
import com.tin.jobms.job.dto.JobDTO;
import com.tin.jobms.job.external.Company;
import com.tin.jobms.job.external.Review;
import com.tin.jobms.job.mapper.JobMapper;
import com.tin.jobms.job.model.Job;
import com.tin.jobms.job.repository.JobRepository;
import com.tin.jobms.job.service.interfaces.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService implements IJobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private ReviewClient reviewClient;

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        // TODO: might be more efficient to handle responding with a list of companies in company microservice
        return jobs.stream().map(this::convertToJobDTO).collect(Collectors.toList());
    }

    private JobDTO convertToJobDTO(Job job) {
        if (job == null) return null;
        // RestTemplate restTemplate = new RestTemplate();
        // Below is an alternative way from OpenFeign for interservice requests
        // restTemplate.getForObject("http://COMPANYMS:8082/companies/" + job.getCompanyId(), Company.class);
        Company company = companyClient.getCompany(job.getCompanyId());

        // Use exchange when response type is a Generic collection
        /*
        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://REVIEWMS:8083/review?companyId=" + job.getCompanyId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {
                });

        List<Review> reviews = reviewResponse.getBody();

         */
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        JobDTO jobDTO = JobMapper.mapToJobDTO(job, company, reviews);
        return jobDTO;
    }

    @Override
    public JobDTO findJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        JobDTO jobDTO = convertToJobDTO(job);
        return jobDTO;
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
