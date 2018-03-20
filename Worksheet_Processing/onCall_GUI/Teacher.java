package onCall_GUI;

public class Teacher {
public final String initiales;
public int amountOnCallsWeek;
public int amountOnCallsMonth;
public boolean assignetToOnCall = false;

public Teacher(String initiales) {
	this.initiales = initiales;
	amountOnCallsWeek = 0;
	amountOnCallsMonth = 0;
	
	
}
public Teacher(String initiales, int amountOnCallsWeek, int amountOnCallsMonth) {
	this.initiales = initiales;
	this.amountOnCallsMonth = amountOnCallsMonth;
	this.amountOnCallsWeek = amountOnCallsWeek;
}
public void assignOnCall() {
	assignetToOnCall = true;
}
}
