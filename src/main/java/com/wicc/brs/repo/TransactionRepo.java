package com.wicc.brs.repo;

import com.wicc.brs.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepo  extends JpaRepository<Transaction, Integer> {
}
