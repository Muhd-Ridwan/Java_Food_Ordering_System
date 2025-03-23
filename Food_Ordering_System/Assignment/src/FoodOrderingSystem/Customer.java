/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Teoh Peng Kee
 */
public class Customer {

    private String ID;

    public Customer(String id) {
	this.ID = id;
    }

    public double getCurrentBalance() {
	double balance = 0.0;

	try {
	    FileReader fr = new FileReader("Customer Profile.txt");
	    BufferedReader br = new BufferedReader(fr);
	    String read;

	    while ((read = br.readLine()) != null) {
		String[] data = read.split(";");

		if (data.length >= 6) { // Ensure valid data format
		    String customerID = data[0]; // Extract customer ID from file
		    double amount = Double.parseDouble(data[7]); // Amount

		    if (customerID.equals(this.ID)) { // Match transactions for this customer
			balance = amount; 
		    }
		}
	    }
	    br.close();
	} catch (IOException | NumberFormatException e) {
	    System.err.println("Error reading balance: " + e.getMessage());
	}

	return balance;
    }
}
