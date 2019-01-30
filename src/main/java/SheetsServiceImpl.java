package spring.boot.rest.thymeleafdemo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.boot.rest.thymeleafdemo.DAO.SheetsDAO;
import spring.boot.rest.thymeleafdemo.entity.SheetsEntity;

@Service
public class SheetsServiceImpl implements SheetsService {

	private SheetsDAO sheetsDAO;
	
	@Autowired
	public SheetsServiceImpl(SheetsDAO theSheetsDAO) {
		sheetsDAO=theSheetsDAO;
	}
	
	@Override
	@Transactional  
	public void save(SheetsEntity se) throws IOException {
		sheetsDAO.save(se);

	}

}
