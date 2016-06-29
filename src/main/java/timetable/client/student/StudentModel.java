package timetable.client.student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timetable.Subject;

public class StudentModel
{
	@SuppressWarnings("deprecation")
	public List<Date> ChangeDate(Date date, int direction)
	{
		List<Date> dates = new ArrayList<Date>();
		if (direction==0)
		{
			dates.add(new Date(date.getYear(), date.getMonth(), date.getDate()-7));
			dates.add(new Date(date.getYear(), date.getMonth(), date.getDate()-1));
		}
		if (direction==1)
		{
			dates.add(new Date(date.getYear(), date.getMonth(), date.getDate()+7));
			dates.add(new Date(date.getYear(), date.getMonth(), date.getDate()+13));
		}
		return dates;
	}
	
	@SuppressWarnings("deprecation")
	public int CalculateYear(Date date)
	{
		int year = date.getYear()+1900;
		return year;
	}
}
