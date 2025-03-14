package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

public class SaleReportDto {

    private Long id;
    private Double amount;
    private LocalDate date;
    private String name;

    public SaleReportDto(Long id, String name, LocalDate date, Double amount) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.amount = amount;
    }

    public SaleReportDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
