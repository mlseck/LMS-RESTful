package com.gcit.lms.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;

@RestController
public class AdminLoanService {
	@Autowired
	BookLoansDAO bldao;
	
	
	public List<BookLoans> getLoansFromBorrower(Borrower b) throws SQLException{
		return bldao.getBookLoansByCardNo(b.getCardNo());
	}
	
	public BookLoans getLoanByPK(Integer cardNo, Integer bookId, Integer branchId, String dateOut) throws SQLException{
		return bldao.getBookLoansByPK(bookId, branchId, cardNo, dateOut);
	}
	
	public void overwriteLoan(BookLoans l) throws SQLException{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
		String date = l.getDueDate();
		LocalDateTime dueDate = LocalDateTime.parse(date, formatter);
		l.setDueDate(dueDate.plusDays(30).toString());
		bldao.updateBookLoans(l);
	}
	
	public Integer getLoansCount() throws SQLException {
		return bldao.getBookLoansCount();
	}
	
	public List<BookLoans> getAllLoans(Integer pageNo, String searchString) throws SQLException{
		return bldao.readAllBookLoans(pageNo, searchString);
	}
}
