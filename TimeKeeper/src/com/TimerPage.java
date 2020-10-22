package com;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class TimerPage {
    JFrame frame;
    JPanel panel;
    JLabel time, soundSelect;
    JButton playButton, select, start, back10;
    SpinnerListModel spinnerList;
    SpinnerModel spinnerModel1, spinnerModel2, spinnerModel3;
    JSpinner spinner1, spinner2, spinner3, spinner4;

    public static void timer() {
        TimerPage obj = new TimerPage();
        obj.createTimer();
    }

    private void createTimer() {
        frame = new JFrame();
        panel = new JPanel();
        frame.setSize(500, 500);
        panel.setLayout(null);
        frame.add(panel);

        time = new JLabel("Enter Time (hh/mm/ss):");
        time.setBounds(50, 50, 150, 25);
        time.setFont(new Font("Serif", Font.PLAIN, 15));
        panel.add(time);

        //spinner for hour
        spinnerModel1 = new SpinnerNumberModel(0, 0, 24, 1);
        spinner1 = new JSpinner(spinnerModel1);
        spinner1.setBounds(250, 50, 35, 25);
        panel.add(spinner1);

        JLabel slash1 = new JLabel("/");
        slash1.setBounds(290, 50, 150, 25);
        panel.add(slash1);

        //spinner for minutes
        spinnerModel2 = new SpinnerNumberModel(0, 0, 59, 1);
        spinner2 = new JSpinner(spinnerModel2);
        spinner2.setBounds(300, 50, 35, 25);
        panel.add(spinner2);

        JLabel slash2 = new JLabel("/");
        slash2.setBounds(340, 50, 150, 25);
        panel.add(slash2);

        //spinner for seconds
        spinnerModel3 = new SpinnerNumberModel(0, 0, 59, 1);
        spinner3 = new JSpinner(spinnerModel3);
        spinner3.setBounds(350, 50, 35, 25);
        panel.add(spinner3);

        soundSelect = new JLabel("Select Sound:");
        soundSelect.setBounds(50, 350, 150, 25);
        soundSelect.setFont(new Font("Serif", Font.PLAIN, 15));
        panel.add(soundSelect);

        playButton = new JButton("Play");
        playButton.setBounds(335, 350, 60, 25);
        panel.add(playButton);

        //String Array containing the name of the Sound Tracks Available
        String[] Sounds = {"Track 1 (default)", "Track 2", "Track 3", "Track 4", "Track 5"};
        spinnerList = new SpinnerListModel(Sounds);
        spinner4 = new JSpinner(spinnerList);
        spinner4.setBounds(150, 350, 175, 25);
        panel.add(spinner4);
        spinner4.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                playButton.setEnabled(true);
                SoundPlayer.stop();
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playButton.setEnabled(false);
                SoundPlayer.setFilePath("src\\com\\" + spinner4.getValue().toString() + ".wav");
                SoundPlayer.vain();
            }
        });

        select = new JButton("Set");
        select.setBounds(400, 350, 60, 25);
        panel.add(select);
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playButton.setEnabled(true);
                SoundPlayer.stop();
            }
        });

        back10 = new JButton("Back to Home Page");
        back10.setBounds(130, 200, 150, 35);
        panel.add(back10);
        back10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Main.visibilty(true);
            }
        });

        start = new JButton("Start Timer");
        start.setBounds(130, 125, 150, 30);
        //Start Timer is clicked
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                startIt obj=new startIt();
                obj.start();

            }
        });
        panel.add(start);
        frame.setVisible(true);

    }

    class startIt extends Thread{
        public void run() {
            JFrame frame2 = new JFrame();
            JPanel panel2 = new JPanel();
            frame2.setSize(500, 500);
            panel2.setLayout(null);
            frame2.add(panel2);
            frame.setVisible(false);
            frame2.setVisible(true);

            int[] c = {Integer.parseInt(spinner1.getValue().toString()), Integer.parseInt(spinner2.getValue().toString()), Integer.parseInt(spinner3.getValue().toString())};
            JLabel counter = new JLabel();
            counter.setBounds(185, 100, 150, 65);
            counter.setFont(new Font("Serif", Font.CENTER_BASELINE, 40));
            panel2.add(counter);
            DecimalFormat form = new DecimalFormat("00");
            String loc = "src\\com\\" + spinner4.getValue().toString() + ".wav";

            //Background Button
            JButton back21 = new JButton("Run in Background");
            back21.setBounds(180, 300, 150, 40);
            back21.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame2.setVisible(false);
                    frame.setVisible(true);
                }
            });
            panel2.add(back21);

            //Abort button is clicked
            JButton abort = new JButton("Abort Timer");
            abort.setBounds(180, 200, 150, 40);
            abort.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stop();
                    frame2.dispose();
                    frame.setVisible(true);
                }
            });
            panel2.add(abort);

            //pause button
            JButton pause = new JButton("Pause Timer");
            pause.setBounds(193, 350, 125, 40);
            //pause button is clicked
            pause.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    suspend();
                }
            });
            panel2.add(pause);


            //play button
            JButton play = new JButton("Resume Timer");
            play.setBounds(193, 400, 125, 40);
            //play button is clicked
            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resume();
                }
            });
            panel2.add(play);
            while (true) {
                counter.setText(form.format(Integer.valueOf(c[0])) + ":" + form.format(Integer.valueOf(c[1])) + ":" + form.format(Integer.valueOf(c[2])));
                if (c[2] == 0 && c[1] != 0) {
                    c[1]--;
                    c[2] = 60;
                } else if (c[1] == 0 && c[0] != 0 && c[2] == 0) {
                    c[0]--;
                    c[1] = 59;
                    c[2] = 60;
                } else if (c[0] == 0 && c[1] == 0 && c[2] == 0) {
                    TimeOver.setFilePath(loc);
                    TimeOver.vain();
                    frame2.setVisible(false);
                    break;
                }
                c[2]--;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}