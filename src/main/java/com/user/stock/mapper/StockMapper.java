package com.user.stock.mapper;

import com.user.stock.entity.Stocks;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;


@Mapper
public interface StockMapper {

    //GET
    @Select("SELECT * FROM stocks ")
    List<Stocks> findAllStocks();

    @Select("SELECT * FROM stocks WHERE symbol = #{symbol}")
    Optional<Stocks> findCertainStock(Integer symbol);
}
