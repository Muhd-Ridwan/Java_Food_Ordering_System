package FoodOrderingSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

public class TopupCredit extends javax.swing.JFrame{

    
    private String customerId;
    private double amountBefore;
    private double amountAfter;
    /**
     * Creates new form TopupCredit
     */
    public TopupCredit() {
        initComponents();
        setNumericOnly(AmountTxtBox);
    }
    
    public TopupCredit(String CID){
        initComponents();
        this.customerId = CID;
        setNumericOnly(AmountTxtBox);
        System.out.println(this.customerId);
    }
    
    // DATE
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = currentDate.format(formatDate);
    
    // TIME
    LocalTime currentTime = LocalTime.now();
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss a");
    String formattedTime = currentTime.format(formatTime);

    private void setNumericOnly(JTextField textField){
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter(){
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if(string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }
            
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if(text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
    
    

    
    public void readDataFromCustomer(){
        String read;
        String temporaryCID ="";
        String temporaryAmountHold = "";
        
        try{            
            FileReader fr = new FileReader("Customer Profile.txt");
            BufferedReader br = new BufferedReader(fr);

            do{
                read = br.readLine();
                if(read == null) break;
                String[] data = read.split(";");
                
                temporaryCID = data[0];
                temporaryAmountHold = data[4];
                
                if(temporaryCID.equalsIgnoreCase(this.customerId)){
                    this.amountBefore = Double.parseDouble(temporaryAmountHold);
                    break;
                }
                
            }while (read != null);
            BalBeforeTxtBox.setText("RM " + String.format("%.2f", this.amountBefore));
            br.close();

//            while((read = br.readLine()) != null){
//                String[] data = read.split(";");
//                
//                temporaryCID = data[0];
//                temporaryAmountHold = data[4];
//            }
//            br.close();
//            System.out.println(temporaryAmountHold + temporaryCID);
//            
//            if(this.customerId.equalsIgnoreCase(temporaryCID)){
//                this.amountBefore = temporaryAmountHold;
//                BalBeforeTxtBox.setText("RM " + String.format("%.2f", temporaryAmountHold));
//            }else{
////                JOptionPane.showMessageDialog(this, "There is no profile of you. Please make sure you have an account with us or contact admin", "System", JOptionPane.ERROR_MESSAGE);
//            }
           
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "There is no profile of you. Please make sure you have an account with us or contact admin", "System", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void updateCustomerCreditAmount(double updatedCreditAmount){
        try{
            File file = new File("Customer Profile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> allData = new ArrayList<>();
            
            String line;
            
            //READ ALL LINE AND UPDATE THE AMOUNT
            while((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                
                if(data.length >= 7 && data[0].equals(this.customerId)){
                    data[4] = String.valueOf(updatedCreditAmount);
                }
                allData.add(String.join(";", data));
            }
            
            reader.close();
            
            //WRITE BACK THE UPDATED DATA TO TXT FILE
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(String updatedLine : allData){
                writer.write(updatedLine);
                writer.newLine();
            }
            
            writer.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error in updating credit amount: " + e.getMessage());
        }
    }
    
    public boolean addDataToTxn(){
        String line;
        int rowCount = 0;
        String TxnId;
        String amount;
        
        try{
            FileReader fr = new FileReader("Transaction.txt");
            BufferedReader br = new BufferedReader(fr);
            
            // SETTING UP TRANSACTION ID
            while((line = br.readLine()) != null){
                rowCount++;
            }
            br.close();
            
            TxnId = "TXN" + String.format("%03d", rowCount + 1);
            
            FileWriter fw = new FileWriter("Transaction.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            amount = AmountTxtBox.getText();
            this.amountAfter = this.amountBefore + Double.parseDouble(amount);
            
            fw.write(
                    TxnId + ";" +
                    this.customerId + ";" +
                    formattedDate + ";" +
                    formattedTime + ";" +
                    amount + ";" +
                    "Credit" + "\n"
            );
            fw.close();
            //BalAfterTxtBox.setText("RM " + String.format("%.2f", this.amountAfter));
            
            updateCustomerCreditAmount(this.amountAfter);
            return true;
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "There's an error during topup the amount. Please contact administrator", "System", JOptionPane.ERROR_MESSAGE);
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

        PaymentMethod = new javax.swing.ButtonGroup();
        CloseBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        AmountTxtBox = new javax.swing.JTextField();
        AmountLbl = new javax.swing.JLabel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        BalBeforeLbl = new javax.swing.JLabel();
        BalBeforeTxtBox = new javax.swing.JTextField();
        TopupBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        CloseBtn.setText("Close");
        CloseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseBtnActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 255, 153));

        AmountLbl.setText("Insert Amount");

        PaymentMethod.add(jRadioButton2);
        jRadioButton2.setText("TnG");

        PaymentMethod.add(jRadioButton3);
        jRadioButton3.setText("Online Banking");

        BalBeforeLbl.setText("Balance");

        BalBeforeTxtBox.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(jRadioButton3)
                        .addGap(59, 59, 59)
                        .addComponent(jRadioButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AmountLbl)
                            .addComponent(BalBeforeLbl))
                        .addGap(57, 57, 57)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(AmountTxtBox, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(BalBeforeTxtBox))))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AmountLbl)
                    .addComponent(AmountTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BalBeforeLbl)
                    .addComponent(BalBeforeTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3))
                .addContainerGap())
        );

        TopupBtn.setText("Topup");
        TopupBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TopupBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CloseBtn)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TopupBtn)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TopupBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(CloseBtn)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        this.setLocationRelativeTo(null);
        readDataFromCustomer();
    }//GEN-LAST:event_formWindowActivated

    private void TopupBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TopupBtnActionPerformed
       if(addDataToTxn() == true){
           JOptionPane.showMessageDialog(this, "The amount has successfully been credited to your account.", "System", JOptionPane.INFORMATION_MESSAGE);
       }else if(addDataToTxn() == false){
           JOptionPane.showMessageDialog(this, "Sorry your action cannot be completed at the moment.", "System", JOptionPane.INFORMATION_MESSAGE);
       }
    }//GEN-LAST:event_TopupBtnActionPerformed

    private void CloseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseBtnActionPerformed
        this.setVisible(false);
        CustomerDashboard CD = new CustomerDashboard(this.customerId);
        CD.setVisible(true);
    }//GEN-LAST:event_CloseBtnActionPerformed

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
            java.util.logging.Logger.getLogger(TopupCredit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TopupCredit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TopupCredit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TopupCredit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TopupCredit().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AmountLbl;
    private javax.swing.JTextField AmountTxtBox;
    private javax.swing.JLabel BalBeforeLbl;
    private javax.swing.JTextField BalBeforeTxtBox;
    private javax.swing.JButton CloseBtn;
    private javax.swing.ButtonGroup PaymentMethod;
    private javax.swing.JButton TopupBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    // End of variables declaration//GEN-END:variables
}
