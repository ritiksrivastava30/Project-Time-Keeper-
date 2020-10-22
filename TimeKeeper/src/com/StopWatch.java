package com;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class StopWatch {
    JFrame frame;
    JPanel panel;
    DecimalFormat form;
    int hours=0, minutes=0, seconds=0, milliseconds=0;
    ArrayList<String> flags = new ArrayList<>();
    //function to be called from first page
    public static void openStopWatch() {
        StopWatch sw=new StopWatch();
        StopWatch.StartStopWatch obj = sw.new StartStopWatch();
        obj.start();
    }

    class StartStopWatch extends Thread{
        public void run() {
            frame = new JFrame();
            panel = new JPanel();
            frame.setSize(500, 500);
            panel.setLayout(null);
            frame.add(panel);
            frame.setVisible(true);

            form = new DecimalFormat("00");
            JLabel counter = new JLabel();
            counter.setBounds(125, 60, 300, 65);
            counter.setFont(new Font("Serif", Font.CENTER_BASELINE, 50));
            counter.setText(form.format(hours)+":"+form.format(minutes)+":"+form.format(seconds)+":"+form.format(milliseconds));
            panel.add(counter);


            //Background Button
            JButton begin = new JButton("Start");
            begin.setBounds(200, 160, 100, 60);
            begin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                                    }
            });
            panel.add(begin);

            //Abort button is clicked
            JButton abort = new JButton("Reset");
            abort.setBounds(350, 150, 100, 40);
            abort.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    suspend();
                    milliseconds=seconds=minutes=hours=0;
                    counter.setText(form.format(hours)+":"+form.format(minutes)+":"+form.format(seconds)+":"+form.format(milliseconds));
                }
            });
            panel.add(abort);

            //pause button
            JButton pause = new JButton("Pause");
            pause.setBounds(50, 150, 85, 40);
            //pause button is clicked
            pause.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    suspend();
                }
            });
            panel.add(pause);

            JButton flag = new JButton("Add Flag");
            flag.setBounds(350, 200, 100, 40);
            flag.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    flags.add(form.format(hours)+":"+form.format(minutes)+":"+form.format(seconds)+":"+form.format(milliseconds));
                }
            });
            panel.add(flag);

            //play button
            JButton play = new JButton("Resume");
            play.setBounds(50, 200, 85, 40);
            //play button is clicked
            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resume();
                }
            });
            panel.add(play);
            while (true) {
                counter.setText(form.format(hours)+":"+form.format(minutes)+":"+form.format(seconds)+":"+form.format(milliseconds));
                milliseconds++;
                if(milliseconds==60){
                    milliseconds = 0;
                    seconds++;
                }
                else if(seconds==60){
                    seconds=0;
                    minutes++;
                }
                else if(minutes==60){
                    minutes=0;
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
}