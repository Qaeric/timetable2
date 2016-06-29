package timetable;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import timetable.server.Server;

public class ServerTest
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}
	

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void shouldRetrieveWeekFromDatabase()
	{
		//when
		Thread serverThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				Server server = new Server(FileSystems.getDefault().getPath("src", "test", "resources", "database.txt"));
			}
		});
		serverThread.start();
		
		List<Subject> subjects = new ArrayList<Subject>();
		try
		{
			Socket clientSocket = new Socket("127.0.0.1", 6112);
			ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
	        ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
	        
	        outputStream.writeObject(new Date(116, 5, 27));
	        Object object = inputStream.readObject();
			subjects = (List<Subject>) object;
			
		} catch (IOException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//given
		Subject subject = new Subject(0, 0, "w fiz", 1);
		
		//then
		assertEquals(subject.getDay(), subjects.get(0).getDay());
		assertEquals(subject.getHour(), subjects.get(0).getHour());
		assertEquals(subject.getName(), subjects.get(0).getName());
		assertEquals(subject.getRoom(), subjects.get(0).getRoom());
	}

}
