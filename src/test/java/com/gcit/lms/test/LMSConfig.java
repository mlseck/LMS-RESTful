package com.gcit.lms.test;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.gcit.lms.dao.*;
import com.gcit.lms.service.*;

@Configuration
public class LMSConfig {
	
	public String driver = "com.mysql.jdbc.Driver";
	public String url = "jdbc:mysql://localhost:3307/library";
	public String username = "root";
	public String password = "admin";
	
	@Bean
	public BasicDataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		
		return ds;
	}
	
	@Bean(name ="MySQL")
	public JdbcTemplate template(){
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
	public AuthorDAO adao(){
		return new AuthorDAO();
	}
	
	@Bean
	public BookDAO bdao(){
		return new BookDAO();
	}
	
	@Bean
	public GenreDAO gdao(){
		return new GenreDAO();
	}
	
	@Bean
	public PublisherDAO pdao(){
		return new PublisherDAO();
	}
	
	@Bean
	public BranchDAO brdao(){
		return new BranchDAO();
	}
	
	@Bean
	public BorrowerDAO bodao(){
		return new BorrowerDAO();
	}
	
	@Bean
	public BookCopiesDAO bcdao(){
		return new BookCopiesDAO();
	}
	
	@Bean
	public BookLoansDAO bldao(){
		return new BookLoansDAO();
	}
	
	@Bean
	public AuthorController authorController(){
		return new AuthorController();
	}
	
	@Bean
	public BranchController branchController(){
		return new BranchController();
	}
	
	@Bean
	public BorrowerController BorrowerController(){
		return new BorrowerController();
	}
	
	@Bean
	public LoanController loanController(){
		return new LoanController();
	}
	
	@Bean
	public BookController bookController(){
		return new BookController();
	}
	
	@Bean
	public PublisherController publisherController(){
		return new PublisherController();
	}
	
	@Bean
	public PlatformTransactionManager txManager(){
		return new DataSourceTransactionManager(dataSource());
	}

}
