package FoodOrderingSystem;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ManageItem extends javax.swing.JFrame {
    String filename;
    private String vendorId;
    private DefaultTableModel tableModel;
    private String foodId;
    private boolean MessageShown = false;
    
    /**
     * Creates new form ManageItem1
     */
    public ManageItem() {
        this.filename = "Menu.txt";
        initComponents();
        initializeTableModel();
        addRowSelectionListener();
        addButtonListeners(); 

    }
    
    public ManageItem(String VendorId){
        this.filename = "Menu.txt";
        initComponents();
        initializeTableModel();
        addRowSelectionListener();
        addButtonListeners();
        this.vendorId = VendorId;
    }
    
    private void initializeTableModel(){
        tableModel = (DefaultTableModel) VendorTable.getModel();
    }
    
    
    private void readMenuTxt(){
        try(BufferedReader br = new BufferedReader(new FileReader("Menu.txt"))){
            String read;
            
//            DefaultTableModel model = (DefaultTableModel) VendorTable.getModel();
//            if(model == null){
//                System.out.println("Vendor Table model not initialized");
//                return;
//            }
            tableModel.setRowCount(0);
            
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
                
                if(this.vendorId.equalsIgnoreCase(tempVendorId)){
                    foundVendor = true;
                    
                    tableModel.addRow(new Object[]{
                        this.vendorId, FoodID, Price, FoodName, Desc, avail
                    });
                }
            }
            
            if(!foundVendor && !MessageShown){
                MessageShown = true;
                JOptionPane.showMessageDialog(this, "You doesn't have any menu yet, please add a menu to see your list of menu", "System", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }catch(IOException e){
            System.out.println("Read Menu TXT Error");
        }
    }
    
    private void addRowSelectionListener(){
        VendorTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e)->{
            if(!e.getValueIsAdjusting() && VendorTable.getSelectedRow() != -1){
                int selectedRow = VendorTable.getSelectedRow();               
                if(selectedRow != -1) {
                    this.foodId = (String) VendorTable.getValueAt(selectedRow,1);
                    System.out.println(foodId);
                }
            }
    });
    }
    
    private void deleteRowFromFile(String fileName, String selectedVendorID, int columnIndex){
        File inputFile = new File(fileName);
        List<String> lines = new ArrayList<>();
        
        // NO NEED TO CLOSE THE br BECAUSE WHEN USING try-with-resources, it automatically closes.
        try(BufferedReader br = new BufferedReader(new FileReader(inputFile))){            
            String line;
            
            while((line = br.readLine()) != null){
                String[] columns = line.split(";");
                if(columns.length > columnIndex && !columns[columnIndex].equals(selectedVendorID)){
                    lines.add(line);
                }
            }
        }catch(IOException e){
            System.out.println("Error in reading file: " + e.getMessage());
            return;
        }
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(inputFile))){
            for(String modifiedLine : lines){
                bw.write(modifiedLine);
                bw.newLine();
            }
//            FileWriter fw = new FileWriter(fileName);
//            BufferedWriter bw = new BufferedWriter(fw);
            
            //THE COLON (:) SEPARATE THE LOOP VARIABLE = FOR EACH LOOP
//            for(String modifiedLine : lines){
//                bw.write(modifiedLine);
//                bw.newLine();
//            }
//            bw.close();
        }catch(IOException e){
            System.out.println("Error");
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

        RemoveItem = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        AddItem = new javax.swing.JButton();
        UpdateItem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        VendorTable = new javax.swing.JTable();
        CloseBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Manage Item");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        RemoveItem.setText("Remove Item");
        RemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveItemActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Vendor Name");

        AddItem.setText("Add Item");
        AddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddItemActionPerformed(evt);
            }
        });

        UpdateItem.setText("Update Item");
        UpdateItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateItemActionPerformed(evt);
            }
        });

        VendorTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Vendor ID", "Food ID", "Price", "Food Name", "Description", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        VendorTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(VendorTable);
        if (VendorTable.getColumnModel().getColumnCount() > 0) {
            VendorTable.getColumnModel().getColumn(0).setResizable(false);
            VendorTable.getColumnModel().getColumn(1).setResizable(false);
            VendorTable.getColumnModel().getColumn(2).setResizable(false);
            VendorTable.getColumnModel().getColumn(3).setResizable(false);
            VendorTable.getColumnModel().getColumn(4).setResizable(false);
            VendorTable.getColumnModel().getColumn(5).setResizable(false);
        }

        CloseBtn.setText("Close");
        CloseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(329, 329, 329))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(38, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(AddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(UpdateItem)
                        .addGap(23, 23, 23)
                        .addComponent(RemoveItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CloseBtn))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UpdateItem)
                            .addComponent(AddItem)
                            .addComponent(RemoveItem))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(CloseBtn)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        this.setLocationRelativeTo(null);
        readMenuTxt();
    }//GEN-LAST:event_formWindowActivated

    private void AddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddItemActionPerformed
        UpdateMenu UM = new UpdateMenu(this.vendorId);
        System.out.println(this.vendorId);
        this.setVisible(false);
        UM.setVisible(true);
    }//GEN-LAST:event_AddItemActionPerformed

    private void UpdateItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateItemActionPerformed
        readMenuTxt();
        if(VendorTable.getRowCount() > 0){
            UpdateMenu UM = new UpdateMenu(this.vendorId, this.foodId);
            this.setVisible(false);
            UM.setVisible(true);
        }
//        UpdateMenu UM = new UpdateMenu(this.vendorId, this.foodId);
//        this.setVisible(false);
//        UM.setVisible(true);
    }//GEN-LAST:event_UpdateItemActionPerformed

    private void RemoveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveItemActionPerformed
        if(this.foodId != null && !this.foodId.isEmpty()){
            deleteRowFromFile("Menu.txt", this.foodId, 1);
            JOptionPane.showMessageDialog(this, "Successfully remove the item.", "System", JOptionPane.INFORMATION_MESSAGE);
        }else if(this.foodId == null){
            System.out.println("No Item selected for deleting.");
            JOptionPane.showMessageDialog(this, "Cannot delete the item. Please ask admin to remove it.", "System" ,JOptionPane.ERROR_MESSAGE);
        }
        readMenuTxt();
    }//GEN-LAST:event_RemoveItemActionPerformed

    private void CloseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseBtnActionPerformed
        VendorHome VH = new VendorHome(this.vendorId);
        VH.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_CloseBtnActionPerformed

    /**
     * @param args
     */
    private void AddItem() {
        // Add a new row with default values
        tableModel.addRow(new Object[]{"", "", "", "", "", ""});
        saveToFile("Menu.txt"); // Save changes to file
        JOptionPane.showMessageDialog(this, "New item added!");
    }

    
    
    // Main method and other existing code remain the same...

    // Add action listeners to the buttons
    private void addButtonListeners() {
        AddItem.addActionListener(e -> AddItemActionPerformed(null));
//        UpdateItem.addActionListener(e -> UpdateItem());
    }

    public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(() -> {
        ManageItem manageItem = new ManageItem();
        manageItem.addButtonListeners(); // Add button listeners
        manageItem.setVisible(true);
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddItem;
    private javax.swing.JButton CloseBtn;
    private javax.swing.JButton RemoveItem;
    private javax.swing.JButton UpdateItem;
    private javax.swing.JTable VendorTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

  private void saveToFile(String filename) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                bw.write(tableModel.getValueAt(i, j).toString());
                if (j < tableModel.getColumnCount() - 1) {
                    bw.write(","); // Separate values with commas
                }
            }
            bw.newLine();
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error writing to file: " + e.getMessage());
    }
  }
}