package com;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.*;

import static java.lang.Thread.sleep;


public class ZoneClock implements Runnable{
    private JPanel contPane;
    private JList list1;
    private JTextField tf1;
    private JTextField tf2;
    private JTextField tf3;
    private JButton bt1;
    private JTextArea ta1;
    private JButton bt2;
    private JButton bt3;
    private ArrayList<ZoneId> list=new ArrayList<>();

    public ZoneClock(){
        JFrame f= new JFrame();
        //super("ZoneClock");
        f.setTitle("ZoneClock");
        f.setSize(500, 500);
        contPane= new JPanel();
        contPane.setBounds(100,100,500,500);
        contPane.setBackground(new Color(204, 255, 204));
        final DefaultListModel<String> l1 = new DefaultListModel<>();
        l1.addElement("Asia/Karachi");
        l1.addElement("Europe/Paris");
        l1.addElement("Africa/Harare");
        l1.addElement("Australia/Darwin");
        l1.addElement("Pacific/Apia");
        l1.addElement("Pacific/Guadalcanal");
        l1.addElement("Asia/Ho_Chi_Minh");
        l1.addElement("America/Puerto_Rico");
        l1.addElement("America/Los_Angeles");
        l1.addElement("Pacific/Auckland");
        l1.addElement("Asia/Tokyo");
        l1.addElement("Asia/Kolkata");
        l1.addElement("Asia/Shanghai");
        l1.addElement("Africa/Cairo");
        JList<String> list1 = new JList<>(l1);
        list1.setBounds(0,2, 140,500);
        contPane.add(list1);
        tf1=new JTextField("Selected ZoneId");
        tf1.setBounds(150, 29, 200, 20);
        tf1.setEditable(false);
        tf2=new JTextField("Date And Time");
        tf2.setBounds(150, 89, 240, 20);
        tf2.setEditable(false);
        tf3=new JTextField("To Set This Time As App Time,Press: SET Button ");
        tf3.setBounds(150, 139, 280, 20);
        tf3.setEditable(false);
        contPane.add(tf1);
        contPane.add(tf2);
        contPane.add(tf3);
        bt1= new JButton("Set");
        bt1.setBounds(400,320,80,30);
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ZoneId zoneId1 = ZoneId.of(list1.getSelectedValue());
                LocalDateTime now1 = LocalDateTime.now(zoneId1);
                DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formatDateTime1 = now1.format(format1);
                DateTimeFormatter format2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formatDateTime2 = now1.format(format2);
                tf1.setText(list1.getSelectedValue());
                tf2.setText(formatDateTime1 +"  "+ formatDateTime2);
            }
        });
        bt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list1.getSelectedIndex()!= -1) {
                    firstPage.zone = ZoneId.of(list1.getSelectedValue());
                    tf1.setText("Selected ZoneId");
                    tf2.setText("Date And Time");
                }
            }
        });
        contPane.add(bt1);
        ta1= new JTextArea();
        bt2= new JButton("Add");
        bt2.setBounds(400, 360, 80, 30);
        contPane.add(bt2);
        bt2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list1.getSelectedIndex()!= -1 && !tf1.getText().equals("Selected ZoneId")) {
                    //ZoneId zoneId1 = ZoneId.of(list1.getSelectedValue());
                    list.add(ZoneId.of(list1.getSelectedValue()));
                    tf1.setText("Selected ZoneId");
                    tf2.setText("Date And Time");
                }
            }
        });
        bt3= new JButton("Remove");
        bt3.setBounds(400, 400, 80, 30);
        bt3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list.size()!=0){
                    list.remove(list.size()-1);
                }
            }
        });
        contPane.add(bt3);
        f.setContentPane(contPane);
        f.setLayout(null);
        f.setVisible(true);
    }

    @Override
    public void run() {
        JScrollPane scrollableTextArea = new JScrollPane(ta1);
        scrollableTextArea.setBounds(145, 170, 250, 260);
        contPane.add(scrollableTextArea);
        while(true) {
            ta1.setText("Total added clock: " + list.size()+ "\n");
            if (list.size()!= 0) {
                //ta1.setText("Kamal");
                String s= "";
                for(int i = 0; i < list.size(); i++) {
                    LocalDateTime now1 = LocalDateTime.now(list.get(i));
                    DateTimeFormatter format2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String formatDateTime2 = now1.format(format2);
                    s = s +"Clock:"+(i+1)+"   "+ list.get(i)+"  "+ formatDateTime2 + "\n";
                }
                ta1.append(s);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}