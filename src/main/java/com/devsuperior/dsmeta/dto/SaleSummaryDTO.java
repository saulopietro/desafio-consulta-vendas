package com.devsuperior.dsmeta.dto;

public class SaleSummaryDTO {

    private String name;
    private Double amount;

    public SaleSummaryDTO(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public SaleSummaryDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
