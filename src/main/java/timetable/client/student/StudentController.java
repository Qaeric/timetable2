package timetable.client.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timetable.Subject;
import timetable.server.Server;

@SuppressWarnings("unused")
public class StudentController
{
	private StudentView studentView;
	private StudentModel studentModel;
	
	public StudentController(StudentView studentView, StudentModel studentModel)
	{
		this.studentView = studentView;
		this.studentModel = studentModel;
		
		this.studentView.addPrevListener(new PrevListener());
		this.studentView.addNextListener(new NextListener());
		
		try
		{
			setTimetable();
		} catch (ClassNotFoundException | IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public class PrevListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Date date = studentView.getCurrentDate();
			List<Date> dates = new ArrayList<Date>();
			dates = studentModel.ChangeDate(date, 0);
			int year1 = studentModel.CalculateYear(dates.get(0));
			int year2 = studentModel.CalculateYear(dates.get(1));
			studentView.setDate(dates.get(0), year1, dates.get(1), year2);
			try
			{
				setTimetable();
			} catch (ClassNotFoundException | IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}
	
	public class NextListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Date date = studentView.getCurrentDate();
			List<Date> dates = new ArrayList<Date>();
			dates = studentModel.ChangeDate(date, 1);
			int year1 = studentModel.CalculateYear(dates.get(0));
			int year2 = studentModel.CalculateYear(dates.get(1));
			studentView.setDate(dates.get(0), year1, dates.get(1), year2);
			try
			{
				setTimetable();
			} catch (ClassNotFoundException | IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}
	
	public void setTimetable() throws IOException, ClassNotFoundException
	{
		studentView.clearTimetable();
		Socket clientSocket = new Socket("127.0.0.1", 6112);
		ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
        
		outputStream.writeObject(studentView.getCurrentDate());
		List<Subject> subjects = new ArrayList<Subject>();
		Object object = inputStream.readObject();
		subjects = (List<Subject>) object;
		studentView.setTimetable(subjects);
		clientSocket.close();
	}
}
