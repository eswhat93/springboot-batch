package com.example.pantosbatch.quartz;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@Getter
@Setter
public class QuartzJobLauncher extends QuartzJobBean {
    private String jobName;
    private JobLauncher jobLauncher;
    private JobLocator jobLocator;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        try{
            Job job = jobLocator.getJob(jobName);
            jobLauncher.run(job, new JobParameters());
        }catch (Exception e){
            log.info("error : {}",e);
        }
    }
}
