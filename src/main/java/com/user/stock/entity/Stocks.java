package com.user.stock.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Stocks {

    private int id;
    private Integer symbol;
    private String companyName;
    private int quantity;
    private int price;
}
