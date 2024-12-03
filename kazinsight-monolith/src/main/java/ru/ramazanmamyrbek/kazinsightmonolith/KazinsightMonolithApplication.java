package ru.ramazanmamyrbek.kazinsightmonolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class KazinsightMonolithApplication {

    public static void main(String[] args) {
        SpringApplication.run(KazinsightMonolithApplication.class, args);
    }

}
