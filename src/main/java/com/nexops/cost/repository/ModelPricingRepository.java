package com.nexops.cost.repository;

import com.nexops.cost.entity.ModelPricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelPricingRepository
        extends JpaRepository<ModelPricing, Long> {

    Optional<ModelPricing> findByProviderAndModel(
            String provider,
            String model
    );
}