package com.clockapp;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class MainWindow {
    private JFrame window;
    private JPanel currApp;
    private JLabel label;

    public MainWindow() {
        // main window frame
        this.window = new JFrame();
        this.window.setLayout(new BorderLayout(0, 0));
        this.window.setMinimumSize(new Dimension(500, 400));
        this.window.setTitle("Clock Application with Java Swing");
        this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.window.setSize(800, 500);
        this.window.setLocationRelativeTo(null); // centers windows

        // label
        this.label = new JLabel("Clock Application with Java Swing");
        this.label.setForeground(Color.WHITE);
        this.label.setFont(new Font("Sans-serif", Font.BOLD, 28));

        // panel containing functions
        this.currApp = new JPanel();
        this.currApp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.currApp.setBackground(Color.GRAY);
        this.currApp.add(label);

        // add to main window frame
        this.window.add(currApp, BorderLayout.NORTH);
        this.window.add(new MenuPane(), BorderLayout.CENTER);
    }

    public void show() {
        this.window.setVisible(true);
    }
}
