package com.gcit.lms.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.service.AuthorController;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes=LMSConfig.class, loader=AnnotationConfigContextLoader.class)
public class AuthorTest {
	
	@InjectMocks
	AuthorController authorController;
	@Mock
	AuthorDAO adao;
	
	@Before
	@Test
	public void testMockCreation(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testInitialization(){
		assertNotNull(authorController);
		assertNotNull(adao);
	}
	
	@Test
	public void testCreateAuthor() throws SQLException{
		Author author = new Author();
		when(adao.addAuthorWithId(author)).thenReturn(new Integer(1));
		author.setAuthorName("New author");
		Integer authorId = authorController.saveAuthorWithId(author);
		assertNotNull(authorId);
		assertEquals(authorId.longValue(), 1);
	}
	
	@Test
	public void listAuthors() throws SQLException{
		List<Author> authors = authorController.getAllAuthors(null, null);
		assertNotNull(authors);
	}
}
