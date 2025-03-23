package FoodOrderingSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

public class OrderHistory extends javax.swing.JFrame implements MyInterface{

    private String cid;
    private String orderIdCompleted;
    private String vendorId;
    private String runnerId;
    private String foodName;
    private String remarks;
    private String year;
    private String month;
    private String day;
    private String amount;
    private String deliveryFees;
    private String totalFood;
    private String status;
    private double creditAmount;
    private String DRID;
    private String orderId;
    private String selectedHistoryId;
    private String runnerIdSelected;
    private String selectedVendorId;
    private String customerName;

    
    public String getCustomerName(){
        return customerName;
    }
    
    public void setCustomerName(String CustomerName){
        this.customerName = CustomerName;
    }
    
    private void readCustomerName(){
        try(BufferedReader br = new BufferedReader(new FileReader("Customer Profile.txt"))){
            String read;
            
            while((read = br.readLine()) != null){
                String[] data = read.split(";");
                
                
                if(this.cid.equalsIgnoreCase(data[0])){
                    setCustomerName(data[2]);
                    System.out.println("Customer Name: " + getCustomerName());
                    break;
                }
            }
            
        }catch(IOException e){
            System.out.println("Error reading customer name.");
        }
    }
    
    // DATE
    LocalDate currentDate = LocalDate.now();
    int dayReOrder = currentDate.getDayOfMonth();
    int monthReOrder = currentDate.getMonthValue();
    int yearReOrder = currentDate.getYear();
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = currentDate.format(formatDate);

    // TIME
    LocalTime currentTime = LocalTime.now();
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss a");
    String formattedTime = currentTime.format(formatTime);
    
    /**
     * Creates new form OrderHistory
     */
    public OrderHistory() {
        initComponents();
        RowSelectionListener();
    }
    
    public OrderHistory(String CID){
        initComponents();
        this.cid = CID;
        RowSelectionListener();
        readCustomerName();
    }

