package com.clockapp.functionpanels;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

public class ClockPanel extends JPanel implements ActionListener {
    public ClockPanel() {
        init();
    }

    private void init() {
        Timer timer = new Timer(1000, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawClockFace(g);
        drawSecondHand(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        drawMinuteHand(g2);

        g2.setStroke(new BasicStroke(5));
        drawHourHand(g2);
    }

    private void drawClockFace(Graphics g) {
        // size of clock face
        int diameter = getHeight() * 3/4; // have the clock resize proportionally to the size of the window
        int radius = diameter/2;
        int xcenter = getWidth()/2 - radius;
        int ycenter = getHeight()/2 - radius;
        g.fillOval(xcenter, ycenter, diameter, diameter);

        // number on the clock face
        double fontSizeFactor = getHeight() * 0.004;
        g.setFont(new Font("TimesRoman", Font.BOLD, (int) (20 * fontSizeFactor)));
        g.setColor(Color.GREEN);

        // cardinal directions
        g.drawString("12",
                xcenter - (int) (11 * fontSizeFactor) + radius,
                ycenter + (int) (23 * fontSizeFactor));
        g.drawString("3",
                xcenter - (int) (18 * fontSizeFactor) + diameter,
                ycenter + (int) (8 * fontSizeFactor) + radius);
        g.drawString("6",
                xcenter - (int) (6 * fontSizeFactor) + radius,
                ycenter - (int) (9 * fontSizeFactor) + diameter);
        g.drawString("9",
                xcenter + (int) (7 * fontSizeFactor),
                ycenter + (int) (8 * fontSizeFactor) + radius);

        // calculations for quadrants
        double cosine30 = Math.cos(Math.toRadians(30));
        double sine30 = Math.sin(Math.toRadians(30));
        double cosine60 = Math.cos(Math.toRadians(60));
        double sine60 = Math.sin(Math.toRadians(60));

        // second quadrant
        g.drawString("2",
                xcenter + (int) (radius * (1 + cosine30)) - (int) (18 * fontSizeFactor),
                ycenter + (int) (radius * (1 - sine30)) + (int) (15 * fontSizeFactor));
        g.drawString("1",
                xcenter + (int) (radius * (1 + cosine60)) - (int) (11 * fontSizeFactor),
                ycenter + (int) (radius * (1 - sine60)) + (int) (23 * fontSizeFactor));

        // fourth quadrant
        g.drawString("4",
                xcenter + (int) (radius * (1 + cosine30)) - (int) (18 * fontSizeFactor),
                ycenter + (int) (radius * (1 + sine30)) + (int) (0 * fontSizeFactor));
        g.drawString("5",
                xcenter + (int) (radius * (1 + cosine60)) - (int) (11 * fontSizeFactor),
                ycenter + (int) (radius * (1 + sine60)) - (int) (7 * fontSizeFactor));

        // third quadrant
        g.drawString("8",
                xcenter + (int) (radius * (1 - cosine30)) + (int) (7 * fontSizeFactor),
                ycenter + (int) (radius * (1 + sine30)) - (int) (1 * fontSizeFactor));
        g.drawString("7",
                xcenter + (int) (radius * (cosine60)) - (int) (0 * fontSizeFactor),
                ycenter + (int) (radius * (1 + sine60)) - (int) (7 * fontSizeFactor));

        // first quadrant
        g.drawString("10",
                xcenter + (int) (radius * (1 - cosine30)) + (int) (5 * fontSizeFactor),
                ycenter + (int) (radius * (1 - sine30)) + (int) (16 * fontSizeFactor));
        g.drawString("11",
                xcenter + (int) (radius * (cosine60)) - (int) (2 * fontSizeFactor),
                ycenter + (int) (radius * (1 - sine60)) + (int) (23 * fontSizeFactor));

    }

    private void drawSecondHand(Graphics g) {
        int diameter = getHeight() * 3/4; // have the clock resize proportionally to the size of the window
        int radius = diameter/2;
        int xcenter = getWidth()/2;
        int ycenter = getHeight()/2;
        int currSeconds = LocalTime.now().getSecond();
        int angle = 360/60 * currSeconds;
        double handSizeFactor = 0.7;

        if (currSeconds >= 0 && currSeconds < 15) {
            // quadrant 2
            double angleToRadians = Math.toRadians(90 - angle);
            g.drawLine(xcenter, ycenter,
                    xcenter + (int) (radius * Math.cos(angleToRadians) * handSizeFactor),
                    ycenter - (int) (radius * Math.sin(angleToRadians) * handSizeFactor));
        } else if (currSeconds >= 15 && currSeconds < 30) {
            // quadrant 3
            double angleToRadians = Math.toRadians(angle - 90);
            g.drawLine(xcenter, ycenter,
                    xcenter + (int) (radius * Math.cos(angleToRadians) * handSizeFactor),
                    ycenter + (int) (radius * Math.sin(angleToRadians) * handSizeFactor));
        } else if (currSeconds >= 30 && currSeconds < 45) {
            //quadrant 4
            double angleToRadians = Math.toRadians(angle - 180);
            g.drawLine(xcenter, ycenter,
                    xcenter - (int) (radius * Math.sin(angleToRadians) * handSizeFactor),
                    ycenter + (int) (radius * Math.cos(angleToRadians) * handSizeFactor));
        } else if (currSeconds >= 45 && currSeconds < 60){
            // quadrant 1
            double angleToRadians = Math.toRadians(angle - 270);
            g.drawLine(xcenter, ycenter,
                    xcenter - (int) (radius * Math.cos(angleToRadians) * handSizeFactor),
                    ycenter - (int) (radius * Math.sin(angleToRadians) * handSizeFactor));
        } else {
            System.err.println("Seconds value is out of bounds");
        }
    }

    private void drawMinuteHand(Graphics g) {
        int diameter = getHeight() * 3/4; // have the clock resize proportionally to the size of the window
        int radius = diameter/2;
        int xcenter = getWidth()/2;
        int ycenter = getHeight()/2;
        int currMinutes = LocalTime.now().getMinute();
        int angle = 360/60 * currMinutes;
        double handSizeFactor = 0.5;

        if (currMinutes >= 0 && currMinutes < 15) {
            // quadrant 2
            double angleToRadians = Math.toRadians(90 - angle);
            g.drawLine(xcenter, ycenter,
                    xcenter + (int) (radius * Math.cos(angleToRadians) * handSizeFactor),
                    ycenter - (int) (radius * Math.sin(angleToRadians) * handSizeFactor));
        } else if (currMinutes >= 15 && currMinutes < 30) {
            // quadrant 3
            double angleToRadians = Math.toRadians(angle - 90);
            g.drawLine(xcenter, ycenter,
                    xcenter + (int) (radius * Math.cos(angleToRadians) * handSizeFactor),
                    ycenter + (int) (radius * Math.sin(angleToRadians) * handSizeFactor));
        } else if (currMinutes >= 30 && currMinutes < 45) {
            //quadrant 4
            double angleToRadians = Math.toRadians(angle - 180);
            g.drawLine(xcenter, ycenter,
                    xcenter - (int) (radius * Math.sin(angleToRadians) * handSizeFactor),
                    ycenter + (int) (radius * Math.cos(angleToRadians) * handSizeFactor));
        } else if (currMinutes >= 45 && currMinutes < 60){
            // quadrant 1
            double angleToRadians = Math.toRadians(angle - 270);
            g.drawLine(xcenter, ycenter,
                    xcenter - (int) (radius * Math.cos(angleToRadians) * handSizeFactor),
                    ycenter - (int) (radius * Math.sin(angleToRadians) * handSizeFactor));
        } else {
            System.err.println("Seconds value is out of bounds");
        }
    }

    private void drawHourHand(Graphics g) {
        int diameter = getHeight() * 3/4; // have the clock resize proportionally to the size of the window
        int radius = diameter/2;
        int xcenter = getWidth()/2;
        int ycenter = getHeight()/2;
        int currHours = LocalTime.now().getHour() % 12;
        double angle = 360/12 * currHours;
        double handSizeFactor = 0.8;

        // calculate offset based on current minute
        int currMinutes = LocalTime.now().getMinute();
        int offset = currMinutes * 30/60;
        angle = angle + offset;

        if (currHours >= 0 && currHours <= 3) {
            // quadrant 2
            double angleToRadians = Math.toRadians(90 - angle);
            g.drawLine(xcenter, ycenter,
                    xcenter + (int) (radius * Math.cos(angleToRadians) * handSizeFactor),
                    ycenter - (int) (radius * Math.sin(angleToRadians) * handSizeFactor));
        } else if (currHours >= 4 && currHours <= 6) {
            // quadrant 3
            double angleToRadians = Math.toRadians(angle - 90);
            g.drawLine(xcenter, ycenter,
                    xcenter + (int) (radius * Math.cos(angleToRadians) * handSizeFactor),
                    ycenter + (int) (radius * Math.sin(angleToRadians) * handSizeFactor));
        } else if (currHours >= 7 && currHours <= 9) {
            //quadrant 4
            double angleToRadians = Math.toRadians(angle - 180);
            g.drawLine(xcenter, ycenter,
                    xcenter - (int) (radius * Math.sin(angleToRadians) * handSizeFactor),
                    ycenter + (int) (radius * Math.cos(angleToRadians) * handSizeFactor));
        } else if (currHours >= 10 && currHours <= 12){
            // quadrant 1
            double angleToRadians = Math.toRadians(angle - 270);
            g.drawLine(xcenter, ycenter,
                    xcenter - (int) (radius * Math.cos(angleToRadians) * handSizeFactor),
                    ycenter - (int) (radius * Math.sin(angleToRadians) * handSizeFactor));
        } else {
            System.err.println("Seconds value is out of bounds");
        }
    }
}
