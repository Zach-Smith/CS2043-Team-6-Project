package com.team6;

import java.util.*;
import java.text.*;
import java.io.*;

public class DailyRecord {
	
	ArrayList<OnCall> onCalls;
	
	public DailyRecord(ArrayList<OnCall> onCallsIn) {
		onCalls = onCallsIn;
		sort();
		try {
			printToFile();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//Sorts the OnCall objects in Array by period
	private void sort() {
		Collections.sort(onCalls);
	}
	
	public void printToFile() throws IOException, FileNotFoundException {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
			Date date = new Date();
			String today = dateFormat.format(date);
			
			File outFile = new File(today + ".txt");
			FileWriter f = new FileWriter(outFile);
			BufferedWriter b = new BufferedWriter(f);
			PrintWriter p = new PrintWriter(b);

			p.println(today + "\n");

			for(int i=1; i<=5; i++) {
				p.println("Period " + i + "\n");
				for(int j=0; j<=onCalls.size()-1; j++)
					if(onCalls.get(j).getCourse().getPeriod() == i) {
						System.out.println(onCalls.get(j).toString());
						p.println(onCalls.get(j).toString());
					}	
			}
			System.out.printf("File is located at %s%n", outFile.getAbsolutePath());
			p.close();
		

	}
	
	
	
}
