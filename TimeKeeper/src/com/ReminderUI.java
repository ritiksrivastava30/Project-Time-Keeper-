package com;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class ReminderUI {
    private JTextField th,tm,dd,mm,yy;
    private JLabel lth,ltm,ldd,lmm,lyy;
    private JButton b1;
    DecimalFormat form=new DecimalFormat("00");
    JFrame f;
    public ReminderUI(int hour, int minute, int date, int month,int year){
        f= new JFrame();
        f.setSize(300, 400);
        f.setTitle("Reminder Details");
        JPanel jp= new JPanel();
        ldd= new JLabel("Date");
        ldd.setBounds(10,40,80,30);
        jp.add(ldd);
        dd= new JTextField(""+ date);
        dd.setHorizontalAlignment(SwingConstants.CENTER);
        dd.setBounds(100, 40, 80, 30);
        jp.add(dd);
        lmm= new JLabel("Month");
        lmm.setBounds(10,90,80,30);
        jp.add(lmm);
        mm= new JTextField(""+ month);
        mm.setHorizontalAlignment(SwingConstants.CENTER);
        mm.setBounds(100, 90, 80, 30);
        jp.add(mm);
        lyy= new JLabel("Year");
        lyy.setBounds(10,140,80,30);
        jp.add(lyy);
        yy= new JTextField(""+ year);
        yy.setHorizontalAlignment(SwingConstants.CENTER);
        yy.setBounds(100, 140, 80, 30);
        jp.add(yy);
        lth= new JLabel("Hour");
        lth.setBounds(10,200,80,30);
        jp.add(lth);
        th= new JTextField(""+ hour);
        th.setHorizontalAlignment(SwingConstants.CENTER);
        th.setBounds(100, 200, 80, 30);
        jp.add(th);
        ltm= new JLabel("Minute");
        ltm.setBounds(10,260,80,30);
        jp.add(ltm);
        tm= new JTextField(""+ minute);
        tm.setHorizontalAlignment(SwingConstants.CENTER);
        tm.setBounds(100, 260, 80, 30);
        jp.add(tm);
        b1= new JButton("Set");
        b1.setBounds(100, 315, 100, 30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String remDate = form.format(Integer.parseInt(dd.getText())) + "-" + form.format(Integer.parseInt(mm.getText())) + "-" + form.format(Integer.parseInt(yy.getText()));
                String remTime = form.format(Integer.parseInt(th.getText())) + ":" + form.format(Integer.parseInt(tm.getText()));
                Event.msg.setText("Reminder is enabled on "+ remDate+" at "+remTime);
                Reminder.setReminder(remTime, remDate, Event.getIndex());
                Event.remind.setVisible(false);
                Event.cancelRemind.setVisible(true);
                f.dispose();
            }});
        jp.add(b1);
        f.setContentPane(jp);
        f.setLayout(null);
        f.setVisible(true);
    }
}
