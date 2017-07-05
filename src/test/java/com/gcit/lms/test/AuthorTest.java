package com.gcit.lms.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.service.AuthorController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=LMSConfig.class, loader=AnnotationConfigContextLoader.class)
public class AuthorTest {
	@Autowired
	AuthorController authorController;
	@Autowired
	AuthorDAO adao;
	
	@Test
	public void testInitialization(){
		assertNotNull(authorController);
		assertNotNull(adao);
	}
}
