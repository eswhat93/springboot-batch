package com.example.pantosbatch.quartz;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Slf4j
@Getter
@Setter
public class QuartzJobLauncher extends QuartzJobBean {
    private String jobName;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobLocator jobLocator;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{
            Job job = jobLocator.getJob(jobName);
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("requestDate", today)
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
        }catch (Exception e){
            log.info("error : {}",e);
        }
    }
}
