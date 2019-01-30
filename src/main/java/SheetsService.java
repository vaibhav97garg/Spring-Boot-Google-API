package spring.boot.rest.thymeleafdemo.service;

import java.io.IOException;

import spring.boot.rest.thymeleafdemo.entity.SheetsEntity;

public interface SheetsService {
	public void save(SheetsEntity se) throws IOException;

}
