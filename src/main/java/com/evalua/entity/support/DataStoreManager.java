package com.evalua.entity.support;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.mpe.entity.User;
import com.mpe.entity.UserFile;

public class DataStoreManager {

	private SessionFactory sessionFactory;	

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session save(EntityBase entity){		
		Session session=getSession();
		session.saveOrUpdate(entity);
		return session;
	}

	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public List<UserFile> listUserFiles(User user){
		return getSession().createQuery("FROM "+UserFile.class.getName()+" pp where pp.user=:user")
				.setParameter("user", user)
				.list();
	}

}
