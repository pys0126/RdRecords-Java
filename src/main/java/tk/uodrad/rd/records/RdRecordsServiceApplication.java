package tk.uodrad.rd.records;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RdRecordsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RdRecordsServiceApplication.class, args);
    }

}
