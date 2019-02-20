package com.xq.fstore.bas.daoImp;

import com.xq.fstore.bas.dao.IGradeDao;
import com.xq.fstore.bas.entity.Grade;

public class GradeDaoImp implements IGradeDao {

	@Override
	public void insert(Grade grade) {
		// TODO Auto-generated method stub
		System.out.println("insert ...");
	}

	@Override
	public void update(Grade grade) {
		// TODO Auto-generated method stub
		System.out.println("update ...");
		throw new RuntimeException("update error");
	}

}
