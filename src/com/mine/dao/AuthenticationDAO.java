package com.mine.dao;

import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mine.component.master.User;

@Repository
public class AuthenticationDAO {
	@Autowired
	SessionFactory factory;
	
	@Transactional
	// get user details for databse user table.
	public User getUser(String userName) {
		Session session = factory.getCurrentSession();
		User user = session.get(User.class, userName);
		return user;
	}
	
	@Transactional
	public void saveUser(User user) {
		Session session = factory.getCurrentSession();
		user.setCreationDate(LocalDate.now());
		session.saveOrUpdate(user);
		session.flush();
	}
	
	@Transactional
	public void updatePassword(User user) {
		Session session = factory.getCurrentSession();
		session.update(user);
		session.flush();
	}
}
