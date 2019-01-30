package spring.boot.rest.thymeleafdemo.DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Repository;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import spring.boot.rest.thymeleafdemo.entity.SheetsEntity;

@Repository
public class SheetsDAOImpl implements SheetsDAO {
	
	private EntityManager entityManager;
	
	@Autowired
	public SheetsDAOImpl(EntityManager theEntityManager) {
		entityManager=theEntityManager;
	}
	private static final String APPLICATION_NAME ="Google Sheets API Java Quickstart";
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
	        System.getProperty("user.home"), ".credentials/2/sheets.googleapis.com-java-quickstart.json");
	private static FileDataStoreFactory DATA_STORE_FACTORY;
	private static final JsonFactory JSON_FACTORY =
			JacksonFactory.getDefaultInstance();
	private static HttpTransport HTTP_TRANSPORT;
	private static final List<String> SCOPES =
			Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);
			 
	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
			}
	}
	public static Credential authorize() throws IOException {
		// Load client secrets.
		FileInputStream in = new FileInputStream("client_secret.json"); //credentials file


		GoogleClientSecrets clientSecrets =
		GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		 
		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow =
		new GoogleAuthorizationCodeFlow.Builder(
		HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
		.setDataStoreFactory(DATA_STORE_FACTORY)
		.setAccessType("offline")
		.build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
		}
	public static Sheets getSheetsService() throws IOException {
		Credential credential = authorize();
		return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
		.setApplicationName(APPLICATION_NAME)
		.build();
		}

	@SuppressWarnings("unused")
	@Override
	public void save(SheetsEntity se) throws IOException {
		Sheets service = getSheetsService();
		String spreadsheetId = "ID";  //Enter Google Sheet ID
		String range = "Sheet1";
		List<List<Object>> arrdata = getdata(se);
		 ValueRange oRange = new ValueRange();
		 oRange.setValues(arrdata);
		  AppendValuesResponse oResp1 = service.spreadsheets().values().append(spreadsheetId, range, oRange)
					.setValueInputOption("RAW").execute();
		
	}
	
	public static List<List<Object>> getdata(SheetsEntity se) {
		List<List<Object>> finaldata = new ArrayList<List<Object>>();
		List<Object> data = new ArrayList<Object>();
		data.add(se.getID());
		data.add(se.getFirstName());
		data.add(se.getLastName());
		data.add(se.getEmail());
		finaldata.add(data);
		return finaldata;
	}

}
