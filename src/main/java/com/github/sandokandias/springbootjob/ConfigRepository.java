package com.github.sandokandias.springbootjob;

import org.springframework.stereotype.Component;

@Component
public class ConfigRepository {

    public String getJobCron() {
        return "0 * * ? * *";
    }

}
