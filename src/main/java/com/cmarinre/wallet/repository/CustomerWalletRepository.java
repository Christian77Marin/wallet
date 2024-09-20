package com.cmarinre.wallet.repository;

import com.cmarinre.wallet.model.CustomerWallet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerWalletRepository extends JpaRepository<CustomerWallet, String> {

  Optional<CustomerWallet> findByClientSecret(String clientSecret);
  Optional<CustomerWallet> findByStripeCustomerId(String customerId);
}
