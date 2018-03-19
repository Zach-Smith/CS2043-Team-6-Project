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
  
  public Course getCourse() {
	  return course;
  }
  
  public int compareTo (OnCall otherOnCall) {
    return course.getPeriod() - otherOnCall.getCourse().getPeriod(); // so classes can be sorted by period
  }

  
  public String toString(){
    String s;
    s = "Replacement teacher: " + onCaller.getInitials() + "\n";
    s += "Usual teacher: " + absentTeacher.getInitials() + "\n";
    s += "Course: " + course.getCourseNumber() + "\n";
    s += "School teacher or supply: ";
    if (supply != null)
      s += "supply teacher\n";
    else
      s += "school teacher\n";
    return s;
  }
  
}