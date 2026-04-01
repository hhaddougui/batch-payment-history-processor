package com.example.balance.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchJobRestController {

    private static final Logger logger = LoggerFactory.getLogger(BatchJobRestController.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @PostMapping("/run")
    public ResponseEntity<String> runBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
            return ResponseEntity.ok("Batch job started successfully");
        } catch (Exception e) {
            logger.error("Failed to start batch job", e);
            return ResponseEntity.internalServerError().body("Batch job failed. Please contact support.");
        }
    }

    @GetMapping("/run")
    public ResponseEntity<String> runBatchJobGet() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
            return ResponseEntity.ok("Batch job started successfully (GET)");
        } catch (Exception e) {
            logger.error("Failed to start batch job", e);
            return ResponseEntity.internalServerError().body("Batch job failed. Please contact support.");
        }
    }
}
