package com.polarbear.dao;

import com.polarbear.service.PageList;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.polarbear.util.Constants.ResultState.DB_DATA_NOT_UNIQUE_ERR;
import static com.polarbear.util.Constants.ResultState.DB_ERR;

/**
 * http://stackoverflow.com/questions/37704414/spring-boot-hibernate-5-integration-error
 * @param <T>
 */
@Repository
public class BaseDao<T> {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void store(T obj) throws DaoException {
        try {
            em.persist(obj);
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    public void delete(T obj) throws DaoException {
        try {
            em.remove(obj);
        } catch (IllegalArgumentException e) {
            throw new DaoException(DB_ERR);
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    @SuppressWarnings("unchecked")
    public T findByIdLock(Class clazz, long id) throws DaoException {
        try {
            return (T) em.find(clazz, id, LockModeType.PESSIMISTIC_WRITE);
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    @SuppressWarnings("unchecked")
    public T findById(Class clazz, long id) throws DaoException {
        try {
            return (T) em.find(clazz, id);
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(Class clazz) throws DaoException {
        StringBuilder hqlSb = new StringBuilder("from ").append(clazz.getSimpleName());
        try {
            return em.createQuery(hqlSb.toString()).getResultList();
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    @SuppressWarnings("unchecked")
    public PageList<T> findPageListByDynamicCondition(final Class clazz, final int pageNo, final int pageSize, final String hqlCondition) throws DaoException {
        try {
            List list = em.createQuery(generateHql(clazz, hqlCondition))
                            .setFirstResult((pageNo - 1) * pageSize)
                            .setMaxResults(pageSize)
                            .getResultList();
            long count = countByDynamicCondition(clazz, hqlCondition);
            return new PageList<T>(count, pageNo, pageSize, list);
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    private String generateHql(Class clazz, String hqlCondition) {
        StringBuilder hqlSb = new StringBuilder("from ").append(clazz.getSimpleName()).append(" where 1=1");
        if (StringUtils.isEmpty(hqlCondition)) {
            return hqlSb.toString();
        }
        return hqlSb.append(" and ").append(hqlCondition).append(" order by id desc").toString();
    }

    @SuppressWarnings("unchecked")
    public long countByDynamicCondition(final Class clazz, final String hqlCondition) throws DaoException {
        try {
            return em.createQuery("select count(*) " + generateHql(clazz, hqlCondition)).getFirstResult();
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    public T findByNamedQueryObject(String nameQuery, Object... values) throws DaoException {
        List<T> list = findByNamedQuery(nameQuery, values);
        int size = list.size();
        if (size == 0)
            return null;
        if (size > 1)
            throw new DaoException(DB_DATA_NOT_UNIQUE_ERR);
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String nameQuery, Object... values) throws DaoException {
        try {
            return (List<T>) em.createNamedQuery(nameQuery);
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    public PageList<T> findByNamedQueryByPage(final String nameQuery, final int pageNo, final int pageSize, final Object... values) throws DaoException {
        try {
            Query query = em.createNamedQuery(nameQuery)
                    .setFirstResult((pageNo - 1) * pageSize)
                    .setMaxResults(pageSize);
            for (int i = 0; values != null && i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
            long count = countByNamedQuery(nameQuery, values);
            return new PageList<T>(count, pageNo, pageSize, query.getResultList());
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    public long countByNamedQuery(final String nameQuery, final Object... values) throws DaoException {
        try {
            Query query = em.createNamedQuery(nameQuery);
            for (int i = 0; values != null && i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
            return query.getMaxResults();
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(final String nameQuery, final Map<String, List> paramLst) throws DaoException {
        try {
            Query query = em.createNamedQuery(nameQuery);
            Set<String> keySet = paramLst.keySet();
            for (String paramName : keySet) {
                query.setParameter(paramName,paramLst.get(paramName));
            }
            return query.getResultList();
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

    public int executeUpdate(final String nameQuery, final Object... values) throws DaoException {
        try {
            Query query = em.createNamedQuery(nameQuery);
            for (int i = 0; values != null && i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
            return query.executeUpdate();
        } catch (DataAccessException e) {
            throw new DaoException(DB_ERR);
        }
    }

}