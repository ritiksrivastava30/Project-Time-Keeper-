package com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Play extends Thread {

    int a, b,clicked;
    public static int i=0;
    public static ArrayList<Play> pl=new ArrayList<>();
    JFrame frame;
    JPanel panel;
    DecimalFormat form = new DecimalFormat("00");

    public Play(int h, int m) {
        this.a = h;
        this.b = m;
    }

    public static void setAlarm(int hour,int minute){
        pl.add(new Play(hour,minute));
        pl.get(i).start();
        i++;
    }
    public void run() {
        int w1 = 0;
        while (w1 == 0) {
            LocalDateTime now1 = LocalDateTime.now();
            DateTimeFormatter format2 = DateTimeFormatter.ofPattern("HH");
            String formatDateTime2 = now1.format(format2);
            int hours = Integer.parseInt(formatDateTime2);
            DateTimeFormatter format1 = DateTimeFormatter.ofPattern("mm");
            String formatDateTime1 = now1.format(format1);
            int mins = Integer.parseInt(formatDateTime1);
            if (a == hours && b == mins && dayCheck()==1) {
                alarmTime();
                SimpleAudioPlayer.vain();
                break;
            }
        }
    }
    public int dayCheck(){
        Date d=new Date();
        return (Integer.parseInt(String.valueOf(AlarmClock.flags.get(i-1).charAt(d.getDay()))));
    }
    public void alarmTime () {
        frame=new JFrame();
        frame.setSize(250,250);
        frame.setTitle("Alarm");
        panel=new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SimpleAudioPlayer.clip.stop();
                SimpleAudioPlayer.clip.close();
            }
        });
        JLabel lb=new JLabel();
        lb.setBounds(70,30,200,50);
        lb.setText(form.format(a) + ":" + form.format(b));
        lb.setFont(new Font("Serif", Font.BOLD, 45));
        panel.add(lb);

        JLabel l=new JLabel("ALARM");
        l.setBounds(80,80,200,50);
        l.setFont(new Font("Serif", Font.PLAIN, 25));
        panel.add(l);

        JButton stop=new JButton("Ok! I,m Up");
        stop.setBounds(25,140,100,35);
        panel.add(stop);
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleAudioPlayer.clip.stop();
                SimpleAudioPlayer.clip.close();
            }
        });

        JLabel m=new JLabel("No more Snoozing allowed");
        m.setBounds(70,175,175,25);
        m.setVisible(false);
        panel.add(m);

        JButton snooze=new JButton("Snooze");
        snooze.setBounds(125,140,100,35);
        panel.add(snooze);
        clicked =0;
        snooze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clicked<3){
                    SimpleAudioPlayer.clip.stop();
                    frame.setVisible(false);
                    try{
                        pl.get(i-1).sleep(300000);
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                    SimpleAudioPlayer.vain();
                    frame.setVisible(true);
                    clicked++;
                }
                else{
                    snooze.setEnabled(false);
                    m.setVisible(true);
                }
            }
        });

        frame.setVisible(true);

    }


}
