package FoodOrderingSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Teoh Peng Kee
 */
public class IDGenerator {

    // Method to generate an ID based on user role
    public static String generateID(String role, String filePath) {
	String prefix = getPrefix(role);
	int nextNumber = getNextNumber(filePath, prefix);
	return prefix + String.format("%03d", nextNumber); // Format as "XXX001"
    }

    public static String generateTxID(String filePath) {
	String prefix = "TXN";
	int nextNumber = getNextNumber(filePath, prefix);
	return prefix + String.format("%03d", nextNumber); // Format as "TXN001"
    }

    public static String generateUserID(String filePath) {
	String prefix = "U";
	int nextNumber = getNextNumber(filePath, prefix);
	return prefix + String.format("%03d", nextNumber); // Format as "TXN001"
    }

    // Get prefix based on user role (Modify as needed)
    private static String getPrefix(String role) {
	switch (role.toLowerCase()) {
	    case "vendor":
		return "VD";
	    case "customer":
		return "C";
	    case "runner":
		return "DR";
	    case "admin":
		return "AD";
	    default:
		return "XX"; // Default prefix for unknown roles
	}
    }

    // Get the next available number from the file
    private static int getNextNumber(String filePath, String prefix) {
	int nextNumber = 1; // Default to 1 if file is empty or no valid entries

	try {
	    FileReader fr = new FileReader(filePath);
	    BufferedReader br = new BufferedReader(fr);
	    String line;
	    String lastLine = null;

	    // Read until the last line
	    while ((line = br.readLine()) != null) {
		lastLine = line;
	    }

	    // If file is empty, return 1
	    if (lastLine == null || lastLine.trim().isEmpty()) {
		return nextNumber;
	    }

	    // Split line by delimiter (assumed ";")
	    String[] data = lastLine.split(";");
	    if (data.length > 0) {
		String lastID = data[0].trim(); // First column (ID)

		// Check if the last ID starts with the prefix
		if (lastID.startsWith(prefix)) {
		    String numberPart = lastID.substring(prefix.length()); // Remove prefix
		    try {
			nextNumber = Integer.parseInt(numberPart) + 1; // Convert and increment
		    } catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid ID format in file: " + lastID); //System.err.println
		    }
		}
	    }
	} catch (IOException e) {
	    JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage()); // System.err.println
	}
	return nextNumber;
    }
}
