package com.nexops.cost.service;

import com.nexops.cost.entity.ModelPricing;
import com.nexops.cost.repository.ModelPricingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CostCalculationService {

    private final ModelPricingRepository pricingRepository;

    public CostCalculationService(
            ModelPricingRepository pricingRepository
    ) {
        this.pricingRepository = pricingRepository;
    }

    public BigDecimal calculateCost(
            String provider,
            String model,
            Integer inputTokens,
            Integer outputTokens
    ) {

        ModelPricing pricing =
                pricingRepository
                        .findByProviderAndModel(provider, model)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Pricing not found for "
                                                + provider
                                                + " / "
                                                + model
                                )
                        );

        BigDecimal inputCost =
                BigDecimal.valueOf(inputTokens)
                        .multiply(pricing.getInputPricePerMillion())
                        .divide(
                                BigDecimal.valueOf(1_000_000),
                                12,
                                RoundingMode.HALF_UP
                        );

        BigDecimal outputCost =
                BigDecimal.valueOf(outputTokens)
                        .multiply(pricing.getOutputPricePerMillion())
                        .divide(
                                BigDecimal.valueOf(1_000_000),
                                12,
                                RoundingMode.HALF_UP
                        );

        return inputCost
                .add(outputCost)
                .setScale(8, RoundingMode.HALF_UP);
    }
}