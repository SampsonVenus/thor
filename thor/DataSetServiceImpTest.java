package com.smc.csp.dataset;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.jaxrs.utils.ExceptionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.smc.csp.audit.AuditDao;
import com.smc.csp.upload.api.Document;
import com.smc.csp.upload.api.DocumentDao;
import com.smc.csp.user.User;
import com.smc.csp.user.UserDao;
import com.smc.csp.user.UserService;
import com.smc.csp.utils.ErrorMessage;

public class DataSetServiceImpTest extends BaseDataSetResourceTest {
	@Autowired
	private DataSetDao dataSetDao; // Mock with spy in

	@Autowired
	private AuditDao auditDao; // Mock with spy

	@Autowired
	private UserDao userDao; // Mock with spy

	@Autowired
	private DocumentDao documentDao; // Mock with spy

	@Autowired
	DataSetService dataSetService;

	@Autowired
	UserService userService;

	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testSaveDataSet() throws Exception {

		DataSet inDataSet = new DataSet();
		inDataSet.setOwnerUserId(2000);
		inDataSet.setDataSetStatusId(0);
		inDataSet.setDataSetKey("df83c674-1ead-4472-9c08-999d816343FS");
		int id = 999;
		// doReturn(resulstIWant).when(myClassSpy).method1();
		doReturn(id).when(dataSetDao).saveDataSet(inDataSet);
		doNothing().when(auditDao).saveAuditById(2000, -1, "Created");
		int returnId = dataSetService.saveDataSet(inDataSet);

		assertEquals("doReturn() does not equal " + id, id, returnId);
		verify(dataSetDao).saveDataSet(inDataSet);
		verify(auditDao).saveAuditById(2000, -1, "Created");
	}

	@Test
	public void testfindDataSetIdByKey() throws Exception {

		DataSet inDataSet = new DataSet();
		inDataSet.setDataSetKey("df83c674-1ead-4472-9c08-999d816343FS");

		int id = 999;
		// doReturn(resulstIWant).when(myClassSpy).method1();
		doReturn(id).when(dataSetDao).findDataSetIdByKey(inDataSet.getDataSetKey());
		int returnId = dataSetService.findDataSetIdByKey(inDataSet.getDataSetKey());
		assertEquals("doReturn() does equal " + id, id, returnId);
	}

	@Test
	public void testfindErrorLogByDocumentId() throws Exception {

		List<ErrorMessage> errors = new ArrayList<>();
		errors.add(new ErrorMessage(1, "First error", 1));
		errors.add(new ErrorMessage(2, "Second", 2));
		int id = 999;
		// doReturn(resulstIWant).when(myClassSpy).method1();
		doReturn(errors).when(dataSetDao).findErrorLogByDocumentId(anyInt());

		List<ErrorMessage> returnErrors = dataSetService.findErrorLogByDocumentId(id);
		assertEquals("doReturn() does not is not " + errors, errors, returnErrors);
	}

	@Test
	public void testupdateDataSet() throws Exception {

		DataSet inDataSet = new DataSet();

		inDataSet.setOwnerUserId(2000);
		inDataSet.setDataSetStatusId(0);
		inDataSet.setDataSetKey("df83c674-1ead-4472-9c08-999d816343FS");

		User user = new User();
		user.setUserName("Franklyn");
		String userName = "user1";

		boolean success = true;
		// doReturn(resulstIWant).when(myClassSpy).method1();
		doReturn(success).when(dataSetDao).updateDataSet(inDataSet);
		doReturn(user).when(userDao).findLicensedUser(anyString());
		doReturn(inDataSet).when(dataSetDao).findDataSetByKey(anyString());

		User approvingUser = userService.findLicensedUser(userName);
		boolean returnSuccess = dataSetService.updateDataSet(inDataSet, approvingUser);

		assertEquals(success, returnSuccess);
	}

	@Test
	public void testfindDataSetStatus() throws Exception {

		DataSet inDataSet = new DataSet();
		inDataSet.setDataSetKey("df83c674-1ead-4472-9c08-999d816343FS");

		int id = 999;
		// doReturn(resulstIWant).when(myClassSpy).method1();
		doReturn(id).when(dataSetDao).findDataSetStatus(anyString());

		int returnId = dataSetService.findDataSetStatus(inDataSet.getDataSetKey());
		assertEquals("doReturn() does not equal " + id, 999, returnId);
	}

