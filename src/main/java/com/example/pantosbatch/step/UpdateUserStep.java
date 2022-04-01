package com.example.pantosbatch.step;

import com.example.pantosbatch.entity.CcSet;
import com.example.pantosbatch.entity.CcUser;
import com.example.pantosbatch.repository.CcSetJpaRepository;
import com.example.pantosbatch.repository.CcUserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UpdateUserStep {

    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int chunkSize = 10;
    private final CcSetJpaRepository ccSetJpaRepository;
    final int ccSetSeq = 1; // 실데이터값으로 수정

    @Bean
    public Step updateSleepStep(@Value("{jobParameters[requestDate]}") String requestDate){
        log.info(">>>>> requestDate = {}", requestDate);
        return stepBuilderFactory.get("updateSleepStep")
                .<CcUser, CcUser>chunk(chunkSize)
                .reader(jpaCursorUSerReader())
                .processor(compositeItemWriterProcessor())
                .writer(compositeItem())
                .build();
    }

    @Bean
    public JpaCursorItemReader<CcUser> jpaCursorUSerReader() {
        LocalDate now = LocalDate.now();
        CcSet entity = ccSetJpaRepository.findByCcSetSeq(ccSetSeq); //
        int zzzCus =  entity.getZzzCus();
        LocalDate daysAgo = now.minusDays(zzzCus); // 180;
        return new JpaCursorItemReaderBuilder<CcUser>()
                .name("jpaCursorUSerReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT c FROM CcUser c where loginDt='"+daysAgo+"'")
                .build();
    }
    @Bean
    public ItemProcessor<CcUser, CcUser> compositeItemWriterProcessor() {
        return CcUser :: changeLockYnN;
    }
    @Bean
    public CompositeItemWriter<CcUser> compositeItem() {
        CompositeItemWriter<CcUser> compositeItemWriter = new CompositeItemWriter<>();
        compositeItemWriter.setDelegates(Arrays.asList(updateProduct())); // Writer 등록, 여러개 추가 가능하도록 리스트로
        return compositeItemWriter;
    }

    @Bean
    public JpaItemWriter<CcUser> updateProduct() {
        log.info("===잠금계정 업데이트 시작==");
        JpaItemWriter<CcUser> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);    // update user
        return itemWriter;
    }
}
