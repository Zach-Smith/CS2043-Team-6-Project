package com.team6;

public class SupplyTeacher {
	private String name;
	private String code;
	
	public SupplyTeacher(String nameIn,String codeIn) {
		name = nameIn;
		code = codeIn;
	}
	
	public String getName(){
		return name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public void setCode(String newCode) {
		code = newCode;
	}
	
	public String toString(){
		return "Name: " + name + ", Code: " + code;
	}
}
