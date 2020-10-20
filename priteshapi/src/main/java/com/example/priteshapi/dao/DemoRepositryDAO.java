package com.example.priteshapi.dao;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.priteshapi.model.Employee;


@Repository
public class DemoRepositryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;




	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate=jdbcTemplate;

	}
	

public String GetById(String b2bDomain) {
	String sql ="SELECT first_name FROM `employees` WHERE id=? ";
	String roleId=null;
	try {
		roleId = jdbcTemplate.queryForObject(sql,new Object[]{b2bDomain}, String.class);
	} catch (DataAccessException e1) {
		// TODO Auto-generated catch block
		//e1.printStackTrace();
		return "N";
	}

	
	 
	
	if(roleId.isEmpty() || roleId == null)
	{
		return "N";
	}
   
	JSONObject j = new JSONObject();
	try {
		j.put("Username", roleId);
		
		return j.toString();
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
		return "N";
	}
}


public String Save(Employee string) {
	String sql ="INSERT INTO employees ( `first_name`, `last_name`, `email_address`) VALUES (?,?,?)";
	try {
		
		Object[] paramSqlPickPack= new Object[] {string.getFirstName(),string.getLastName(),string.getEmailId()};

		int []  typesSqlPickPack =new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		int rowOrderHistory =	jdbcTemplate.update(sql ,paramSqlPickPack,typesSqlPickPack);
		System.out.println(rowOrderHistory);
		return "INSERTED";
	} catch (DataAccessException e1) {
		System.out.println(e1.getMessage());

		// TODO Auto-generated catch block
		//e1.printStackTrace();
		return "N";
	}

}

public String Update(Employee string) {
	
	try {
		
		String sqlUpdatePickPack ="UPDATE `employees` SET first_name=?, last_name=?,email_address=? WHERE `first_name`=? ";

		int updateBillPickPack = jdbcTemplate.update(sqlUpdatePickPack,string.getFirstName(),string.getLastName(),string.getEmailId(),string.getFirstName());
		
		return "UPDATE";

	} catch (DataAccessException e1) {
		System.out.println(e1.getMessage());

		// TODO Auto-generated catch block
		//e1.printStackTrace();
		return "N";
	}

}
public String Delete(Employee string) {
	
	try {
		

		String sqlDeleteTrans = "DELETE  FROM `employees` WHERE first_name=?";
	int deletetran =  jdbcTemplate.update(sqlDeleteTrans,new Object[] {string.getFirstName()});
		
		return "DELETE";

	} catch (DataAccessException e1) {
		System.out.println(e1.getMessage());

		// TODO Auto-generated catch block
		//e1.printStackTrace();
		return "N";
	}

}

public String GetAll() {
	
	try {
		

		String sqlDeleteTrans = "SELECT * FROM `employees`";
		List<Map<String, Object>> allUserList = jdbcTemplate.queryForList(sqlDeleteTrans);
		System.out.println("all User List @@@@@"+allUserList);
		JSONObject json = new JSONObject();
	
		if(allUserList.size() >= 0) {
			
			try {
				json.put("UsersInformation", allUserList);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return json.toString();

	} catch (DataAccessException e1) {
		System.out.println(e1.getMessage());

		// TODO Auto-generated catch block
		//e1.printStackTrace();
		return "N";
	}

}

}
