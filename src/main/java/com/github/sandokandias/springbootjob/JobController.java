package com.github.sandokandias.springbootjob;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class JobController {

    private final JobScheduler jobScheduler;

    public JobController(JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }


    @PostMapping(value = "/jobs", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> turnOn(@RequestBody Map payload) {

        String tenant = (String) payload.get("tenant");
        jobScheduler.turnOn(tenant);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/jobs/{tenant}")
    public ResponseEntity<?> turnOff(@PathVariable("tenant") String tenant) {

        jobScheduler.turnOff(tenant);

        return ResponseEntity.ok().build();
    }
}
