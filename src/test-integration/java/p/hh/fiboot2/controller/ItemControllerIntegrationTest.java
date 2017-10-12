package p.hh.fiboot2.controller;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ItemControllerIntegrationTest extends AbstractControllerIntegrationTest {

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

        MvcResult result = mvc.perform(withToken(post("/user").content(payload))).andReturn();

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

        MvcResult result = mvc.perform(withToken(post("/item").content(payload)))
                .andDo(print())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Long itemId = JsonPath.parse(content).read("$.itemId", Long.class);
        return itemId;
    }

    private void readItem(Long itemId) throws Exception {
        mvc.perform(withToken(get("/item/" + itemId)))
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
                withToken(put("/item").content(payload))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(newItemName)))
                .andReturn();
    }

    private void deleteItem(Long itemId) throws Exception {
        mvc.perform(withToken(delete("/item/" + itemId)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private void readNonExistItem(Long itemId) throws Exception {
        mvc.perform(withToken(get("/item/" + itemId)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
