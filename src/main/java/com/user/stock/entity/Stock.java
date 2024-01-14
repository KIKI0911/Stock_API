package com.user.stock.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class Stock {

    private Integer id;
    private Integer symbol;
    private String companyName;
    private Integer quantity;
    private Integer price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(id, stock.id) && Objects.equals(symbol, stock.symbol) && Objects.equals(companyName, stock.companyName) && Objects.equals(quantity, stock.quantity) && Objects.equals(price, stock.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, symbol, companyName, quantity, price);
    }
}
