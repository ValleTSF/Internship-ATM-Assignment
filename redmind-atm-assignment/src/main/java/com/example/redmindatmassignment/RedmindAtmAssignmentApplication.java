package com.example.redmindatmassignment;

import com.example.redmindatmassignment.domain.ATM;
import com.example.redmindatmassignment.domain.Bill;
import com.example.redmindatmassignment.repository.ATMRepository;
import com.example.redmindatmassignment.services.ATMService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedmindAtmAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedmindAtmAssignmentApplication.class, args);
    }
}
