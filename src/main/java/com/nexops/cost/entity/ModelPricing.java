package com.nexops.cost.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "model_pricing")
public class ModelPricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, precision = 12, scale = 8)
    private BigDecimal inputPricePerMillion;

    @Column(nullable = false, precision = 12, scale = 8)
    private BigDecimal outputPricePerMillion;

    public ModelPricing() {
    }

    public ModelPricing(
            String provider,
            String model,
            BigDecimal inputPricePerMillion,
            BigDecimal outputPricePerMillion
    ) {
        this.provider = provider;
        this.model = model;
        this.inputPricePerMillion = inputPricePerMillion;
        this.outputPricePerMillion = outputPricePerMillion;
    }

    public Long getId() {
        return id;
    }

    public String getProvider() {
        return provider;
    }

    public String getModel() {
        return model;
    }

    public BigDecimal getInputPricePerMillion() {
        return inputPricePerMillion;
    }

    public BigDecimal getOutputPricePerMillion() {
        return outputPricePerMillion;
    }
}