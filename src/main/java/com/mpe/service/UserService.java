package com.mpe.service;

import javax.annotation.Resource;
import com.evalua.entity.support.DataStoreManager;
import com.mpe.entity.User;

public class UserService {
	
	@Resource
	private DataStoreManager dataStoreManager;
	

	public void setDataStoreManager(DataStoreManager dataStoreManager) {
		this.dataStoreManager = dataStoreManager;
	}

	@Resource
	private FacadeRepository facadeRepository;	
	
	public void setFacadeRepository(FacadeRepository repository) {
		this.facadeRepository = repository;
	}
	
	public User validate(String email,String password){
		User user=facadeRepository.findUserByEmail(email);
		if(user!=null){
			if(user.getPassword().equals(password)){
				return user;
			}		
		}
		return null;
	}
	
	public void addUser(UserForm userForm){
		User user=new User();
		user.setEmail(userForm.getEmail());
		user.setName(userForm.getName());
		user.setPassword(userForm.getPassword());
		dataStoreManager.save(user);
	}
}
