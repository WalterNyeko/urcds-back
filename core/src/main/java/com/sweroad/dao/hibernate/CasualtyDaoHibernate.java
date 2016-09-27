package com.sweroad.dao.hibernate;

import com.sweroad.dao.GenericDao;
import com.sweroad.model.Casualty;
import org.springframework.stereotype.Repository;

/**
 * Created by Frank on 2/22/15.
 */
@Repository("casualtyDao")
public class CasualtyDaoHibernate  extends GenericDaoHibernate<Casualty, Long>  implements GenericDao<Casualty, Long> {

    public CasualtyDaoHibernate() {
        super(Casualty.class);
    }
}