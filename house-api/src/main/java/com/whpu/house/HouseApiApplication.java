package com.whpu.house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class HouseApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseApiApplication.class, args);
    }

}

