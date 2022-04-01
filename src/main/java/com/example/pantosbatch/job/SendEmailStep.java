package com.example.pantosbatch.job;

import com.example.pantosbatch.entity.CcSet;
import com.example.pantosbatch.entity.CcUser;
import com.example.pantosbatch.repository.CcSetJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SendEmailStep {

    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private static final int chunkSize = 10;
    private final CcSetJpaRepository ccSetJpaRepository;
    final int ccSetSeq = 1;

    LocalDate now = LocalDate.now();

    static int frsNtfMl;
    static int scnNtfMl;
    static LocalDate firstAlarmDate; // 181
    static LocalDate secondAlarmDate; // 211

    public void setAlarmDate(){
        CcSet entity = ccSetJpaRepository.findByCcSetSeq(ccSetSeq);
        frsNtfMl =  entity.getFrsNtfMl();
        scnNtfMl =  entity.getScnNtfMl();
        firstAlarmDate = now.minusDays(frsNtfMl); // 181
        secondAlarmDate = now.minusDays(scnNtfMl); // 211
    }

    //계정폐기 테이블의 1차 메일 알림일(181) = 오늘 날짜 - 사용자 접속일시 이면 메일전송
    @Bean
    public Step sendEmailStep1(){
        setAlarmDate();
        return stepBuilderFactory.get("sendEmailStep1")
                .<CcUser, CcUser>chunk(chunkSize)
                .reader(jpaCursorItemReader1())
                .writer(itemWriter1())
                .build();
    }

    //계정폐기 테이블의 2차 메일 알림일(211) = 오늘 날짜 - 사용자 접속일시 이면 메일전송
    @Bean
    public Step sendEmailStep2(){
        return stepBuilderFactory.get("sendEmailStep2")
                .<CcUser, CcUser>chunk(chunkSize)
                .reader(jpaCursorItemReader2())
                .writer(itemWriter2())
                .build();
    }

    @Bean
    public JpaCursorItemReader<CcUser> jpaCursorItemReader1(){
        log.info("===이메일 1차 발송==");
        log.info(String.valueOf(firstAlarmDate));
        return new JpaCursorItemReaderBuilder<CcUser>()
                .name("jpaCursorItemReader1")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT c FROM CcUser c where loginDt='"+firstAlarmDate+"'")
                .build();
    }

    @Bean
    public JpaCursorItemReader<CcUser> jpaCursorItemReader2(){
        log.info("===이메일 2차 발송==");
        log.info(String.valueOf(secondAlarmDate));
        return new JpaCursorItemReaderBuilder<CcUser>()
                .name("jpaCursorItemReader2")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT c FROM CcUser c where loginDt='"+secondAlarmDate+"'")
                .build();
    }
    @Bean
    public ItemWriter<CcUser> itemWriter1(){
        log.info("===1차 이메일 발송 시작==");
        return list -> {
            for (CcUser user: list) {
                log.info("1 : email send user ={}", user);
            }
        };
    }
    @Bean
    public ItemWriter<CcUser> itemWriter2(){
        log.info("===2차 이메일 발송 시작==");
        return list -> {
            for (CcUser user: list) {
                log.info("2 : email send user ={}", user);
            }
        };
    }



}
