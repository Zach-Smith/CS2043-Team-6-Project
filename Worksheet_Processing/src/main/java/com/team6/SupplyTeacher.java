package com.team6;

public class SupplyTeacher {
	private String name;
	private int code;
	
	public SupplyTeacher(String nameIn,int codeIn) {
		name = nameIn;
		code = codeIn;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public void setCode(int newCode) {
		code = newCode;
	}
}
