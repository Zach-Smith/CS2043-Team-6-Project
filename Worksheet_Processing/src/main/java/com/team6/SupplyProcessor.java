package com.team6;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class SupplyProcessor {
	AbsencesReader ar;
	ArrayList<ArrayList<String>> arr;
	
	public SupplyProcessor(int week) throws InvalidFormatException, IOException{
		ar = new AbsencesReader(week);
		arr = ar.readAbsences();
	}
	
	public ArrayList<SupplyTeacher> generateSupplyList() throws InvalidFormatException, IOException{
		 ArrayList<SupplyTeacher> supplies = new ArrayList<SupplyTeacher>();
		 SupplyTeacher s;
		 for(int i = 1; !arr.get(i).get(27).equals("a"); i++) {
			 s = new SupplyTeacher(arr.get(i).get(27), Integer.parseInt(arr.get(i).get(28)));
			 supplies.add(s);
		 }
		 return supplies;
	 }
	
	public ArrayList<OnCall> assignSupplyTeacher(ArrayList<Teacher> teachers, ArrayList<SupplyTeacher> supplyTeachers) {
		ArrayList<OnCall> list = new ArrayList<OnCall>();
		OnCall o;
		int indexJ = 2;
		for(int i = 3; !arr.get(i).get(0).equals("a"); i++) {
			String initials = arr.get(i).get(0);
			int index;
			for(index = 0;index < teachers.size(); index++) {
				if(initials.equals(teachers.get(index).getInitials()))
					break;
			}	
			
			int scheduleIndex = 0;
			for(int j = indexJ; j < indexJ+5; j++) {
				for(int k = 0; k < supplyTeachers.size(); k++) {
					if(!arr.get(i).get(j).equals("x") && !arr.get(i).get(j).equals("a")) {
						if(Integer.parseInt(arr.get(i).get(j)) == (supplyTeachers.get(k).getCode())) {
							o = new OnCall(supplyTeachers.get(k), teachers.get(index).getSchedule().get(scheduleIndex), teachers.get(index));
							list.add(o);
						}
					}
				}	
				scheduleIndex++;
				if(scheduleIndex == 5) {
					scheduleIndex = 0;
				}
			}
		}
		indexJ = indexJ+5;
		return list;
	}
}
