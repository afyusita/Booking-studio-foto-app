package com.crud.statistics;

import javax.swing.*;
import java.awt.*;

public class Show {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Show::createGUI);
    }

    public static void createGUI(){
        Interface UI = new Interface();
        JPanel root = UI.getMainPanel();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.setPreferredSize(new Dimension(1300, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
