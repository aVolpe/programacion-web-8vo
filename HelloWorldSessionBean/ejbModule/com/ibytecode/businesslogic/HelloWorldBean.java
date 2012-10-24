package com.ibytecode.businesslogic;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ibytecode.business.HelloWorld;

/**
 * Session Bean implementation class HelloWorldBean
 */
@Stateless
@LocalBean
public class HelloWorldBean implements HelloWorld {

	/**
	 * Default constructor.
	 */
	public HelloWorldBean() {
		// TODO Auto-generated constructor stub
	}

	public String sayHello() {
		return "Hello World !!!";
	}
}
