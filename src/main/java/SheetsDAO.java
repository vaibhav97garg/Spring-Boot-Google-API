package spring.boot.rest.thymeleafdemo.DAO;

import java.io.IOException;

import spring.boot.rest.thymeleafdemo.entity.SheetsEntity;

public interface SheetsDAO {
	public void save(SheetsEntity se) throws IOException;
}
