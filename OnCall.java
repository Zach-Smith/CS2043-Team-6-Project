package onCall_GUI;

public class OnCall implements Comparable<OnCall>{
public Teacher onCaller;
public Course course;
public Teacher absentTeacher;
public boolean supply;

  public OnCall(Teacher onCaller, Course course, Teacher absentTeacher) {
	  this.onCaller = onCaller;
	  this.absentTeacher = absentTeacher;
	  this.course = course;
  }
  
  public int compareTo (OnCall otherOnCall) {
    return course.period - otherOnCall.course.period ; // so classes can be sorted by period
  }

  public String toString(){
    String s = course.getPeriod() + "\n\n";
    s += "Replacement teacher: " + onCaller.getInitials() + "\n";
    s += "Usual teacher: " + absentTeacher.getInitials() + "\n";
    s += "Course: " + course.getCourseNumber() + "\n";
    s += "School teacher or supply: ";
    if (supply == true)
      s += "supply teacher";
    else
      s += "school teacher";
    return s;
  }
  
}
