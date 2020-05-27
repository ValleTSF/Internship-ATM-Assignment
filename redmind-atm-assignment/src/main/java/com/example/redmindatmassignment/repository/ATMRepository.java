package com.example.redmindatmassignment.repository;

import com.example.redmindatmassignment.domain.ATM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ATMRepository extends JpaRepository<ATM, Long> {
    Optional<ATM> findById(Long id);
}
