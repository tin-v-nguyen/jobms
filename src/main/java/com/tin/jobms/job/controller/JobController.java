package com.tin.jobms.job.controller;

import com.tin.jobms.job.dto.JobWithCompanyDTO;
import com.tin.jobms.job.model.Job;
import com.tin.jobms.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    JobService jobService;


    @GetMapping
    public ResponseEntity<List<JobWithCompanyDTO>> findAll() {
        return new ResponseEntity<>(jobService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findJobById(@PathVariable Long id) {
        Job job = jobService.findJobById(id);
        ResponseEntity<Job> response;
        response = job == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(job, HttpStatus.OK);
        return response;
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job newJob = jobService.createJob(job);
        return new ResponseEntity<>(newJob, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        boolean deleted = jobService.deleteJobById(id);
        ResponseEntity<String> response;
        response = deleted ? new ResponseEntity<>("Job deleted successfully", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job) {
        Job updatedJob = jobService.updateJob(id, job);
        ResponseEntity<Job> response;
        response = updatedJob == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(updatedJob, HttpStatus.OK);
        return response;
    }
}
