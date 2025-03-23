package FoodOrderingSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Teoh Peng Kee
 */
public class Vendor {
    
    private String ID;
    private String firstName; 
    private String lastName; 
    private String dob; 
    private String businessName; 
    private String businessRegNum; 
    private String licenseExpiryDate;
    private String contactNum;
    private String address;
    private String email;
    
    
    public Vendor(){
    }
    
    // Getters
    public String getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDob() {
        return dob;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessRegNum() {
        return businessRegNum;
    }

    public String getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public String getContactNum() {
        return contactNum;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setBusinessRegNum(String businessRegNum) {
        this.businessRegNum = businessRegNum;
    }

    public void setLicenseExpiryDate(String licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public ArrayList<String> retrieveInfo_ByName(String vendorID, String vendorName){
	int indexIDRead = 0; 
	int indexNameRead = 4; 
	ArrayList<String> info = new ArrayList<String>(); 
	
	try{
	    FileReader fr = new FileReader("Vendor.txt");
            BufferedReader br = new BufferedReader(fr);
            String read;
            while ((read = br.readLine()) != null) {
		String[] line = read.split(";"); 
		if (line[indexIDRead].equals(vendorID) && line[indexNameRead].equals(vendorName)){
		    for(int i = 0; i <line.length; i++){
			info.add(line[i]);
		    }
		}
	    }
	    
	}catch(Exception e){
	    System.out.println(e.getMessage());
	}
	
		

	return info; 
    }
    
    
}
