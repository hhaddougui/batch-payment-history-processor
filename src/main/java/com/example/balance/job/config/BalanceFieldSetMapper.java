/**
 * 
 */
package com.example.balance.job.config;

import org.springframework.batch.infrastructure.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.infrastructure.item.file.mapping.FieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import com.example.balance.dao.InputDetailRecord;

/**
 * 
 */
@Configuration
public class BalanceFieldSetMapper {

	@Bean
	public FieldSetMapper<InputDetailRecord> fieldSetMapper() {
		BeanWrapperFieldSetMapper<InputDetailRecord> fieldSetMapper = new BeanWrapperFieldSetMapper<InputDetailRecord>();

		fieldSetMapper.setPrototypeBeanName("input");

		return fieldSetMapper;
	}

	
	@Bean
	@Scope("prototype")
	public InputDetailRecord input() {
		return new InputDetailRecord();
	}

}