package com.tin.jobms.job.clients;

import com.tin.jobms.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//TODO: Move review handling to the companyms, it makes more sense for reviews to be nested within companies
@FeignClient(name = "REVIEWMS")
public interface ReviewClient {

    @GetMapping("/review")
    List<Review> getReviews(@RequestParam("companyId") Long companyId);
}
