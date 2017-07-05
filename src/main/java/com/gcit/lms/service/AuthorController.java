package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.entity.Author;

@RestController
public class AuthorController {
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bdao;
	
	@Transactional
	@RequestMapping(value = "/author/save", method = RequestMethod.POST, consumes="application/json")
	public void saveAuthor(@RequestBody Author author) throws SQLException{	
		if(author.getAuthorId() != null){
			adao.updateAuthor(author);
		} else{
			adao.addAuthor(author);
		}
	}
	
	@RequestMapping(value = "/author/delete", method = RequestMethod.POST, consumes="application/json")
	public void deleteAuthor(@RequestBody Author author) throws SQLException{
		adao.deleteAuthor(author);
	}
	
	@RequestMapping(value = "/author/getAuthor/{authorId}", method = RequestMethod.GET, produces="application/json")
	public Author getAuthorByPK(@PathVariable Integer authorId) throws SQLException {
		Author author = adao.getAuthorByPK(authorId);
		author.setBooks(bdao.getBooksWithAuthor(authorId));
		return author;
	}
	
	@RequestMapping(value = "/author/getCount", method = RequestMethod.GET, produces="application/json")
	public Integer getAuthorsCount() throws SQLException {
		return adao.getAuthorsCount();
	}
	
	@RequestMapping(value = "author/getAuthors/{pageNo}/{searchString}", method = RequestMethod.GET, produces="application/json")
	public List<Author> getAllAuthors(@PathVariable Integer pageNo, @PathVariable String searchString) throws SQLException{
		List<Author> authors = adao.readAllAuthors(pageNo, searchString);
		for(Author a:authors){
			a.setBooks(bdao.getBooksWithAuthor(a.getAuthorId()));
		}
		return authors;
	}
	
}
