package com.gcit.lms.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class AuthorAspect {
	@Before("execution(* com.gcit.lms.service.*.getAllAuthors(*,*))")
	public void accessAuthorsAdvice(){
		System.out.println("***Request made: Accessing All Authors***");
	}
}
