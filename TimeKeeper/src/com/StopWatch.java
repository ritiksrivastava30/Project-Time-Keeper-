package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class StopWatch extends Thread{
    private JPanel pane;
    private JButton b1, b2, b3;
    private JTextArea ta1;
    private JLabel counter;
    private DecimalFormat form;
    private int hours = 0, minutes = 0, seconds = 0, milliseconds = 0;
    public StopWatch() {
        CreateUIComponent();
    }

    private void CreateUIComponent() {
        JFrame f = new JFrame("STOPWATCH");
        f.setSize(400, 500);
        pane = new JPanel();
        pane.setBounds(100, 100, 500, 500);
        pane.setBackground(new Color(204, 255, 204));
        form = new DecimalFormat("00");
        counter = new JLabel();
        counter.setBounds(60, 30, 300, 65);
        counter.setFont(new Font("Serif", Font.CENTER_BASELINE, 50));
        counter.setText(form.format(hours) + ":" + form.format(minutes) + ":" + form.format(seconds) + ":" + form.format(milliseconds));
        pane.add(counter);
        b1 = new JButton("Start");
        b1.setBounds(142, 140, 86, 30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(b1.getLabel().equals("Start"))
                {
                    b2.setEnabled(true);
                    b3.setEnabled(true);
                }
                if(b1.getLabel().equals("Start") || b1.getLabel().equals("Resume"))
                {
                    resume();
                    b1.setLabel("Pause");
                }
                else if(b1.getLabel().equals("Pause"))
                {
                    suspend();
                    b1.setLabel("Resume");
                }
            }
        });
        pane.add(b1);
        b2 = new JButton("Reset");
        b2.setBounds(260, 115, 70, 30);
        b2.setEnabled(false);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
                StopWatch sw= new StopWatch();
                sw.start();
            }
        });
        pane.add(b2);
        b3 = new JButton("Lap");
        b3.setBounds(40, 115, 70, 30);
        b3.setEnabled(false);
        b3.addActionListener(new ActionListener() {
            int c= 1;
            @Override
            public void actionPerformed(ActionEvent e) {
                ta1.append("# LAP "+c+ ")   "+hours+ ": "+ minutes+ ": "+ seconds+ ": "+ milliseconds+ "\n");
                c= c+1;
            }
        });
        pane.add(b3);
        ta1= new JTextArea();
        //ta1.setBounds(40, 200, 300, 200);
        JScrollPane scrollableTextArea = new JScrollPane(ta1);
        scrollableTextArea.setBounds(40, 200, 300, 200);
        pane.add(scrollableTextArea);
        f.setContentPane(pane);
        f.setLayout(null);
        f.setVisible(true);
    }
    public void run() {
        suspend();
        while (true) {
            counter.setText(form.format(hours) + ":" + form.format(minutes) + ":" + form.format(seconds) + ":" + form.format(milliseconds));
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
