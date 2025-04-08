package com.pedroguerra.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.pedroguerra")
public class PedroguerraApplication {
    public static void main(String[] args) {
        SpringApplication.run(PedroguerraApplication.class, args);
    }
}
