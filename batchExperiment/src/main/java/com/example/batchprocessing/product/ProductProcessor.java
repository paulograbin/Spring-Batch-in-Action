package com.example.batchprocessing.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ProductProcessor implements ItemProcessor<Product, Product> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductProcessor.class);


    @Override
    public Product process(Product item) throws Exception {
        LOG.info("process");

        String name = item.getName();

        Product product = new Product(item.getId());
        product.setName(name.toUpperCase());

        return product;
    }
}
