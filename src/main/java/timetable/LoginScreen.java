package timetable;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.FileSystems;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import timetable.client.admin.AdminController;
import timetable.client.admin.AdminModel;
import timetable.client.admin.AdminView;
import timetable.client.student.StudentController;
import timetable.client.student.StudentModel;
import timetable.client.student.StudentView;
import timetable.server.Server;

public class LoginScreen extends JFrame
{
	private static final long serialVersionUID = 5088337545025943308L;
	//protected AdminController adminController;
	protected StudentController studentController;
	
	protected JTextField usernameField=new JTextField();
    protected JPasswordField passwordField=new JPasswordField();
    protected JButton adminButton=new JButton("Login as Admin");
    protected JButton userButton=new JButton("Login as User");
    
    LoginScreen()
    {
    	Thread serverThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				Server server = new Server(FileSystems.getDefault().getPath("src", "main", "resources", "database.txt"));
			}
		});
		serverThread.start();
    	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout( null );
        this.setBounds(new Rectangle(10,10,450,225));
        
        usernameField.setBounds(new Rectangle(30,20,150,25));
        this.getContentPane().add(usernameField,null);
        
        passwordField.setBounds(new Rectangle(30,60,150,25));
        this.getContentPane().add(passwordField,null);
        
        adminButton.setBounds(new Rectangle(30,120,150,25));
		adminButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				adminButton_actionPerformed(e);
		    }
		});
        this.getContentPane().add(adminButton,null);
        
        userButton.setBounds(new Rectangle(250,120,150,25));
        userButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                userButton_actionPerformed(e);
            }
        });
        this.getContentPane().add(userButton,null);
        
        this.setVisible(true);
    }
    
    private void adminButton_actionPerformed(ActionEvent e)
    {	
		try
		{
			Socket clientSocket = new Socket("127.0.0.1", 6111);
			ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
			outputStream.writeObject(usernameField.getText());
			outputStream.writeObject(new String(passwordField.getPassword()));
			
			Object object = inputStream.readObject();
			boolean logged = (boolean)object;
			if(logged == true)
			{
				AdminView adminView = new AdminView();
				AdminModel adminModel = new AdminModel();
				
				AdminController adminController = new AdminController(adminView, adminModel);
				
				this.setVisible(false);
				adminView.setLocationRelativeTo( null );
				adminView.setVisible(true);
				clientSocket.close();
			}
			else
			{
	            JOptionPane.showMessageDialog(this, 
                        "B³¹d, po³¹cz siê ponownie",
                        "B³¹d", 
                        JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException | ClassNotFoundException e1)
		{
			e1.printStackTrace();
		}
    }
    
    private void userButton_actionPerformed(ActionEvent e)
    {
		StudentView studentView = new StudentView();
		StudentModel studentModel = new StudentModel();
		studentController = new StudentController(studentView, studentModel);
		this.setVisible(false);
		studentView.setLocationRelativeTo( null );
		studentView.setVisible(true);
    }
    
    public static void main(String[] args) throws IOException
	{
    	LoginScreen loginScreen = new LoginScreen();
    	loginScreen.setLocationRelativeTo( null );
	}
}
