package com.example.redmindatmassignment.repository;

import com.example.redmindatmassignment.domain.ATM;
import com.example.redmindatmassignment.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findById(Long id);
}
