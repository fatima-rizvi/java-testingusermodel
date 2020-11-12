package com.lambdaschool.usermodel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.services.HelperFunctions;
import com.lambdaschool.usermodel.services.RoleService;
import com.lambdaschool.usermodel.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc
public class UserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    HelperFunctions helperFunctions;

    List<User> userList;

    @Before
    public void setUp() throws Exception {

        userList = new ArrayList<>();

        //Data
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1.setRoleid(1);
        r2.setRoleid(2);
        r3.setRoleid(3);

        // admin, data, user
        User u1 = new User("test admin",
                "password",
                "admin@lambdaschool.local");

        u1.setUserid(10);

        u1.getRoles()
                .add(new UserRoles(u1,
                        r1));
        u1.getRoles()
                .add(new UserRoles(u1,
                        r2));
        u1.getRoles()
                .add(new UserRoles(u1,
                        r3));
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@email.local"));
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@mymail.local"));

        u1.getUseremails().get(0).setUseremailid(20);
        u1.getUseremails().get(1).setUseremailid(21);

        //userService.save(u1);
        userList.add(u1);

        // data, user
        User u2 = new User("test cinnamon",
                "1234567",
                "cinnamon@lambdaschool.local");

        u2.setUserid(11);

        u2.getRoles()
                .add(new UserRoles(u2,
                        r2));
        u2.getRoles()
                .add(new UserRoles(u2,
                        r3));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "cinnamon@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "hops@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "bunny@email.local"));

        u2.getUseremails().get(0).setUseremailid(22);
        u2.getUseremails().get(1).setUseremailid(23);
        u2.getUseremails().get(2).setUseremailid(24);

        //userService.save(u2);
        userList.add(u2);

        // user
        User u3 = new User("test barnbarn",
                "ILuvM4th!",
                "barnbarn@lambdaschool.local");
        u3.setUserid(12);
        u3.getRoles()
                .add(new UserRoles(u3,
                        r2));
        u3.getUseremails()
                .add(new Useremail(u3,
                        "barnbarn@email.local"));

        u3.getUseremails().get(0).setUseremailid(25);

        //userService.save(u3);
        userList.add(u3);

        User u4 = new User("test puttat",
                "password",
                "puttat@school.lambda");
        u4.setUserid(13);

        u4.getRoles()
                .add(new UserRoles(u4,
                        r2));

        //userService.save(u4);
        userList.add(u4);

        User u5 = new User("test misskitty",
                "password",
                "misskitty@school.lambda");

        u5.setUserid(14);

        u5.getRoles()
                .add(new UserRoles(u5,
                        r2));

        //userService.save(u5);
        userList.add(u5);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listAllUsers() throws Exception { //Works

        String apiUrl = "/users/users";

        Mockito.when(userService.findAll()).thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String testResult = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expectedResult = mapper.writeValueAsString(userList);

        assertEquals(expectedResult, testResult);

    }

    @Test
    public void getUserById() throws Exception{ //Works
        String apiUrl = "/users/user/7";
        Mockito.when(userService.findUserById(7)).thenReturn(userList.get(2));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String testResult = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String expectedResult = mapper.writeValueAsString(userList.get(2));

        assertEquals(expectedResult, testResult);
    }

    @Test
    public void getUserByIdNotFound() throws Exception { //Works
        String apiUrl = "/users/user/100";
        Mockito.when(userService.findUserById(100)).thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String testResult = r.getResponse().getContentAsString();

        //ObjectMapper mapper = new ObjectMapper();
        String expectedResult = "";

        assertEquals(expectedResult, testResult);
    }


    @Test
    public void getUserByName() {
    }

    @Test
    public void getUserLikeName() {
    }

    @Test
    public void addNewUser() throws Exception{

        User u3 = new User("test neil",
                "ILuvM4th!",
                "barnbarn@lambdaschool.local");

        Role role2 = new Role("user");
        role2.setRoleid(2);

        u3.getRoles()
                .add(new UserRoles(u3,
                        role2));
        u3.getUseremails()
                .add(new Useremail(u3,
                        "barnbarn@email.local"));

        u3.getUseremails().get(0).setUseremailid(30);
        u3.setUserid(13);

        String apiUrl = "/users/user/";

        //Convert to Json
        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u3);

        Mockito.when(userService.save(any(User.class))).thenReturn(u3);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void updateFullUser() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void deleteUserById() {
    }
}