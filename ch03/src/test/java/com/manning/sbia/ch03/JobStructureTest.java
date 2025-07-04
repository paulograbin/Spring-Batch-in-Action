package com.manning.sbia.ch03;

import org.junit.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration()
public class JobStructureTest extends AbstractJobStructureTest {

    @Test
    public void delimitedJob() throws Exception {
        jobLauncher.run(importProductsJob, new JobParameters());
    }

}
