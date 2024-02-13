/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package student.management.system.codsoft.task.pkg5;

import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Dell
 */
public class StudentManagementSystem extends javax.swing.JFrame {

    /**
     * Creates new form StudentManagementSystem
     */
    Connection conn;
    Statement stmt;
    ResultSet rs;
    PreparedStatement ps;

    String name;
    int roll;
    String gender;
    long personalNo;
    long parentNo;
    String grade;

    int searchRoll;

    public StudentManagementSystem() {
        initComponents();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagementsystem", "root", "admin@123456789");
            if (conn != null) {
                System.out.println("connection established");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void addStudent() throws Exception {
        name = nameTxt.getText().toString();
        roll = Integer.parseInt(rollTxt.getText().toString());

        if (genderSelect.getSelectedItem() == "Male") {
            gender = "Male";
        }
        if (genderSelect.getSelectedItem() == "Female") {
            gender = "Female";
        }
        personalNo = Long.parseLong(personalTxt.getText().toString());
        parentNo = Long.parseLong(parentTxt.getText().toString());
        grade = gradeTxt.getText().toString().toUpperCase();

        stmt = conn.createStatement();
        String insert = "insert into student (RollNo,Name,Gender,PersonalNo,ParentNo,Grade) values ('" + roll + "','" + name + "','" + gender + "','" + personalNo + "','" + parentNo + "','" + grade + "');";
        stmt.executeUpdate(insert);
        JOptionPane.showMessageDialog(null, "Student inserted successfully", "Student Management System", JOptionPane.INFORMATION_MESSAGE);
        clearAllFields();
    }

    private void removeStudent() throws Exception {
        searchRoll = Integer.parseInt(searchTxt.getText().toString());
        String query = "delete from student where RollNo='" + searchRoll + "';";
        stmt = conn.createStatement();
        stmt.executeUpdate(query);
        JOptionPane.showMessageDialog(null, "Student deleted successfully", "Student Management System", JOptionPane.INFORMATION_MESSAGE);
        clearAllFields();
    }

    private void searchStudent() throws Exception {
        searchRoll = Integer.parseInt(searchTxt.getText().toString());
        String query = "select * from student where RollNo='" + searchRoll + "';";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);

        if (rs.next()) {
            int roll = rs.getInt("RollNo");
            String name = rs.getString("Name");
            String gender = rs.getString("Gender");
            long personalNo = rs.getLong("PersonalNo");
            long parentNo = rs.getLong("ParentNo");
            String grade = rs.getString("Grade");

            nameTxt.setText(name);
            rollTxt.setText(Integer.toString(roll));
            genderSelect.setSelectedItem(gender);
            personalTxt.setText(Long.toString(personalNo));
            parentTxt.setText(Long.toString(parentNo));
            gradeTxt.setText(grade);
            JOptionPane.showMessageDialog(null, "Student found", "Student Management System", JOptionPane.INFORMATION_MESSAGE);
            updateBtn.setEnabled(true);
            deleteBtn.setEnabled(true);

        } else {
            JOptionPane.showMessageDialog(null, "Student not found", "Student Management System", JOptionPane.ERROR_MESSAGE);
            updateBtn.setEnabled(false);
            deleteBtn.setEnabled(false);
            clearAllFields();
        }

    }

    private void clearAllFields() {
        searchTxt.setText("");
        nameTxt.setText("");
        rollTxt.setText("");
        personalTxt.setText("");
        parentTxt.setText("");
        gradeTxt.setText("");
        genderSelect.setSelectedIndex(0);
    }

    private void updateStudent() throws Exception {
        name = nameTxt.getText().toString();
        roll = Integer.parseInt(rollTxt.getText().toString());

        if (genderSelect.getSelectedItem() == "Male") {
            gender = "Male";
        }
        if (genderSelect.getSelectedItem() == "Female") {
            gender = "Female";
        }
        personalNo = Long.parseLong(personalTxt.getText().toString());
        parentNo = Long.parseLong(parentTxt.getText().toString());
        grade = gradeTxt.getText().toString().toUpperCase();

        String query = "update student set Name='" + name + "',Gender='" + gender + "',PersonalNo='" + personalNo + "',ParentNo='" + parentNo + "',Grade='" + grade + "' where RollNo='" + roll + "';";

        ps = conn.prepareStatement(query);
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Student updated", "Student Management System", JOptionPane.INFORMATION_MESSAGE);
        clearAllFields();
    }

    private void displayStudents() throws Exception {
        DisplayStudents obj = new DisplayStudents();
        obj.show();
        clearAllFields();
    }

    private void exitApplication() {
        System.exit(0);
    }

    private void nameValidation(JTextField txt, java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (Character.isLetter(c) || evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
            txt.setEditable(true);

        } else {
            txt.setEditable(false);
            txt.setToolTipText("Please enter letters only");
        }
    }

    private void rollNoValidation(JTextField textField, java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            textField.setEditable(false);

            textField.setToolTipText("Please enter number only");
        } else {
            textField.setEditable(true);
        }

        String number = textField.getText();

        int length = number.length();

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') {

            if (length < 6) {
                textField.setEditable(true);

            } else {
                textField.setEditable(false);
            }

        } else {
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                textField.setEditable(true);
            } else {
                textField.setEditable(false);
            }
        }
    }

    private void phoneNoValidation(JTextField textField, java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            textField.setEditable(false);

            textField.setToolTipText("Please enter number only");
        } else {
            textField.setEditable(true);
        }

        String number = textField.getText();

        int length = number.length();

        if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') {

            if (length < 10) {
                textField.setEditable(true);

            } else {
                textField.setEditable(false);
            }

        } else {
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                textField.setEditable(true);
            } else {
                textField.setEditable(false);
            }
        }
    }

    private void gradeValidaion(JTextField textField, java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();

        if (Character.isLetter(c) || evt.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
            textField.setEditable(true);

        } else {
            textField.setEditable(false);
            textField.setToolTipText("Please enter letters only");
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        nameTxt = new javax.swing.JTextField();
        rollTxt = new javax.swing.JTextField();
        parentTxt = new javax.swing.JTextField();
        personalTxt = new javax.swing.JTextField();
        gradeTxt = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        searchTxt = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        genderSelect = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        displayBtn = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        exitBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Management System");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Student Data");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(328, 328, 328)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(306, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Name");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 171, 210, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Roll number");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, 157, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("Gender");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 170, 157, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Personal Number");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 265, 200, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Parent Number");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 265, 157, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Grade");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 260, 157, -1));

        nameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nameTxtKeyPressed(evt);
            }
        });
        jPanel2.add(nameTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 206, -1));

        rollTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rollTxtKeyPressed(evt);
            }
        });
        jPanel2.add(rollTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, 188, -1));

        parentTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                parentTxtKeyPressed(evt);
            }
        });
        jPanel2.add(parentTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 305, 190, -1));

        personalTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                personalTxtKeyPressed(evt);
            }
        });
        jPanel2.add(personalTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 305, 210, -1));

        gradeTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gradeTxtKeyPressed(evt);
            }
        });
        jPanel2.add(gradeTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 300, 200, -1));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 16))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Search By Roll number");

        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchTxtKeyPressed(evt);
            }
        });

        searchBtn.setText("Search");
        searchBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 370, 120));

        genderSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Gender", "Male", "Female" }));
        jPanel2.add(genderSelect, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 210, 200, 30));

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Fill Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 16))); // NOI18N

        displayBtn.setText("Display all students");
        displayBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        displayBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayBtnActionPerformed(evt);
            }
        });

        addBtn.setText("Add");
        addBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        updateBtn.setText("Update");
        updateBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        updateBtn.setEnabled(false);
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        deleteBtn.setText("Delete");
        deleteBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        exitBtn.setText("Exit");
        exitBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(displayBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(209, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displayBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 720, 320));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        try {
            // TODO add your handling code here:
            searchStudent();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Some error occured", "Student Management System", JOptionPane.ERROR_MESSAGE);
            clearAllFields();
        }
    }//GEN-LAST:event_searchBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        try {
            // TODO add your handling code here:
            addStudent();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Some error occured", "Student Management System", JOptionPane.ERROR_MESSAGE);
            clearAllFields();
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        try {
            // TODO add your handling code here:
            updateStudent();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Some Error occured", "Student Management System", JOptionPane.ERROR_MESSAGE);
            clearAllFields();
        }
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        try {
            // TODO add your handling code here:
            removeStudent();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Some error occured", "Student Management System", JOptionPane.ERROR_MESSAGE);
            clearAllFields();
        }


    }//GEN-LAST:event_deleteBtnActionPerformed

    private void displayBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayBtnActionPerformed
        try {
            // TODO add your handling code here:
            displayStudents();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Some error occured", "Student Management System", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_displayBtnActionPerformed

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        // TODO add your handling code here:
        exitApplication();

    }//GEN-LAST:event_exitBtnActionPerformed

    private void nameTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameTxtKeyPressed
        // TODO add your handling code here:
        nameValidation(nameTxt, evt);

    }//GEN-LAST:event_nameTxtKeyPressed

    private void rollTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rollTxtKeyPressed
        // TODO add your handling code here:
        rollNoValidation(rollTxt, evt);
    }//GEN-LAST:event_rollTxtKeyPressed

    private void personalTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_personalTxtKeyPressed
        // TODO add your handling code here:
        phoneNoValidation(personalTxt, evt);
    }//GEN-LAST:event_personalTxtKeyPressed

    private void parentTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_parentTxtKeyPressed
        // TODO add your handling code here:
        phoneNoValidation(parentTxt, evt);
    }//GEN-LAST:event_parentTxtKeyPressed

    private void gradeTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gradeTxtKeyPressed
        // TODO add your handling code here
        gradeValidaion(gradeTxt, evt);
    }//GEN-LAST:event_gradeTxtKeyPressed

    private void searchTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyPressed
        // TODO add your handling code here:
        rollNoValidation(searchTxt,evt);
    }//GEN-LAST:event_searchTxtKeyPressed

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
            java.util.logging.Logger.getLogger(StudentManagementSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentManagementSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentManagementSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentManagementSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentManagementSystem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton displayBtn;
    private javax.swing.JButton exitBtn;
    private javax.swing.JComboBox<String> genderSelect;
    private javax.swing.JTextField gradeTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField nameTxt;
    private javax.swing.JTextField parentTxt;
    private javax.swing.JTextField personalTxt;
    private javax.swing.JTextField rollTxt;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
