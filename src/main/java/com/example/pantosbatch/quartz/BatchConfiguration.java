package com.example.pantosbatch.quartz;

import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

//Quartz Job Class 의 auto-wiring 을 지원하는 JobFactory
//
//출처: https://blog.kingbbode.com/38 [kingbbode]
@Configuration
public class BatchConfiguration {

    @Bean
    public JobFactory jobFactory(AutowireCapableBeanFactory beanFactory
) {
        return new SpringBeanJobFactory(){
            @Override
            protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
                Object job = super.createJobInstance(bundle);
                beanFactory.autowireBean(job);
                return job;
            }
        };

    }


}
