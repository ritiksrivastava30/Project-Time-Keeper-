package com;
// Java program to play an Audio
// file using Clip Object
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class TimeOver {
    static Clip clip;
    AudioInputStream audioInputStream;
    static String filePath="src\\com\\Tracks\\Track 1 (default).wav";

    // constructor to initialize streams and clip
    public TimeOver() throws UnsupportedAudioFileException,IOException, LineUnavailableException {
        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        // create clip reference
        clip = AudioSystem.getClip();
        // open audioInputStream to the clip
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void vain() {
        JFrame frame3 = new JFrame();
        JPanel panel3 = new JPanel();
        frame3.setSize(500, 500);
        frame3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stop();
            }
        });
        panel3.setLayout(null);
        frame3.add(panel3);

        JLabel timesUp = new JLabel("Times' Up!!");
        timesUp.setBounds(170, 140, 300, 50);
        timesUp.setFont(new Font("Serif", Font.PLAIN, 35));
        panel3.add(timesUp);

        JButton pause = new JButton("Ok! Got It");
        pause.setBounds(160, 220, 200, 50);
        panel3.add(pause);

        JButton re_timer = new JButton("Set Another Timer");
        re_timer.setBounds(180, 320, 150, 35);
        panel3.add(re_timer);

        JButton home = new JButton("Home Page");
        home.setBounds(180, 370, 150, 35);
        panel3.add(home);


        frame3.setVisible(true);

        try {
            TimeOver audioPlayer = new TimeOver();
            audioPlayer.play();
            pause.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stop();
                }
            });

            re_timer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame3.dispose();
                    stop();
                    TimerPage.timer();
                }
            });

            home.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stop();
                    frame3.dispose();
                    Main.visibilty(true);
                }
            });

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
    }
    public static void setFilePath(String nameOfSong){
        filePath = nameOfSong;
    }
    public static void play()
    {
        //start the clip
        clip.start();
    }

    public static void stop() {
        clip.stop();
        clip.close();
    }
}
