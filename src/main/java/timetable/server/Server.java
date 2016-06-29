package timetable.server;

import java.awt.image.WritableRenderedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import timetable.Subject;

public class Server
{
	private ServerSocket sSocket1;
	private ServerSocket sSocket2;
	private ServerSocket sSocket3;
	private ServerSocket sSocket4;
	
	private Path path;
	
	public Server(Path path)
	{
		this.path = path;
		try
		{
			sSocket1 = new ServerSocket(6112);
			sSocket2 = new ServerSocket(6113);
			sSocket3 = new ServerSocket(6114);
			sSocket4 = new ServerSocket(6111);
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					searchAdminDatabaseForUser();
				} catch (IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					retrieveWeekFromDatabase();
				} catch (IOException | ClassNotFoundException | ParseException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					writeSubjectToDatabase();
				} catch (IOException | ClassNotFoundException | ParseException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
		try
		{
			deleteSubjectFromDatabase();
		} catch (ClassNotFoundException | IOException | ParseException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void retrieveWeekFromDatabase() throws IOException, ClassNotFoundException, ParseException
	{
		while(true)
		{
			List<Subject> subjects = new ArrayList<Subject>(35);
			
			Socket cSocket = sSocket1.accept();
			ObjectOutputStream outputStream = new ObjectOutputStream(cSocket.getOutputStream());
			ObjectInputStream inputStream = new ObjectInputStream(cSocket.getInputStream());
			Date passedDate = (Date)inputStream.readObject();
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
			SimpleDateFormat dateFormat = new SimpleDateFormat ("dd-MM-yyyy");
			String line;
			Date databaseDate = null;
			long databaseTime = 0;
			long time = ((passedDate.getTime())/(1000*3600*24));
			while((line=bufferedReader.readLine())!=null)
			{
				Scanner scanner = new Scanner(line);
	        	scanner.useDelimiter(", ");
				databaseDate = dateFormat.parse(scanner.next());
				databaseTime = ((databaseDate.getTime())/(1000*3600*24));
				
				if (databaseTime>=time && databaseTime<=time+4)
				{
					int dayOfWeek = databaseDate.getDay();
					int hour = scanner.nextInt();
					String name = scanner.next();
					int room = scanner.nextInt();
					Subject subject = new Subject(dayOfWeek-1, hour-1, name, room);
					subjects.add(subject);
				}
				scanner.close();
			}
			outputStream.writeObject(subjects);
			bufferedReader.close();
		}
	}
	
	public void writeSubjectToDatabase() throws IOException, ClassNotFoundException, ParseException
	{
		while(true)
		{
			Socket cSocket = sSocket2.accept();
			ObjectInputStream inputStream = new ObjectInputStream(cSocket.getInputStream());
			Date passedDate = (Date)inputStream.readObject();
			Subject passedSubject = (Subject)inputStream.readObject();
			
			File file = path.toFile();
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			Path path2 = FileSystems.getDefault().getPath("src", "main", "resources", "database2.txt");
			File temp = path2.toFile();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(temp));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat ("dd-MM-yyyy");
			long time = passedDate.getTime() + passedSubject.getDay()*(1000*3600*24);
			Date date = new Date(time);
			
			Date databaseDate = null;
			
			String line;
			while((line=bufferedReader.readLine())!=null)
			{
				Scanner scanner = new Scanner(line);
	        	scanner.useDelimiter(", ");
				databaseDate = dateFormat.parse(scanner.next());
				int hour = scanner.nextInt();
				if (databaseDate.getTime()==date.getTime() && hour==passedSubject.getHour()+1)
				{
					continue;
				}
				else
				{
					bufferedWriter.write(line+System.getProperty("line.separator"));
				}
				
			}
			
			String newLine = new String(
					date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900)
					+", "+(passedSubject.getHour()+1)+", "+passedSubject.getName()+", "
					+passedSubject.getRoom());
			bufferedWriter.write(newLine+System.getProperty("line.separator"));
			
			bufferedReader.close();
		    bufferedWriter.close();
		    
		    file.delete();
		    temp.renameTo(file);
		}
	}
	
	private void deleteSubjectFromDatabase() throws IOException, ClassNotFoundException, ParseException
	{
		while(true)
		{
			Socket cSocket = sSocket3.accept();
			ObjectInputStream inputStream = new ObjectInputStream(cSocket.getInputStream());
			Date passedDate = (Date)inputStream.readObject();
			Subject passedSubject = (Subject)inputStream.readObject();
			
			File file = path.toFile();
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			Path path2 = FileSystems.getDefault().getPath("src", "main", "resources", "database2.txt");
			File temp = path2.toFile();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(temp));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat ("dd-MM-yyyy");
			long time = passedDate.getTime() + passedSubject.getDay()*(1000*3600*24);
			Date date = new Date(time);
			
			Date databaseDate = null;
			
			String line;
			while((line=bufferedReader.readLine())!=null)
			{
				Scanner scanner = new Scanner(line);
	        	scanner.useDelimiter(", ");
				databaseDate = dateFormat.parse(scanner.next());
				int hour = scanner.nextInt();
				if (databaseDate.getTime()==date.getTime() && hour==passedSubject.getHour()+1)
				{
					continue;
				}
				else
				{
					bufferedWriter.write(line+System.getProperty("line.separator"));
				}
			}
			bufferedReader.close();
		    bufferedWriter.close();
		    
		    file.delete();
		    temp.renameTo(file);
		}
	}
	
	private void searchAdminDatabaseForUser() throws IOException, ClassNotFoundException
	{
		while(true)
		{
			Socket cSocket = sSocket4.accept();
			Path path2 = FileSystems.getDefault().getPath("src", "main", "resources", "adminsDatabase.txt");
			ObjectOutputStream outputStream = new ObjectOutputStream(cSocket.getOutputStream());
			ObjectInputStream inputStream = new ObjectInputStream(cSocket.getInputStream());
			String passedUser = (String)inputStream.readObject();
			String passedPass = (String)inputStream.readObject();
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path2.toFile()));
			String line;
			boolean logged = false;
			while((line=bufferedReader.readLine())!=null)
			{
				Scanner scanner = new Scanner(line);
	        	scanner.useDelimiter(", ");
	        	
	        	String user = scanner.next();
	        	String pass = scanner.next();
	        	if (user.equals(passedUser) && pass.equals(passedPass) )
	        	{
	        		logged = true;
	        		break;
	        	}
			}
			outputStream.writeObject(logged);
			bufferedReader.close();
		}
	}
}
