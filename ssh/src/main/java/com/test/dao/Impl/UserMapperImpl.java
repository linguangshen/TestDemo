package com.test.dao.Impl;

import com.test.bean.User;
import com.test.dao.UserMapper;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserMapperImpl implements UserMapper {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }

    @Override
    public List<User> findUsers() {
        String sql = "SELECT * FROM students";
        SQLQuery sqlQuery = getCurrentSession().createSQLQuery(sql);
//        Query query = getCurrentSession().createQuery("from students");
        Query query = sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<User> list = query.list();
        return list;
    }
}
