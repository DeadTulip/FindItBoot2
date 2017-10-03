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

import java.util.Date;

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
public class ItemControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private Date today = new Date();

    @Test
    public void basicCrudTest() throws Exception {
        Long userId = createUser();

        String itemName = "myComputer";
        Long itemId = createItem(userId, itemName);
        readItem(itemId);

//        String newItemName = "myNewComputer";
//        updateItem(itemId, newItemName);
//        readItem(itemId);
//
//        deleteItem(itemId);
//        readNonExistItem(itemId);
    }

    private Long createUser() throws Exception {
        String payload = new JSONObject()
                .put("username", "haihan")
                .put("password", "haihanPassword")
                .toString();

        MvcResult result = mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        ).andReturn();

        String content = result.getResponse().getContentAsString();
        Long userId = JsonPath.parse(content).read("$.userId", Long.class);
        return userId;
    }

    private Long createItem(Long userId, String itemName) throws Exception {
        String payload = new JSONObject()
                .put("user", new JSONObject()
                        .put("userId", userId)
                )
                .put("name", itemName)
                .put("dateCreated", today)
                .put("dateUpdated", today)
                .put("eventStartTime", today)
                .put("description", "myDescription")
                .toString();

        MvcResult result = mvc.perform(
                post("item").contentType(MediaType.APPLICATION_JSON).content(payload)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Long itemId = JsonPath.parse(content).read("$.itemId", Long.class);
        return itemId;
    }

    private void readItem(Long itemId) throws Exception {
        mvc.perform(get("item/" + itemId).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private void updateItem(Long itemId, String newItemName) throws Exception {
        String payload = new JSONObject()
                .put("user", new JSONObject()
                        .put("userId", 1)
                        .put("userName", "haihan")
                )
                .put("name", newItemName)
                .put("dateCreated", today)
                .put("dateUpdated", today)
                .put("eventStartTime", today)
                .put("description", "myDescription")
                .toString();

        MvcResult result = mvc.perform(
                put("item/" + itemId).contentType(MediaType.APPLICATION_JSON).content(payload)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private void deleteItem(Long itemId) {
    }

    private void readNonExistItem(Long itemId) {
    }
}
