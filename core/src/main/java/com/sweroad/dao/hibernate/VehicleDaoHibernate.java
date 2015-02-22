package com.sweroad.dao.hibernate;

import com.sweroad.dao.GenericDao;
import com.sweroad.model.Vehicle;
import org.springframework.stereotype.Repository;

/**
 * Created by Frank on 2/22/15.
 */
@Repository("vehicleDao")
public class VehicleDaoHibernate extends GenericDaoHibernate<Vehicle, Long> implements GenericDao<Vehicle, Long> {

    public VehicleDaoHibernate() {
        super(Vehicle.class);
    }
}
