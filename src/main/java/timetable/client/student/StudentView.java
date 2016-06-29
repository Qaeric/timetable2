package timetable.client.student;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import timetable.Subject;


public class StudentView extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	protected JButton prev = new JButton("previous");
	protected JButton next = new JButton("next");
	public JLabel date = new JLabel("", SwingConstants.CENTER);
	
	protected DefaultTableModel timetableModel = new DefaultTableModel();
	protected JTable timetable = new JTable(timetableModel);
	protected JScrollPane timetablePane = new JScrollPane(timetable);
	
	protected JLabel hour8 = new JLabel("8:00");
	protected JLabel hour10 = new JLabel("10:00");
	protected JLabel hour12 = new JLabel("12:00");
	protected JLabel hour14 = new JLabel("14:00");
	protected JLabel hour16 = new JLabel("16:00");
	protected JLabel hour18 = new JLabel("18:00");
	
	private Date currentDate;
	private Date nextDate;
	
	@SuppressWarnings("deprecation")
	public StudentView()
	{
		try
		{
			Date temp = new Date();
			while (temp.getDay()!=1)
			{
				temp = new Date(temp.getYear(), temp.getMonth(), temp.getDate()-1);
			}
	        currentDate = new Date(temp.getYear(), temp.getMonth(), temp.getDate());
	        nextDate = new Date(temp.getYear(), temp.getMonth(), temp.getDate()+6);
	        int year1 = currentDate.getYear()+1900;
	        int year2 = nextDate.getYear()+1900;
			date.setText(currentDate.getDate()+"-"+(currentDate.getMonth()+1)+"-"+year1+" - "+
					nextDate.getDate()+"-"+(nextDate.getMonth()+1)+"-"+year2);
			
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.getContentPane().setLayout(null);
			this.setPreferredSize(new Dimension(600, 400));
			this.setBounds(new Rectangle(10,10,1100,450));
			
			prev.setBounds(new Rectangle(300, 10, 100, 30));
			this.getContentPane().add(prev, null);
			
			date.setBounds(new Rectangle(400, 10, 200, 30));
			this.getContentPane().add(date, null);
			
			next.setBounds(new Rectangle(600, 10, 100, 30));
			this.getContentPane().add(next, null);
			
	        hour8.setBounds(new Rectangle(25,75,50,50));
	        hour10.setBounds(new Rectangle(25,125,50,50));
	        hour12.setBounds(new Rectangle(25,175,50,50));
	        hour14.setBounds(new Rectangle(25,225,50,50));
	        hour16.setBounds(new Rectangle(25,275,50,50));
	        hour18.setBounds(new Rectangle(25,325,50,50));
	        this.getContentPane().add(hour8, null);
	        this.getContentPane().add(hour10, null);
	        this.getContentPane().add(hour12, null);
	        this.getContentPane().add(hour14, null);
	        this.getContentPane().add(hour16, null);
	        this.getContentPane().add(hour18, null);
			
	        timetable.setRowHeight(50);
	        timetableModel.addColumn("poniedzia³ek");
	        timetableModel.addColumn("wtorek");
	        timetableModel.addColumn("œroda");
	        timetableModel.addColumn("czwartek");
	        timetableModel.addColumn("pi¹tek");
			timetablePane.setBounds(new Rectangle(75,50,1000,323));
			timetablePane.getViewport().add(timetable, null);
			this.getContentPane().add(timetablePane, null);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	void addPrevListener(ActionListener listenForPrevButton)
	{
		prev.addActionListener(listenForPrevButton);
	}
	void addNextListener(ActionListener listenForNextButton)
	{
		next.addActionListener(listenForNextButton);
	}
	
	public Date getCurrentDate()
	{
		return currentDate;
	}

	public void clearTimetable()
	{
		while(timetableModel.getRowCount()!=0) timetableModel.removeRow(0);
		for (int i=0; i<6; i++)
		{
			Vector<Integer> v = new Vector<Integer>();
			timetableModel.addRow(v);
		}
	}
	
	public void setTimetable(List<Subject> subjects)
	{	
		for (int it=0; it<subjects.size(); it++)
		{
			Subject subject = subjects.get(it);
			timetable.setValueAt(subject.getName()+", "+subject.getRoom(), subject.getHour(), subject.getDay());
		}
	}
	
	@SuppressWarnings("deprecation")
	public void setDate(Date currentDate, int year1, Date nextDate, int year2)
	{
		this.currentDate = currentDate;
		this.nextDate = nextDate;
		date.setText(currentDate.getDate()+"-"+(currentDate.getMonth()+1)+"-"+year1+" - "+
				nextDate.getDate()+"-"+(nextDate.getMonth()+1)+"-"+year2);
	}
}

