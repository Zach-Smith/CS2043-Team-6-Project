package onCall_GUI;

public class Teacher {
public final String initiales;
public int amountOnCallsWeek;
public int amountOnCallsMonth;
public int amountOnCallsTearm;
public boolean assignetToOnCall = false;
public Course[] courses = new Course[]{new Course(1,"Test"),null,null,null,null};

public Teacher(String initiales) {
	this.initiales = initiales;
	amountOnCallsWeek = 0;
	amountOnCallsMonth = 0;	
}
public Teacher(String initiales, int amountOnCallsWeek, int amountOnCallsMonth) {
	this.initiales = initiales;
	this.amountOnCallsMonth = amountOnCallsMonth;
	this.amountOnCallsWeek = amountOnCallsWeek;
}
public void assignOnCall() {
	assignetToOnCall = true;
}

public void addCourse(Course course, int period) {
	courses[period] = course;
}
public void removeCourse(int period) {
	courses[period] = null;
}
public boolean freeIn(int period) {
		if(courses[period-1] == null) {
			return true;
		}
		else return false;
}
}
