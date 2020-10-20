package com.example.priteshapi.repositry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.priteshapi.dao.DemoRepositryDAO;
import com.example.priteshapi.model.Employee;



@Repository
public class DemoServicerepositry {
	@Autowired
	DemoRepositryDAO b2bClientGraphDao;
	
public String GetById(String string) {
		
		return	b2bClientGraphDao.GetById(string);
	}

public String Save(Employee string) {
	
	return	b2bClientGraphDao.Save(string);
}

public String Update(Employee id) {
	// TODO Auto-generated method stub
	return	b2bClientGraphDao.Update(id);
	}
public String Delete(Employee id) {
	// TODO Auto-generated method stub
	return	b2bClientGraphDao.Delete(id);
	}
public String GetAll() {
	// TODO Auto-generated method stub
	return	b2bClientGraphDao.GetAll();
	}
}
