package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.Branch;

public class LibrarianService {
	@Autowired
	BranchDAO brdao;
	@Autowired
	BookCopiesDAO bcdao;
	@Autowired
	BookDAO bdao;

	public Integer getBranchesCount() throws SQLException {
		return brdao.getBranchesCount();
	}

	public List<Branch> getAllBranches(Integer pageNo, String searchString)
			throws SQLException {
		return brdao.readAllBranches(pageNo, searchString);
	}

	public List<Book> getAllBooks(Integer pageNo, String searchString)
			throws SQLException {
		return bdao.readAllBooks(pageNo, searchString);
	}

	public List<Book> getAllBooksInBranch(Branch br) throws SQLException {
		return bdao.getBooksInBranch(br.getBranchId());
	}

	public List<BookCopies> getBookCopiesInBranch(Branch br)
			throws SQLException {
		return bcdao.getBookCopiesByBranchId(br.getBranchId());
	}

	public void addBookCopies(List<Book> books, Branch branch)
			throws SQLException {
		for (Book book : books) {
			BookCopies newCopy = new BookCopies();
			newCopy.setBook(book);
			newCopy.setBranch(branch);
			newCopy.setCopies(0);
			bcdao.addBookCopies(newCopy);
		}
	}

	public void addBookCopies(BookCopies bc) throws SQLException {
		bcdao.addBookCopies(bc);
	}

	public void updateBookCopies(BookCopies bc) throws SQLException {
		bcdao.updateBookCopies(bc);
	}

	public void updateBranch(Branch br) throws SQLException {
		brdao.updateBranch(br);
	}

	public Book getBookByPK(Integer bookId) throws SQLException {
		return bdao.getBookByPK(bookId);
	}

	public Branch getBranchByPK(Integer branchId) throws SQLException {
		return brdao.getBranchByPK(branchId);
	}

	public BookCopies getBookCopiesByPK(Integer branchId, Integer bookId)
			throws SQLException {
		return bcdao.getBookCopiesByPK(bookId, branchId);
	}
}
