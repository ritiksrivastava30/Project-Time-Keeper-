package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;


public class Reminder extends Thread{
    private int index;
    public static Reminder[] remObj=new Reminder[100];
    private JFrame frame;
    private JPanel panel;
    DecimalFormat form = new DecimalFormat("00");
    public static ZoneId zone = ZoneId.systemDefault();
    private String remDate,remTime;
    public static ArrayList<String> reminders = new ArrayList<String>();

    public Reminder(String remTime, String remDate, int index){
        this.remDate=remDate;
        this.remTime=remTime;
        this.index=index;
    }

    public static void setReminder(String remTime, String remDate, int index){
        reminders.add(remTime+remDate+index);
        remObj[index]=new Reminder(remTime,remDate,index);
        remObj[index].start();
    }

    public void run() {
        while (true) {

            LocalDateTime now = LocalDateTime.now(zone);
            DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String date = now.format(format1);

            LocalDateTime now1 = LocalDateTime.now(zone);
            DateTimeFormatter format2 = DateTimeFormatter.ofPattern("HH:mm");
            String time = now1.format(format2);

            DateTimeFormatter format3 = DateTimeFormatter.ofPattern("ss");
            String formatDateTime3 = now1.format(format3);
            int sec = Integer.parseInt(formatDateTime3);

            if (remTime.equals(time) && remDate.equals(date) && sec==0) {
                showRem();
                SoundPlayer.vain();
                break;
            }
        }
    }
    private void showRem(){
        frame=new JFrame();
        frame.setSize(400,300);
        frame.setTitle("Event Reminder");
        panel=new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SoundPlayer.stop();
            }
        });
        JLabel lb=new JLabel();
        lb.setBounds(60,30,350,60);
        lb.setHorizontalAlignment(SwingConstants.CENTER);
        lb.setText(Event.eventNames.get(Event.getIndex()));
        lb.setFont(new Font("Serif", Font.BOLD, 45));
        panel.add(lb);

        JLabel lb2=new JLabel();
        lb2.setHorizontalAlignment(SwingConstants.CENTER);
        lb2.setBounds(60,100,200,40);
        lb2.setText(Event.dateOfEvents.get(2*Event.getIndex()+1));
        lb2.setFont(new Font("Serif", Font.PLAIN, 25));
        panel.add(lb2);

        JButton stop=new JButton("Ok! I'm Ready");
        stop.setBounds(25,150,150,35);
        panel.add(stop);
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.stop();
                frame.dispose();
            }
        });


        JButton snooze=new JButton("Show Details");
        snooze.setBounds(175,150,150,35);
        panel.add(snooze);
        snooze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.stop();
                frame.dispose();
                new Event().showDetails(index);
            }
        });
        frame.setVisible(true);
    }
    public static void deleteReminder(int in){
        int t;
        for(t=0;t<reminders.size();t++){
            if(Integer.parseInt(reminders.get(t).substring(15))==in){
                reminders.remove(t);
                break;
            }
        }
    }
}
