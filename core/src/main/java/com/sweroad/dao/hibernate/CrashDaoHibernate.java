package com.sweroad.dao.hibernate;

import java.util.List;

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

}
