package com.clockapp;

import com.clockapp.functionpanels.ClockPanel;
import com.clockapp.functionpanels.StopwatchPanel;
import com.clockapp.functionpanels.TimerPanel;

import javax.swing.*;

public class MenuPane extends JTabbedPane {
    public MenuPane() {
        init();
    }

    private void init() {
        this.addTab("Analog Clock", null, new ClockPanel(), "Analog Clock");
        this.addTab("Stopwatch", null, new StopwatchPanel(), "Stopwatch");
        this.addTab("Timer", null, new TimerPanel(), "Timer");
    }
}
