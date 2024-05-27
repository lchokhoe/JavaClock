package com.clockapp;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow clock = new MainWindow();
                clock.show();
            }
        });
    }
}