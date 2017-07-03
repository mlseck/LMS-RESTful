package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.entity.Borrower;

public class AdminBorrowerService {
	
	@Autowired
	BorrowerDAO bodao;
	
	public void saveBranch(Borrower borrower) throws SQLException{
		if (borrower.getCardNo() != null){
				bodao.updateBorrower(borrower);
		} else{
				bodao.addBorrower(borrower);
		}
	}
	
	public void deleteBorrower(Borrower borrower) throws SQLException{
		bodao.deleteBorrower(borrower);
	}
	
	public Integer getBorrowersCount() throws SQLException {
		return bodao.getBorrowersCount();
	}
	
	public List<Borrower> getAllBorrowers(Integer pageNo, String searchString) throws SQLException{
		return bodao.readAllBorrowers(pageNo, searchString);
	}
	
}
