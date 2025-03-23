package FoodOrderingSystem;

import Utility.File_Manager.CreateFileInfo;

import Utility.File_Manager;
import Utility.File_Manager.CreateFileInfo;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Admin {

    public boolean generateCustomerReceipt(String customerID, String date, String receiptContent) {
	try {
	    // Define the folder and file name
	    String folderPath = "Customer_" + customerID +  "/" ;
	    String fileName = customerID + "_" + date + ".txt"; // Example format: C01_18-02-2025.txt

	    // Check if file exists
	    File_Manager.DirInfo fileInfo = File_Manager.fileHandlerGetdirs(folderPath, fileName);
	    if (fileInfo.isFileExist) {
		return true; // File already exists
	    }

	    // If file doesn't exist, create a new file with the receipt content
	    File_Manager.CreateFileInfo newFile = File_Manager.fileHandlerCreate(folderPath, fileName, receiptContent);

	    return newFile.isFilepathCreated; // Return true if the file was successfully created

	} catch (IOException e) {
	    JOptionPane.showMessageDialog(null, "Error generating customer receipt: " + e.getMessage());
	    return false;
	}
    }
}
