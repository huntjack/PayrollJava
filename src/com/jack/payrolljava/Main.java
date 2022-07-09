package com.jack.payrolljava;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
	
	protected ArrayList<String> first = new ArrayList<String>();
	protected ArrayList<String> last = new ArrayList<String>();
	protected ArrayList<String> middleInitial = new ArrayList<String>();
	protected ArrayList<Character> job = new ArrayList<Character>();
	protected ArrayList<Integer> age = new ArrayList<Integer>();
	protected ArrayList<String> sex = new ArrayList<String>();
	protected ArrayList<String> company = new ArrayList<String>();
	protected ArrayList<Integer> regularHours = new ArrayList<Integer>();
	protected ArrayList<Integer> penaltyNumber = new ArrayList<Integer>();
	protected ArrayList<Integer> overtimeHours = new ArrayList<Integer>();
	protected ArrayList<Float> gross = new ArrayList<Float>();
	protected ArrayList<Float> net = new ArrayList<Float>();
	protected ArrayList<Float> penalty = new ArrayList<Float>();
	protected ArrayList<Float> finalPay = new ArrayList<Float>();
	
	public float findGross(int counter) {
		float gross = 0;
        switch (job.get(counter)) {
            case 'A':
            case 'a':
                gross = (float) ((regularHours.get(counter) * 450) + overtimeHours.get(counter) * (450 * 1.25));
                break;
            case 'B':
            case 'b':
                gross = (float) ((regularHours.get(counter) * 400) + overtimeHours.get(counter) * (400 * 1.25));
                break;
            case 'C':
            case 'c':
                gross = (float) ((regularHours.get(counter) * 350) + overtimeHours.get(counter) * (350 * 1.25));
                break;

        }
        return gross;
		
	}
	
	public void setFirst (String first) {
		this.first.add(first);
	}
	
	public void setMiddleInitial (String middleInitial) {
		this.middleInitial.add(middleInitial);
	}
	
	public void setLast (String last) {
		this.last.add(last);
	}
	
	public void setJob (char job) {
		this.job.add(job);
	}
	
	public void setAge (int age) {
		this.age.add(age);
	}
	
	public int getAge(int counter) {
		return this.age.get(counter);
	}
	
	public void setSex (String sex) {
		this.sex.add(sex);
	}
	
	public String getSex (int counter) {
		return this.sex.get(counter);
	}
	
	public void setCompany (String company) {
		this.company.add(company);
	}
	
	public String getCompany(int counter) {
		return this.company.get(counter);
	}
	
	public void setRegularHours (int regularHours) {
		this.regularHours.add(regularHours);
	}
	
	public int getRegularHours (int counter) {
		return this.regularHours.get(counter);
	}
	
	public void setOvertimeHours (int overtimeHours) {
		this.overtimeHours.add(overtimeHours);
	}
	
	public int getOvertimeHours (int counter) {
		return this.overtimeHours.get(counter);
	}
	
	public void setPenaltyNumber (int penaltyNumber) {
		this.penaltyNumber.add(penaltyNumber);
	}
	
	public int getPenaltyNumber (int counter) {
		return this.penaltyNumber.get(counter);
	}
	
	public void setGross (float employeeGross) {
		this.gross.add(employeeGross);
	}
	
	public float getGross (int counter) {
		return this.gross.get(counter);
	}
	
	public float getNet (int counter) {
		return this.net.get(counter);
	}
	
	public void setNet (float employeeNet) {
		this.net.add(employeeNet);
	}
	
	public float getPenalty (int counter) {
		return this.penalty.get(counter);
	}
	
	public void setPenalty (float employeePenalty) {
		this.penalty.add(employeePenalty);
	}
	
	public float getFinalPay (int counter ) {
		return this.finalPay.get(counter);
	}
	
	public void setFinalPay (float employeeFinalPay) {
		this.finalPay.add(employeeFinalPay);
	}
	
	public String getLastName (int counter) {
		return this.last.get(counter);
	}
	
	public String getFirstName (int counter) {
		return this.first.get(counter);
	}
	
	public String getMiddle (int counter) {
		return this.middleInitial.get(counter);
	}
	
	public float findNet (int counter) {
		float net = (float) (gross.get(counter) - (gross.get(counter) * 0.05));
        return net;
    }
	
	public float findPenalty (int counter) {
		float penalty = (float) (net.get(counter) * (penaltyNumber.get(counter) * 0.12));
        return penalty;
    }
	
	public float findFinalPay (int counter) {
		float finalPay;
        finalPay = net.get(counter) - penalty.get(counter);
        return finalPay;
    }
	
	public void doCalculations (int counter) {
		this.setGross(this.findGross(counter));
		this.setNet(this.findNet(counter));
		this.setPenalty(this.findPenalty(counter));
		this.setFinalPay(this.findFinalPay(counter));
		
	}
	
	public void printData (int counter) {
		System.out.println("Name: " + this.getFirstName(counter) + 
		" " + this.getMiddle(counter) + " " + this.getLastName(counter));
    	System.out.println("Regular hours worked: " + this.getRegularHours(counter));
    	System.out.println("Overtime hours worked: " + this.getOvertimeHours(counter));
    	System.out.println("Gross wages: " + this.getGross(counter));
    	System.out.println("Wages after accounting for taxes and benefits: " + this.getNet(counter));
    	System.out.println("Penalties incurred: " + this.getPenaltyNumber(counter));
    	System.out.println("Total penalties: " + this.getPenalty(counter));
    	System.out.println("Final pay: " + this.getFinalPay(counter));
    	System.out.println();
		
	}
	
	public static void main (String[] args) {
		Main main1 = new Main();
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the total number of employees that you will be processing in this session:");
		int n = in.nextInt();
		
	    
	    for (int i = 0; i < n; i++) {
	    	in.nextLine();
	    	System.out.println("Please enter the first name of the employee:");
			main1.setFirst(in.nextLine());
			System.out.println();
			System.out.println("Please enter the middle initial of the employee:");
			main1.setMiddleInitial(in.nextLine());
			System.out.println();
			System.out.println("Please enter the last name of the employee:");
			main1.setLast(in.nextLine());
			System.out.println();
			System.out.println("Please enter a letter to designate the job of the employee. You may choose from following:");
			System.out.println("Hipster = A Hacker = B Hustler = C");
			main1.setJob(in.next().charAt(0));
			System.out.println();
			System.out.println("Please enter the age of the employee:");
			main1.setAge(in.nextInt());
			in.nextLine();
			System.out.println();
			System.out.println("Please enter the sex of the employee:");
			main1.setSex(in.nextLine());
			System.out.println();
			System.out.println("Please enter the name of the employee's company:");
			main1.setCompany(in.nextLine());
			System.out.println();
			System.out.println("Please enter the number of regular hours that the employee worked this month:");
			main1.setRegularHours(in.nextInt());
			System.out.println();
			System.out.println("Please enter the number of overtime hours that the employee worked this month:");
			main1.setOvertimeHours(in.nextInt());
			System.out.println();
			System.out.println("Please enter the number of penalties that the employee received this month:");
			main1.setPenaltyNumber(in.nextInt());
			System.out.println();
	    }
	    for (int i=0; i<n; i++) {
	    
	    	//Calculations
	    	main1.doCalculations (i);
	    }
	    
	    for (int i = 0; i < n; i++) {
	    	
	    	//Display
	    	main1.printData(i);
	
	    }
	    in.close();
	}

}
