package com.user.stock.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Stocks {

    private Integer id;
    private Integer symbol;
    private String companyName;
    private Integer quantity;
    private Integer price;
}
