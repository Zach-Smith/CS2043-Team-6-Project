import com.team6.*;

import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;


public class Application {
	
	private final String ONCALL_OUTPUT_FILE = "./OnCalls_List_Output.txt";
	
	private int max_on_calls;
	private int starting_month; //first month of term in on call tally
	private int starting_day;
	private int starting_year;
	private JTextField startingMonthField;
	private JTextField startingDayField;
	private JTextField startingYearField;
	private JButton startingDateButton;
	private int tallySheet;
	private int month; 
	private int day;
	private int dayOfWeek; //Monday is 0, Tuesday is 1, etc.
	private int year;
	private int week; //1 corresponds to first week in workbook, 2 corresponds to second, etc.
	private JFrame appFrame;
	private JPanel appPanel1;
	private JPanel appPanel2;
	private JPanel appPanel3;
	private JPanel appPanel4;
	private JPanel appPanel5;
	private JTextField monthField;
	private JTextField dayField;
	private JTextField yearField;
	private JButton dateButton;
	private JLabel outputMessage;
	private JButton maxOnCallsButton;
	private JTextField maxOnCallsField;
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
		appFrame.setSize(600,600);
		appFrame.setLayout(new GridLayout(5,1));
		
		appFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });
		
		
		
		appPanel1 = new JPanel();
		appPanel1.setLayout(new FlowLayout());
		
		appPanel2 = new JPanel();
		appPanel2.setLayout(new FlowLayout());
		
		appPanel3 = new JPanel();
		appPanel3.setLayout(new FlowLayout());
		
		appPanel4 = new JPanel();
		appPanel4.setLayout(new FlowLayout());
		
		appPanel5 = new JPanel();
		appPanel5.setLayout(new FlowLayout());
		
		//First Panel
		monthField = new JTextField("MM",2);
		dayField = new JTextField("DD",2);
		yearField = new JTextField("YYYY",4);
		dateButton = new JButton("Submit current date");
		
		
		appPanel1.add(monthField);
		appPanel1.add(dayField);
		appPanel1.add(yearField);
		appPanel1.add(dateButton);
		
		//Second Panel
		startingMonthField = new JTextField("MM",2);
		startingDayField = new JTextField("DD",2);
		startingYearField = new JTextField("YYYY",4);
		startingDateButton = new JButton("Submit start-of-term date");
				
				
		appPanel2.add(startingMonthField);
		appPanel2.add(startingDayField);
		appPanel2.add(startingYearField);
		appPanel2.add(startingDateButton);
		
		//Third Panel
		maxOnCallsField = new JTextField("",5); //Assuming the maximum on calls wouldn't have more than 5 digits
		maxOnCallsButton = new JButton("Enter Max # of On Calls for Term");
		
		appPanel3.add(maxOnCallsField);
		appPanel3.add(maxOnCallsButton);
		
		//Fourth Panel
		
		onCallButton = new JButton("Assign On-Calls");
		
		appPanel4.add(onCallButton);

		//Fifth Panel
		outputMessage = new JLabel("");
		
		appPanel5.add(outputMessage);
		
		appFrame.add(appPanel1);
		appFrame.add(appPanel2);
		appFrame.add(appPanel3);
		appFrame.add(appPanel4);
		appFrame.add(appPanel5);
		appFrame.setVisible(true);
		
	}
	
	private void processEvents(){
		dateButton.addActionListener(new dateButtonClickListener());
		startingDateButton.addActionListener(new startingDateClickActionListener());
		maxOnCallsButton.addActionListener(new maxButtonClickListener());
		onCallButton.addActionListener(new onCallButtonClickListener());
		
	}
	
	
	
	private class dateButtonClickListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				month = Integer.parseInt(monthField.getText());
				day = Integer.parseInt(dayField.getText());
				year = Integer.parseInt(yearField.getText());
			
				Calendar cal = Calendar.getInstance();
				cal.set(year,month - 1,day);
				int namedDay = cal.get(Calendar.DAY_OF_WEEK);
			
				if (month >= 1 && month <= 12 && day >= 1 && day <= 31 && namedDay >= 2 && namedDay <=6) {
					dayOfWeek = namedDay - 2;
					outputMessage.setText("Current date Entered");
					
					
				}
				else if (namedDay == 1 || namedDay == 7) {
					outputMessage.setText("Current date falls on a weekend. Please Enter a new current date.");
				}
				else {
					outputMessage.setText("Invalid current date. Please enter a new current date");
				}
			}
			catch(NumberFormatException nException){
				outputMessage.setText("Invalid current date. Please enter a new current date.");
			}
		}
		
	}
	
	private class startingDateClickActionListener implements ActionListener{
		
		private int findWeek(){
			Calendar cal_term = Calendar.getInstance();
			Calendar cal_curr = Calendar.getInstance();
			cal_term.set(starting_year,starting_month-1,starting_day);
			cal_curr.set(year,month-1,day);
			
			int dInterval =  daysInterval(cal_term.getTime(),cal_curr.getTime());
			int weekInterval = dInterval/7;
			
			
			if (cal_curr.get(Calendar.DAY_OF_WEEK) < cal_term.get(Calendar.DAY_OF_WEEK) ) {
				weekInterval++;
			}
			
			weekInterval++;
			return weekInterval;
			
			
		}
		
		private int daysInterval(Date date1,Date date2) {
			return (int)((date2.getTime() - date1.getTime())/(1000 * 60 * 60 * 24));
		}
		
		public void actionPerformed(ActionEvent e) {
			try {
				starting_month = Integer.parseInt(startingMonthField.getText());
				starting_day = Integer.parseInt(startingDayField.getText());
				starting_year = Integer.parseInt(startingYearField.getText());
			
				Calendar cal = Calendar.getInstance();
				cal.set(starting_year,starting_month - 1,starting_day);
				int namedDay = cal.get(Calendar.DAY_OF_WEEK);
			
				if (starting_month >= 1 && starting_month <= 12 && starting_day >= 1 && starting_day <= 31 && namedDay >= 2 && namedDay <=6) {
					outputMessage.setText("Term start date Entered");
					tallySheet = month - starting_month;
					week = findWeek();
				}
				else if (namedDay == 1 || namedDay == 7) {
					outputMessage.setText("Term start date falls on a weekend. Please Enter a new date.");
				}
				else {
					outputMessage.setText("Invalid term start date. Please enter a new date");
				}
			}
			catch(NumberFormatException nException){
				outputMessage.setText("Invalid term start date. Please enter a new date.");
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
					PrintWriter writer = new PrintWriter(ONCALL_OUTPUT_FILE,"UTF-8");
					
					if (ocp.generateOnCallList()) {
						System.out.println(ocp);
						
						writer.println("On-Calls and supplies assigned for " + month + "/" + day + "/" + year + "\n");
						writer.println(ocp);
						writer.close();
						
						outputMessage.setText("On-calls and supplies assigned");						
					}
					else {
						
						writer.println("On-Calls and supplies assigned for " + month + "/" + day + "/" + year + "\n");
						writer.println("None");
						writer.close();
						outputMessage.setText("No on-calls or supplies assigned");
					}						
					
					
					
					
				}
				else {
					System.out.println("Schedule is NOT in correct format. Please check headers");			
				}
				
				
				// Test WorkbookWriter		
				WorkbookWriter.writeAbsences("TestTeacher2",month,starting_month,day,"Period 2","./OnCall_Tallies_Example_edited.xlsx");
				
			}
			catch(Exception excp){
				excp.printStackTrace();
			}
		
		}
		
	}
	
	private class maxButtonClickListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				max_on_calls = Integer.parseInt(maxOnCallsField.getText());
				
				outputMessage.setText("Max # of on calls of " + max_on_calls + " entered");
				
			}
			catch(NumberFormatException nException){
				outputMessage.setText("Invalid entry for max number of on calls. Please enter a positive number.");
			}
		}
	}
		
}
