package spring.boot.rest.thymeleafdemo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.boot.rest.thymeleafdemo.entity.SheetsEntity;
import spring.boot.rest.thymeleafdemo.service.SheetsService;

@Controller
@RequestMapping("/api")
public class DemoController {
	
	@Autowired
	private SheetsService sheetsService;
	
	@GetMapping("/hello")
	public String anything() {
		return "firstpage";
	}
	
	@GetMapping("/sheets")
	public String sayHello(Model theModel) {
		SheetsEntity se = new SheetsEntity();
		theModel.addAttribute("sheets" ,se);
		return "student";
	}
	
	
	@PostMapping("/save")
	public String saveData(@ModelAttribute("sheets") SheetsEntity se) throws IOException {
		sheetsService.save(se);
		return null;
		
	}
	
	
}
