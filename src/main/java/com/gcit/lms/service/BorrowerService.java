package com.gcit.lms.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;

@RestController
public class BorrowerService {
	@Autowired
	BorrowerDAO bodao;
	@Autowired
	BranchDAO brdao;
	@Autowired
	BookCopiesDAO bcdao;
	@Autowired
	BookLoansDAO bldao;
	
	public Borrower getBorrower(int cardNo) throws SQLException{
		return bodao.getBorrowerByPK(cardNo);
	}
	
	public Integer getBranchesCount() throws SQLException {
		return brdao.getBranchesCount();
	}
	
	public List<Branch> getAllBranches(Integer pageNo, String searchString) throws SQLException{
		return brdao.readAllBranches(pageNo, searchString);
	}
	
	public List<BookLoans> getAllBorrowerLoans(Borrower br) throws SQLException{
		return bldao.getBookLoansByCardNo(br.getCardNo());
	}
	
	public void checkOutBook(BookCopies bc, Borrower br) throws SQLException{
		BookLoans bl = new BookLoans();
		bl.setBook(bc.getBook());
		bl.setBranch(bc.getBranch());
		bl.setBorrower(br);
		bl.setDateOut(LocalDateTime.now().toString());
		bl.setDueDate(LocalDateTime.now().plusDays(30).toString());
		
		bldao.addBookLoans(bl);
	}
	
	public void returnBook(BookLoans bl) throws SQLException{
		
		BookCopies bc = new BookCopies();
		
		bl = bldao.getBookLoansByPK(bl.getBook().getBookId(), bl.getBranch().getBranchId(), bl.getBorrower().getCardNo(), bl.getDateOut());
		bl.setDateIn(LocalDateTime.now().toString());
		bldao.updateBookLoans(bl);
		bc = bcdao.getBookCopiesByPK(bl.getBook().getBookId(), bl.getBranch().getBranchId());
		bc.setCopies(bc.getCopies()+1);
		bcdao.updateBookCopies(bc);
	}
	
	public List<BookCopies> getBookCopiesInBranch(Branch br) throws SQLException{
		return bcdao.getBookCopiesByBranchId(br.getBranchId());
	}
}
