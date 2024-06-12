package com.matheus.fooddeliveryapi;

import com.matheus.fooddeliveryapi.infraestructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class FoodDeliveryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodDeliveryApiApplication.class, args);
    }

}
