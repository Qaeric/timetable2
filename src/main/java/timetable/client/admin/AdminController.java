package timetable.client.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timetable.Subject;
import timetable.client.student.StudentController;
import timetable.server.Server;

public class AdminController extends StudentController
{
	protected AdminView adminView;
	private AdminModel adminModel;
	private Entry entry;
	protected Subject subject;
	
	private int col;
	private int row;

	public AdminController(AdminView adminView, AdminModel adminModel)
	{
		super(adminView, adminModel);
		this.adminView = adminView;
		this.adminModel = adminModel;
		
		adminView.addMouseListener(new TableMouseListener());
		this.adminView.addModifyListener(new ModifyListener());
		this.adminView.addDeleteListener(new DeleteListener());
		entry = new Entry();
		entry.setLocationRelativeTo( null );
		this.entry.addAcceptListener(new AcceptListener());
	}
	
	class TableMouseListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			if (e.getButton()==1)
			{
				col = adminView.getTimetable().columnAtPoint(e.getPoint());
				row = adminView.getTimetable().rowAtPoint(e.getPoint());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e){}

		@Override
		public void mouseExited(MouseEvent e){}

		@Override
		public void mousePressed(MouseEvent e){}

		@Override
		public void mouseReleased(MouseEvent e){}
	}
	
	class AcceptListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			entry.subject.setName(entry.subjectField.getText());
			entry.subject.setRoom(Integer.parseInt(entry.classField.getText()));
			if (!entry.subject.getName().equals(""))
			{
				adminView.setSubject(entry.subject);
				subject.setName(subject.getName());
				subject.setRoom(subject.getRoom());
				Date date = adminView.getCurrentDate();
				try
				{
					addSubjectToDatabase(date);
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
			entry.setVisible(false);
		}
		
	}
	
	class ModifyListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String line = adminView.getSubject(col, row);
			subject = new Subject();
			if (line!=null)
			{
				subject = adminModel.convertToSubject(line, col, row);
			}
			else
			{
				subject.setDay(col);
				subject.setHour(row);
			}
			entry.modify(subject);
			entry.setVisible(true);
		}
	}
	
	class DeleteListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String line = adminView.getSubject(col, row);
			subject = new Subject();
			if (line!=null)
			{
				subject = adminModel.convertToSubject(line, col, row);
				try
				{
					removeSubjectFromDatabase(adminView.getCurrentDate());
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
			
		}
	}
	
	
	public void addSubjectToDatabase(Date date) throws UnknownHostException, IOException
	{
		Socket clientSocket = new Socket("127.0.0.1", 6113);
		ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        outputStream.writeObject(date);
        outputStream.writeObject(subject);
        
        clientSocket.close();
	}
	
	public void removeSubjectFromDatabase(Date date) throws UnknownHostException, IOException
	{
		Socket clientSocket = new Socket("127.0.0.1", 6114);
		ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        outputStream.writeObject(date);
        outputStream.writeObject(subject);
        adminView.removeSubject(col, row);
        
        clientSocket.close();
	}
}
