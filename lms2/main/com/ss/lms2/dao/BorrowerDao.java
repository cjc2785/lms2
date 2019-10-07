package com.ss.lms2.dao;

import java.sql.SQLException;
import java.util.Optional;
import java.util.List;

import com.ss.lms2.db.Db;
import com.ss.lms2.db.TableRow;
import com.ss.lms2.pojo.*;

public class BorrowerDao {

	private Db db;
	
	private static BorrowerDao dao = new BorrowerDao(Db.getConnection());
	
	public static BorrowerDao getDao() {
		return dao;
	}
	
	public BorrowerDao(Db db) {
		this.db = db;
	}

	public Optional<Borrower> get(int cardNo) throws SQLException {
		
		String query = "SELECT * FROM library.tbl_borrower bor " + 
				"WHERE bor.cardNo=?";
		
		List<Borrower> borrowers = db.withQuery(
				query, 
				this::rowToBorrower, 
				parameterList -> {
					parameterList.setInt(1, cardNo);
				});
		
		return borrowers.isEmpty() ?
				Optional.empty() : 
				Optional.of(borrowers.get(0));
	}
	
	public List<Borrower> getAll() throws SQLException{
		String query = "SELECT * FROM library.tbl_borrower ";
				
			return db.withQuery(query, this::rowToBorrower);
	}
	
	public void delete(Borrower borrower) throws SQLException {
		
		String query = "DELETE FROM library.tbl_borrower " + 
				"WHERE cardNo=?";
		
		db.withUpdate(query, parameterList -> {
			parameterList.setInt(1, borrower.getCardNo());
		});
	}
	
	public void insert(Borrower borrower) throws SQLException {
		
		String query = "INSERT INTO library.tbl_borrower VALUES " + 
				"(?,?,?,?) ";
		
		db.withUpdate(query, parameterList -> {
			parameterList.setInt(1, borrower.getCardNo());
			parameterList.setString(2, borrower.getName());
			parameterList.setString(3, borrower.getAddress());
			parameterList.setString(4, borrower.getPhone());
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
	
	private Borrower rowToBorrower(TableRow row) {
		
		int cardNo = row.getInt("cardNo");
		String name = row.getString("name");
		String address = row.getString("address");
		String phone = row.getString("phone");
		
		return new Borrower(cardNo, name, address, phone);
	}
}
