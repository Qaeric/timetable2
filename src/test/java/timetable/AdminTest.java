package timetable;

import static org.junit.Assert.*;

import java.awt.event.MouseListener;
import java.nio.file.FileSystems;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import timetable.client.admin.AdminController;
import timetable.client.admin.AdminModel;
import timetable.client.admin.AdminView;
import timetable.client.admin.Entry;
import timetable.server.Server;

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
	
	@Test
	public void shouldSetFieldsInEntry()
	{
		//given
		Entry entry = new Entry();
		Subject subject = new Subject(0, 0, "fizyka", 12);
		
		//when
		String resultName = "fizyka";
		int resultRoom = 12;
		
		//then
		entry.modify(subject);
		String name = entry.subjectField.getText();
		int room = Integer.parseInt(entry.classField.getText());
		assertEquals(resultName, name);
		assertEquals(resultRoom, room);
	}

}
