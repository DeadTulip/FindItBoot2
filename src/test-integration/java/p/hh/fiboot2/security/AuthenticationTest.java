package p.hh.fiboot2.security;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class AuthenticationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void loginTest() throws Exception {
        String[] authInfo = loginWithUsernameAndPassword("haihan", "hh");
        accessWithToken(authInfo[0], authInfo[1]);

    }

    private String[] loginWithUsernameAndPassword(String username, String password) throws Exception {
        MvcResult result = mvc.perform(post("/authenticate")
                .header("X-Auth-Username", username)
                .header("X-Auth-Password", password)
        )
                .andDo(print())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String userId = JsonPath.parse(content).read("$.userId", String.class);
        String token = JsonPath.parse(content).read("$.token", String.class);
        return new String[] {userId, token};
    }

    private void accessWithToken(String userId, String token) throws Exception {
        mvc.perform(get("/user/" + userId).header("X-Auth-Token", "invalidToken"))
                .andExpect(status().isUnauthorized())
                .andDo(print());

        mvc.perform(get("/user/" + userId).header("X-Auth-Token", token))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
