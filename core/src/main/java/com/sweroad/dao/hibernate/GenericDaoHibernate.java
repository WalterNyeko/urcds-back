package com.sweroad.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.sweroad.model.NameIdModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.util.Version;
import org.hibernate.HibernateException;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.sweroad.dao.GenericDao;
import com.sweroad.dao.SearchException;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * <p/>
 * <p>To register this class in your Spring context file, use the following XML.
 * <pre>
 *      &lt;bean id="fooDao" class="com.sweroad.dao.hibernate.GenericDaoHibernate"&gt;
 *          &lt;constructor-arg value="com.sweroad.model.Foo"/&gt;
 *      &lt;/bean&gt;
 * </pre>
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 *      Updated by jgarcia: update hibernate3 to hibernate4
 * @author jgarcia (update: added full text search + reindexing)
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public class GenericDaoHibernate<T, PK extends Serializable> implements GenericDao<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());
    private Class<T> persistentClass;
    @Resource
    private SessionFactory sessionFactory;
    private Analyzer defaultAnalyzer;

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
        defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_35);
    }

    /**
     * Constructor that takes in a class and sessionFactory for easy creation of DAO.
     *
     * @param persistentClass the class type you'd like to persist
     * @param sessionFactory  the pre-configured Hibernate SessionFactory
     */
    public GenericDaoHibernate(final Class<T> persistentClass, SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
        defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_35);
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public Session getSession() throws HibernateException {
        Session session = getSessionFactory().getCurrentSession();
        if (session == null) {
            session = getSessionFactory().openSession();
        }
        return session;
    }

    @Autowired
    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        Session session = getSession();
        return session.createCriteria(persistentClass).list();
    }

    /**
     * {@inheritDoc}
     */
    public List<T> getAllDistinct() {
        Collection<T> result = new LinkedHashSet<T>(getAll());
        return new ArrayList<T>(result);
    }

    @Override
    public List<T> getAllActive() {
        List<T> list = this.getAllDistinct();
        if (list.size() > 0 && list.get(0) instanceof NameIdModel) {
            return list.stream()
                    .filter(item -> ((NameIdModel)item).isActive())
                    .collect(Collectors.toList());
        } else {
            return list;
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	public List<T> search(String searchTerm) throws SearchException {
        Session session = getSession();
        FullTextSession txtSession = Search.getFullTextSession(session);

        org.apache.lucene.search.Query qry;
        try {
            qry = HibernateSearchTools.generateQuery(searchTerm, this.persistentClass, session, defaultAnalyzer);
        } catch (ParseException ex) {
            throw new SearchException(ex);
        }
        org.hibernate.search.FullTextQuery hibQuery = txtSession.createFullTextQuery(qry,
                this.persistentClass);
        return hibQuery.list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        Session session = getSession();
        IdentifierLoadAccess byId = session.byId(persistentClass);
        T entity = (T) byId.load(id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        Session session = getSession();
        IdentifierLoadAccess byId = session.byId(persistentClass);
        T entity = (T) byId.load(id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T save(T object) {
        Session session = getSession();
        return (T) session.merge(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(T object) {
        Session session = getSession();
        session.delete(object);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	public void remove(PK id) {
        Session session = getSession();
        IdentifierLoadAccess byId = session.byId(persistentClass);
        T entity = (T) byId.load(id);
        session.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        return findByNamedQuery(createQuery(queryName, queryParams));
    }

    private Query createQuery(String queryName, Map<String, Object> queryParams) {
        Session session = getSession();
        Query namedQuery = session.getNamedQuery(queryName);

        if (queryParams != null) {
            for (String s : queryParams.keySet()) {
                namedQuery.setParameter(s, queryParams.get(s));
            }
        }
        return namedQuery;
    }

    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams, int page, int maxSize) {
        Query namedQuery = createQuery(queryName, queryParams);
        namedQuery.setFirstResult((page - 1) * maxSize);
        namedQuery.setMaxResults(maxSize);
        return findByNamedQuery(namedQuery);
    }

    protected List<T> findByNamedQuery(Query namedQuery) {
        return namedQuery.list();
    }

    /**
     * {@inheritDoc}
     */
    public void reindex() {
        HibernateSearchTools.reindex(persistentClass, getSessionFactory().getCurrentSession());
    }


    /**
     * {@inheritDoc}
     */
    public void reindexAll(boolean async) {
        HibernateSearchTools.reindexAll(async, getSessionFactory().getCurrentSession());
    }
}
