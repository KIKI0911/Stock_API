package integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.user.stock.StockApplication;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    public void 存在しないシンボルを指定するとステータスコード404とエラーメッセージを取得すること()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/stocks/9999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getErrorMessage();
    }
}