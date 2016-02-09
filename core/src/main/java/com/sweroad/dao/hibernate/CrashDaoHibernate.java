package com.sweroad.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sweroad.query.CrashQuery;
import com.sweroad.query.Queryable;
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
    public List<Crash> findByTarNo(String tarNo) {
        List<Crash> crashList = getSession().createCriteria(Crash.class).add(Restrictions.eq("tarNo", tarNo)).list();
        if (crashList.size() > 0) {
            return crashList;
        }
        return new ArrayList<>();
    }

    public List<Crash> findCrashesByQueryCrash(CrashQuery crashQuery) {
        Session session = getSession();
        Query namedQuery = session.createQuery(crashQuery.toString());
        if (crashQuery.getQueryables().size() > 0) {
            for (String s : crashQuery.getQueryables().keySet()) {
                if (s.endsWith("List")) {
                    namedQuery.setParameterList(s, crashQuery.getQueryables().get(s));
                } else {
                    namedQuery.setParameter(s, crashQuery.getQueryables().get(s));
                }
            }
        }
        if (crashQuery.getParameters().size() > 0) {
            for (String s : crashQuery.getParameters().keySet()) {
                namedQuery.setParameter(s, crashQuery.getParameters().get(s));
            }
        }
        if (crashQuery.getCustomQueryables().size() > 0) {
            for (String queryable : crashQuery.getCustomQueryables().keySet()) {
                Map<String, Object> paramMap = crashQuery.getCustomQueryables().get(queryable);
                for (String s : paramMap.keySet()) {
                    if (paramMap.get(s) instanceof List) {
                        namedQuery.setParameterList(s.concat("List"), (List) paramMap.get(s));
                    } else {
                        namedQuery.setParameter(s, paramMap.get(s));
                    }
                }
            }
        }
        return findByNamedQuery(namedQuery);
    }
}
