import com.team6.*;

import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Calendar;
import java.io.*;


public class Application {
	
	
	private final int max_on_calls = 30;
	private final int STARTING_MONTH = 1; //first month in on call tally
	private final int STARTING_WEEK = 1; //the week in STARTING_MONTH corresponding to the first week in absences record of workbook
	private final String ONCALL_OUTPUT_FILE = "./OnCalls_List_Output.txt";
	private int tallySheet;
	private int month; 
	private int day;
	private int dayOfWeek; //Monday is 0, Tuesday is 1, etc.
	private int year;
	private int week = 1; //first week
	private JFrame appFrame;
	private JPanel appPanel1;
	private JPanel appPanel2;
	private JTextField monthField;
	private JTextField dayField;
	private JTextField yearField;
	private JButton dateButton;
	private JLabel dateConfirmed;
	private JButton onCallButton;
	
	public Application() {
		beginGUI();
	}
	
	
	public static void main(String args[]) {
			Application app = new Application();
			app.processEvents();
							
		
		
	}
	
	private void beginGUI() {
		appFrame = new JFrame("On Call Tracker");
		appFrame.setSize(400, 400);
		appFrame.setLayout(new FlowLayout());
		
		appFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });
		
		
		
		appPanel1 = new JPanel();
		appPanel1.setLayout(new FlowLayout());
		
		appPanel2 = new JPanel();
		appPanel2.setLayout(new FlowLayout());
		
		
		monthField = new JTextField("MM",2);
		dayField = new JTextField("DD",2);
		yearField = new JTextField("YYYY",4);
		dateButton = new JButton("Submit date");
		dateConfirmed = new JLabel("");
		
		appPanel1.add(monthField);
		appPanel1.add(dayField);
		appPanel1.add(yearField);
		appPanel1.add(dateButton);
		appPanel1.add(dateConfirmed);
		
		onCallButton = new JButton("Assign On-Calls");
		
		appPanel2.add(onCallButton);
		
		appFrame.add(appPanel1);
		appFrame.add(appPanel2);
		appFrame.setVisible(true);
		
	}
	
	private void processEvents(){
		dateButton.addActionListener(new dateButtonClickListener());
		onCallButton.addActionListener(new onCallButtonClickListener());
	}
	
	private class dateButtonClickListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				month = Integer.parseInt(monthField.getText());
				day = Integer.parseInt(dayField.getText());
				year = Integer.parseInt(yearField.getText());
			
				Calendar cal = Calendar.getInstance();
				cal.set(2018,month - 1,day);
				int namedDay = cal.get(Calendar.DAY_OF_WEEK);
			
				if (month >= 1 && month <= 12 && day >= 1 && day <= 31 && namedDay >= 2 && namedDay <=6) {
					dateConfirmed.setText("Date Entered");
					dayOfWeek = namedDay - 2;
					tallySheet = month - STARTING_MONTH;
				}
				else if (namedDay == 1 || namedDay == 7) {
					dateConfirmed.setText("Date falls on a weekend. Please Enter a new date.");
				}
				else {
					dateConfirmed.setText("Invalid date. Please enter a new date");
				}
			}
			catch(NumberFormatException nException){
				dateConfirmed.setText("Invalid date. Please enter a new date.");
			}
		}
		
	}
	
	private class onCallButtonClickListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				
				//Test ScheduleProcessor
				
				ScheduleProcessor sp = new ScheduleProcessor();

				if(sp.checkScheduleFormat()) {
					System.out.println("Schedule has correct headers");
					
					System.out.println();
					
					ArrayList<Teacher> teachers = sp.generateTeachers();
					
					for (int i = 0; i < teachers.size(); i++) {
						System.out.println(teachers.get(i));
					}
					
					System.out.println("----------------------------------------------------------------------\n");
					System.out.println("After reading weekly, monthly, and total on-call tallies for each teacher (from on-call tallies sheet):");
					
					//Test ReadOnCallTally
					ArrayList<ArrayList<String>> onCallTally = ReadOnCallTally.readOnCallTally(tallySheet);
					teachers = ReadOnCallTally.updateTeachersFromOnCall(onCallTally, teachers,day);
					for (int i=0; i<=teachers.size()-1; i++)
						System.out.println(teachers.get(i).toString());
					
					//Test AbsencesProcessor
					
					AbsencesProcessor ap = new AbsencesProcessor(week);
					ArrayList<ArrayList<Course>> absenteeCoursesOfWeek = ap.findAbsences(teachers);
					
					System.out.println("Courses that need to be covered during week " + week + "\n");
					
					for(int i = 0; i < absenteeCoursesOfWeek.size(); i++) {
						if(i == 0) {
							System.out.println("Monday:");
						}
						if(i == 1) {
							System.out.println("Tuesday:");
						}
						if(i == 2) {
							System.out.println("Wednesday:");
						}
						if(i == 3) {
							System.out.println("Thursday:");
						}
						if(i == 4) {
							System.out.println("Friday:");
						}
						for(int j = 0; j < absenteeCoursesOfWeek.get(i).size(); j++) {
							System.out.println(absenteeCoursesOfWeek.get(i).get(j));
						}
					}
					
					System.out.println();
					ArrayList<Course> absenteeCourses = absenteeCoursesOfWeek.get(dayOfWeek);
					
					System.out.println();
					
					System.out.println("Courses that need to be covered during week " + week + ", day of week " + dayOfWeek + "\n");
		
					for(int j = 0; j < absenteeCourses.size(); j++) {
						System.out.println(absenteeCourses.get(j));
					}
					
					System.out.println();
					System.out.println("List of supply teachers:\n");
					
					ArrayList<SupplyTeacher> supplyTeachers = ap.generateSupplyList();
					
					for (int i = 0; i < supplyTeachers.size(); i++) {
						System.out.println(supplyTeachers.get(i));
						System.out.println();
					}
					
					System.out.println();
					System.out.println("On Calls during Month = " + month + ", Day = " + day + ", Year = " + year);
					System.out.println("----------------------------------------------------------------------\n");		
					
									
					//Test OnCallProcessor
					OnCallProcessor ocp = new OnCallProcessor(teachers,supplyTeachers,absenteeCourses,max_on_calls);
					
					if (ocp.generateOnCallList()) {
						System.out.println(ocp);
						PrintWriter writer = new PrintWriter(ONCALL_OUTPUT_FILE,"UTF-8");
						writer.println("On-Calls and supplies assigned for " + month + "/" + day + "/" + year + "\n");
						writer.println(ocp);
						writer.close();
					}
					
					else {
						System.out.println("Could not cover all absences");
					}
					
					
				}
				else {
					System.out.println("Schedule is NOT in correct format. Please check headers");			
				}
				
				
				// Test WorkbookWriter		
				WorkbookWriter.writeAbsences("MC",1,2,"Period 1","./OnCall_Tallies_Example_edited.xlsx","updated-file.xlsx");
				
			}
			catch(Exception excp){
				excp.printStackTrace();
			}
		
		}
		
	}
	
}
