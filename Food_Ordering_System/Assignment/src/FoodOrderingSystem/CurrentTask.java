package FoodOrderingSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class CurrentTask extends javax.swing.JFrame {
    private DefaultTableModel tableModel;
    private DefaultTableModel currentTaskTableModel;
    private String currentRunnerID;
    private String runnerId;


    /**
     * Creates new form Task
     */
    public CurrentTask() {
        initComponents();
    }
    
    public CurrentTask(String RunnerId){
        initComponents();
        this.runnerId = RunnerId;
    }
    
    private void loadCurrentTasks(String runnerID) {
        String filename = "Order Status.txt"; // Ensure the file is correctly located
        DefaultTableModel model = (DefaultTableModel) CurrentTaskTable.getModel();
        model.setRowCount(0); // Clear previous data

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                // Ensure valid row format and check conditions
                if (data.length >= 12) {  
                    String assignedRunner = data[2];  // Runner ID in column index 2
                    String status = data[11];  // Status in column index 10
                
                    if (runnerID.equalsIgnoreCase(assignedRunner) && !status.equalsIgnoreCase("Completed")) {
                        model.addRow(data);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String getNextAvailableRunner(String currentRunnerID) {
        String runnerFile = "Runner.txt";
        List<String[]> runnerList = new ArrayList<>();
        boolean foundCurrent = false;
        String firstAvailableRunner = null;

        try (BufferedReader br = new BufferedReader(new FileReader(runnerFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                if (data.length == 3) {
                    runnerList.add(data);
                    if (data[2].equalsIgnoreCase("available") && firstAvailableRunner == null) {
                        firstAvailableRunner = data[0]; // Store first available runner
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading runner file!", "File Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // Search for the next available runner **after** the current one
        for (String[] runner : runnerList) {
            if (foundCurrent && runner[2].equalsIgnoreCase("available")) {
                return runner[0]; // Return the next available runner
            }
            if (runner[0].equalsIgnoreCase(currentRunnerID)) {
                foundCurrent = true; // Start checking after this runner
            }
        }

        // If no next available runner was found, return the first available one
        return firstAvailableRunner;
    }
    
    private void declineTask() {
        String orderID = OrderIDtxt.getText().trim(); // Get the entered Order ID

        if (orderID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter an Order ID!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String orderFile = "Order Status.txt";
        List<String> updatedOrders = new ArrayList<>();
        boolean taskUpdated = false;
        String declinedRunnerID = null;

        try (BufferedReader br = new BufferedReader(new FileReader(orderFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                if (data.length >= 12 && data[0].equalsIgnoreCase(orderID)) {
                    declinedRunnerID = data[2]; // Get the current Runner ID
                    break; // Exit loop early since we found the task
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading order file!", "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (declinedRunnerID == null || declinedRunnerID.equalsIgnoreCase("null")) {
            JOptionPane.showMessageDialog(null, "This task is not assigned to a runner yet!", "Decline Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Find the next available runner
        String nextRunnerID = getNextAvailableRunner(declinedRunnerID);

        if (nextRunnerID == null) {
            JOptionPane.showMessageDialog(null, "No available runners to take this task!", "Reallocation Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(orderFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                // Check if this is the declined task
                if (data.length >= 12 && data[0].equalsIgnoreCase(orderID) && data[2].equalsIgnoreCase(declinedRunnerID)) {
                    data[2] = nextRunnerID; // Assign to the next available runner
                    taskUpdated = true;
                }

                updatedOrders.add(String.join(";", data));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading order file!", "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (taskUpdated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(orderFile))) {
                for (String order : updatedOrders) {
                    bw.write(order);
                    bw.newLine();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error updating order file!", "File Error", JOptionPane.ERROR_MESSAGE);
            }

            JOptionPane.showMessageDialog(null, "Task has been reallocated to " + nextRunnerID, "Task Reallocated", JOptionPane.INFORMATION_MESSAGE);
            OrderIDtxt.setText(""); // Clear input field
            loadCurrentTasks(declinedRunnerID); // Refresh table
        }
    }
    
    private void markAsDelivered() {
        String orderID = OrderIDtxt.getText().trim(); // Get the entered Order ID

        if (orderID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter an Order ID!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String orderFile = "Order Status.txt";
        List<String> updatedOrders = new ArrayList<>();
        boolean orderUpdated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(orderFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                // Check if this is the order to be marked as Delivered
                if (data.length >= 12 && data[0].equalsIgnoreCase(orderID)) {
                    data[11] = "Completed"; // Update the status column (index 10)
                    orderUpdated = true;
                }

                updatedOrders.add(String.join(";", data));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading order file!", "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (orderUpdated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(orderFile))) {
                for (String order : updatedOrders) {
                    bw.write(order);
                    bw.newLine();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error updating order file!", "File Error", JOptionPane.ERROR_MESSAGE);
            }

            JOptionPane.showMessageDialog(null, "Order " + orderID + " has been marked as Delivered!", "Success", JOptionPane.INFORMATION_MESSAGE);
            OrderIDtxt.setText(""); // Clear input field
            loadCurrentTasks("ALL"); // Refresh table (pass "ALL" to reload everything)
        } else {
            JOptionPane.showMessageDialog(null, "Order ID not found or already delivered!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String findAvailableRunner(String runnerFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(runnerFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 3 && data[2].equalsIgnoreCase("available")) { // Check if runner is available
                    return data[0]; // Return the first available Runner ID
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading runner file!", "File Error", JOptionPane.ERROR_MESSAGE);
        }
        return null; // No available runner found
    }
    
    
    //////////////ORDER PART ///////////////////
    private void allocateTasksToRunner() {
        String runnerFile = "Runner.txt"; // File storing runner info
        String orderFile = "Order Status.txt"; // File storing order info
        List<String> updatedOrders = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(orderFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                // Check if Runner ID is null (task unassigned)
                if (data.length >= 12 && (data[2].equalsIgnoreCase("null") || data[2].isEmpty())) {
                    String availableRunner = findAvailableRunner(runnerFile);
                
                    if (availableRunner != null) {
                        data[2] = availableRunner; // Assign the available runner to this order
                    } else {
                        JOptionPane.showMessageDialog(null, "No available runners at the moment!", "Allocation Failed", JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                }
            
                // Rebuild the line and store it in updatedOrders
                updatedOrders.add(String.join(";", data));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading order file!", "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Write the updated orders back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(orderFile))) {
            for (String order : updatedOrders) {
                bw.write(order);
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error updating order file!", "File Error", JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(null, "Tasks have been allocated to available runners!", "Task Allocation", JOptionPane.INFORMATION_MESSAGE);
    }
    
     private void filterTableByOrderID() {
        String searchOrderID = OrderIDtxt.getText().trim(); // Get text from the input field
        DefaultTableModel model = (DefaultTableModel) CurrentTaskTable.getModel();
        DefaultTableModel filteredModel = new DefaultTableModel(); 

        if (searchOrderID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter an Order ID!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Copy column names to the new model
        for (int i = 0; i < model.getColumnCount(); i++) {
            filteredModel.addColumn(model.getColumnName(i));
        }

        // Filter table data based on entered Order ID
        for (int i = 0; i < model.getRowCount(); i++) {
            String orderID = (String) model.getValueAt(i, 0); // Assuming Order ID is in the first column
            if (orderID.equalsIgnoreCase(searchOrderID)) {
                Object[] row = new Object[model.getColumnCount()];
                for (int j = 0; j < model.getColumnCount(); j++) {
                    row[j] = model.getValueAt(i, j);
                }
                filteredModel.addRow(row);
            }
        }

        // Update table with filtered data
        CurrentTaskTable.setModel(filteredModel);
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
        DeclineButton = new javax.swing.JButton();
        DeliveredButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        CurrentTaskTable = new javax.swing.JTable();
        RefreshButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        OrderIDtxt = new javax.swing.JTextField();
        CloseBtn = new javax.swing.JButton();
        SearchBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Current Task");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setText("Current Task");

        DeclineButton.setText("Decline");
        DeclineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeclineButtonActionPerformed(evt);
            }
        });

        DeliveredButton.setText("Delivered");
        DeliveredButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeliveredButtonActionPerformed(evt);
            }
        });

        CurrentTaskTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Order ID", "VendorID", "DRID", "CustomerID", "Year", "Month", "Day", "Items", "Amount", "DeliveryFee", "Number", "Status", "Remark"
            }
        ));
        jScrollPane1.setViewportView(CurrentTaskTable);

        RefreshButton.setText("Refresh");
        RefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("OrderID:");

        CloseBtn.setText("Close");
        CloseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseBtnActionPerformed(evt);
            }
        });

        SearchBtn.setText("Search");
        SearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(55, 55, 55)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(OrderIDtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DeclineButton)
                        .addGap(18, 18, 18)
                        .addComponent(DeliveredButton)
                        .addGap(18, 18, 18)
                        .addComponent(RefreshButton)
                        .addGap(31, 31, 31)
                        .addComponent(SearchBtn)
                        .addGap(0, 238, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(CloseBtn)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DeclineButton)
                            .addComponent(DeliveredButton)
                            .addComponent(RefreshButton)
                            .addComponent(jLabel2)
                            .addComponent(OrderIDtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SearchBtn))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(CloseBtn)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DeclineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeclineButtonActionPerformed
        declineTask();
    }//GEN-LAST:event_DeclineButtonActionPerformed

    private void DeliveredButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeliveredButtonActionPerformed
        markAsDelivered();
    }//GEN-LAST:event_DeliveredButtonActionPerformed

    private void RefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshButtonActionPerformed
        loadCurrentTasks(this.runnerId); 
    }//GEN-LAST:event_RefreshButtonActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowActivated

    private void CloseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseBtnActionPerformed
        HomepageRunner HR = new HomepageRunner(this.runnerId);
        this.setVisible(false);
        HR.setVisible(true);
    }//GEN-LAST:event_CloseBtnActionPerformed

    private void SearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBtnActionPerformed
        filterTableByOrderID();
    }//GEN-LAST:event_SearchBtnActionPerformed

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
            java.util.logging.Logger.getLogger(CurrentTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CurrentTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CurrentTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CurrentTask.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CurrentTask().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseBtn;
    private javax.swing.JTable CurrentTaskTable;
    private javax.swing.JButton DeclineButton;
    private javax.swing.JButton DeliveredButton;
    private javax.swing.JTextField OrderIDtxt;
    private javax.swing.JButton RefreshButton;
    private javax.swing.JButton SearchBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
