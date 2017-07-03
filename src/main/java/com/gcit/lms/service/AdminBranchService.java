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

import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.entity.Branch;

@RestController
public class AdminBranchService {
	
	@Autowired
	BranchDAO brdao;
	
	@Transactional
	@RequestMapping(value = "/saveBranch", method = RequestMethod.POST, consumes="application/json")
	public void saveBranch(@RequestBody Branch branch) throws SQLException{
		if (branch.getBranchId() != null){
				brdao.updateBranch(branch);
		} else{
				brdao.addBranch(branch);
		}
	}
	
	@RequestMapping(value = "/deleteBranch", method = RequestMethod.POST, consumes="application/json")
	public void deleteBranch(@RequestBody Branch branch) throws SQLException{
		brdao.deleteBranch(branch);
	}
	
	@RequestMapping(value = "/getBranchesCount", method = RequestMethod.GET, produces="application/json")
	public Integer getBranchesCount() throws SQLException {
		return brdao.getBranchesCount();
	}
	
	@RequestMapping(value = "/getBranches/{pageNo}/{searchString}", method = RequestMethod.GET, produces="application/json")
	public List<Branch> getAllBranches(@PathVariable Integer pageNo, @PathVariable String searchString) throws SQLException{
		return brdao.readAllBranches(pageNo, searchString);
	}
	
	@RequestMapping(value = "/getBranhByPK/{branchId}", method = RequestMethod.GET, produces="application/json")
	public Branch getBranchById(@PathVariable Integer branchId) throws SQLException{
		return brdao.getBranchByPK(branchId);
	}
	
}
