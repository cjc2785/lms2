package com.ss.lms2.service;

import java.sql.SQLException;
import java.util.Optional;

import com.ss.lms2.dao.*;
import com.ss.lms2.pojo.*;

public class BorrowerService {

	private BorrowerDao borrowerDao;
	
	private static BorrowerService service = new BorrowerService(
			BorrowerDao.getDao()
			);
	
	public static BorrowerService getService() {
		return service;
	}
	
	private BorrowerService(BorrowerDao borrowerDao) {
		this.borrowerDao = borrowerDao;
	}

	public Optional<Borrower> get(int cardNo) throws SQLException {
		return borrowerDao.get(cardNo);
	}
}
