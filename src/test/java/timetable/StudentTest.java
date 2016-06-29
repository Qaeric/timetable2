package timetable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import timetable.client.student.StudentController;
import timetable.client.student.StudentModel;
import timetable.client.student.StudentView;

public class StudentTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}
	
	private StudentView studentView = new StudentView();
	private StudentModel studentModel = new StudentModel();
	private StudentController studentController = new StudentController(studentView, studentModel);
	
	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void shouldReturnPreviousAndNextDate()
	{
		//given
		Date date = new Date(116, 7, 27);
		
		//when
		List<Date> dates1 = new ArrayList<Date>();
		dates1.add(new Date(date.getYear(), date.getMonth(), date.getDate()-7));
		dates1.add(new Date(date.getYear(), date.getMonth(), date.getDate()-1));
		
		List<Date> dates2 = new ArrayList<Date>();
		dates2.add(new Date(date.getYear(), date.getMonth(), date.getDate()+7));
		dates2.add(new Date(date.getYear(), date.getMonth(), date.getDate()+13));
		
		//then
		assertEquals(dates1, studentModel.ChangeDate(date, 0) );
		assertEquals(dates2, studentModel.ChangeDate(date, 1) );
	}
	
	@Test
	public void shouldReturnYearOfDate()
	{
		//given
		Date date = new Date(116, 7, 27);
		
		//when
		int year = 2016;
		
		//then
		assertEquals(year, studentModel.CalculateYear(date) );
	}
	
	@Test
	public void shouldSetDateindateLabel()
	{
		//given
		Date currentDate = new Date(116, 5, 20);
		Date nextDate = new Date(116, 5, 26);
		int year1 = 2016;
		int year2 = 2016;
		studentView.setDate(currentDate, year1, nextDate, year2);
		
		//when
		String date ="20-6-2016 - 26-6-2016";
		
		//then
		assertEquals(date, studentView.date.getText());
		
	}
	
//	public void shouldSetDateToPrevious()
//	{
//		//given
//		StudentController.PrevListener prevListener = Mockito.mock(StudentController.PrevListener.class);
//		ActionEvent mockEvent = mock(ActionEvent.class);
//		
//		//when
//		prevListener.actionPerformed(mockEvent);
//		
//		//then
//		
//	}
}
