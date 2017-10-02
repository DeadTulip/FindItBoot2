package p.hh.fiboot2.controller;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void userOperationTest() throws Exception {
        String userName = "haihan";
        long userId = createUser(userName);
        readUser(userId, userName);

        String newUserName = "newhaihan";
        updateUser(userId, newUserName);
        readUser(userId, newUserName);

        deleteUser(userId);
        readNonExistUser(userId);
    }

    private Long createUser(String userName) throws Exception {
        String payload = new JSONObject()
                .put("username", userName)
                .put("password", "haihanPassword")
                .toString();

        MvcResult result = mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(userName)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Long userId = JsonPath.parse(content).read("$.userId", Long.class);
        return userId;
    }


    private void readUser(Long userId, String userName) throws Exception {
        mvc.perform(get("/user/" + userId).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(userId.intValue())))
                .andExpect(jsonPath("$.username", is(userName)));
    }

    private void updateUser(Long userId, String userName) throws Exception {
        String payload = new JSONObject()
                .put("userId", userId)
                .put("username", userName)
                .put("password", "haihanPassword")
                .toString();

        MvcResult result = mvc.perform(put("/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(userName)))
                .andReturn();
    }

    private void deleteUser(Long userId) throws Exception {
        mvc.perform(delete("/user/" + userId).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private void readNonExistUser(Long userId) throws Exception {
        mvc.perform(get("/user/" + userId).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
