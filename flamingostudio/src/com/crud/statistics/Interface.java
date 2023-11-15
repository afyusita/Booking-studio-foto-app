package com.crud.statistics;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Interface {
    public Interface() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String statStudloc, statPackage, statName, statEmail, statWanum, statInstagram, statBookdate, statBooktime, statAddperson, statAddbg;
                statStudloc = comboBoxLocation.getSelectedItem().toString();
                statPackage = comboBoxPackage.getSelectedItem().toString();
                statName = textFieldName.getText();
                statEmail = textFieldEmail.getText();
                statWanum = textFieldWaNum.getText();
                statInstagram = textFieldInstaId.getText();
                statBookdate = textFieldBookdate.getText();
                statBooktime = textFieldBookTime.getText();
                statAddperson = textFieldAddPerson.getText();
                statAddbg = textFieldAddBG.getText();

                try {
                    Connection connection = Connector.connectDB();
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO pelanggan (studloc, package, name, email, wanum, instagram, bookdate, booktime, addperson, addbg) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    preparedStatement.setString(1, statStudloc);
                    preparedStatement.setString(2, statPackage);
                    preparedStatement.setString(3, statName);
                    preparedStatement.setString(4, statEmail);
                    preparedStatement.setString(5, statWanum);
                    preparedStatement.setString(6, statInstagram);
                    preparedStatement.setString(7, statBookdate);
                    preparedStatement.setString(8, statBooktime);
                    preparedStatement.setString(9, statAddperson);
                    preparedStatement.setString(10, statAddbg);
                    preparedStatement.executeUpdate();
                    showData();
                    JOptionPane.showMessageDialog(null, "Data Successfully Inserted");
                } catch (SQLException err) {
                    Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, err);
                }
                    textFieldName.setText("");
                    textFieldEmail.setText("");
                    textFieldWaNum.setText("");
                    textFieldInstaId.setText("");
                    textFieldBookdate.setText("");
                    textFieldBookTime.setText("");
                    textFieldAddPerson.setText("");
                    textFieldAddBG.setText("");

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(Interface::createUpdateGUI);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(Interface::createDeleteGUI);
            }
        });
        refreshButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showData();
                    JOptionPane.showMessageDialog(null, "Data Refreshed");
            }
        });
    }
    public JPanel getMainPanel (){
        showData();
        return mainPanel;
    }

    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private void showData(){
        try {
            Object[] columnTitle = {"Id Cust","Studio location", "Package", "Name", "E-mail", "WA Number", "Instagram", "Bookdate", "Booktime", "Add Person", "Add Background"};
                    tableModel = new DefaultTableModel(null, columnTitle);
                    table1.setModel(tableModel);



            //retrieve connection from DB
            Connection connection = Connector.connectDB();
            Statement statement = connection.createStatement();
            tableModel.getDataVector().removeAllElements();

            // initiate result with SQL QUARY
            resultSet = statement.executeQuery("SELECT * FROM pelanggan");
            while (resultSet.next()){
                Object [] data = {
                        resultSet.getString("id_cust"),
                        resultSet.getString("studloc"),
                        resultSet.getString("package"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("wanum"),
                        resultSet.getString("instagram"),
                        resultSet.getString("bookdate"),
                        resultSet.getString("booktime"),
                        resultSet.getString("addperson"),
                        resultSet.getString("addbg")
                };
                tableModel.addRow(data);
            }
        } catch (SQLException err) {
            throw new RuntimeException(err);

        }
    }

    private static void createUpdateGUI(){
        UpdatePanel updateUI = new UpdatePanel();
        JPanel updateRoot = updateUI.getUpdatePanel();
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(updateRoot);
        jFrame.setPreferredSize(new Dimension(1300, 600));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private static void createDeleteGUI(){
        DeletePanel deleteUI = new DeletePanel();
        JPanel deleteRoot = deleteUI.getDeletePanel();
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(deleteRoot);
        jFrame.setPreferredSize(new Dimension(1300, 600));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    private JPanel mainPanel;
    private JLabel TittlePanel;
    private JComboBox comboBoxLocation;
    private JComboBox comboBoxPackage;
    private JTextField textFieldName;
    private JTextField textFieldEmail;
    private JTextField textFieldWaNum;
    private JTextField textFieldInstaId;
    private JTextField textFieldBookdate;
    private JTextField textFieldBookTime;
    private JTextField textFieldAddPerson;
    private JTextField textFieldAddBG;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable table1;
    private JPanel JFirstPanel;
    private JPanel JSecondPanel;
    private JPanel JthirdPanel;
    private JButton refreshButton;
}
