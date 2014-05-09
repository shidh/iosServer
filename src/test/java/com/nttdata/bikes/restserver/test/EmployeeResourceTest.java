package com.nttdata.bikes.restserver.test;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.nttdata.bikes.restserver.EmployeeResource;


public class EmployeeResourceTest {
//
//	private static String getAllEmployeesString = "{\"rawType\":\"java.util.ArrayList\",\"type\":{\"actualTypeArguments\":[\"com.nttdata.bikes.entities.Employee\"],\"rawType\":\"java.util.List\"},\"entity\":[{\"idEmployee\":0,\"name\":\"Bob\",\"surname\":\"Brown\",\"username\":\"user1\",\"password\":\"pass1\",\"mobileNo\":\"123\",\"travelTime\":0.0,\"totalKm\":0.0},{\"idEmployee\":0,\"name\":\"Alice\",\"surname\":\"Alles\",\"username\":\"user2\",\"password\":\"pass2\",\"mobileNo\":\"12331\",\"travelTime\":0.1,\"totalKm\":0.1}]}";
//	private static String getEmployeeWithUnAndPass = "{\"rawType\":\"com.nttdata.bikes.entities.Employee\",\"type\":\"com.nttdata.bikes.entities.Employee\",\"entity\":{\"idEmployee\":0,\"name\":\"Bob\",\"surname\":\"Brown\",\"username\":\"user1\",\"password\":\"pass1\",\"mobileNo\":\"123\",\"travelTime\":0.0,\"totalKm\":0.0}}";
//	
//	@Mock
//	private EmployeeResource employeeResource;
//	
//	@Before
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//		TestObjects.InitializeObjects();
//	}
//	
//	@Test
//	public void testgetAllEmployees() {
//		when(employeeResource.getAllEmployees()).thenCallRealMethod();
//		when(employeeResource.getAllEmployeesFromDatabase()).thenReturn(TestObjects.listAllEmployees);
//		Response answer = employeeResource.getAllEmployees();
//		String json = TestObjects.ResponseToJson(answer);
//		assertEquals(json,getAllEmployeesString);
//		verify(employeeResource).getAllEmployeesFromDatabase();
//	}
//	
//	@Test
//	public void testgetEmployeewithUsernameandPassword() {
//		when(employeeResource.getEmployeewithUsernameandPassword(anyString(),anyString())).thenCallRealMethod();
//		when(employeeResource.getOneEmployeewithUsernameandPassword(anyString(),anyString())).thenCallRealMethod();
//		when(employeeResource.getAllEmployeesFromDatabase()).thenReturn(TestObjects.listAllEmployees);
//		Response answer = employeeResource.getEmployeewithUsernameandPassword(TestObjects.listAllEmployees.get(0).getUsername(),TestObjects.listAllEmployees.get(0).getPassword());
//		String json = TestObjects.ResponseToJson(answer);
//		assertEquals(json, getEmployeeWithUnAndPass);
//		verify(employeeResource).getAllEmployeesFromDatabase();
//	}

}
