package com.github.sandokandias.springbootjob;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
public class JobScheduler {

    private final ApplicationContext appContext;
    private final TaskScheduler taskScheduler;
    private final TenantRepository tenantRepository;
    private final ConfigRepository configRepository;
    private final Map<String, ScheduledFuture> schedules = new HashMap<>();


    public JobScheduler(ApplicationContext appContext,
                        TaskScheduler taskScheduler,
                        TenantRepository tenantRepository, ConfigRepository configRepository) {
        this.appContext = appContext;
        this.taskScheduler = taskScheduler;
        this.tenantRepository = tenantRepository;
        this.configRepository = configRepository;
    }

    @PostConstruct
    public void schedule() {

        String jobCron = configRepository.getJobCron();

        tenantRepository.findTenants()
                .forEach(tenant -> schedule(jobCron, tenant));
    }

    private void schedule(String jobCron, String tenant) {

        JobTask task = appContext.getBean(JobTask.class);
        task.setTenant(tenant);
        ScheduledFuture<?> schedule = taskScheduler.schedule(task, new CronTrigger(jobCron));
        schedules.put(tenant, schedule);
        System.out.println(String.format("Task scheduled for tenant [%s] with cron [%s].", tenant, jobCron));
    }


    public void turnOn(String tenant) {

        System.out.println("Rescheduling job for tenant " + tenant + "...");

        ScheduledFuture scheduledFuture = schedules.get(tenant);
        if (scheduledFuture.isCancelled()) {
            String jobCron = configRepository.getJobCron();
            schedule(jobCron, tenant);
        } else {
            System.out.println("Job for tenant " + tenant + " is already running.");

        }
    }

    public void turnOff(String tenant) {
        System.out.println("Stopping job for tenant " + tenant + "...");

        ScheduledFuture scheduledFuture = schedules.get(tenant);
        scheduledFuture.cancel(false);
    }

}
