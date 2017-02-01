package com.sweroad.service.impl;

import com.sweroad.dao.RoleDao;
import com.sweroad.model.Role;
import com.sweroad.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of RoleService interface.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
@Service("roleService")
public class RoleServiceImpl extends GenericServiceImpl<Role, Long> implements RoleService {
    RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        super(roleDao);
        this.roleDao = roleDao;
    }

    /**
     * {@inheritDoc}
     */
    public List<Role> getRoles(Role role) {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public Role getRole(String rolename) {
        return roleDao.getRoleByName(rolename);
    }

    /**
     * {@inheritDoc}
     */
    public Role saveRole(Role role) {
        return dao.save(role);
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        roleDao.removeRole(rolename);
    }
}