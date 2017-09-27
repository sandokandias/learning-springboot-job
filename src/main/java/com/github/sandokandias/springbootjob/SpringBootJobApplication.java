package com.github.sandokandias.springbootjob;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
public class SpringBootJobApplication {

    @Bean
    public TaskScheduler taskScheduler(@Value("${scheduler.pool.size}") Integer schedulerPoolSize) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(schedulerPoolSize);
        return taskScheduler;
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringBootJobApplication.class, args);
    }
}
