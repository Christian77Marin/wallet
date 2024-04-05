package com.cmarinre.wallet.repository;

import com.cmarinre.wallet.model.CustomerWallet;
import com.cmarinre.wallet.model.Transaction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
