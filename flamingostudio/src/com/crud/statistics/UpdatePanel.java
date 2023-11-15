package com.crud.statistics;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.Objects;

public class UpdatePanel {

    DefaultTableModel tableModelUpdate;
    ResultSet resultSetUpdate = null;

    public UpdatePanel() {
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId, userloc, userpackage, userName, useremail, userwanum, userinsid, userbookdate, userbooktime, useraddperson, useraddbg;
                userId = textFieldUser.getText();
                userloc = comboBoxStudio1.getSelectedItem().toString();
                userpackage = comboBoxPackage1.getSelectedItem().toString();
                userName = textFieldName1.getText();
                useremail = textFieldEmail1.getText();
                userwanum = textFieldWanum1.getText();
                userinsid = textFieldInsid1.getText();
                userbookdate = textFieldBookdate1.getText();
                userbooktime = textFieldBooktime1.getText();
                useraddperson = textFieldAddperson1.getText();
                useraddbg = textFieldAddbg1.getText();
                if(!Objects.equals(userId, "") && !Objects.equals(userloc, "") && !Objects.equals(userpackage, "") && !Objects.equals(userName, "") && !Objects.equals(useremail, "") && !Objects.equals(userwanum, "") && !Objects.equals(userinsid, "") && !Objects.equals(userbookdate, "") && !Objects.equals(userbooktime, "") && !Objects.equals(useraddperson, "") && !Objects.equals(useraddbg, "")) {
                    try {
                        preparedStatement = Connector.connectDB().prepareStatement("UPDATE pelanggan SET studloc=?, package=?, name=?, email=?, wanum=?, instagram=?, bookdate=?, booktime=?, addperson=?, addbg=? WHERE id_cust=?");
                        preparedStatement.setString(1, userloc);
                        preparedStatement.setString(2, userpackage);
                        preparedStatement.setString(3, userName);
                        preparedStatement.setString(4, useremail);
                        preparedStatement.setString(5, userwanum);
                        preparedStatement.setString(6, userinsid);
                        preparedStatement.setString(7, userbookdate);
                        preparedStatement.setString(8, userbooktime);
                        preparedStatement.setString(9, useraddperson);
                        preparedStatement.setString(10, useraddbg);
                        preparedStatement.setString(11, userId);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Update Data Success");
                        JComponent component = (JComponent) e.getSource();
                        Window window = SwingUtilities.getWindowAncestor(component);
                        window.dispose();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Input shouldn't empty ");
                }

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent component = (JComponent) e.getSource();
                Window window = SwingUtilities.getWindowAncestor(component);
                window.dispose();
            }
        });
        textFieldSearchUpdate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String key = textFieldSearchUpdate.getText();
                System.out.println(key);

                if (!Objects.equals(key, "")){
                    try {
                        searchDataUpdate(key);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void searchDataUpdate(String key) throws SQLException {
        Object[] columnTitle = {"Id Cust", "Studio Location", "Package", "Name", "E-mail", "WA Number", "Instagram", "Bookdate", "Booktime", "Add Person", "Add Background"};
        tableModelUpdate = new DefaultTableModel(null, columnTitle);
        tableSearchdata.setModel(tableModelUpdate);

        // retrieve mySQL DB
        Connection connect = Connector.connectDB();
        Statement stt = connect.createStatement();
        tableModelUpdate.getDataVector().removeAllElements();

        // set quarry to fetch data
        resultSetUpdate = stt.executeQuery("SELECT * FROM pelanggan WHERE studloc LIKE '%"+key+"%' OR package LIKE '%"+key+"%' OR name LIKE '%"+key+"%' OR email LIKE '%"+key+"%' OR wanum LIKE '%"+key+"%' OR instagram LIKE '%"+key+"%' OR bookdate LIKE '%"+key+"%' OR booktime LIKE '%"+key+"%' OR addperson LIKE '%"+key+"%' OR addbg LIKE '%"+key+"%'");
        while (resultSetUpdate.next()){
            Object[] data = {
                    resultSetUpdate.getString("id_cust"),
                    resultSetUpdate.getString("studloc"),
                    resultSetUpdate.getString("package"),
                    resultSetUpdate.getString("name"),
                    resultSetUpdate.getString("email"),
                    resultSetUpdate.getString("wanum"),
                    resultSetUpdate.getString("instagram"),
                    resultSetUpdate.getString("bookdate"),
                    resultSetUpdate.getString("booktime"),
                    resultSetUpdate.getString("addperson"),
                    resultSetUpdate.getString("addbg"),
            };
            tableModelUpdate.addRow(data);
        }
    }

    public JPanel getUpdatePanel(){
        return mainUpdatePanel;
    }

    private PreparedStatement preparedStatement;
    private JPanel mainUpdatePanel;
    private JTextField textFieldUser;
    private JComboBox comboBoxStudio1;
    private JComboBox comboBoxPackage1;
    private JTextField textFieldName1;
    private JTextField textFieldEmail1;
    private JTextField textFieldWanum1;
    private JTextField textFieldInsid1;
    private JTextField textFieldBookdate1;
    private JTextField textFieldBooktime1;
    private JTextField textFieldAddperson1;
    private JTextField textFieldAddbg1;
    private JButton cancelButton;
    private JButton updateButton;
    private JTextField textFieldSearchUpdate;
    private JTable tableSearchdata;
}
