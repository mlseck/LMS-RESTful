package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
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
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;

@RestController
public class BookController {

	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	GenreDAO gdao;
	
	@Transactional
	@RequestMapping(value = "/saveBook", method = RequestMethod.POST, consumes="application/json")
	public void saveBook(@RequestBody Book book) throws SQLException{
		if(book.getBookId() != null){
			bdao.updateBook(book);
		} else{
			bdao.addBook(book);
		}
	}
	
	@RequestMapping(value = "/saveBookWithId", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	public Integer saveBookWithId(@RequestBody Book book) throws SQLException{
		return bdao.addBookWithId(book);
	}
	
	@RequestMapping(value = "/deleteBook", method = RequestMethod.POST, consumes="application/json")
	public void deleteBook(@RequestBody Book book) throws SQLException{
		bdao.deleteBook(book);
	}
	
	@RequestMapping(value = "/getBookByPK/{bookId}", method = RequestMethod.GET, produces="application/json")
	public Book getBookByPK(@PathVariable Integer bookId) throws SQLException {
		Book book = bdao.getBookByPK(bookId);
		book.setPublisher(pdao.getBookPublisher(bookId));
		book.setAuthors(adao.getAuthorsWithBook(bookId));
		book.setGenres(gdao.getGenresWithBook(bookId));
		return book;
	}
	
	@RequestMapping(value = "/getBooksCount", method = RequestMethod.GET, produces="application/json")
	public Integer getBooksCount() throws SQLException {
		return bdao.getBooksCount();
	}
	
	@RequestMapping(value = "/getBooks/{pageNo}/{searchString}", method = RequestMethod.GET, produces="application/json")
	public List<Book> getAllBooks(@PathVariable Integer pageNo, @PathVariable String searchString) throws SQLException{
		List<Book> books = bdao.readAllBooks(pageNo, searchString);
		for (Book b:books){
			b.setAuthors(adao.getAuthorsWithBook(b.getBookId()));
			if (pdao.getBookPublisher(b.getBookId()) != null){
				b.setPublisher(pdao.getBookPublisher(b.getBookId()));
			}
			b.setGenres(gdao.getGenresWithBook(b.getBookId()));
		}
		return books;
	}
	
	public void setBookAuthor(Book book, Author author) throws SQLException{
		bdao.addAuthorToBook(book, author);
	}
	
	public void setBookGenre(Book book, Genre genre) throws SQLException{
		bdao.addGenreToBook(book, genre);
	}
	
	public void removeBookAuthor(Book book, Author author) throws SQLException{
		bdao.deleteAuthorFromBook(book, author);
	}
	
	public void removeBookGenre(Book book, Genre genre) throws SQLException{
		bdao.deleteGenreFromBook(book, genre);
	}
	
	public void editBookAuthors(Book book, ArrayList<Author> authors) throws SQLException{
		List<Author> removeAuthors = new ArrayList<>(book.getAuthors());
		removeAuthors.removeAll(authors);

		List<Author> newAuthors = authors;
		newAuthors.removeAll(book.getAuthors());
		
		for(Author removeAuthor:removeAuthors){
			bdao.deleteAuthorFromBook(book, removeAuthor);
		}
		for (Author newAuthor:newAuthors){
			bdao.addAuthorToBook(book, newAuthor);
		}	
	}

	public void editBookGenres(Book book, ArrayList<Genre> genres) throws SQLException {
		List<Genre> removeGenres = new ArrayList<>(book.getGenres());
		removeGenres.removeAll(genres);
		
		ArrayList<Genre> newGenres = genres;
		newGenres.removeAll(book.getGenres());
		
		for(Genre removeGenre:removeGenres){
			bdao.deleteGenreFromBook(book, removeGenre);
		}
		for (Genre newGenre:newGenres){
			bdao.addGenreToBook(book, newGenre);
		}
		
	}
}
