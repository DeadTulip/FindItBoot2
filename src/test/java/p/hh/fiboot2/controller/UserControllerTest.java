package p.hh.fiboot2.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import p.hh.fiboot2.security.provider.ApplicationUserService;
import p.hh.fiboot2.service.UserService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends AbstractControllerTest<UserController> {

    @MockBean
    private UserService userService;

    @Override
    protected UserController setUpController() {
        UserController controller = new UserController();
        controller.setUserService(userService);
        return controller;
    }
    
    @Test
    public void test_readNonExistUser() throws Exception {
        given(this.userService.getUser(1L)).willReturn(null);
        this.mvc.perform(get("/user/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
