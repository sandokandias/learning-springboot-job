package com.github.sandokandias.springbootjob;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class JobTask implements Runnable {

    private String tenant;

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + " - Running task for " + tenant);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " - Task for " + tenant + " finished.");
        }
    }


    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
