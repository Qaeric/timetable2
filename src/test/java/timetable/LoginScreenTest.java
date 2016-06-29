package timetable;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import timetable.client.student.StudentView;

public class LoginScreenTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}
	
	
	
	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void shouldReturnNotEmptyLoginScreen()
	{
		//given
		LoginScreen loginScreen = new LoginScreen();
		
		//when
		
		//then
		assertNotNull(loginScreen);
	}
	
	@Test
	public void shouldReturnNotEmptyStudentController()
	{
		//given
		LoginScreen loginScreen = new LoginScreen();
		
		//when
		loginScreen.userButton.doClick();
		
		//then
		assertNotNull(loginScreen.studentController);
	}
}
