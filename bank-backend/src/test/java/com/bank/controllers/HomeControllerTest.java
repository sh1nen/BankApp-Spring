package com.bank.controllers;

import com.bank.domain.User;
import com.bank.services.RoleService;
import com.bank.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HomeControllerTest
{
    
    @Mock
    private RoleService roleService;
    
    @Mock
    private UserService userService;
    
    @Mock
    private HomeController homeController;
    
    @Mock
    private Model model;
    
    @Mock
    private Principal principal;
    
    @Mock
    private User user;
    
    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        homeController = new HomeController(userService, roleService);
        user = new User("username", "password", "firstname", "lastname", "email", "phone");
    }
    
    @Test
    public void getHomePageTest()
    {
        String viewName = homeController.home();
        assertEquals("redirect:/index", viewName);
    }
    
    @Test
    public void getIndexPageTest()
    {
        String viewName = homeController.index();
        assertEquals("index", viewName);
    }
    
    @Test
    public void signupTest()
    {
        String viewName = homeController.singup(model);
        assertEquals("signup", viewName);
        verify(model, times(1)).addAttribute(eq("user"), any());
    }
    
    @Test
    public void userFrontTest()
    {
        String viewName = homeController.userFront(principal, model);
        assertEquals("userFront", viewName);
        verify(userService, times(1)).findByUsername("username");
        verify(model, times(1)).addAttribute(eq("primaryAccount"), any());
        verify(model, times(1)).addAttribute(eq("savingsAccount"), any());
    }
}