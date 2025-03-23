package FoodOrderingSystem;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Review extends javax.swing.JFrame {

    
    private String cid;
    private String RunnerReviewId;
    private String VendorReviewId;
    private String runnerId;
    private String vendorId;
    private String orderId;
    private String runnerName;
    private String vendorName;
    private String customerName;
    
//     // DATE
//    LocalDate currentDate = LocalDate.now();
//    int dayReOrder = currentDate.getDayOfMonth();
//    int monthReOrder = currentDate.getMonthValue();
//    int yearReOrder = currentDate.getYear();
//    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//    String formattedDate = currentDate.format(formatDate);
    
    /**
     * Creates new form Review
     */
    public Review() {
        initComponents();
        preventEnterKeyNewLine();
    }
    
    public Review(String CID, String RunnerId, String VendorId, String OrderId, String CustomerName){
        initComponents();
        preventEnterKeyNewLine();
        this.cid = CID;
        this.runnerId = RunnerId;
        this.vendorId = VendorId;
        this.orderId = OrderId;
        
        if(readFromRunner() && readFromVendor()){
            VendorNameLbl.setText(vendorName);
            if(this.runnerId == null || this.runnerId.equalsIgnoreCase("null")){
                RunnerNameLbl.setText("No Runner");
            }else{
                RunnerNameLbl.setText(runnerName);
            }            
        }else{
            JOptionPane.showMessageDialog(this, "There's an error in loading runner & vendor name. Please ignore this issue.", "System", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void preventEnterKeyNewLine(){
        KeyAdapter KA = new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    e.consume(); //PREVENT NEW LINE
                    JTextArea textArea = (JTextArea) e.getSource();
                    textArea.append(" ");
                }
            }
        };
        
        VendorReview.addKeyListener(KA);
        RunnerReview.addKeyListener(KA);
    }

    private boolean readFromRunner(){
        
        if(this.runnerId != null && !this.runnerId.equalsIgnoreCase("null")){
            
            try(BufferedReader br = new BufferedReader(new FileReader("Runner.txt"))){
            String read;
            
            while((read = br.readLine()) != null){
                String[] data = read.split(";");
                
                String tempRunId = data[0];
                
                if(this.runnerId.equalsIgnoreCase(tempRunId)){
                    this.runnerName = data[1];
                    break;
                }
            }         
                
                return true;
            }catch(IOException e){
                return false;
            }
        }else{
            this.runnerName = "No Runner";
            return true;
        }       
    }
    
    private boolean readFromVendor(){
        try(BufferedReader br = new BufferedReader(new FileReader("vendor.txt"))){
            String read;
            
            while((read = br.readLine()) != null){
                String[] data = read.split(";");
                
                String tempVenId = data[0];
                
                if(this.vendorId.equalsIgnoreCase(tempVenId)){
                    this.vendorName = data[1];
                    break;
                }
                
            }
            
            return true;
        }catch(IOException e){
            return false;
        }
    }
    
    private void readRunnerReview(){
        try(BufferedReader br = new BufferedReader(new FileReader("Runner Review.txt"))){
            String line;
            int rowCount = 0;
            
            while((line = br.readLine()) != null){
                rowCount++;
            }
            
            this.RunnerReviewId = "RR" + String.format("%03d", rowCount + 1);
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error in reading runner review.", "System", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void readVendorReview(){
        try(BufferedReader br = new BufferedReader(new FileReader("Vendor Review.txt"))){
            String line;
            int rowCount = 0;
            
            while((line = br.readLine()) != null){
                rowCount++;
            }
            
            this.VendorReviewId = "VR" + String.format("%03d", rowCount + 1);
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error in reading vendor review.", "System", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean SubmitReviewRunner(){
        if(this.runnerId == null || this.runnerId.equalsIgnoreCase("null")){
            System.out.println("No runner review");
            RatingGroupRunner.clearSelection();
            return true;
        }else{
            String rating = "0";
            String review = "";

            if(RunnerStar1.isSelected()){
                rating = "1";
            }else if(RunnerStar2.isSelected()){
                rating = "2";
            }else if(RunnerStar3.isSelected()){
                rating = "3";
            }else if(RunnerStar4.isSelected()){
                rating = "4";
            }else if(RunnerStar5.isSelected()){
                rating = "5";
            }else if(RatingGroupRunner.getSelection() == null){
                rating = "3";
            }

            if(RunnerReview.getText().isEmpty()){
                review = "-";
            }else{
                review = RunnerReview.getText();
            }

            try(BufferedWriter bw = new BufferedWriter(new FileWriter("Runner Review.txt"));
                    PrintWriter pw = new PrintWriter(bw)){

                pw.write(
                    this.RunnerReviewId + ";" +
                    this.orderId + ";" +
                    this.runnerId + ";" +
                    rating + ";" +
                    RunnerReview.getText() + "\n"
                );

                return true;
            }catch(IOException e){
                System.out.println("" + e.getMessage());
                return false;
            }
        }
        
    }
    
    private boolean SubmitVendorReview(){
        
        String rating = "0";
        String review = "";
        
        if(Star1.isSelected()){
            rating = "1";
        }else if(Star2.isSelected()){
            rating = "2";
        }else if(Star3.isSelected()){
            rating = "3";
        }else if(Star4.isSelected()){
            rating = "4";
        }else if(Star5.isSelected()){
            rating = "5";
        }else if(RatingGroupVendor.getSelection() == null){
            rating = "3";
        }
        
        if(VendorReview.getText().isEmpty()){
            review = "-";
        }else{
            review = VendorReview.getText();
        }
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("Vendor Review.txt", true));
                PrintWriter pw = new PrintWriter(bw)){
            
            pw.write(
                   this.VendorReviewId + ";" +
                   this.orderId + ";" +
                   this.vendorId + ";" +    
                   this.vendorName + ";" +
                   this.customerName + ";" +
                   rating + ";" +
                   VendorReview.getText() + "\n"
            );
            
            return true;
        }catch(IOException e){
            System.out.println("" + e.getMessage());
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

        RatingGroupVendor = new javax.swing.ButtonGroup();
        RatingGroupRunner = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        VendorReview = new javax.swing.JTextArea();
        VendorNameLbl = new javax.swing.JLabel();
        Star1 = new javax.swing.JRadioButton();
        Star2 = new javax.swing.JRadioButton();
        Star3 = new javax.swing.JRadioButton();
        Star4 = new javax.swing.JRadioButton();
        Star5 = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        RunnerReview = new javax.swing.JTextArea();
        RunnerNameLbl = new javax.swing.JLabel();
        RunnerStar5 = new javax.swing.JRadioButton();
        RunnerStar4 = new javax.swing.JRadioButton();
        RunnerStar3 = new javax.swing.JRadioButton();
        RunnerStar2 = new javax.swing.JRadioButton();
        RunnerStar1 = new javax.swing.JRadioButton();
        CloseBtn = new javax.swing.JButton();
        SubmitBtn = new javax.swing.JButton();
        AppsNameLbl = new javax.swing.JLabel();
        AppsNameLbl1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Review");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        VendorReview.setColumns(20);
        VendorReview.setRows(5);
        jScrollPane1.setViewportView(VendorReview);

        VendorNameLbl.setText("Vendor Name");

        RatingGroupVendor.add(Star1);
        Star1.setText("1");

        RatingGroupVendor.add(Star2);
        Star2.setText("2");

        RatingGroupVendor.add(Star3);
        Star3.setText("3");

        RatingGroupVendor.add(Star4);
        Star4.setText("4");

        RatingGroupVendor.add(Star5);
        Star5.setText("5");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(Star1)
                        .addGap(18, 18, 18)
                        .addComponent(Star2)
                        .addGap(18, 18, 18)
                        .addComponent(Star3)
                        .addGap(18, 18, 18)
                        .addComponent(Star4)
                        .addGap(18, 18, 18)
                        .addComponent(Star5))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(VendorNameLbl)
                            .addGap(77, 77, 77))))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(VendorNameLbl)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Star1)
                    .addComponent(Star2)
                    .addComponent(Star3)
                    .addComponent(Star4)
                    .addComponent(Star5))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        RunnerReview.setColumns(20);
        RunnerReview.setRows(5);
        jScrollPane2.setViewportView(RunnerReview);

        RunnerNameLbl.setText("Runner Name");

        RatingGroupRunner.add(RunnerStar5);
        RunnerStar5.setText("5");

        RatingGroupRunner.add(RunnerStar4);
        RunnerStar4.setText("4");

        RatingGroupRunner.add(RunnerStar3);
        RunnerStar3.setText("3");

        RatingGroupRunner.add(RunnerStar2);
        RunnerStar2.setText("2");

        RatingGroupRunner.add(RunnerStar1);
        RunnerStar1.setText("1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(RunnerStar1)
                                .addGap(18, 18, 18)
                                .addComponent(RunnerStar2)
                                .addGap(18, 18, 18)
                                .addComponent(RunnerStar3)
                                .addGap(18, 18, 18)
                                .addComponent(RunnerStar4)
                                .addGap(18, 18, 18)
                                .addComponent(RunnerStar5))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(RunnerNameLbl)))
                .addContainerGap(119, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 9, Short.MAX_VALUE)
                .addComponent(RunnerNameLbl)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RunnerStar1)
                    .addComponent(RunnerStar2)
                    .addComponent(RunnerStar3)
                    .addComponent(RunnerStar4)
                    .addComponent(RunnerStar5)))
        );

        CloseBtn.setText("Close");
        CloseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseBtnActionPerformed(evt);
            }
        });

        SubmitBtn.setText("Submit");
        SubmitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitBtnActionPerformed(evt);
            }
        });

        AppsNameLbl.setFont(new java.awt.Font("STCaiyun", 1, 48)); // NOI18N
        AppsNameLbl.setText("MAKAN");

        AppsNameLbl1.setFont(new java.awt.Font("STCaiyun", 1, 48)); // NOI18N
        AppsNameLbl1.setText("JOM");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CloseBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(SubmitBtn)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(224, 224, 224)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addComponent(AppsNameLbl1))
                                    .addComponent(AppsNameLbl))))
                        .addGap(0, 98, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AppsNameLbl1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AppsNameLbl)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SubmitBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(CloseBtn)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CloseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseBtnActionPerformed
        OrderHistory OH = new OrderHistory(this.cid);        
        OH.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_CloseBtnActionPerformed

    private void SubmitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitBtnActionPerformed
        String[] options = {"Proceed", "Back"};
        
        int response = JOptionPane.showOptionDialog(
                this,
                "Are you sure want to submit your review ?",
                "Confirm Submission",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        
        if (response == 0){
            readRunnerReview();
            readVendorReview();

            if(SubmitVendorReview() && SubmitReviewRunner()){
                JOptionPane.showMessageDialog(this, "Thank you ! Hope you enjoyed the service.", "System", JOptionPane.INFORMATION_MESSAGE);
                VendorReview.setText("");
                RunnerReview.setText("");
            }else{
                JOptionPane.showMessageDialog(this, "There's something wrong when submitting the review.", "System", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }//GEN-LAST:event_SubmitBtnActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        this.setLocationRelativeTo(null);
        System.out.println("Runner Id: " + this.runnerId);
        
        if(this.runnerId == null || this.runnerId.equalsIgnoreCase("null")){
            RunnerReview.setEditable(false);
        }
    }//GEN-LAST:event_formWindowActivated

    
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
            java.util.logging.Logger.getLogger(Review.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Review.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Review.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Review.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Review().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AppsNameLbl;
    private javax.swing.JLabel AppsNameLbl1;
    private javax.swing.JButton CloseBtn;
    private javax.swing.ButtonGroup RatingGroupRunner;
    private javax.swing.ButtonGroup RatingGroupVendor;
    private javax.swing.JLabel RunnerNameLbl;
    private javax.swing.JTextArea RunnerReview;
    private javax.swing.JRadioButton RunnerStar1;
    private javax.swing.JRadioButton RunnerStar2;
    private javax.swing.JRadioButton RunnerStar3;
    private javax.swing.JRadioButton RunnerStar4;
    private javax.swing.JRadioButton RunnerStar5;
    private javax.swing.JRadioButton Star1;
    private javax.swing.JRadioButton Star2;
    private javax.swing.JRadioButton Star3;
    private javax.swing.JRadioButton Star4;
    private javax.swing.JRadioButton Star5;
    private javax.swing.JButton SubmitBtn;
    private javax.swing.JLabel VendorNameLbl;
    private javax.swing.JTextArea VendorReview;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
