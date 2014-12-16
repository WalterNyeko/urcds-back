package com.sweroad.dao.hibernate;

import java.util.List;

import com.sweroad.query.QueryCrash;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.sweroad.dao.CrashDao;
import com.sweroad.model.Crash;

@Repository("crashDao")
public class CrashDaoHibernate extends GenericDaoHibernate<Crash, Long> implements CrashDao {

    public CrashDaoHibernate() {
        super(Crash.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Crash findByTarNo(String tarNo) {
        List<Crash> crashList = getSession().createCriteria(Crash.class).add(Restrictions.eq("tarNo", tarNo)).list();
        if (crashList.size() > 0) {
            return crashList.get(0);
        }
        return null;
    }

    public List<Crash> findCrashesByQueryCrash(QueryCrash queryCrash) {
        Session session = getSession();
        Query namedQuery = session.createQuery(queryCrash.toString());
        if (queryCrash.getQueryables().size() > 0) {
            for (String s : queryCrash.getQueryables().keySet()) {
                if (s.endsWith("List")) {
                    namedQuery.setParameterList(s, queryCrash.getQueryables().get(s));
                } else {
                    namedQuery.setParameter(s, queryCrash.getQueryables().get(s));
                }
            }
        }
        if(queryCrash.getParameters().size() > 0) {
            for(String s : queryCrash.getParameters().keySet()){
                namedQuery.setParameter(s, queryCrash.getParameters().get(s));
            }
        }
        return findByNamedQuery(namedQuery);
    }
}
