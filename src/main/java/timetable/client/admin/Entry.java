package timetable.client.admin;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import timetable.Subject;

public class Entry extends JFrame
{
	private static final long serialVersionUID = -1180069535459922422L;
	protected JLabel jLabel1=new JLabel("Subject name:");
	protected JLabel jLabel2=new JLabel("Room:");
    protected JTextArea subjectField=new JTextArea();
    protected JTextArea classField=new JTextArea();
    protected JButton acceptButton=new JButton();
    
    protected Subject subject;
    
    public Entry()
    {	
    	this.setLocationRelativeTo( null );
    	this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setPreferredSize(new Dimension(400, 250));
		this.setBounds(new Rectangle(10,10,400,250));
		
        jLabel1.setBounds(new Rectangle(40,30,100,25));
        this.getContentPane().add(jLabel1,null);
        jLabel2.setBounds(new Rectangle(40,65,100,25));
        this.getContentPane().add(jLabel2,null);
        
        subjectField.setBounds(new Rectangle(160,30,200,25));
        this.getContentPane().add(subjectField,null);
        
        classField.setBounds(new Rectangle(160,65,200,25));
        this.getContentPane().add(classField,null);
        
        acceptButton.setText("Accept");
        acceptButton.setBounds(new Rectangle(150,145,100,25));
        this.getContentPane().add(acceptButton,null);
    }
    
    void addAcceptListener(ActionListener listenForAcceptButton)
	{
		acceptButton.addActionListener(listenForAcceptButton);
	}
	
	public void modify(Subject subject)
	{
		this.subject = subject;
		subjectField.setText(this.subject.getName());
		String room = Integer.toString(this.subject.getRoom());
		classField.setText(room);
	}
}
