package com.example.pantosbatch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.Period;
@SpringBootTest
class PantosBatchApplicationTests {

    @Test
    void contextLoads() {
        LocalDate now = LocalDate.now();
        LocalDate daysAgo = now.minusDays(180);
        System.out.println("date1>>>>>>>>"+daysAgo);

        Period period = Period.between(now,daysAgo);
        System.out.println(">>>>>>>>>"+period.getDays());
    }



}
