package com.example.priteshapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.priteshapi.model.Employee;
import com.example.priteshapi.repositry.DemoServicerepositry;





@Controller
public class DemoControler {

	@Autowired
	DemoServicerepositry b2bClientGraphService;

	@RequestMapping(value="/Save/{firstName}/{lastName}/{emailId}",method=RequestMethod.POST,headers="Accept=application/json")
	@ResponseBody
	public String Save(@ModelAttribute  Employee id )
	{
		
		System.out.println("allOrderStatusGraph=---"+id.getFirstName());
		return b2bClientGraphService.Save(id);
	}
	
	@RequestMapping(value="/GetById/{id}",method=RequestMethod.GET,headers="Accept=application/json")
	@ResponseBody
	public String GetById(@ModelAttribute  Employee id )
	{
		
		System.out.println("allOrderStatusGraph=---"+id.getFirstName());
		return b2bClientGraphService.GetById(id.getId());
	}
	
	@RequestMapping(value="/Update/{firstName}/{lastName}/{emailId}",method=RequestMethod.PUT,headers="Accept=application/json")
	@ResponseBody
	public String Update(@ModelAttribute  Employee id )
	{
		
		System.out.println("allOrderStatusGraph=---"+id.getFirstName());
		return b2bClientGraphService.Update(id);
	}
	
	@RequestMapping(value="/Delete/{firstName}",method=RequestMethod.DELETE,headers="Accept=application/json")
	@ResponseBody
	public String Delete(@ModelAttribute  Employee id )
	{
		
		System.out.println("allOrderStatusGraph=---"+id.getFirstName());
		return b2bClientGraphService.Delete(id);
	}
	@RequestMapping(value="/GetAll",method=RequestMethod.GET,headers="Accept=application/json")
	@ResponseBody
	public String GetAll()
	{		
		return b2bClientGraphService.GetAll();
	}
	

}
