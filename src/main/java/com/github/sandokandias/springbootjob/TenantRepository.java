package com.github.sandokandias.springbootjob;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TenantRepository {

    public List<String> findTenants() {

        return Arrays.asList("rw_zup_1", "rw_zup_2", "rw_zup_3");
    }
}
