package com.manning.sbia.ch05.database;

import org.junit.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.test.context.ContextConfiguration;

import com.manning.sbia.ch05.AbstractJobStructureTest;

import java.util.List;

@ContextConfiguration
public class JobStructureHibernateCursorTest extends AbstractJobStructureTest {

    @Test
    public void hibernateCursorJob() throws Exception {
        jobLauncher.run(job, new JobParameters());

        List<String> products = List.of("PR....210", "PR....211", "PR....212", "PR....213", "PR....214", "PR....215", "PR....216", "PR....217");

        checkProducts(writer.getProducts(), products.toArray(String[]::new));
    }

}
