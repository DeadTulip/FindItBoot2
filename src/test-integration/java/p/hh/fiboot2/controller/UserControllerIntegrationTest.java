package p.hh.fiboot2.controller;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Test
    public void basicCrudTest() throws Exception {
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

        MvcResult result = mvc.perform(withToken(post("/user").content(payload)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(userName)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Long userId = JsonPath.parse(content).read("$.userId", Long.class);
        return userId;
    }


    private void readUser(Long userId, String userName) throws Exception {
        mvc.perform(withToken(get("/user/" + userId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(userId.intValue())))
                .andExpect(jsonPath("$.username", is(userName)))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    private void updateUser(Long userId, String userName) throws Exception {
        String payload = new JSONObject()
                .put("userId", userId)
                .put("username", userName)
                .put("password", "haihanPassword")
                .toString();

        MvcResult result = mvc.perform(withToken(put("/user").content(payload)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(userName)))
                .andReturn();
    }

    private void deleteUser(Long userId) throws Exception {
        mvc.perform(withToken(delete("/user/" + userId)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private void readNonExistUser(Long userId) throws Exception {
        mvc.perform(withToken(get("/user/" + userId)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
