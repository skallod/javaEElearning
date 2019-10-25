package com.bookstore.resource;

import com.bookstore.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginResource.class)
public class LoginResourceTest {

    @Autowired
    private MockMvc accountResource;

    @MockBean
    private UserService service;

    @Test
    public void shouldNotGetToken() throws Exception {
        accountResource.perform(get("/api/v1/token")).andDo(res->{
            String contentAsString = res.getResponse().getContentAsString();
            System.out.println("contentAsString = " + contentAsString);
        }).andExpect(status().is(401));
    }

    @WithMockUser(
            username = "admin",
            password = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void shouldGetUserToken() throws Exception {
        accountResource.perform(get("/api/v1/token")).andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"1\"}"));
    }
}
