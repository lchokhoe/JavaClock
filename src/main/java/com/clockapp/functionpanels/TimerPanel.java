package com.clockapp.functionpanels;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.MaskFormatter;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.time.Duration;

import static com.clockapp.ClockUtil.convertDurationToString;
import static com.clockapp.ClockUtil.convertTime;

public class TimerPanel extends JPanel implements FocusListener {
    private String timeOnScreen;
    private String startTime = "00:00:00";
    private String pausedTime = "00:00:00";
    private JFormattedTextField timeField;
    private JButton startButton;
    private JButton resetButton;
    private JButton confirmButton;
    private JButton cancelButton;
    private Timer timer;
    public TimerPanel() {
        init();
    }

    private void init() {
        JPanel timerPanel = new JPanel();
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        try {
            MaskFormatter formatter = new MaskFormatter("##:##:##");
            formatter.setPlaceholderCharacter('0');

            timeField = new JFormattedTextField(formatter);
            timeField.setColumns(10); // Set preferred width
            timeField.setFont(new Font("Arial", Font.PLAIN, 20));
            timerPanel.add(new JLabel("Countdown Time: "));
            timerPanel.add(timeField);
            timeField.addFocusListener(this);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 3;
            this.add(timerPanel, gbc);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }

        JPanel controlPanel1 = new JPanel(new GridLayout(1, 0, 10, 0));
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(controlPanel1, gbc);

        startButton = new JButton("Start");
        startButton.setEnabled(false);
        resetButton = new JButton("Reset");
        resetButton.setEnabled(false);
        controlPanel1.add(startButton);
        controlPanel1.add(resetButton);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeOnScreen = timeField.getText();
                long remainingTime = convertTime(timeOnScreen + ":00");
                Duration duration = Duration.ofMillis(remainingTime).minusSeconds(1);
                String durationToString = convertDurationToString(duration);
                timeField.setText(durationToString.substring(0, durationToString.lastIndexOf(":")));
                checkTimer();
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startButton.getText().equals("Start")) {
                    timer.start();
                    timeField.setEditable(false);
                    startButton.setText("Pause");
                } else if (startButton.getText().equals("Pause")) {
                    timer.stop();
                    timeField.setEditable(true);
                    startButton.setText("Start");
                    pausedTime = timeField.getText();
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeField.setText(startTime);
            }
        });

        JPanel controlPanel2 = new JPanel(new GridLayout(1, 0, 10, 0));
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(controlPanel2, gbc);

        confirmButton = new JButton("Confirm");
        controlPanel2.add(confirmButton);
        cancelButton = new JButton("Cancel");
        controlPanel2.add(cancelButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long currTime = convertTime(timeField.getText() + ":00");
                if (currTime == 0) {
                    startButton.setEnabled(false);
                    resetButton.setEnabled(false);
                } else {
                    startButton.setEnabled(true);
                    resetButton.setEnabled(true);
                    confirmButton.setEnabled(false);
                    cancelButton.setEnabled(false);
                }
                startTime = timeField.getText();
                pausedTime = startTime;
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long originalStart = convertTime(startTime + ":00");
                if (originalStart == 0) {
                    startButton.setEnabled(false);
                    resetButton.setEnabled(false);
                    confirmButton.setEnabled(true);
                    cancelButton.setEnabled(true);
                } else {
                    startButton.setEnabled(true);
                    resetButton.setEnabled(true);
                    confirmButton.setEnabled(false);
                    cancelButton.setEnabled(false);
                }
                timeField.setText(pausedTime);
            }
        });
    }

    private void checkTimer() {
        long currTime = convertTime(timeField.getText() + ":00");
        if (currTime == 0) {
            timer.stop();
            startButton.setText("Start");
            startButton.setEnabled(true);
            timeField.setText(startTime);
            timeField.setEditable(true);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Time is up!");
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        confirmButton.setEnabled(true);
        cancelButton.setEnabled(true);
        startButton.setEnabled(false);
        resetButton.setEnabled(false);
        startTime = timeField.getText();
    }

    @Override
    public void focusLost(FocusEvent e) {
        // do nothing
    }
}
