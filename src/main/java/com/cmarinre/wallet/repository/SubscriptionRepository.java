package com.cmarinre.wallet.repository;

import com.cmarinre.wallet.model.SubscriptionWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<SubscriptionWallet, String> {

}