	@Test
	public void testisCompanyAuthorized() throws Exception {

		DataSet inDataSet = new DataSet();
		int id = 999;
		boolean success = true;

		inDataSet.setOwnerUserId(2000);
		inDataSet.setDataSetStatusId(0);
		inDataSet.setDataSetKey("df83c674-1ead-4472-9c08-999d816343FS");

		// doReturn(resulstIWant).when(myClassSpy).method1();
		doReturn(success).when(dataSetDao).isCompanyAuthorized(anyInt(), anyInt());
		boolean returnSuccess = dataSetService.isCompanyAuthorized(inDataSet, id);
		assertEquals(success, returnSuccess);
	}

	@Test
	public void testfindDataSetById() throws Exception {

		DataSet inDataSet = new DataSet();
		inDataSet.setDataSetKey("df83c674-1ead-4472-9c08-999d816343FS");

		int id = 999;
		// doReturn(resulstIWant).when(myClassSpy).method1();
		doReturn(inDataSet).when(dataSetDao).findDataSetById(anyInt());

		DataSet returnDataSet = dataSetService.findDataSetById(id);
		assertEquals("doReturn() id not  " + inDataSet, inDataSet, returnDataSet);
	}

	@Test
	public void testsaveDataSetDocument() throws Exception {

		DataSet inDataSet = new DataSet();
		inDataSet.setOwnerUserId(2000);
		inDataSet.setDataSetStatusId(0);
		inDataSet.setDataSetKey("df83c674-1ead-4472-9c08-999d816343FS");
		int id = 999;
		Document document = new Document();
		document.setDocumentId(10);

		doNothing().when(dataSetDao).saveDataSetDocument(anyInt(), anyInt());
		// doReturn(resulstIWant).when(myClassSpy).method1();
		doReturn(id).when(documentDao).saveDocument(document);

		int returnId = dataSetService.saveDocument(document, inDataSet);
		assertEquals("doReturn does not return " + id, id, returnId);
	}

	@Test
	public void testupdateFileStatus() throws Exception {
		doNothing().when(documentDao).updateDataSetDocumentStatus(anyInt(), anyInt());
		dataSetService.updateFileStatus(10, 20);
		try {
			verify(documentDao, times(1)).updateDataSetDocumentStatus(anyInt(), anyInt());
		} catch (Exception e) {
			ExceptionUtils.getStackTrace(e);
		}
	}

	@Test
	public void testsaveAuthorization() throws Exception {

		DataSet inDataSet = new DataSet();
		inDataSet.setOwnerUserId(2000);
		inDataSet.setDataSetStatusId(0);
		inDataSet.setDataSetKey("df83c674-1ead-4472-9c08-999d816343FS");

		User authorizingUser = new User();
		authorizingUser.setUserName("manual.contract.user1@smc3.com");

		User userToBeAuthorized = new User();
		userToBeAuthorized.setUserName("manual.contract.consumer1@smc3.com");

		AuthorizeRequest request = new AuthorizeRequest();

		doNothing().when(dataSetDao).saveAuthorization(anyInt(), anyInt(), anyInt(), any(), any());
		doNothing().when(auditDao).saveAuditById(anyInt(), anyInt(), anyString());

		try {
			dataSetService.authorizeDataSet(authorizingUser, userToBeAuthorized, inDataSet, request);
			verify(dataSetDao, times(1)).saveAuthorization(anyInt(), anyInt(), anyInt(), any(), any());
			verify(auditDao, times(1)).saveAuditById(anyInt(), anyInt(), anyString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSaveErrorLog() throws Exception {
		Document document = new Document();

		List<ErrorMessage> errors = new ArrayList<>();
		errors.add(new ErrorMessage(1, "Email1", 1));
		errors.add(new ErrorMessage(2, "Email2", 2));

		boolean isTrue = true;
		doReturn(isTrue).when(documentDao).saveDataDocument(anyObject());
		doReturn(isTrue).when(documentDao).saveErrorLog(anyObject());
		
		try {
			dataSetService.saveErrorLog(document, errors);
			verify(documentDao, times(1)).saveDataDocument(anyObject());
			verify(documentDao, times(1)).saveErrorLog(anyObject());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@After
	public void destroy() throws Exception {
		reset(dataSetDao);
		reset(documentDao);
		reset(auditDao);
		reset(userDao);
	}
	
	
}
