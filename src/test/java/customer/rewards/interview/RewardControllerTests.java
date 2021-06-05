package customer.rewards.interview;

import customer.rewards.interview.controllers.RewardsController;
import customer.rewards.interview.services.OrderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(RewardsController.class)
@ComponentScan("customer.rewards.interview")
@AutoConfigureMockMvc

public class RewardControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    OrderService orderService;

    @Test
    public void statusIsOk() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/rewards")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void customer1March2021RewardsTest() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/rewards")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.Customer1").exists())
                .andExpect(jsonPath("$.Customer1.MARCH-2021").exists())
                .andExpect(jsonPath("$.Customer1.MARCH-2021").value("165"));
    }

    @Test
    public void customer1NoRewardsTest() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/rewards")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.Customer1").exists())
                .andExpect(jsonPath("$.Customer1.OCTOBER-2020").exists())
                .andExpect(jsonPath("$.Customer1.OCTOBER-2020").value("0"));
    }

    @Test
    public void customer1RewardShouldNotExistTest() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/rewards")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.Customer1").exists())
                .andExpect(jsonPath("$.Customer1.JULY-2021").doesNotExist());
    }

    @Test
    public void customerShouldNotExistTest() throws Exception {
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/rewards")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.Customer3").doesNotExist());
    }
}
