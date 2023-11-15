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

public class DeletePanel {

    DefaultTableModel tableModelDelete;
    ResultSet resultSetDelete = null;

    public DeletePanel() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userIdDelete;
                userIdDelete = textFieldCustId.getText();
                if (!Objects.equals(userIdDelete, "")) {
                    try {
                        preparedStatement = Connector.connectDB().prepareStatement("DELETE FROM pelanggan WHERE id_cust=?;");
                        preparedStatement.setString(1, userIdDelete);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data Successfully Deleted");
                        JComponent component = (JComponent) e.getSource();
                        Window window = SwingUtilities.getWindowAncestor(component);
                        window.dispose();
                    } catch (SQLException err) {
                        err.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Customer ID shouldn't empty");
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
        textFieldSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String key = textFieldSearch.getText();
                System.out.println(key);

                if (!Objects.equals(key, "")){
                    try {
                        searchDataDelete(key);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void searchDataDelete (String key) throws SQLException {
        Object[] columnTitle = {"Id Cust", "Studio Location", "Package", "Name", "E-mail", "WA Number", "Instagram", "Bookdate", "Booktime", "Add Person", "Add Background"};
        tableModelDelete = new DefaultTableModel(null, columnTitle);
        tableSearch.setModel(tableModelDelete);

        // retrieve mySQL DB
        Connection connect = Connector.connectDB();
        Statement stt = connect.createStatement();

        // set quarry to fatch data
        resultSetDelete = stt.executeQuery("SELECT * FROM pelanggan WHERE studloc LIKE '%"+key+"%' OR package LIKE '%"+key+"%' OR name LIKE '%"+key+"%' OR email LIKE '%"+key+"%' OR wanum LIKE '%"+key+"%' OR instagram LIKE '%"+key+"%' OR bookdate LIKE '%"+key+"%' OR booktime LIKE '%"+key+"%' OR addperson LIKE '%"+key+"%' OR addbg LIKE '%"+key+"%'");
        while (resultSetDelete.next()){
            Object[] data = {
                    resultSetDelete.getString("id_cust"),
                    resultSetDelete.getString("studloc"),
                    resultSetDelete.getString("package"),
                    resultSetDelete.getString("name"),
                    resultSetDelete.getString("email"),
                    resultSetDelete.getString("wanum"),
                    resultSetDelete.getString("instagram"),
                    resultSetDelete.getString("bookdate"),
                    resultSetDelete.getString("booktime"),
                    resultSetDelete.getString("addperson"),
                    resultSetDelete.getString("addbg"),
            };
            tableModelDelete.addRow(data);
        }
    }

    public JPanel getDeletePanel (){
        return deletePanel;
    }
    public PreparedStatement preparedStatement;
    private JPanel deletePanel;
    private JPanel JpanelID;
    private JPanel JpanelButton;
    private JTextField textFieldCustId;
    private JButton cancelButton;
    private JButton deleteButton;
    private JTextField textFieldSearch;
    private JTable tableSearch;
}
