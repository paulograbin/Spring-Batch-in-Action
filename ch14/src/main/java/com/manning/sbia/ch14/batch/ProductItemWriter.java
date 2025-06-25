package com.manning.sbia.ch14.batch;

import com.manning.sbia.ch14.domain.Product;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


public class ProductItemWriter implements ItemWriter<Product> {

    public static final String INSERT_SQL = "INSERT INTO PRODUCT (ID, NAME, DESCRIPTION, PRICE) VALUES (:id, :name, :description, :price)";
    public static final String UPDATE_SQL = "UPDATE PRODUCT SET NAME=:name, DESCRIPTION=:description, PRICE=:price WHERE ID=:id";
    private ItemSqlParameterSourceProvider<Product> itemSqlParameterSourceProvider;
    private JdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setSimpleJdbcTemplate(JdbcTemplate simpleJdbcTemplate) {
        this.simpleJdbcTemplate = simpleJdbcTemplate;
    }

    @Autowired
    public void setItemSqlParameterSourceProvider(
            ItemSqlParameterSourceProvider<Product> itemSqlParameterSourceProvider) {
        this.itemSqlParameterSourceProvider = itemSqlParameterSourceProvider;
    }


    @Override
    public void write(Chunk<? extends Product> chunk) {
        for (Product item : chunk.getItems()) {
            SqlParameterSource args = itemSqlParameterSourceProvider.createSqlParameterSource(item);
            int updated = simpleJdbcTemplate.update(UPDATE_SQL, args);
            if (updated == 0) {
                simpleJdbcTemplate.update(INSERT_SQL, args);
            }
        }
    }
}
