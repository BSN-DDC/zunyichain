package com.slabs.ddc.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author joey
 */
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration.class})
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }

}
