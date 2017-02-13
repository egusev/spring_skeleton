package ru.erfolk.skeleton.web.controllers;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.erfolk.BaseDBUnitTest;
import ru.erfolk.config.DataConfiguration;
import ru.erfolk.config.RootConfiguration;
import ru.erfolk.config.WebConfig;

import java.io.IOException;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = {AuthenticationTests.class, RootConfiguration.class, DataConfiguration.class, WebConfig.class})
@Configuration
@TestExecutionListeners({
        DirtiesContextBeforeModesTestExecutionListener.class
        , DependencyInjectionTestExecutionListener.class
        , DirtiesContextTestExecutionListener.class
        , TransactionalTestExecutionListener.class
        , SqlScriptsTestExecutionListener.class
        , DbUnitTestExecutionListener.class
})
public class AuthenticationTests extends BaseDBUnitTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    private JSONObject inputObj;

    @Before
    public void setup() {
        JSONParser parser = new JSONParser();
        try {
            String input = loadFile("/dto/authentication.json");
            inputObj = (JSONObject) parser.parse(input);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser
    @DatabaseSetup({
            "/database/users.xml",
    })
    public void testAuthentication() throws Exception {
/*
        ResultActions result;
        result = mvc.perform(
                put(Endpoints.AUTH_USER).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
                        .with(httpBasic("user", "password1"))
        )
                .andExpect(status().is2xxSuccessful());
        System.out.println(result.andReturn().getResponse().getContentAsString());
*/
    }

    @Test
    @WithMockUser
    @DatabaseSetup({
            "/database/users.xml",
    })
    public void testInvalidPasswdAuthentication() throws Exception {
/*
        ResultActions result;
        inputObj.put("password", "");
        result = mvc.perform(
                put(Endpoints.AUTH_USER).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
                        .content(inputObj.toJSONString())
        )
                .andExpect(status().is4xxClientError());
        System.out.println(result.andReturn().getResponse().getContentAsString());
*/
    }

    @Test
    @WithMockUser
    @DatabaseSetup({
            "/database/users.xml",
    })
    public void testInvalidUserNameAuthentication() throws Exception {
/*
        ResultActions result;
        inputObj.put("username", "");
        result = mvc.perform(
                put(Endpoints.AUTH_USER).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
                        .content(inputObj.toJSONString())
        )
                .andExpect(status().is4xxClientError());
        System.out.println(result.andReturn().getResponse().getContentAsString());
*/
    }

    @Test
    @WithMockUser
    @DatabaseSetup({
            "/database/users.xml",
    })
    public void testWildCardPasswdAuthentication() throws Exception {
/*
        ResultActions result;
        inputObj.put("password", ".*");
        result = mvc.perform(
                put(Endpoints.AUTH_USER).accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
                        .content(inputObj.toJSONString())
        )
                .andExpect(status().is4xxClientError());
        System.out.println(result.andReturn().getResponse().getContentAsString());
*/
    }
}