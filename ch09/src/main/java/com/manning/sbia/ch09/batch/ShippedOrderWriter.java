package com.manning.sbia.ch09.batch;

import com.manning.sbia.ch09.domain.Order;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class ShippedOrderWriter implements ItemWriter<Order> {
	
	private JdbcTemplate jdbcTemplate;
	
	public ShippedOrderWriter(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void write(final List<? extends Order> items) throws Exception {
		jdbcTemplate.batchUpdate("update orders set shipped = ?",
			new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setBoolean(1, true);
				}
				
				@Override
				public int getBatchSize() {
					return items.size();
				}
			}	
		);
	}

}
