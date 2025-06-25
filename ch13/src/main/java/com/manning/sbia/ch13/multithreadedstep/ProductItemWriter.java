package com.manning.sbia.ch13.multithreadedstep;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.manning.sbia.ch13.ThreadUtils;
import com.manning.sbia.ch13.domain.Product;

public class ProductItemWriter extends JdbcDaoSupport implements ItemWriter<Product> {

    @Override
    public void write(Chunk<? extends Product> chunk) throws Exception {
        ThreadUtils.writeThreadExecutionMessage("write", chunk.getItems());
        for (Product product : chunk.getItems()) {
            getJdbcTemplate().update("update product set processed=? where id=?",
                    true, product.getId());
            // Writing then the product content
        }
    }
}