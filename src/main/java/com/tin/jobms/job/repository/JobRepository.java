package com.tin.jobms.job.repository;

import com.tin.jobms.job.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
