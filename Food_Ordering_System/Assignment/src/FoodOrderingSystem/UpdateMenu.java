package FoodOrderingSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class UpdateMenu extends javax.swing.JFrame {

    private String vendorId;
    private String imagePath;
    private String foodId;
    /**
     * Creates new form UpdateMenu
     */
    public UpdateMenu() {
        initComponents();
    }
    
    public UpdateMenu(String VendorId){
        initComponents();
        this.vendorId = VendorId;
        VendorIdTxtBox.setText(this.vendorId);
        newFoodId();
        FoodIdTxtBox.setText(this.foodId);
    }
    
    public UpdateMenu(String VendorId, String FoodId){
        initComponents();
        this.vendorId = VendorId;
        this.foodId = FoodId;
        readFromMenu();
    }
    
    private void newFoodId(){
        try(BufferedReader br = new BufferedReader(new FileReader("Menu.txt"))){
            String read;
            int rowCount = 0;
            
            while((read = br.readLine()) != null){
                rowCount++;
            }
            
            this.foodId = "F" + String.format("%03d", rowCount + 1);
            
        }catch(IOException e){
            System.out.println("Error in setting food id");
        }
    }
    
    private void readFromMenu(){
        try(BufferedReader br = new BufferedReader(new FileReader("Menu.txt"))){
            String read;
            
            
            boolean foundVendor = false;
            
            while((read = br.readLine()) != null){
                String[] data = read.split(";");
                
                String tempVendorId = data[0];
                String FoodID = data[1];
                String Price = data[3];
                String FoodName = data[2];
                String Desc = data[4];
                String avail = data[5];
                String IMG = data[6];
                
                if(this.vendorId.equalsIgnoreCase(tempVendorId) && this.foodId.equalsIgnoreCase(FoodID)){
                    foundVendor = true;
                    
                    VendorIdTxtBox.setText(this.vendorId);
                    FoodIdTxtBox.setText(this.foodId);
                    AmountTxtBox.setText(Price);
                    FoodNameTxtBox.setText(FoodName);
                    DescTxtBox.setText(Desc);
                    StatusTxtBox.setText(avail);
                    if(ImagePathTxtBox != null){
                        ImagePathTxtBox.setText(IMG);
                        ImagePathTxtBox.revalidate();
                        ImagePathTxtBox.repaint();
                        System.out.println("Image Path Set:" + ImagePathTxtBox.getText());
                    }else{
                        System.out.println("Error in imagepathtxtbox.");
                    }
                }
            }
            
            if(!foundVendor){
                JOptionPane.showMessageDialog(this, "You doesn't have any menu yet, please add a menu to see your list of menu", "System", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }catch(IOException e){
            
        }
    }

    private void readImagePath(){
        //OPEN FILE CHOOSER
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));
        
        int returnValue = fileChooser.showOpenDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            this.imagePath = selectedFile.getAbsolutePath(); // GETTING THE FILE PATH
//            ImagePathTxtBox.setText(imagePath);
            //copyPathToClipboard(imagePath);
        }
    }
    
    public boolean EditMenuData(){
        try{
            File file = new File("Menu.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> allData = new ArrayList<>();
            
            String line;
            
            // READ ALL LINE AND STORE IT FIRST IN ARRAY
            while((line = reader.readLine()) != null){
                String[] data = line.split(";");
                
                if(data.length >= 5 && data[0].equals(this.vendorId) && data[1].equalsIgnoreCase(this.foodId)){
                    data[2] = String.valueOf(FoodNameTxtBox.getText());
                    data[3] = String.valueOf(AmountTxtBox.getText());
                    data[4] = String.valueOf(DescTxtBox.getText());
                    data[5] = String.valueOf(StatusTxtBox.getText());
                    data[6] = String.valueOf(ImagePathTxtBox.getText());
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
//            JOptionPane.showMessageDialog(this, "There's an error in editing your menu. Please contact admin.", "System", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private boolean AddMenu(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("Menu.txt", true));
                PrintWriter pw = new PrintWriter(bw)){
            
            pw.write(
                    this.vendorId + ";" +
                    this.foodId + ";" +                    
                    FoodNameTxtBox.getText() + ";" +
                    AmountTxtBox.getText() + ";" +
                    DescTxtBox.getText() + ";" +
                    StatusTxtBox.getText() + ";" +
                    ImagePathTxtBox.getText() + "\n"
            );
            
            return true;            
        }catch(IOException e){
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        VendorIdTxtBox = new javax.swing.JTextField();
        FoodIdTxtBox = new javax.swing.JTextField();
        AmountTxtBox = new javax.swing.JTextField();
        FoodNameTxtBox = new javax.swing.JTextField();
        DescTxtBox = new javax.swing.JTextField();
        StatusTxtBox = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ImagePathTxtBox = new javax.swing.JTextField();
        AddImageBtn = new javax.swing.JButton();
        CloseBtn = new javax.swing.JButton();
        AddBtn = new javax.swing.JButton();
        UpdateBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Add & Update Menu");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setText("Vendor ID   :");

        jLabel2.setText("Food ID       :");

        jLabel3.setText("Food Name :");

        jLabel4.setText("Amount       :");

        jLabel5.setText("Status          :");

        jLabel6.setText("Description :");

        VendorIdTxtBox.setEditable(false);

        FoodIdTxtBox.setEditable(false);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setText("Add/Update Menu");

        jLabel7.setText("Image         :");

        ImagePathTxtBox.setEditable(false);
        ImagePathTxtBox.setMaximumSize(new java.awt.Dimension(64, 22));

        AddImageBtn.setText("Add Image");
        AddImageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddImageBtnActionPerformed(evt);
            }
        });

        CloseBtn.setText("Close");
        CloseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseBtnActionPerformed(evt);
            }
        });

        AddBtn.setText("Add");
        AddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBtnActionPerformed(evt);
            }
        });

        UpdateBtn.setText("Update");
        UpdateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateBtnActionPerformed(evt);
            }
        });

        //JTextArea ImagePathTxtBox = new JTextArea(2, 30);
        //ImagePathTxtBox.setLineWrap(true);
        //ImagePathTxtBox.setWrapStyleWord(true);
        //ImagePathTxtBox.setEditable(false);
        //JScrollPane imageScrollPane = new JScrollPane(ImagePathTxtBox);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(AddBtn)
                                .addGap(139, 139, 139)
                                .addComponent(UpdateBtn))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(FoodIdTxtBox)
                                    .addComponent(AmountTxtBox)
                                    .addComponent(FoodNameTxtBox)
                                    .addComponent(VendorIdTxtBox, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ImagePathTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(StatusTxtBox)
                                    .addComponent(DescTxtBox))
                                .addGap(44, 44, 44)
                                .addComponent(AddImageBtn)))))
                .addGap(169, 169, 169))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CloseBtn)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CloseBtn)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(VendorIdTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(FoodIdTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(AmountTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(FoodNameTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(DescTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(StatusTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(ImagePathTxtBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AddImageBtn))
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UpdateBtn)
                            .addComponent(AddBtn))
                        .addGap(22, 22, 22))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddImageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddImageBtnActionPerformed
        readImagePath();
        ImagePathTxtBox.setText(this.imagePath);
    }//GEN-LAST:event_AddImageBtnActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        this.setLocationRelativeTo(null);
//        readFromMenu();
    }//GEN-LAST:event_formWindowActivated

    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed
        if(AddMenu()){
            JOptionPane.showMessageDialog(this, "Successfully add your new item.", "System", JOptionPane.INFORMATION_MESSAGE);            
        }else{
            JOptionPane.showMessageDialog(this, "There's an error in adding your item. Contact administrator", "System", JOptionPane.ERROR_MESSAGE);
        }
        newFoodId();
        FoodIdTxtBox.setText(this.foodId);
        AmountTxtBox.setText("");
        DescTxtBox.setText("");
        StatusTxtBox.setText("");
        ImagePathTxtBox.setText("");
    }//GEN-LAST:event_AddBtnActionPerformed

    private void UpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateBtnActionPerformed
        if(EditMenuData() == true){
            JOptionPane.showMessageDialog(this, "Your menu information has been updated.", "System", JOptionPane.INFORMATION_MESSAGE);
        }else if(EditMenuData() == false){
            JOptionPane.showMessageDialog(this, "There's an error in updating your menu. Please contact admin.", "System", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_UpdateBtnActionPerformed

    private void CloseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseBtnActionPerformed
        ManageItem MI = new ManageItem(this.vendorId);
        MI.setVisible(true);
        this.setVisible(false);
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
            java.util.logging.Logger.getLogger(UpdateMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton AddImageBtn;
    private javax.swing.JTextField AmountTxtBox;
    private javax.swing.JButton CloseBtn;
    private javax.swing.JTextField DescTxtBox;
    private javax.swing.JTextField FoodIdTxtBox;
    private javax.swing.JTextField FoodNameTxtBox;
    private javax.swing.JTextField ImagePathTxtBox;
    private javax.swing.JTextField StatusTxtBox;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JTextField VendorIdTxtBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables
}
