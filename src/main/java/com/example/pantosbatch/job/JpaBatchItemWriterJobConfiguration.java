package com.example.pantosbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JpaBatchItemWriterJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final UpdateUserStep updateUserStep;
    private final SendEmailStep sendEmailStep;
    @Bean
    public Job jpaBatchItemWriterJob() {
        return jobBuilderFactory.get("jpaBatchItemWriterJob")
            .start(updateUserStep.updateSleepStep())
                .next(sendEmailStep.sendEmailStep1())
                .next(sendEmailStep.sendEmailStep2())
            .build();
    }


}