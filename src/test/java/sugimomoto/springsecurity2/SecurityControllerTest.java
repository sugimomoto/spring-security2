package sugimomoto.springsecurity2;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import sugimomoto.springsecurity2.model.SiteUser;
import sugimomoto.springsecurity2.util.Role;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("登録エラーがある時、エラーを表示することを期待します")
    void whenThereIsRegistrationError_expectToSeeErrors()throws Exception{
        mockMvc.perform(
            post("/register")
            .flashAttr("user", new SiteUser())
            .with(csrf())
        )
        .andExpect(model().hasErrors())
        .andExpect(view().name("register"));
    }

    @Test
    @DisplayName("管理者ユーザとして登録する時、成功することを期待します。")
    void whenRegisteringAsAdminUser_expectToSucceed() throws Exception{
        var user = new SiteUser();
        user.setUsername("管理者ユーザ");
        user.setPassword("password");
        user.setEmail("admin@example.com");
        user.setGender(0);
        user.setAdmin(true);
        user.setRole(Role.ADMIN.name());

        mockMvc.perform(post("/register")
            .flashAttr("user",user).with(csrf()))
            .andExpect(model().hasNoErrors())
            .andExpect(redirectedUrl("/login?register"))
            .andExpect(status().isFound());
    }

    @Test
    @DisplayName("管理者ユーザでログイン時、ユーザ一覧を表示することを期待します。")
    @WithMockUser(username="admin", roles="ADMIN")
    void whenLoggedInAsAdminUser_expectToseeListOfUsers() throws Exception{

        mockMvc.perform(get("/admin/list"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("ユーザ一覧")))
            .andExpect(view().name("list"));
    }
    
}
