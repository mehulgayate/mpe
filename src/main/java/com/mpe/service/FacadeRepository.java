package com.mpe.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.mpe.entity.User;
import com.mpe.entity.User.UserRole;
import com.mpe.entity.User.UserStatus;


@Transactional
public class FacadeRepository {

	@Resource
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	public User findUserByEmail(String email){
		return (User) getSession().createQuery("From "+User.class.getName()+" where email=:email")
				.setParameter("email",email).uniqueResult();
	}

	public User findUserById(Long id){
		return (User) getSession().createQuery("From "+User.class.getName()+" where id=:id")
				.setParameter("id",id).uniqueResult();	
	}
	
	public List<User> listAllUsers(){		
		return getSession().createQuery("FROM "+User.class.getName() +" u where u.status=:status AND u.role=:role")
				.setParameter("status", UserStatus.ACTIVE)
				.setParameter("role", UserRole.NORMAL).list();
	}
	
	public List<User> listAllBlockedUsers(){		
		return getSession().createQuery("FROM "+User.class.getName() +" u where u.status=:status")
				.setParameter("status", UserStatus.BLOCKED).list();
	}	

}
