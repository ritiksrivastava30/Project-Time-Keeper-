package com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
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

    int a, b;
    public static int i=0;
    public static ArrayList<Play> pl=new ArrayList<>();
    private String path;

    public Play(int h, int m,String lP) {
        this.a = h;
        this.b = m;
        this.path= lP;
    }

    public static void setAlarm(int hour,int minute,String lP){
        pl.add(new Play(hour,minute,lP));
        pl.get(i).start();
        i++;
    }
    public void run() {
        //String path1=path;
        //System.out.println(path1);
        while (true) {
            int a1=a;
            int b1=b;
            LocalDateTime now1 = LocalDateTime.now();
            DateTimeFormatter format2 = DateTimeFormatter.ofPattern("HH");
            String formatDateTime2 = now1.format(format2);
            int hours = Integer.parseInt(formatDateTime2);
            DateTimeFormatter format1 = DateTimeFormatter.ofPattern("mm");
            String formatDateTime1 = now1.format(format1);
            int mins = Integer.parseInt(formatDateTime1);
            DateTimeFormatter format3 = DateTimeFormatter.ofPattern("ss");
            String formatDateTime3 = now1.format(format3);
            int sec = Integer.parseInt(formatDateTime3);
            if (a1 == hours && b1 == mins && sec == 0 && dayCheck()==1) {
                SimpleAudioPlayer.setFilePath(this.path);
                SimpleAudioPlayer.vain();
                alarmTime();
                break;
            }
        }
    }
    public int dayCheck(){
        Date d=new Date();
        return (Integer.parseInt(String.valueOf(AlarmClock.flags.get(i-1).charAt(d.getDay()))));
    }
    public void alarmTime () {
        JFrame frame=new JFrame();
        frame.setSize(250,250);
        frame.setTitle("Alarm");
        JPanel panel=new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        DecimalFormat form = new DecimalFormat("00");
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
//        clicked =0;
        snooze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if(clicked<3){
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
//                    clicked++;
//                }
//                else{
//                    snooze.setEnabled(false);
//                    m.setVisible(true);
//                }
            }
        });

        frame.setVisible(true);

    }
    public String pathGetter(){
        return this.path;
    }
    public static void dataUpdate(){
        //creating file output stream
        String st,st2,st3;
        int i;
        try {
            FileWriter fileWriter = new FileWriter("./alarms.txt", false);
            for (i = 0; i < AlarmClock.ls.size(); i++) {
                st = AlarmClock.ls.get(i);
                st2=AlarmClock.flags.get(i);
                st3= Play.pl.get(i).pathGetter();
                fileWriter.write(st2 + st + st3+"\n");
            }
            fileWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
