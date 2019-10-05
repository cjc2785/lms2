package com.ss.lms2.dao;

import java.sql.SQLException;
import java.util.List;

import com.ss.lms2.db.Db;
import com.ss.lms2.pojo.LibraryBranch;

public class LibraryBranchDao {

	private Db db;
	
	private static LibraryBranchDao dao = new LibraryBranchDao(Db.getConnection());
	
	public static LibraryBranchDao getDao() {
		return dao;
	}
	
	public LibraryBranchDao(Db db) {
		this.db = db;
	}
	
	
	public List<LibraryBranch> getAll() throws SQLException {
		String query = "SELECT * FROM library.tbl_library_branch";
		return db.withQuery(query, row -> {
			int branchId = row.getInt("branchId");
			String branchName = row.getString("branchName");
			String branchAddress = row.getString("branchAddress");
			return new LibraryBranch(branchId, branchName, branchAddress);
		});
	}


	public void update(LibraryBranch branch) throws SQLException {
		
		String query = "UPDATE library.tbl_library_branch SET " + 
				"branchName=?, " +
				"branchAddress=? " +
				"WHERE branchId=?";
		
		db.withUpdate(query, parameterList -> {
			parameterList.setString(1, branch.getBranchName());
			parameterList.setString(2, branch.getBranchAddress());
			parameterList.setInt(3, branch.getBranchId());
		});
	}
}
