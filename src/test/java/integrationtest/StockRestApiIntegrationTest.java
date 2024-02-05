package integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.user.stock.StockApplication;
import com.user.stock.entity.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@SpringBootTest(classes = StockApplication.class)
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StockRestApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @Transactional
    void 株式が全件取得できること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/stocks"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                    [
                    {
                        "id": 1,
                        "symbol": 7203,
                        "companyName": "トヨタ自動車",
                        "quantity": 100,
                        "price": 2640
                    },
                    {
                        "id": 2,
                        "symbol": 9861,
                        "companyName": "吉野家ホールディングス",
                        "quantity": 100,
                        "price": 3131
                    },
                    {
                        "id": 3,
                        "symbol": 3197,
                        "companyName": "スカイラークホールディングス",
                        "quantity": 100,
                        "price": 2059
                    },
                    {
                        "id": 4,
                        "symbol": 9101,
                        "companyName": "日本郵船",
                        "quantity": 100,
                        "price": 4333
                    }
                ]
                                """, response, JSONCompareMode.STRICT);

    }

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @Transactional
    void シンボルで指定した株式が取得できること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/stocks/7203"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                    "id": 1,
                    "symbol": 7203,
                    "companyName": "トヨタ自動車",
                    "quantity": 100,
                    "price": 2640
                }
                            """, response, JSONCompareMode.STRICT);

    }

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @Transactional
    public void 存在しないシンボルを指定するとステータスコード404とエラーメッセージを取得すること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/stocks/9999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getErrorMessage();
    }

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @ExpectedDataSet(value = "datasets/insertStockTest.yml", ignoreCols = "id")
    @Transactional
    public void 新規の株式がDBに登録されるとステータスコード201が返ってくる事() throws Exception {
        Stock stock = new Stock(5, 2897, "日清食品", 100, 5160);
        ObjectMapper mapper = new ObjectMapper();
        String jason = mapper.writeValueAsString(stock);
        mockMvc.perform(MockMvcRequestBuilders.post("/stocks").contentType(MediaType.APPLICATION_JSON).content(jason))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @Transactional
    public void 株式の重複登録の場合はステータスコード400とエラーメッセージが返ってくる() throws Exception {
        Stock stock = new Stock(5, 7203, "トヨタ自動車", 100, 5160);
        ObjectMapper mapper = new ObjectMapper();
        String jason = mapper.writeValueAsString(stock);
        mockMvc.perform(MockMvcRequestBuilders.post("/stocks").contentType(MediaType.APPLICATION_JSON).content(jason))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString().contains("Stock already exists");
    }

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @ExpectedDataSet(value = "datasets/updateStockTest.yml", ignoreCols = "id")
    @Transactional
    public void 存在する株式情報を更新するとステータスコード200が出力せれ更新されているか確認すること() throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.patch("/stocks/7203")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "id": 1,
                                        "symbol": 7203,
                                        "companyName": "トヨタ自動車",
                                        "quantity": 500,
                                        "price": 3000
                                    }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString().contains("Stock updated"));
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/stocks/7203"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                        
                {
                           "id": 1,
                           "symbol": 7203,
                           "companyName": "トヨタ自動車",
                           "quantity": 500,
                           "price": 3000
                       }
                """, response, JSONCompareMode.STRICT);
    }


    @Test
    @DataSet(value = "datasets/stocks.yml")
    @ExpectedDataSet(value = "datasets/updateTest.yml", ignoreCols = "id")
    @Transactional
    public void どれか一つだけnullではない場合その値だけが更新されていることを確認する() throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.patch("/stocks/7203")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "id": null,
                                        "symbol": null,
                                        "companyName": null,
                                        "quantity": 500,
                                        "price": null
                                    }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString().contains("Stock updated"));
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/stocks/7203"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                        
                {
                           "id": 1,
                           "symbol": 7203,
                           "companyName": "トヨタ自動車",
                           "quantity": 500,
                           "price": 2640
                       }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @ExpectedDataSet(value = "datasets/stocks.yml", ignoreCols = "id")
    @Transactional
    public void 全ての値がnullの場合更新されないことを確認する() throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.patch("/stocks/7203")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": null,
                                    "symbol": null,
                                    "companyName": null,
                                    "quantity": null,
                                    "price": null
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString().contains("Stock updated"));
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/stocks/7203"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                        
                {
                           "id": 1,
                           "symbol": 7203,
                           "companyName": "トヨタ自動車",
                           "quantity": 100,
                           "price": 2640
                       }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @Transactional
    public void 存在しない株式を更新するとステータスコード404とエラーメッセージが出力() throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.patch("/stocks/2897")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                               "id" :5,
                                               "symbol": 2897,
                                               "companyName": "日清食品",
                                               "quantity": 100,
                                               "price": 5160
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString().contains("Not Found"));
    }

    @Test
    @DataSet(value = "datasets/stocks.yml")
    @ExpectedDataSet(value = "datasets/deleteStockTest.yml")
    @Transactional
    public void 存在する株式を削除しステータスコード200の確認と指定のメッセージを取得できること() throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.delete("/stocks/7203"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString().contains("Stock deleted"));
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/stocks"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                [
                {
                               "id": 2,
                               "symbol": 9861,
                               "companyName": "吉野家ホールディングス",
                               "quantity": 100,
                               "price": 3131
                           },
                           {
                               "id": 3,
                               "symbol": 3197,
                               "companyName": "スカイラークホールディングス",
                               "quantity": 100,
                               "price": 2059
                           },
                           {
                               "id": 4,
                               "symbol": 9101,
                               "companyName": "日本郵船",
                               "quantity": 100,
                               "price": 4333
                           }
                       ]
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value ="datasets/stocks.yml")
    @Transactional
    public void 存在しない株式を削除しステータスコード404の確認とエラーメッセージを取得()throws Exception {
        Assertions.assertTrue(mockMvc.perform(MockMvcRequestBuilders.delete("/stocks/9999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString().contains("Not Found"));
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/stocks"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                [
                    {
                        "id": 1,
                        "symbol": 7203,
                        "companyName": "トヨタ自動車",
                        "quantity": 100,
                        "price": 2640
                    },
                    {
                        "id": 2,
                        "symbol": 9861,
                        "companyName": "吉野家ホールディングス",
                        "quantity": 100,
                        "price": 3131
                    },
                    {
                        "id": 3,
                        "symbol": 3197,
                        "companyName": "スカイラークホールディングス",
                        "quantity": 100,
                        "price": 2059
                    },
                    {
                        "id": 4,
                        "symbol": 9101,
                        "companyName": "日本郵船",
                        "quantity": 100,
                        "price": 4333
                    }
                ]
                 """, response, JSONCompareMode.STRICT);
    }

}
