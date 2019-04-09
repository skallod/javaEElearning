package com.bookstore.resource;

import com.bookstore.config.SecurityConfig;
import com.bookstore.service.BookService;
import com.bookstore.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.server.csrf.CsrfWebFilter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import java.lang.reflect.Field;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
//@WebAppConfiguration

@RunWith(SpringRunner.class)
@WebMvcTest(/*value = BookResource.class*/)
//@AutoConfigureMockMvc(secure = false)
public class BookResourceTest {

    @Autowired
    private MockMvc bookResource;

    @Autowired
    private UserService userService;

    @MockBean
    private BookService bookService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    @Before
    public void setup() {
//        Field filterChains = ReflectionUtils.findField(FilterChainProxy.class, "filterChains");
//        ReflectionUtils.makeAccessible(filterChains);
//        filterChains.
//        ((FilterChainProxy)springSecurityFilterChain).filterChains.get(0).getFilters()
//                .removeIf(f->f.getClass().getName().equals(CsrfFilter.class.getName()));
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }
    @Test
    public void shouldReturn401() throws Exception {
        mvc.perform(post("/book/add")).andExpect(status().is(401));
//        bookResource.perform(post("/book/add").with(csrf())).andExpect(status().is(401));
    }

    @WithMockUser(
            username = "admin",
            password = "admin",
            authorities = {"ROLE_USER"}
    )
    @Test
    public void shouldForbiddenPostBook() throws Exception {
        mvc.perform(post("/book/add").with(csrf()).with(
                user("admin").password("pass").roles("USER"/*,"ADMIN"*/)
                ).contentType(MediaType.APPLICATION_JSON)
        .content("{\"author\":\"fadsfa\",\"title\":\"fdasdfaf\",\"publisher\":\"fdadfsa\"}")
        ).andExpect(status().isOk()).andDo(res->{
            String contentAsString = res.getResponse().getContentAsString();
            System.out.println("contentAsString = " + contentAsString);
        });
    }

}
