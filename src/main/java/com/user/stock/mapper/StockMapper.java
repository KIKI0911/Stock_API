package com.user.stock.mapper;

import com.user.stock.entity.Stocks;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;


@Mapper
public interface StockMapper {

    //GET
    @Select("SELECT * FROM stocks ")
    List<Stocks> findAllStocks();

    @Select("SELECT * FROM stocks WHERE symbol = #{symbol}")
    Optional<Stocks> findStockBySymbol(Integer symbol);

    @Insert("INSERT INTO stocks (symbol, companyName, quantity, price) VALUES (#{symbol}, #{companyName}, #{quantity}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertStock(Stocks stocks);

    @Update("UPDATE stocks SET companyName = #{companyName}, quantity = #{quantity}, price = #{price} WHERE symbol = #{symbol}")
    int updateStock(Stocks existingStock);
}
