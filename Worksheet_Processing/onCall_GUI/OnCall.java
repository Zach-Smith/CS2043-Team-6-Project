package onCall_GUI;

public class OnCall implements Comparable<OnCall>{
public Teacher onCaller;
public Course course;
public Teacher absentTeacher;

public OnCall(Teacher onCaller, Course course, Teacher absentTeacher) {
	this.onCaller = onCaller;
	this.absentTeacher = absentTeacher;
	this.course = course;
}
public int compareTo (OnCall otherOnCall) {
return course.period - otherOnCall.course.period ; // so classes can be sorted by period
}
}
