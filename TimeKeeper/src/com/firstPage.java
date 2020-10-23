package com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Calendar;

import static java.lang.Thread.sleep;

public class firstPage extends JFrame implements Runnable{
    private JTextField t1;
    private JTextField t2;
    private JTextField t3;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5,b6;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JPanel contentPane;
    public static ZoneId zone = ZoneId.systemDefault();
    //ZoneClock zz;
    public void run(){
        LocalDateTime now = LocalDateTime.now(zone);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formatDateTime1 = now.format(format1);
        t3.setText(formatDateTime1);
        OffsetDateTime offsetDT = OffsetDateTime.now(zone);
        DayOfWeek day= offsetDT.getDayOfWeek();
        t2.setText(day.toString());
        while(true){
            LocalDateTime now1 = LocalDateTime.now(zone);
            DateTimeFormatter format2 = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formatDateTime2 = now1.format(format2);
            t1.setText(formatDateTime2);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public firstPage() {
        createUIComponents();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        setTitle("What Time It Is?");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 450);
        contentPane= new JPanel();
        contentPane.setBounds(100,100,300,400);
        contentPane.setBackground(new Color(204, 255, 204));
        l1=new JLabel("Time: ");
        l1.setBounds(30,20, 100,30);
        t1=new JTextField("");
        t1.setBounds(80, 29, 100, 18);
        t1.setEditable(false);
        l2=new JLabel("Day: ");
        l2.setBounds(30,40, 100,30);
        t2=new JTextField("");
        t2.setBounds(80, 49, 100, 18);
        t2.setEditable(false);
        l3=new JLabel("Date: ");
        l3.setBounds(30,60, 100,30);
        t3=new JTextField("");
        t3.setBounds(80, 69, 100, 18);
        t3.setEditable(false);
        contentPane.add(l1);
        contentPane.add(l2);
        contentPane.add(l3);
        contentPane.add(t1);
        contentPane.add(t2);
        contentPane.add(t3);
        b1=new JButton("STOPWATCH");
        b1.setBounds(50,100,180,30);
        b1.setBackground(Color.yellow);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StopWatch sW= new StopWatch();
                sW.start();
            }
        });
        contentPane.add(b1);
        b2=new JButton("ALARM");
        b2.setBounds(50,150,180,30);
        b2.setBackground(Color.RED);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AlarmClock zz = new AlarmClock();
            }
        });
        contentPane.add(b2);
        b3=new JButton("TIMER");
        b3.setBounds(50,200,180,30);
        b3.setBackground(Color.BLUE);
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimerPage.timer();
            }
        });
        contentPane.add(b3);
        b4=new JButton("EVENTS");
        b4.setBounds(50,250,180,30);
        b4.setBackground(Color.GREEN);
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Event.openEvent();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        contentPane.add(b4);
        b5=new JButton("Zone Clock");
        b5.setBounds(50,300,180,30);
        b5.setBackground(Color.PINK);
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZoneClock zz= new ZoneClock();
                Thread z =new Thread(zz);
                z.start();
            }
        });
        contentPane.add(b5);
        b6=new JButton("CALENDER");
        b6.setBounds(50,350,180,30);
        b6.setBackground(Color.yellow);
        b6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Calender();
            }
        });
        contentPane.add(b6);
        setContentPane(contentPane);
        setLayout(null);
    }

}
