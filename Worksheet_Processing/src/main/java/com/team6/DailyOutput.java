package com.team6;

import java.util.*;
import java.text.*;
import java.io.*;


public class DailyOutput {
	
	ArrayList<OnCall> onCalls;
	
	public DailyOutput(ArrayList<OnCall> onCallsIn) {
		onCalls = onCallsIn;
		sort();
		try {
			printDailyRecord();
			printTeacherSchedules();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//Sorts the OnCall objects in Array by period
	private void sort() {
		Collections.sort(onCalls);
	}
	
	public void printDailyRecord() throws IOException, FileNotFoundException {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
			Date date = new Date();
			String today = dateFormat.format(date);
			
			String filename = today + ".txt";
			File dir = new File("Output");
			dir.mkdirs();
			File outFile = new File(dir, filename);
			outFile.createNewFile();
			FileWriter f = new FileWriter(outFile);
			BufferedWriter b = new BufferedWriter(f);
			PrintWriter p = new PrintWriter(b);

			int periodIndex;
			p.println(today + "\n");

			for(int i=0; i<=4; i++) {
				String period = Course.getIndexFromPeriod(i);
				p.println("\n" + period + "\n");
				for(int j=0; j<=onCalls.size()-1; j++) {
					periodIndex = onCalls.get(j).getCourse().getPeriodIndex(onCalls.get(j).getCourse().getPeriod());
					if(periodIndex == i) {
						p.println(onCalls.get(j).toString());
					}	
				}
			}
			
			p.close();
	}
	
	public void printTeacherSchedules()throws IOException, FileNotFoundException  {
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Date date = new Date();
		String today = dateFormat.format(date);
		
		File dir = new File("Output/Teacher-schedules");
		dir.mkdirs();
		String initials;
		File tmp;
		ArrayList<Integer> onCallIndexes = new ArrayList<Integer>();
		for(int i=0; i<= onCalls.size()-1; i++) {
			initials = onCalls.get(i).getAbsentTeacher().getInitials() + ".txt";
			tmp = new File(dir, initials);
			tmp.createNewFile();
			FileWriter f = new FileWriter(tmp);
			BufferedWriter b = new BufferedWriter(f);
			PrintWriter p = new PrintWriter(b);
			
			p.println("\tRegular schedule: \n");
			p.println(onCalls.get(i).getAbsentTeacher().getTextSchedule());
			p.println("\n\tSubstitutions: ");
			onCallIndexes = findMultipleOnCalls(onCalls.get(i).getAbsentTeacher());
			for(int j=0; j<=onCallIndexes.size()-1; j++)
				p.println(onCalls.get(onCallIndexes.get(j)).toString() + "\n");
			p.close();
		}
	}
	
	public ArrayList<Integer> findMultipleOnCalls(Teacher t){
		ArrayList<Integer> onCallIndexes = new ArrayList<Integer>();
		String initials = t.getInitials();
		for(int i=0; i<=onCalls.size()-1; i++)
			if(initials.equals(onCalls.get(i).getAbsentTeacher().getInitials()))
				onCallIndexes.add(i);
		return onCallIndexes;
	}
	
	
	
}