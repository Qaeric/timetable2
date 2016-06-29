package timetable;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import timetable.client.admin.AdminController;
import timetable.client.admin.AdminModel;
import timetable.client.admin.AdminView;

public class AdminTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}
	
	private AdminView adminView = new AdminView();
	private AdminModel adminModel = new AdminModel();
	private AdminController adminController = new AdminController(adminView, adminModel);
	
	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void shouldConvertToSubjectHavingCellFieldHourAndDay()
	{
		//given
		String line = "fizyka, 12";
		int day = 1;
		int hour = 1;
		
		//when
		Subject result = new Subject(1, 1, "fizyka", 12);
		
		//then
		Subject subject = adminModel.convertToSubject(line, day, hour);
		assertEquals(result.getDay(), subject.getDay());
		assertEquals(result.getHour(), subject.getHour());
		assertEquals(result.getName(), subject.getName());
		assertEquals(result.getRoom(), subject.getRoom());
	}

}
