package com.example.redmindatmassignment.services;

import com.example.redmindatmassignment.domain.ATM;
import com.example.redmindatmassignment.domain.Bill;
import com.example.redmindatmassignment.repository.ATMRepository;
import com.example.redmindatmassignment.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class ATMService {

    /* in my service class I want to be able to reach both the atm and the bill repositories to find
      the correct bills and atms in my database */
    private ATMRepository atmRepository;
    private BillRepository billRepository;


    @Autowired
    public ATMService(ATMRepository atmRepository, BillRepository billRepository) {
        this.atmRepository = atmRepository;
        this.billRepository = billRepository;
    }

    public void save(ATM atm) {
        atmRepository.save(atm);
    }

    public ATM getByID(Long id) {
        return atmRepository.findById(id).get();
    }

    public List<Bill> getBillList(Long id) {
        return atmRepository.findById(id).get().getBillList();
    }

    /* This is my actual transaction for when a user is withdrawing money from the ATM.
       you specify the ATM you want to withdraw from and the amount of money to be withdrawn. */
    @Transactional
    public List<Bill> withdrawMoney(Long id, float amount) throws Exception {
        Bill tempBill;
        List<Bill> allBills = getBillList(id);
        List<Bill> billsToWithdraw = new ArrayList<>();


        /* I solved the "ATM should prioritize the biggest bill" issue with while loops and
        comparing the requested amount to all bill values. After a bill has been placed in my billsToWithdraw list i
        subtract that amount from the requested amount and also remove it from the database.

        I also check at the end of the transaction if the ATM has sufficient amount of money and
        bills to fulfill the request, lastly i return the list with all requested bills if no errors occurred. */

        while (amount >= 1000 && containsBill(allBills, 1000)) {
            tempBill = getBillFromList(allBills, 1000);
            billsToWithdraw.add(tempBill);
            allBills.remove(tempBill);
            amount -= 1000;
        }
        while (amount >= 500 && containsBill(allBills, 500)) {
            tempBill = getBillFromList(allBills, 500);
            billsToWithdraw.add(tempBill);
            allBills.remove(tempBill);
            amount -= 500;
        }
        while (amount >= 100 && containsBill(allBills, 100)) {
            tempBill = getBillFromList(allBills, 100);
            billsToWithdraw.add(tempBill);
            allBills.remove(tempBill);
            amount -= 100;
        }
        if (amount > 0 && allBills.isEmpty()) {
            throw new Exception("Not enough funds error");
        }
        if (amount > 0 && !containsBill(allBills, 100)) {
            throw new Exception("Out of hundred bills");
        }
        if (amount > 0 && !containsBill(allBills, 500)) {
            throw new Exception("Out of five hundred bills");
        }
        if (amount > 0 && !containsBill(allBills, 1000)) {
            throw new Exception("Out of thousand bills");
        }
        if (amount == 0) {
            for (Bill billToWithdraw : billsToWithdraw) {
                billRepository.delete(billToWithdraw);
            }
        }
        return billsToWithdraw;
    }

    // With this method I check if the requested bill exists in the ATM.
    public boolean containsBill(List<Bill> billList, int amount) {
        return billList.stream().anyMatch(o -> o.getValue() == amount);
    }

    // With this method i get a requested bill from the list.
    public Bill getBillFromList(List<Bill> billList, int billValue) {
        for (Bill bill : billList) {
            if (bill.getValue() == billValue)
                return bill;
        }
        return null;
    }
}
