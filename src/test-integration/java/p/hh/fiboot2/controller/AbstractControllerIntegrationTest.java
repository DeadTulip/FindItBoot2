package p.hh.fiboot2.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public abstract class AbstractControllerIntegrationTest {

    @Autowired
    protected MockMvc mvc;

    protected String token;

    @Before
    public void login() throws Exception {
        MvcResult result = mvc.perform(post("/authenticate")
                .header("X-Auth-Username", "haihan")
                .header("X-Auth-Password", "hh")
        ).andReturn();

        String content = result.getResponse().getContentAsString();
        this.token = JsonPath.parse(content).read("$.token", String.class);
    }

    protected MockHttpServletRequestBuilder withToken(MockHttpServletRequestBuilder builder) {
        return builder.contentType(MediaType.APPLICATION_JSON).header("X-Auth-Token", token);
    }
}
