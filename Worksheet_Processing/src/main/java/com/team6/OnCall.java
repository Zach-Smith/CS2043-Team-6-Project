package com.team6;

public class OnCall implements Comparable<OnCall>{
public Teacher onCaller;
public Course course;
public Teacher absentTeacher;
public SupplyTeacher supply;

  public OnCall(Teacher onCaller, Course course, Teacher absentTeacher) {
	  this.onCaller = onCaller;
	  this.absentTeacher = absentTeacher;
	  this.course = course;
	  this.supply = null;
  }
  
  public OnCall(SupplyTeacher supplyIn, Course course, Teacher absentTeacher) {
	  this.onCaller = null;
	  this.absentTeacher = absentTeacher;
	  this.course = course;
	  this.supply = supplyIn;
  }
  
  
  
  public int compareTo (OnCall otherOnCall) {
    return Course.getPeriodIndex(course.period) - Course.getPeriodIndex(otherOnCall.course.period) ; // so classes can be sorted by period
  }

  public boolean isFullTime() {
	  if (onCaller != null) {
		  return true;
	  }
	  else {
		  return false;
	  }
  }
  
  public Course getCourse() {
	  return course;
  }
  
  public String toString(){
	String s = "";
    s += "Replacement teacher: ";
    if (this.isFullTime()) {
    	s += onCaller.getInitials() + "\n";
    }
    else {
    	s += supply.getName() + "\n";
    }
    s += "Usual teacher: " + absentTeacher.getInitials() + "\n";
    s += "Course: " + course.getCourseNumber() + "\n";
    s += "Full-time teacher or supply: ";
    if (isFullTime())
      s += "full-time teacher";
    else
      s += "supply teacher";
    return s;
  }
  
}
