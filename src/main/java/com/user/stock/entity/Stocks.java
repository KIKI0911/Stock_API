package com.user.stock.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Stocks {

    private int id;
    private Integer symbol;
    private String companyName;
    private int quantity;
    private int price;
}
