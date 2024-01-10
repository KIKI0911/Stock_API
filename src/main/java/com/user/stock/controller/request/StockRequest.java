package com.user.stock.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@AllArgsConstructor
public class StockRequest {

    private Integer id;
    private Integer symbol;
    private String companyName;
    private Integer quantity;
    private Integer price;

}
