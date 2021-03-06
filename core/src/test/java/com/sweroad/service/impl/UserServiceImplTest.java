package com.sweroad.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sweroad.Constants;
import com.sweroad.dao.UserDao;
import com.sweroad.model.Role;
import com.sweroad.model.User;
import com.sweroad.service.UserExistsException;

public class UserServiceImplTest extends BaseManagerMockTestCase {
    //~ Instance fields ========================================================

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordTokenManager passwordTokenManager;


    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();


    //~ Methods ================================================================

    @Test
    public void testGetUser() throws Exception {
        //given
        final User testData = new User("1");
        testData.getRoles().add(new Role("user"));

        given(userDao.get(1L)).willReturn(testData);

        //then
        User user = userService.getUser("1");

        //then
        assertTrue(user != null);
        assert user != null;
        assertTrue(user.getRoles().size() == 1);
    }

    @Test
    public void testSaveUser() throws Exception {
        //given
        final User testData = new User("1");
        testData.getRoles().add(new Role("user"));

        given(userDao.get(1L)).willReturn(testData);


        final User user = userService.getUser("1");
        user.setPhoneNumber("303-555-1212");

        given(userDao.saveUser(user)).willReturn(user);


        //when
        User returned = userService.saveUser(user);

        //then
        assertTrue(returned.getPhoneNumber().equals("303-555-1212"));
        assertTrue(returned.getRoles().size() == 1);
    }

    @Test
    public void testAddAndRemoveUser() throws Exception {
        //given
        User user = new User();

        // call populate method in super class to populate test data
        // from a properties file matching this class name
        user = (User) populate(user);


        Role role = new Role(Constants.USER_ROLE);
        user.addRole(role);

        final User user1 = user;
        given(userDao.saveUser(user1)).willReturn(user1);


        //when
        user = userService.saveUser(user);

        //then
        assertTrue(user.getUsername().equals("john"));
        assertTrue(user.getRoles().size() == 1);

        //given
        willDoNothing().given(userDao).remove(5L);
        userService.removeUser("5");

        given(userDao.get(5L)).willReturn(null);

        //when
        user = userService.getUser("5");

        //then
        assertNull(user);
        verify(userDao).remove(5L);
    }

    @Test
    public void testUserExistsException() {
        // set expectations
        final User user = new User("admin");
        user.setEmail("matt@raibledesigns.com");

        willThrow(new DataIntegrityViolationException("")).given(userDao).saveUser(user);

        // run test
        try {
            userService.saveUser(user);
            fail("Expected UserExistsException not thrown");
        } catch (UserExistsException e) {
            log.debug("expected exception: " + e.getMessage());
            assertNotNull(e);
        }
    }
}
