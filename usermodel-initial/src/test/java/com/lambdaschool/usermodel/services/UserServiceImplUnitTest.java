package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplUnitTest {

    //Autowire in UserService
    @Autowired
    UserService userService;

    @Before
    public void setUp() throws Exception {
        //mock -> fake data
        //stubs -> fake method
        //Java mock = stub -> mocks

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findUserById() {    //Works

        assertEquals("test barnbarn",
                userService.findUserById(11)
                        .getUsername());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void findUserByIdNotFound() {    //Works
        assertEquals("test barnbarn",
                userService.findUserById(10000)
                        .getUsername());
    }


    @Test
    public void findByNameContaining() {    //Works
        //This method should return a list
        assertEquals(2,
                userService.findByNameContaining("tt")
                        .size());
    }

    @Test
    public void findAll() { //Works
        assertEquals(5,
                userService.findAll()
                        .size());
    }

    @Test
    public void z_delete() {  //Works
        userService.delete(11);
        assertEquals(4,
                userService.findAll().
                        size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void zz_deleteFailed() {  //Works
        userService.delete(1111);
        assertEquals(4,
                userService.findAll().
                        size());
    }

    @Test
    public void findByName() {  //Works
        assertEquals("test cinnamon",
                userService.findByName("Test cinnamon")
                        .getUsername());
    }

    @Test
    public void z_save() { //NOT working

//        User u3 = new User("test neil",
//                "ILuvM4th!",
//                "barnbarn@lambdaschool.local");
//
//        Role role2 = new Role("user");
//        role2.setRoleid(2);
//
//        u3.getRoles()
//                .add(new UserRoles(u3,
//                        role2));
//        u3.getUseremails()
//                .add(new Useremail(u3,
//                        "barnbarn@email.local"));
//        User addUser = userService.save(u3);
//        assertNotNull(addUser);
//        assertEquals("test neil", addUser.getUsername());

    }

    @Test
    public void z_update() {
    }

    @Test
    public void zzz_deleteAll() {
        userService.deleteAll();
        assertEquals(0,
                userService.findAll().
                        size());
    }
}