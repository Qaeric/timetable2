package timetable.client.admin;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import timetable.Subject;
import timetable.client.student.StudentView;

public class AdminView extends StudentView
{
	private static final long serialVersionUID = -3240638302084197900L;
	private JButton modify = new JButton("Modify Selected");
	private JButton delete = new JButton("Delete Selected");
	
	public AdminView()
	{
		super();
		
		modify.setBounds(new Rectangle(350, 380, 200, 25));
		this.getContentPane().add(modify, null);
		
		delete.setBounds(new Rectangle(550, 380, 200, 25));
		this.getContentPane().add(delete, null);
	}
	
	void addModifyListener(ActionListener listenForModifyButton)
	{
		modify.addActionListener(listenForModifyButton);
	}
	void addDeleteListener(ActionListener listenForDeleteButton)
	{
		delete.addActionListener(listenForDeleteButton);
	}
	
	public void addMouseListener(MouseListener tableMouseListener)
	{
		timetable.addMouseListener(tableMouseListener);
	}
	
	public JTable getTimetable()
	{
		return timetable;
	}
	
	public String getSubject(int day, int hour)
	{
		return (String)timetableModel.getValueAt(hour, day);
	}
	
	public void setSubject(Subject subject)
	{
		timetable.setValueAt(subject.getName()+", "+subject.getRoom(), subject.getHour(), subject.getDay());
	}
	
	public void removeSubject(int col, int row)
	{
		timetable.setValueAt("", row, col);
	}
}
