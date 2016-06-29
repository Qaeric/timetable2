package timetable.client.admin;

import java.util.Scanner;

import timetable.Subject;
import timetable.client.student.StudentModel;

public class AdminModel extends StudentModel
{
	
	public Subject convertToSubject(String line, int day, int hour)
	{
		Subject subject = new Subject();
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(", ");
		
		subject.setDay(day);
		subject.setHour(hour);
		subject.setName(scanner.next());
		subject.setRoom(scanner.nextInt());
		return subject;
	}
}
