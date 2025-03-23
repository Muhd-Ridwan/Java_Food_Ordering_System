package FoodOrderingSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EditProfile extends javax.swing.JFrame implements MyInterface {

    private String customerId;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;
    private String dateBirth;
    /**
     * Creates new form EditProfile
     */
    public EditProfile() {
        initComponents();
    }
    
    public EditProfile(String CID){
        initComponents();
        this.customerId = CID;
    }
    
    @Override
    public void refreshData(){
        String read;
        try{
            FileReader fr = new FileReader("Customer Profile.txt");
            BufferedReader br = new BufferedReader(fr);
            
            do{
                read = br.readLine();
                if(read == null) break;
                String[] data = read.split(";");
                
                String tempCID = data[0];
                if(tempCID.equalsIgnoreCase(this.customerId)){
                    this.firstName = data[2];
                    this.lastName = data[3];
                    this.address = data[5];
                    this.phoneNumber = data[4];
                    this.email = data[6];
                    String dobCust = data[1];
                    
                    try{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate dobDate = LocalDate.parse(dobCust, formatter);
                        String formattedDOB = dobDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        this.dateBirth = formattedDOB;
                    }catch(Exception e){
                        //System.out.println("Error Parsing Date: " + e.getMessage());
                        JOptionPane.showMessageDialog(this,"There's an error in loading your birth date. Please contact administrator.", "System", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
            }while(read != null);
            
            
            CIDTxtBox.setText(this.customerId);
            FirstNameTxtBox.setText(this.firstName);
            LastNameTxtBox.setText(this.lastName);
            AddressTxtBox.setText(this.address);
            PhoneTxtBox.setText(this.phoneNumber);
            EmailTxtBox.setText(this.email);
            DOBTxtBox.setText(this.dateBirth);
//            while((read = br.readLine()) != null){
//                String[] data = read.split(";");
//                
//                String tempCID = data[0];
//                
//                if(this.customerId.equalsIgnoreCase(tempCID)){
//                    this.firstName = data[1];
//                    this.lastName = data[2];
//                    this.address = data[3];
//                    this.phoneNumber = data[5];
//                    this.email = data[6]; 
//                }
//            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error in loading your profile. Please contact administrator for this issue.", "System", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean EditCustomerData(){
        try{
            File file = new File("Customer Profile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> allData = new ArrayList<>();
            
            String line;
            
            // READ ALL LINE AND STORE IT FIRST IN ARRAY
            while((line = reader.readLine()) != null){
                String[] data = line.split(";");
                
                if(data.length >= 7 && data[0].equals(this.customerId)){
                    data[4] = String.valueOf(PhoneTxtBox.getText());
                    data[5] = String.valueOf(AddressTxtBox.getText());
                    data[6] = String.valueOf(EmailTxtBox.getText());
                }
                allData.add(String.join(";", data));
            }
            reader.close();
            
            // WRITE THE DATA EDIT INTO THE TXT FILE
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(String updatedLine : allData){
                writer.write(updatedLine);
                writer.newLine();
            }
            writer.close();
            return true;
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "There's an error in editing your profiel. Please contact admin.", "System", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        CIDTxtBox = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        EmailTxtBox = new javax.swing.JTextField();
        PhoneTxtBox = new javax.swing.JTextField();
        AddressTxtBox = new javax.swing.JTextField();
        LastNameTxtBox = new javax.swing.JTextField();
        FirstNameTxtBox = new javax.swing.JTextField();
        DOB = new javax.swing.JLabel();
        DOBTxtBox = new javax.swing.JTextField();
        CloseBtn = new javax.swing.JButton();
        EditBtn = new javax.swing.JButton();
        AppsNameLbl1 = new javax.swing.JLabel();
        AppsNameLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Edit Profile");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 255, 204));

        CIDTxtBox.setEditable(false);

        jLabel1.setText("Customer ID");

        jLabel2.setText("First Name");

        jLabel3.setText("Last Name");

        jLabel4.setText("Address");

        jLabel5.setText("Number Phone");

        jLabel6.setText("Email");

        LastNameTxtBox.setEditable(false);

        FirstNameTxtBox.setEditable(false);

        DOB.setText("DOB");

        DOBTxtBox.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(DOB))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DOBTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmailTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PhoneTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddressTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LastNameTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FirstNameTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CIDTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(189, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(CIDTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addComponent(jLabel2))
                            .addComponent(FirstNameTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addComponent(jLabel3))
                    .addComponent(LastNameTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DOB)
                    .addComponent(DOBTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(AddressTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addComponent(jLabel5))
                    .addComponent(PhoneTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(EmailTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        CloseBtn.setText("Close");
        CloseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseBtnActionPerformed(evt);
            }
        });

        EditBtn.setText("Edit");
        EditBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditBtnActionPerformed(evt);
            }
        });

        AppsNameLbl1.setFont(new java.awt.Font("STCaiyun", 1, 48)); // NOI18N
        AppsNameLbl1.setText("JOM");

        AppsNameLbl.setFont(new java.awt.Font("STCaiyun", 1, 48)); // NOI18N
        AppsNameLbl.setText("MAKAN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(CloseBtn)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(EditBtn)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(AppsNameLbl)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addComponent(AppsNameLbl1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(80, 80, 80))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(16, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(AppsNameLbl1)
                        .addGap(69, 69, 69)
                        .addComponent(AppsNameLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(EditBtn)
                .addGap(11, 11, 11)
                .addComponent(CloseBtn)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        this.setLocationRelativeTo(null);
        refreshData();
    }//GEN-LAST:event_formWindowActivated

    private void CloseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseBtnActionPerformed
        this.setVisible(false);
        CustomerDashboard CD = new CustomerDashboard(this.customerId);
        CD.setVisible(true);
    }//GEN-LAST:event_CloseBtnActionPerformed

    private void EditBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditBtnActionPerformed
        if(EditCustomerData() == true){
            JOptionPane.showMessageDialog(this, "Your information has been updated.", "System", JOptionPane.INFORMATION_MESSAGE);
        }else if(EditCustomerData() == false){
            JOptionPane.showMessageDialog(this, "There's an error in updating your profile. Please contact admin.", "System", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_EditBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditProfile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AddressTxtBox;
    private javax.swing.JLabel AppsNameLbl;
    private javax.swing.JLabel AppsNameLbl1;
    private javax.swing.JTextField CIDTxtBox;
    private javax.swing.JButton CloseBtn;
    private javax.swing.JLabel DOB;
    private javax.swing.JTextField DOBTxtBox;
    private javax.swing.JButton EditBtn;
    private javax.swing.JTextField EmailTxtBox;
    private javax.swing.JTextField FirstNameTxtBox;
    private javax.swing.JTextField LastNameTxtBox;
    private javax.swing.JTextField PhoneTxtBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
