package com.example.balance.job.config;

import com.example.balance.dao.InputDetailRecord;
import com.example.balance.dao.OutputDetailRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.infrastructure.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.infrastructure.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.infrastructure.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);

    @Autowired
    private BalanceItemProcessor balanceItemProcessor;
    
    @Autowired
    private BalanceItemWriter balanceItemWriter;
    
    
    @Bean
	public FixedLengthTokenizer fixedLengthTokenizer() {
		FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();

		tokenizer.setNames("DESCRIPTION", "ACQUIRER", "CLIENT", "TRANSACTION_AMOUNT", "TYPE", "BALANCE", "FICTIOUS_BALANCE", "RECEIVER");
		tokenizer.setColumns(
			new Range(1, 6),      // DESCRIPTION
			new Range(7, 18),     // first_client
			new Range(19, 25),    // clientA
			new Range(26, 29),    // 2000
			new Range(30, 33),    // visa
			new Range(34, 38),    // 50000
			new Range(39, 43),    // 48000
			new Range(44, 50)     // ID1021
		);

		return tokenizer;
	}

    @Bean
    public FlatFileItemReader<InputDetailRecord> reader() {
    	
    	FlatFileItemReader<InputDetailRecord> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("input.csv")); // File in resources directory
        // Configure line mapper
        DefaultLineMapper<InputDetailRecord> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(fixedLengthTokenizer());
        BeanWrapperFieldSetMapper<InputDetailRecord> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(InputDetailRecord.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public ItemProcessor<InputDetailRecord, OutputDetailRecord> processor() {
        return balanceItemProcessor;
    }

    @Bean
    public ItemWriter<OutputDetailRecord> writer() {
        return balanceItemWriter;
    }

    @Bean
	Job balanceBatchJob(JobRepository repository, Step step1) {
		return new JobBuilder("balanceJob", repository)
			.start(step1)
			.build();
	}

    
    @Bean
    public Step step1(JobRepository repository,
                      PlatformTransactionManager txm,
                      FlatFileItemReader<InputDetailRecord> reader,
                      ItemProcessor<InputDetailRecord, OutputDetailRecord> processor,
                      ItemWriter<OutputDetailRecord> writer) {
        return new StepBuilder("one", repository)
                .<InputDetailRecord, OutputDetailRecord>chunk(10, txm)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    
}