package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

public class StopWatch extends Thread{
    private JPanel pane;
    private JButton start, reset, lap;
    private JTextArea lapScreen;
    private JLabel counter;
    private DecimalFormat form;
    private int hours = 0, minutes = 0, seconds = 0, milliseconds = 0;

    //constructor
    public StopWatch() {
        CreateUIComponent();
    }

    //method for creating stopwatchUI and implementing stopwatch functionality
    private void CreateUIComponent() {
        //main stopwatch frame
        JFrame f = new JFrame("STOPWATCH");
        f.setSize(400, 500);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                firstPage.flag= 0;
            }
        });

        pane = new JPanel();
        pane.setBounds(100, 100, 500, 500);
        pane.setBackground(new Color(204, 255, 204));
        f.setContentPane(pane);

        //for stopwatch time look like "00: 00: 00: 00"
        //otherwise time will be of form 0: 0: 0
        //and setting stopwatch time as counter label
        form = new DecimalFormat("00");
        counter = new JLabel();
        counter.setBounds(60, 30, 300, 65);
        counter.setFont(new Font("Serif", Font.CENTER_BASELINE, 50));
        counter.setText(form.format(hours) + ":" + form.format(minutes) + ":" + form.format(seconds) + ":" + form.format(milliseconds));
        pane.add(counter);

        //this "start" button is for three functionalities i.e. start,stop,resume
        start = new JButton("Start");
        start.setBounds(142, 140, 86, 30);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(start.getLabel().equals("Start"))
                {
                    reset.setEnabled(true);
                    lap.setEnabled(true);
                }
                if(start.getLabel().equals("Start") || start.getLabel().equals("Resume"))
                {
                    resume();
                    start.setLabel("Pause");
                }
                else if(start.getLabel().equals("Pause"))
                {
                    suspend();
                    start.setLabel("Resume");
                }
            }
        });
        pane.add(start);

        //this button reset everything
        //mainly it just create new object of stopwatch
        reset = new JButton("Reset");
        reset.setBounds(260, 115, 70, 30);
        reset.setEnabled(false);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
                StopWatch sw= new StopWatch();
                sw.start();
            }
        });
        pane.add(reset);

        //button notes instance of stopwatch time in lapScreen
        lap = new JButton("Lap");
        lap.setBounds(40, 115, 70, 30);
        lap.setEnabled(false);
        lap.addActionListener(new ActionListener() {
            int c= 1;
            @Override
            public void actionPerformed(ActionEvent e) {
                lapScreen.append("# LAP "+c+ ")   "+hours+ ": "+ minutes+ ": "+ seconds+ ": "+ milliseconds+ "\n");
                c= c+1;
            }
        });
        pane.add(lap);

        //lapScreen for added laps
        lapScreen = new JTextArea();
        JScrollPane scrollableTextArea = new JScrollPane(lapScreen);
        scrollableTextArea.setBounds(40, 200, 300, 200);
        pane.add(scrollableTextArea);

        f.setLayout(null);
        f.setVisible(true);
    }

    //thread function
    public void run() {
        suspend();
        while (true) {
            counter.setText(form.format(hours) + ":" + form.format(minutes) + ":" + form.format(seconds) + ":" + form.format(milliseconds));
            // logic for stopWatch time
            milliseconds++;
            if (milliseconds == 60) {
                milliseconds = 0;
                seconds++;
            } else if (seconds == 60) {
                seconds = 0;
                minutes++;
            } else if (minutes == 60) {
                minutes = 0;
                hours++;
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
