package com.datagroup.ESLS.springbatch;

import com.datagroup.ESLS.entity.Good;
import com.datagroup.ESLS.springbatch.GoodsCsvMapper;
import com.datagroup.ESLS.springbatch.GoodsWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.io.File;


@Configuration
@Slf4j

public class GoodsReaderConfigruation {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("${project.profile}")
    private String FILE_PATH;
    @Autowired
    @Qualifier("GoodsWriter")
    private GoodsWriter goodsWriter;
    @Bean("goodsJob")
    public Job goodsJob(JobBuilderFactory jobBuilderFactory, Step goodsJobStep) {
        return jobBuilderFactory
                .get("flatFileJob")
                .start(goodsJobStep)
                .build();
    }

    @Bean
    public Step goodsJobStep() {
        return stepBuilderFactory.get("goodsJobStep")
                .<Good, Good>chunk(2)
                .reader(goodsReader())
                .writer(goodsWriter)
                .build();
    }
    @Bean
    @StepScope
    public FlatFileItemReader<Good> goodsReader() {
        FlatFileItemReader<Good> reader=new FlatFileItemReader<Good>();
      // reader.setResource(new ClassPathResource("/data/goods.csv"));
        reader.setResource(new FileSystemResource(new File(FILE_PATH+"goods.csv")));
        DelimitedLineTokenizer tokenizer=new DelimitedLineTokenizer();

        tokenizer.setNames(
                new String[]
                {"barCode","name","price","promotePrice","promotionReason","unit","origin","spec","category","shelfNumber","rfus01","rfus02","qrCode","provider",}
        );
        DefaultLineMapper<Good> lineMapper=new DefaultLineMapper<Good>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new GoodsCsvMapper());
        lineMapper.afterPropertiesSet();
        reader.setLineMapper(lineMapper);
        return reader;
    }
}
