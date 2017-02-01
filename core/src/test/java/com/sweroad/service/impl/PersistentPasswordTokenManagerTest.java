package com.sweroad.service.impl;

import com.sweroad.service.UserService;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 
 * @author ivangsa
 * 
 */
@Ignore("create table password_reset_token before running this test")
public class PersistentPasswordTokenManagerTest extends PasswordTokenManagerTest {

    @Autowired
    @Qualifier("persistentPasswordTokenManager.userManager")
    public void setUserService(UserService userService) {
	    super.setUserService(userService);
    }

    @Autowired
    @Qualifier("persistentPasswordTokenManager")
    public void setPasswordTokenManager(PasswordTokenManager passwordTokenManager) {
        super.setPasswordTokenManager(passwordTokenManager);
    }
}
