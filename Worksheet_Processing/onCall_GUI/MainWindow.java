package onCall_GUI;

import javax.swing.JFrame;
import javax.swing.JScrollPane;


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class MainWindow extends JFrame{
	String[] headersOnCalls = {"","Teacher", "Periode", "Class", "On-Calls this week", "On-Calls this month"};
	String[] headersNextOnCallers = {"","Teacher", "On-Calls this week", "On-Calls this month", "On-Calls left this Week", "On-Calls left this Month"};
	
	public MainWindow() {
		
		JButton btnInitialize_1 = new JButton("Initialize");
		btnInitialize_1.setBounds(48, 39, 119, 37);
		btnInitialize_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				initialize();
			}
		});
		
		setSize(1400,600);
		
		getContentPane().setLayout(null);
		
		String [][] fillerData = {{""}};
		
		tableOnCalls = new JTable();
		DefaultTableModel defaultTableModel1 = new DefaultTableModel (fillerData, headersOnCalls);
		tableOnCalls.setModel(defaultTableModel1);
		tableOnCalls.setBounds(33, 143, 610, 178);
		tableOnCalls.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		JScrollPane scrollPlane1 = new JScrollPane();
		scrollPlane1.setBounds(48, 215, 710, 67);
		scrollPlane1.setViewportView(tableOnCalls);
		
		tableNextOnCallers = new JTable();
		DefaultTableModel defaultTableModel2 = new DefaultTableModel (fillerData, headersNextOnCallers);
		tableNextOnCallers.setModel(defaultTableModel2);
		tableNextOnCallers.setBounds(33, 243, 610, 178);
		tableNextOnCallers.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		JScrollPane scrollPlane2 = new JScrollPane();
		scrollPlane2.setBounds(48, 344, 710, 153);
		scrollPlane2.setViewportView(tableNextOnCallers);

		
		setVisible(true);
		setLocation(20, 20);
		getContentPane().setLayout(null);
		getContentPane().add(btnInitialize_1);
		getContentPane().add(scrollPlane1);
		getContentPane().add(scrollPlane2);
		
		JButton btnCover = new JButton("Cover");
		btnCover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cover();
			}
		});
		btnCover.setBounds(245, 39, 155, 37);
		getContentPane().add(btnCover);
		
		JButton btnPrint_1 = new JButton("Print");
		btnPrint_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				print();
			}
		});
		btnPrint_1.setBounds(475, 39, 155, 37);
		getContentPane().add(btnPrint_1);
		
		sourceFolderPath = new JTextField();
		sourceFolderPath.setBounds(48, 125, 206, 35);
		getContentPane().add(sourceFolderPath);
		sourceFolderPath.setColumns(10);
		
		JLabel lblSourceFolder_1 = new JLabel("Source Folder");
		lblSourceFolder_1.setBounds(48, 92, 162, 29);
		getContentPane().add(lblSourceFolder_1);
		
		JLabel lblOncalls = new JLabel("On-Calls");
		lblOncalls.setBounds(48, 184, 104, 29);
		getContentPane().add(lblOncalls);
		
		JLabel lblNextOncaller = new JLabel("Next On-Caller");
		lblNextOncaller.setBounds(48, 306, 162, 29);
		getContentPane().add(lblNextOncaller);

	}

	protected Shell shell;
	private JTable tableOnCalls;
	private JTable tableNextOnCallers;
	private JTextField sourceFolderPath;

	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.openWindow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openWindow() {
		Display display = Display.getDefault();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private ArrayList<Teacher> teachers;
	ArrayList<Course> courses;
	ArrayList<OnCall> onCalls;
	public int maxOnCallsPerWeek;
	public int maxOnCallsPerMonth;
	
	public void cover() {
				// read in files here
				// simulated input start
				teachers= new ArrayList<Teacher>(); 
				teachers.add(new Teacher("KST", 2, 5));
				teachers.add(new Teacher("WWE", 0, 2));
				teachers.add(new Teacher("MIMI", 1, 1));
				teachers.add(new Teacher("TEA", 1, 1));
				teachers.add(new Teacher("CS", 2, 3));
				teachers.add(new Teacher("ME", 2, 2));
				teachers.add(new Teacher("SJD", 1, 2));
				courses= new ArrayList<Course>(); 
				courses.add(new Course(1,"Math"));
				courses.add(new Course(1,"English"));
				courses.add(new Course(2,"Sport"));
				courses.add(new Course(2,"Sport"));
				courses.add(new Course(2,"Music"));
				onCalls= new ArrayList<OnCall>(); 
				onCalls.add(new OnCall(teachers.get(0),courses.get(0)));
				onCalls.add(new OnCall(teachers.get(1),courses.get(1)));
				onCalls.add(new OnCall(teachers.get(2),courses.get(2)));
				onCalls.add(new OnCall(teachers.get(3),courses.get(3)));
				onCalls.add(new OnCall(teachers.get(4),courses.get(4)));
				maxOnCallsPerWeek = 2;
				maxOnCallsPerMonth = 5;
				//simulated input end
				
				String [][] data1 = formatOnCallData();
				DefaultTableModel defaultTableModel1 = new DefaultTableModel (data1, headersOnCalls);
				tableOnCalls.setModel(defaultTableModel1);
				String [][] data2 = formatNextOnCallerData();
				DefaultTableModel defaultTableModel2 = new DefaultTableModel (data2, headersNextOnCallers);
				tableNextOnCallers.setModel(defaultTableModel2);

	}
	
	public void initialize() {
		//not yet implemented
		//opens the on-call tallie, so the VP can mark absent teachers.
	}
	public void print() {
		//not yet implemented
		//creates a printable version of the output
	}
	
	private String[][] formatOnCallData(){
		ArrayList<ArrayList<String>> onCallInformation = new ArrayList<ArrayList<String>>();
		for(int i = 0; i< onCalls.size();i++) {
			ArrayList<String> outputRow = new ArrayList<String>();
			outputRow.add(Integer.toString(i+1));
			outputRow.add(onCalls.get(i).onCaller.initiales);
			outputRow.add(Integer.toString(onCalls.get(i).course.periode));
			outputRow.add(onCalls.get(i).course.courseNumber);
			outputRow.add(Integer.toString(onCalls.get(i).onCaller.amountOnCallsWeek));
			outputRow.add(Integer.toString(onCalls.get(i).onCaller.amountOnCallsMonth));
			onCallInformation.add(outputRow);
		}
		return arryListTo2dArray(onCallInformation);
	}
	
	private String[][] formatNextOnCallerData(){
		ArrayList<ArrayList<String>> onCallInformation = new ArrayList<ArrayList<String>>();
		ArrayList<Teacher> teachersSorted = sortTeachers();
		for(int i = 0; i< teachers.size(); i++) {
			if (!teachers.get(i).assignetToOnCall) {
				ArrayList<String> outputRow = new ArrayList<String>();
				outputRow.add(Integer.toString(i+1));
				outputRow.add(teachersSorted.get(i).initiales);
				outputRow.add(Integer.toString(teachersSorted.get(i).amountOnCallsWeek));
				outputRow.add(Integer.toString(teachersSorted.get(i).amountOnCallsMonth));
				outputRow.add(Integer.toString(maxOnCallsPerWeek - teachersSorted.get(i).amountOnCallsWeek));
				outputRow.add(Integer.toString(maxOnCallsPerMonth - teachersSorted.get(i).amountOnCallsMonth));
				onCallInformation.add(outputRow);
			}
		}
		return arryListTo2dArray(onCallInformation);
	}
	
	private ArrayList<Teacher> sortTeachers() {
		ArrayList<Teacher> teachersSorted = new ArrayList<Teacher>();
		teachersSorted.add(teachers.get(0));
		for(int i = 1; i< teachers.size(); i++) {
			for(int n = 0; n< teachersSorted.size(); n++) {
				if(teachers.get(i).amountOnCallsMonth < teachersSorted.get(n).amountOnCallsMonth || (teachers.get(i).amountOnCallsMonth == teachersSorted.get(n).amountOnCallsMonth && teachers.get(i).amountOnCallsWeek < teachersSorted.get(n).amountOnCallsWeek)) {
					teachersSorted.add(n, teachers.get(i)); // indexes?
					break;
				}
				else if (n == teachersSorted.size()) {
					teachersSorted.add(teachers.get(i));
					break;
				}
			}
		}
		return teachersSorted;
	}
	
	private String[][] arryListTo2dArray (ArrayList<ArrayList<String>> onCallInformation) {
			    String[][] array = new String[onCallInformation.size()][];
				for (int i = 0; i < onCallInformation.size(); i++) {
						ArrayList<String> row = onCallInformation.get(i);
						array[i] = row.toArray(new String[row.size()]);
					}
				return array;
	}

}
