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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void basicCrudTest() throws Exception {
        Long userId = createUser();

        String itemName = "myComputer";
        Long itemId = createItem(userId, itemName);
        readItem(itemId);

        String newItemName = "myNewComputer";
        updateItem(itemId, newItemName);
        readItem(itemId);

        deleteItem(itemId);
        readNonExistItem(itemId);
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
                .put("owner", new JSONObject()
                        .put("userId", userId)
                )
                .put("itemType", "Digital")
                .put("name", itemName)
                .put("dateCreated", sdf.format(today))
                .put("dateUpdated", sdf.format(today))
                .put("eventStartTime", sdf.format(today))
                .put("description", "myDescription")
                .put("originalFileName", "myOriginalFileName")
                .put("fileName", "myFileName")
                .toString();

        MvcResult result = mvc.perform(
                post("/item").contentType(MediaType.APPLICATION_JSON).content(payload)
        )
                .andDo(print())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Long itemId = JsonPath.parse(content).read("$.itemId", Long.class);
        return itemId;
    }

    private void readItem(Long itemId) throws Exception {
        mvc.perform(get("/item/" + itemId).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private void updateItem(Long itemId, String newItemName) throws Exception {
        String payload = new JSONObject()
                .put("itemId", itemId)
                .put("name", newItemName)
                .put("dateCreated", sdf.format(today))
                .put("dateUpdated", sdf.format(today))
                .put("eventStartTime", sdf.format(today))
                .put("description", "myDescription")
                .put("originalFileName", "myOriginalFileName")
                .put("fileName", "myFileName")
                .toString();

        mvc.perform(
                put("/item").contentType(MediaType.APPLICATION_JSON).content(payload)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(newItemName)))
                .andReturn();
    }

    private void deleteItem(Long itemId) throws Exception {
        mvc.perform(delete("/item/" + itemId).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private void readNonExistItem(Long itemId) throws Exception {
        mvc.perform(get("/item/" + itemId).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
