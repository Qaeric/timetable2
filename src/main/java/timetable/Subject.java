package timetable;

import java.io.Serializable;

public class Subject implements Serializable
{
	private static final long serialVersionUID = -7696473846517288596L;
	private int day;
	private int hour;
	private String name;
	private int room;
	
	public Subject()
	{
		day = 0;
		hour = 0;
		name = null;
		room = 0;
	}
	
	public Subject(int day, int hour, String name, int room)
	{
		this.day = day;
		this.hour = hour;
		this.name = name;
		this.room = room;
	}

	public int getDay()
	{
		return day;
	}

	public int getHour()
	{
		return hour;
	}

	public String getName()
	{
		return name;
	}

	public int getRoom()
	{
		return room;
	}

	public void setDay(int day)
	{
		this.day = day;
	}

	public void setHour(int hour)
	{
		this.hour = hour;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setRoom(int room)
	{
		this.room = room;
	}
}
