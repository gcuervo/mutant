package com.colon.mutantproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MutantProjectApplication {

	public static void main(String[] args) {
		//ionContext ctx =
		SpringApplication.run(MutantProjectApplication.class, args);
/*
		DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		*/
	}
}
