package application.model;

import java.io.Serializable;
import application.configuration.Util;
import application.other.Day;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProjectionDate implements Serializable, Comparable<ProjectionDate> {

	private static final long serialVersionUID = 6533859460399769737L;
	
	private Day day;
	private Integer hours;
	private Integer minutes;

	public ProjectionDate() {

	}

	public ProjectionDate(Day day, Integer hours, Integer minutes) {
		super();
		
		try {
			
			if(hours > 23 && hours < 0) {
				throw new Exception("Wrong hours range, hourse range from 0 - 23: " + hours);
			}
			
			if(minutes > 59 && minutes < 0) {
				throw new Exception("Wrong minute range, minute range from 0 - 59: " + minutes);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		this.day = day;
		this.hours = hours;
		this.minutes = minutes;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	
	public boolean equals(ProjectionDate projectionDate) {
		
		if(projectionDate == null) {
			return false;
		}
		
		if(this.day == projectionDate.getDay() && this.hours == projectionDate.getHours() && this.minutes == projectionDate.getMinutes()) {
			return true;
		}
		
		return false;
		
	}
	
	public int compareTo(ProjectionDate projectionDate) {
		
		int thisDayNumber = -1;
		int thatDayNumber = -1;
		try {
			thisDayNumber = Util.getDayNumber(this.getDay());
			thatDayNumber = Util.getDayNumber(projectionDate.getDay());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(thisDayNumber < thatDayNumber) {
			return -1;
		}
		
		if(thisDayNumber > thatDayNumber) {
			return 1;
		}
		
		// If ProjectionDate Days are equals then we watch hours and minutes
		if(thisDayNumber == thatDayNumber) {
			
			if(this.getHours() < projectionDate.getHours()) {
				return -1;
			}
			
			if(this.getHours() > projectionDate.getHours()) {
				return 1;
			}
			
			if(this.getHours() == projectionDate.getHours()) {
				if(this.getMinutes() < projectionDate.getMinutes()) {
					return -1;
				}
				else if(this.getMinutes() > projectionDate.getMinutes()) {
					return 1;
				}
				else if(this.getMinutes() == projectionDate.getMinutes()) {
					return 0;
				}
			}
		}
		
		return 0;
	}
	
	
	
	@Override
	public String toString() {
		return "ProjectionDate [day=" + day + ", hours=" + hours + ", minutes=" + minutes + "]";
	}

}
