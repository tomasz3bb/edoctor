package pl.edu.wszib.edoctor.services.impl;


import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.edu.wszib.edoctor.model.User;
import pl.edu.wszib.edoctor.services.IUserService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class UserServiceImplTest {
    @Test
    public void getAll() {
        UserServiceImpl userService = mock(UserServiceImpl.class);
        Mockito.when(userService.getAll()).thenReturn(prepareMockData());
        MatcherAssert.assertThat(userService.getAll(), Matchers.hasSize(3));
    }

    @Test
    public void getUserById() {
        UserServiceImpl userService = mock(UserServiceImpl.class);
        when(userService.getUserById(0)).thenReturn(prepareMockData().get(0));
        Assertions.assertEquals(userService.getUserById(0).toString(), prepareMockData().get(0).toString());
    }

    @Test
    public void saveUser(){
        UserServiceImpl userService = mock(UserServiceImpl.class);
        Byte[] photo = {};
        User test = new User(0, "test", "test", User.Role.Pacjent, photo);
        given(userService.save(test)).willReturn(prepareMockData().add(test));
        when(userService.save(test)).thenReturn(prepareMockData().add(test));
        Assertions.assertEquals(userService.save(test), prepareMockData().add(test));
    }


    private List<User> prepareMockData() {
        List<User> userList = new ArrayList<>();
        Byte[] photo = {};
        userList.add(new User(0, "admin", "admin", User.Role.ADMIN, photo));
        userList.add(new User(0, "admin", "admin", User.Role.ADMIN, photo));
        userList.add(new User(0, "admin", "admin", User.Role.ADMIN, photo));
        return userList;
    }

}

