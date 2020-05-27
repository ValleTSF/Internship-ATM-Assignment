package com.example.redmindatmassignment.controllers;
import com.example.redmindatmassignment.domain.ATM;
import com.example.redmindatmassignment.domain.Bill;
import com.example.redmindatmassignment.services.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ATM")
public class ATMController {

    private ATMService atmService;

    @Autowired
    public ATMController(ATMService atmService) {
        this.atmService = atmService;
    }

    //Get method to get an ATM using id number.
    @GetMapping(path = "/{id}")
    public ATM getATMById(@PathVariable long id){
        return atmService.getByID(id);
    }

    // Post method that will withdraw the amount from the ATM with corresponding id number.
    @PostMapping(path = "/{id}/withdraw/{amount}")
    public List<Bill> withdrawMoney(@PathVariable long id,@PathVariable float amount) throws Exception {
       return atmService.withdrawMoney(id,amount);
    }
}
