package br.com.futurodev.primeiraapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * Spring Boot application starter class
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        //Esta é a linha principal que roda o projeto Java Spring Boot
        SpringApplication.run(Application.class, args);
        System.out.println(new BCryptPasswordEncoder().encode("203040"));
    }
}
