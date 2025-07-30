package com.manning.sbia.ch07;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.manning.sbia.ch01.domain.Product;


public class JdbcProductDao implements ProductDao {
	
	private static final String SQL_SELECT_PRODUCT = "select id,name,description,price from product where id = ?";

	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Product> rowMapper = new ProductRowMapper();

	@Override
	public Product load(String productId) {	
		return jdbcTemplate.queryForObject(SQL_SELECT_PRODUCT, rowMapper, productId);
	}
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
