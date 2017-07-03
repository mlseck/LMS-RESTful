package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.entity.Branch;

public class AdminBranchService {
	
	@Autowired
	BranchDAO brdao;
	
	
	public void saveBranch(Branch branch) throws SQLException{
		if (branch.getBranchId() != null){
				brdao.updateBranch(branch);
		} else{
				brdao.addBranch(branch);
		}
	}
	
	public void deleteBranch(Branch branch) throws SQLException{
		brdao.deleteBranch(branch);
	}
	
	public Integer getBranchesCount() throws SQLException {
		return brdao.getBranchesCount();
	}
	
	public List<Branch> getAllBranches(Integer pageNo, String searchString) throws SQLException{
		return brdao.readAllBranches(pageNo, searchString);
	}
	
	public Branch getBranchById(Integer branchId) throws SQLException{
		return brdao.getBranchByPK(branchId);
	}
	
}
