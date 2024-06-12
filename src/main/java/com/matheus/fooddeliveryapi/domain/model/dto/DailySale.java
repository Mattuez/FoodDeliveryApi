package com.matheus.fooddeliveryapi.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class DailySale {

    private Date date;
    private Long salesQuantity;
    private BigDecimal salesValue;
}