    @Override
    public void refreshData(){
        try{
            Object[] options = {"Ok", "Close"};
            String read;
            String fileName = "Order Status.txt";
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            
            // START USE THE JTABLE
            DefaultTableModel model = (DefaultTableModel) OrderHistoryTable.getModel();
            model.setRowCount(0);
            
            // TO CHECK THE CUSTOMER ID. IF DOESN'T HAVE WILL EQUAL FALSE
            boolean found = false;
            
            while((read = br.readLine()) != null){
                String[] data = read.split(";");
                
                String tempCustId = data[3];
                this.orderIdCompleted = data[0];
//                this.historyId = data[0];
                this.vendorId = data[1];
                this.runnerId = data[2];
                this.year = data[4];
                this.month = data[5];
                this.day = data[6];
                this.foodName = data[7];
                this.amount = data[8];
                this.deliveryFees = data[9];
                this.totalFood = data[10];
//                this.status = data[11];
                String temporaryStatusData = data[11];
                this.remarks = data[12];
                
                // EXTRACT THE HISTORY WITH SAME CUSTOMER ID
                if(this.cid.equalsIgnoreCase(tempCustId) && (temporaryStatusData.equalsIgnoreCase("Completed") || temporaryStatusData.equalsIgnoreCase("Cancelled"))){
                    found = true;
                    //ADD DATA TO TABLE
                    model.addRow(new Object[]{
                        this.orderIdCompleted, this.vendorId, this.runnerId, this.year, this.month, this.day, this.foodName, this.amount, this.totalFood, this.remarks
                    });                                   
                }
            }
            br.close();
            if(!found){
                int response = JOptionPane.showOptionDialog(null, "There is no order history yet.", "System", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);
                if(response == 0 || response == 1){
                    this.setVisible(false);
                    CustomerDashboard CD = new CustomerDashboard(this.cid);
                    CD.setVisible(true);
                }
            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Please contact administrator.", "System", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

     private void addDataToTxn(double Amount){
        
        try{
            FileReader fr = new FileReader("Transaction.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            int rowCount = 0;
            
            while((line = br.readLine()) != null){
                rowCount++;
//                if(rowCount != 0){
//                    this.txnId = "TXN" + String.format("%03d", rowCount + 1);
//                }else if(rowCount > 0){
//                    this.txnId = "TXN" + String.format("%03d", rowCount + 1);
//                }
            }
            br.close();
            
            String txnId = "TXN" + String.format("%03d", rowCount + 1);
                
            FileWriter fw = new FileWriter("Transaction.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.write(
                    txnId + ";" +
                    this.cid + ";" +                        
                    formattedDate + ";" +
                    formattedTime + ";" +
                    Amount + ";" +
                    "Debit" + "\n"
            );
            pw.close();
            bw.close();
            fw.close();
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "506 There's an error occured. Your order has been submitted to vendor. Please contact administrator for this error.", "System", JOptionPane.ERROR_MESSAGE);
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
                
                if(data.length >= 7 && data[0].equals(this.cid)){
                    data[7] = String.valueOf(updatedCreditAmount);
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
    
    
    // USE TO STORE THE VALUE OF CRED AMOUNT OF CUSTOMER
    private void readDataFromCustomerProfile(){
        try{
            String read;
            String fileName = "Customer Profile.txt";
            Object[] options = {"Ok", "Close"};
            FileReader fr = new FileReader(fileName);
            
            // Prevent Glitch from file use BufferedReader
            BufferedReader br = new BufferedReader(fr);
                      
            int rowIndex = 0;
            boolean foundVendor = false;
            
            List<String> allData = new ArrayList<>();
            
            while((read = br.readLine()) != null){
                allData.add(read);
            }
            
            boolean founderCustomer = false;
            
            for (String line : allData){
                String[] data = line.split(";");
                
                if(data.length < 8) continue;               
                    String custIds = data[0];
                    String credAmnt = data[7];
                    
                    if(custIds.equals(this.cid)){
                        System.out.println(credAmnt);
                        founderCustomer = true;
                        this.creditAmount = Double.parseDouble(credAmnt);
                        System.out.println(creditAmount);
                    }
            }
            if(!founderCustomer){
                int response = JOptionPane.showOptionDialog(this,"No customer record found. Please register an account first.", "System", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options,
                            options[0]);
//                        if(response == 0 || response == 1) {
//                            dispose();
//                        }
            }
            br.close();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, e, "System", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void getAvailableRunnerId(){
        try(BufferedReader runnerReader = new BufferedReader(new FileReader("Runner.txt"))) {
            String line;
            
            while((line = runnerReader.readLine()) != null) {
                String[] runnerColumns = line.split(";");
                String tempRunnerId = runnerColumns[0];
                String runnerStat = runnerColumns[2];
                
                //IF THE RUNNER ACTIVE
                if("Available".equalsIgnoreCase(runnerStat)) {
                    this.DRID = tempRunnerId;
                }
//                }else{
//                    JOptionPane.showMessageDialog(null, "No available runner at the moment !", "System", JOptionPane.INFORMATION_MESSAGE);
//                }
            }
        }catch(IOException e) {
            JOptionPane.showMessageDialog(null, "No available runner at the moment !", "System", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Error reading from Runner.txt" + e.getMessage());
        }
    }

    private void RowSelectionListener(){
        OrderHistoryTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e)->{
            if(!e.getValueIsAdjusting()){
                int selectedRow = OrderHistoryTable.getSelectedRow();
                if(selectedRow != -1) {
                    selectedHistoryId = (String) OrderHistoryTable.getValueAt(selectedRow,0);
                    this.runnerIdSelected = (String) OrderHistoryTable.getValueAt(selectedRow, 2);
                    this.selectedVendorId = (String) OrderHistoryTable.getValueAt(selectedRow, 1);
                    System.out.println("Runner ID SELECTED: " + runnerIdSelected);
                    System.out.println("History ID SELECTED: " + selectedHistoryId);
                }
            }
    });
    }

    public boolean reOrder(){
        getAvailableRunnerId();
        int priceIndex = 5;
        int rowCount = 0;
        //SET THE CRED AMOUNT
        readDataFromCustomerProfile();
        
        // ENSURING CREDIT AMOUNT WAS SET
        if(this.creditAmount <= 0){
            JOptionPane.showMessageDialog(this, "Unable to retrieve customer credit. Please contact admin.", "System", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try(BufferedReader br = new BufferedReader(new FileReader("Order Status.txt"))){            
            
            while(br.readLine() != null){
                rowCount++;
            }         
        } catch(IOException e){
            System.out.println("Error Reading File: " + e.getMessage());
            return false;
        }
            
        this.orderId = "JM" + String.format("%03d", rowCount + 1);
            
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("Order Status.txt", true));
            PrintWriter pw = new PrintWriter(bw)){

                if(this.runnerIdSelected == null || this.runnerIdSelected.equalsIgnoreCase("null")){
                    pw.write(
                        this.orderId + ";" +
                        this.vendorId + ";" +
                        "null" + ";" +
                        this.cid + ";" +
                        yearReOrder + ";" +
                        monthReOrder + ";" +
                        dayReOrder + ";" +
                        this.foodName + ";" +
                        this.amount + ";" +
                        this.deliveryFees + ";" +
                        this.totalFood + ";" +
                        "Preparing" + ";" +
                        this.remarks + "\n"
                    );
                }else{
                    pw.write(
                        this.orderId + ";" +
                        this.vendorId + ";" +
                        this.DRID + ";" +
                        this.cid + ";" +
                        yearReOrder + ";" +
                        monthReOrder + ";" +
                        dayReOrder + ";" +
                        this.foodName + ";" +
                        this.amount + ";" +
                        this.deliveryFees + ";" +
                        this.totalFood + ";" +
                        "Preparing" + ";" +
                        this.remarks + "\n"
                    );
                }
                double totalPrice = Double.parseDouble(this.amount) + Double.parseDouble(this.deliveryFees);

                if(this.creditAmount >= Double.parseDouble(this.amount)){              
                    double updatedAmount = this.creditAmount - totalPrice;
                    updateCustomerCreditAmount(updatedAmount);

                    // ADD DATA TO TXN FILE
                    addDataToTxn(updatedAmount);  

                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient credit amount.", "System", JOptionPane.ERROR_MESSAGE);
                }
                return true;

                }catch(IOException e){
                    System.out.println("Error writing to text file: " + e.getMessage());
                    return false;
//            return false;
                    }
//        return false;
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AppsNameLbl = new javax.swing.JLabel();
        AppsNameLbl1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        OrderHistoryTable = new javax.swing.JTable();
        CloseBtn = new javax.swing.JButton();
        ReOrderBtn = new javax.swing.JButton();
        FeedbackBtn = new javax.swing.JButton();
        AppsNameLbl2 = new javax.swing.JLabel();
        AppsNameLbl3 = new javax.swing.JLabel();

        AppsNameLbl.setFont(new java.awt.Font("STCaiyun", 1, 48)); // NOI18N
        AppsNameLbl.setText("MAKAN");

        AppsNameLbl1.setFont(new java.awt.Font("STCaiyun", 1, 48)); // NOI18N
        AppsNameLbl1.setText("JOM");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Order History");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        OrderHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Vendor ID", "Runner ID", "Year", "Month", "Day", "Food Name", "Amount", "Total Food", "Remarks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        OrderHistoryTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(OrderHistoryTable);
        if (OrderHistoryTable.getColumnModel().getColumnCount() > 0) {
            OrderHistoryTable.getColumnModel().getColumn(0).setResizable(false);
            OrderHistoryTable.getColumnModel().getColumn(1).setResizable(false);
            OrderHistoryTable.getColumnModel().getColumn(2).setResizable(false);
            OrderHistoryTable.getColumnModel().getColumn(3).setResizable(false);
            OrderHistoryTable.getColumnModel().getColumn(4).setResizable(false);
            OrderHistoryTable.getColumnModel().getColumn(5).setResizable(false);
            OrderHistoryTable.getColumnModel().getColumn(6).setResizable(false);
            OrderHistoryTable.getColumnModel().getColumn(7).setResizable(false);
            OrderHistoryTable.getColumnModel().getColumn(8).setResizable(false);
            OrderHistoryTable.getColumnModel().getColumn(9).setResizable(false);
        }

        CloseBtn.setText("Close");
        CloseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseBtnActionPerformed(evt);
            }
        });

        ReOrderBtn.setBackground(new java.awt.Color(153, 255, 153));
        ReOrderBtn.setText("Re-Order");
        ReOrderBtn.setOpaque(true);
        ReOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReOrderBtnActionPerformed(evt);
            }
        });

        FeedbackBtn.setBackground(new java.awt.Color(153, 255, 255));
        FeedbackBtn.setText("Give Feedback");
        FeedbackBtn.setOpaque(true);
        FeedbackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FeedbackBtnActionPerformed(evt);
            }
        });

        AppsNameLbl2.setFont(new java.awt.Font("STCaiyun", 1, 48)); // NOI18N
        AppsNameLbl2.setText("MAKAN");

        AppsNameLbl3.setFont(new java.awt.Font("STCaiyun", 1, 48)); // NOI18N
        AppsNameLbl3.setText("JOM");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CloseBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(FeedbackBtn)
                                        .addGap(18, 18, 18)
                                        .addComponent(ReOrderBtn))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 753, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(330, 330, 330)
                                .addComponent(AppsNameLbl2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(361, 361, 361)
                                .addComponent(AppsNameLbl3)))
                        .addGap(0, 59, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AppsNameLbl3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AppsNameLbl2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ReOrderBtn)
                    .addComponent(FeedbackBtn))
                .addGap(18, 18, 18)
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
        CustomerDashboard CD = new CustomerDashboard(this.cid);
        this.setVisible(false);
        CD.setVisible(true);
    }//GEN-LAST:event_CloseBtnActionPerformed

    private void ReOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReOrderBtnActionPerformed
        if(reOrder()){
            JOptionPane.showMessageDialog(this, "Successfully ordered your item.", "System", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "There is something wrong during reorder your order. Please proceed to normal order", "System", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_ReOrderBtnActionPerformed

    private void FeedbackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FeedbackBtnActionPerformed
        Review R = new Review(this.cid, this.runnerIdSelected, this.selectedVendorId, this.orderIdCompleted, getCustomerName());
        this.setVisible(false);
        R.setVisible(true);
    }//GEN-LAST:event_FeedbackBtnActionPerformed

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
            java.util.logging.Logger.getLogger(OrderHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderHistory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AppsNameLbl;
    private javax.swing.JLabel AppsNameLbl1;
    private javax.swing.JLabel AppsNameLbl2;
    private javax.swing.JLabel AppsNameLbl3;
    private javax.swing.JButton CloseBtn;
    private javax.swing.JButton FeedbackBtn;
    private javax.swing.JTable OrderHistoryTable;
    private javax.swing.JButton ReOrderBtn;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
