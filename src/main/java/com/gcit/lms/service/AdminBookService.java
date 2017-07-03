package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.entity.Book;

public class AdminBookService {
	
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	GenreDAO gdao;
	
	
	public void saveAuthor(Author author) throws SQLException{	
		if (author.getAuthorId() != null){
			adao.updateAuthor(author);
		} else{
			adao.addAuthor(author);
		}
	}
	
	public void saveBook(Book book) throws SQLException{
		if(book.getBookId() != null){
			bdao.updateBook(book);
		} else{
			bdao.addBook(book);
		}
	}
	
	public Integer saveBookWithId(Book book) throws SQLException{
		return bdao.addBookWithId(book);
	}
	
	public void deleteAuthor(Author author) throws SQLException{
		adao.deleteAuthor(author);
	}
	
	public void deleteBook(Book book) throws SQLException{
		bdao.deleteBook(book);
	}
	
	public Book getBookByPK(Integer bookId) throws SQLException {
		Book book = bdao.getBookByPK(bookId);
		book.setPublisher(pdao.getBookPublisher(bookId));
		book.setAuthors(adao.getAuthorsWithBook(bookId));
		book.setGenres(gdao.getGenresWithBook(bookId));
		return book;
	}
	
	public Author getAuthorByPK(Integer authorId) throws SQLException {
		Author author = adao.getAuthorByPK(authorId);
		author.setBooks(bdao.getBooksWithAuthor(authorId));
		return author;
	}
	
	public Publisher getPublisherByPK(Integer pubId) throws SQLException {
		Publisher publisher = pdao.getPublisherByPK(pubId);
		publisher.setBooks(bdao.getBooksWithPublisher(pubId));
		return publisher;
	}
	
	public Genre getGenreByPK(Integer genreId) throws SQLException {
		Genre genre = gdao.getGenreByPK(genreId);
		genre.setBooks(bdao.getBooksWithGenre(genreId));
		return genre;
	}
	
	public Integer getAuthorsCount() throws SQLException {
		return adao.getAuthorsCount();
	}
	
	public Integer getBooksCount() throws SQLException {
		return bdao.getBooksCount();
	}
	
	public List<Author> getAllAuthors(Integer pageNo, String searchString) throws SQLException{
		List<Author> authors = adao.readAllAuthors(pageNo, searchString);
		for(Author a:authors){
			a.setBooks(bdao.getBooksWithAuthor(a.getAuthorId()));
		}
		return authors;
	}
	
	public List<Book> getAllBooks(Integer pageNo, String searchString) throws SQLException{
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
	
	public void savePublisher(Publisher publisher) throws SQLException{
		if (publisher.getPublisherId() != null){
				pdao.updatePublisher(publisher);
		}else{
				pdao.addPublisher(publisher);
		}
	}
	
	
	public void deletePublisher(Publisher publisher) throws SQLException{
		pdao.deletePublisher(publisher);
	}
	
	public Integer getPublishersCount() throws SQLException {
		return pdao.getPublishersCount();
	}
	
	public List<Publisher> getAllPublishers(Integer pageNo, String searchString) throws SQLException{
		List<Publisher> publishers = pdao.readAllPublishers(pageNo, searchString);
		for (Publisher p:publishers){
			p.setBooks(bdao.getBooksWithPublisher(p.getPublisherId()));
		}
		return publishers;
	}
	
	public void saveGenre(Genre genre) throws SQLException{
		if (genre.getGenreId() != null){
				gdao.updateGenre(genre);
		} else{
				gdao.addGenre(genre);
		}
	}
	
	public void deleteGenre(Genre genre) throws SQLException{
		gdao.deleteGenre(genre);
	}
	
	public Integer getGenresCount() throws SQLException {
		return gdao.getGenresCount();
	}
	
	public List<Genre> getAllGenres(Integer pageNo, String searchString) throws SQLException{
		List<Genre> genres = gdao.readAllGenres(pageNo, searchString);
		for (Genre g:genres){
			g.setBooks(bdao.getBooksWithGenre(g.getGenreId()));
		}
		return genres;
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
