/**
 *
 */
package com.manning.sbia.ch09.jta;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.Optional;

/**
 * @author acogoluegnes
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JtaTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void setUp() throws Exception {
        jdbcTemplate.update("delete from product");
        jdbcTemplate.update("insert into product (id,name,description,price) values(?,?,?,?)", "PR....214", "Nokia 2610 Phone", "", 102.23);
    }


    @Test
    public void batchTablesAndApplicationTablesOnDifferentDb() throws Exception {
        int initial = jdbcTemplate.queryForObject("select count(1) from product", Integer.class);

        jobLauncher.run(
                job,
                new JobParametersBuilder()
                        .addString("inputResource",
                                "classpath:/com/manning/sbia/ch09/jta/products.zip")
                        .addString("targetDirectory",
                                "./target/importproductsbatch/")
                        .addString("targetFile", "products.txt")
                        .addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters());

        Assert.assertEquals(Optional.of(initial + 7), jdbcTemplate.queryForObject("select count(1) from product", Integer.class));
    }

}
