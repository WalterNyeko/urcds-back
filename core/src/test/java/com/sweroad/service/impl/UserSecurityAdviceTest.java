package com.sweroad.service.impl;

import com.sweroad.Constants;
import com.sweroad.dao.UserDao;
import com.sweroad.model.Role;
import com.sweroad.model.User;
import com.sweroad.service.UserService;
import com.sweroad.service.UserSecurityAdvice;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserSecurityAdviceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    ApplicationContext ctx = null;
    SecurityContext initialSecurityContext = null;

    @Before
    public void setUp() throws Exception {
        // store initial security context for later restoration
        initialSecurityContext = SecurityContextHolder.getContext();

        SecurityContext context = new SecurityContextImpl();
        User user = new User("user");
        user.setId(1L);
        user.setPassword("password");
        user.addRole(new Role(Constants.USER_ROLE));

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        token.setDetails(user);
        context.setAuthentication(token);
        SecurityContextHolder.setContext(context);
    }

    @After
    public void tearDown() {
        SecurityContextHolder.setContext(initialSecurityContext);
    }

    @Test
    public void testAddUserWithoutAdminRole() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertTrue(auth.isAuthenticated());
        UserService userService = makeInterceptedTarget();
        User user = new User("admin");
        user.setId(2L);

        try {
            userService.saveUser(user);
            fail("AccessDeniedException not thrown");
        } catch (AccessDeniedException expected) {
            assertNotNull(expected);
            Assert.assertEquals(expected.getMessage(), UserSecurityAdvice.ACCESS_DENIED);
        }
    }

    @Test
    public void testAddUserAsAdmin() throws Exception {
        SecurityContext securityContext = new SecurityContextImpl();
        User user = new User("admin");
        user.setId(2L);
        user.setPassword("password");
        user.addRole(new Role(Constants.ADMIN_ROLE));
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        token.setDetails(user);
        securityContext.setAuthentication(token);
        SecurityContextHolder.setContext(securityContext);

        UserService userService = makeInterceptedTarget();
        final User adminUser = new User("admin");
        adminUser.setId(2L);

        given(userDao.saveUser(adminUser)).willReturn(adminUser);
        given(passwordEncoder.encode(adminUser.getPassword())).willReturn(adminUser.getPassword());

        userService.saveUser(adminUser);
    }

    @Test
    public void testUpdateUserProfile() throws Exception {
        UserService userService = makeInterceptedTarget();
        final User user = new User("user");
        user.setId(1L);
        user.getRoles().add(new Role(Constants.USER_ROLE));

        given(userDao.saveUser(user)).willReturn(user);
        given(passwordEncoder.encode(user.getPassword())).willReturn(user.getPassword());

        userService.saveUser(user);
    }

    // Test fix to http://issues.appfuse.org/browse/APF-96
    @Test
    public void testChangeToAdminRoleFromUserRole() throws Exception {
        UserService userService = makeInterceptedTarget();
        User user = new User("user");
        user.setId(1L);
        user.getRoles().add(new Role(Constants.ADMIN_ROLE));

        try {
            userService.saveUser(user);
            fail("AccessDeniedException not thrown");
        } catch (AccessDeniedException expected) {
            assertNotNull(expected);
            assertEquals(expected.getMessage(), UserSecurityAdvice.ACCESS_DENIED);
        }
    }

    // Test fix to http://issues.appfuse.org/browse/APF-96
    @Test
    public void testAddAdminRoleWhenAlreadyHasUserRole() throws Exception {
        UserService userService = makeInterceptedTarget();
        User user = new User("user");
        user.setId(1L);
        user.getRoles().add(new Role(Constants.ADMIN_ROLE));
        user.getRoles().add(new Role(Constants.USER_ROLE));

        try {
            userService.saveUser(user);
            fail("AccessDeniedException not thrown");
        } catch (AccessDeniedException expected) {
            assertNotNull(expected);
            assertEquals(expected.getMessage(), UserSecurityAdvice.ACCESS_DENIED);
        }
    }

    // Test fix to http://issues.appfuse.org/browse/APF-96
    @Test
    public void testAddUserRoleWhenHasAdminRole() throws Exception {
        SecurityContext securityContext = new SecurityContextImpl();
        User user1 = new User("user");
        user1.setId(1L);
        user1.setPassword("password");
        user1.addRole(new Role(Constants.ADMIN_ROLE));
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword(), user1.getAuthorities());
        token.setDetails(user1);
        securityContext.setAuthentication(token);
        SecurityContextHolder.setContext(securityContext);

        UserService userService = makeInterceptedTarget();
        final User user = new User("user");
        user.setId(1L);
        user.getRoles().add(new Role(Constants.ADMIN_ROLE));
        user.getRoles().add(new Role(Constants.USER_ROLE));

        given(userDao.saveUser(user)).willReturn(user);
        given(passwordEncoder.encode(user.getPassword())).willReturn(user.getPassword());

        userService.saveUser(user);
    }

    // Test fix to http://issues.appfuse.org/browse/APF-96
    @Test
    public void testUpdateUserWithUserRole() throws Exception {
        UserService userService = makeInterceptedTarget();
        final User user = new User("user");
        user.setId(1L);
        user.getRoles().add(new Role(Constants.USER_ROLE));

        given(userDao.saveUser(user)).willReturn(user);
        given(passwordEncoder.encode(user.getPassword())).willReturn(user.getPassword());

        userService.saveUser(user);
    }

    private UserService makeInterceptedTarget() {
        ctx = new ClassPathXmlApplicationContext("/applicationContext-test.xml");

        UserService userService = (UserService) ctx.getBean("target");
        userService.setUserDao(userDao);
        userService.setPasswordEncoder(passwordEncoder);
        return userService;
    }
}
