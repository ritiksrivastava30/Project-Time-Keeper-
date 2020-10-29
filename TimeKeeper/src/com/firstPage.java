package com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class firstPage extends JFrame implements Runnable{
    private JTextField forTime;
    private JTextField forDay;
    private JTextField forDate;
    private JButton stopWatch;
    private JButton alarm;
    private JButton timer;
    private JButton events;
    private JButton zoneClock, calender;
    private JLabel timeLable;
    private JLabel dayLable;
    private JLabel dateLable;
    private JPanel contentPane;
    private File file;
    private String temp;
    public static int flag= 0;
    public static ZoneId zone = ZoneId.systemDefault();
    public void run(){
        //logic for "forTime","forDay","forDate" textField
        while(true){
            //dateTime of zone
            LocalDateTime zoneDateTime = LocalDateTime.now(zone);

            //formatting it in dd-mm-yyyy format and set it in textField
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String inDateFormat = zoneDateTime.format(dateFormat);
            forDate.setText(inDateFormat);

            //finding day of week and set it in textField
            OffsetDateTime offsetDT = OffsetDateTime.now(zone);
            DayOfWeek day= offsetDT.getDayOfWeek();
            forDay.setText(day.toString());

            //formatting it in HH:mm:ss format and set it in textField
            DateTimeFormatter innerTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
            String inTimeFormat = zoneDateTime.format(innerTimeFormat);
            forTime.setText(inTimeFormat);

            try {
                sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public firstPage() throws IOException {

        //Reading data from file for ZoneClock
        file = new File("./Zonedata.txt");
        String temp1;
        if (file.exists()) {
            try {
                Scanner sc = new Scanner(file); //generating an input stream

                while (sc.hasNextLine()) {
                    temp1 = sc.nextLine();
                    if (temp1.equals("$"))
                        break;
                    ZoneClock.selectedZoneList.add(ZoneId.of(temp1));
                }

                if(sc.hasNextLine()) {
                    temp1 = sc.nextLine();
                    firstPage.zone = ZoneId.of(temp1);
                }

                sc.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {
            file.createNewFile(); //creating new file, if any file of given name does not exists
        }

        //Reading data from file for alarm
        file = new File("./alarms.txt");
        if (file.exists()) {
            Scanner sc = new Scanner(file); //generating an input stream
            try {
                while (sc.hasNextLine()) {
                    temp = sc.nextLine();
                    AlarmClock.flags.add(temp.substring(0,7));
                    AlarmClock.ls.addElement(temp.substring(7,40));
                    Play.setAlarm(Integer.parseInt(temp.substring(9,11)),Integer.parseInt(temp.substring(12,14)),
                            temp.substring(40));
                }

                sc.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            file.createNewFile(); //creating new file, if any file of given name does not exists
        }

        //Reading events data from file
        file = new File("./data.txt");
        String temp;
        if (file.exists()) {
            Scanner sc = new Scanner(file); //generating an input stream

            try {
                while (sc.hasNextLine()) {
                    temp = sc.nextLine();
                    if (temp.equals("$"))
                        break;
                    Event.eventNames.addElement(temp);
                }

                while (sc.hasNextLine()) {
                    temp = sc.nextLine();
                    if (temp.equals("$"))
                        break;
                    Event.dateOfEvents.addElement(temp);
                }

                while (sc.hasNextLine()) {
                    temp = sc.nextLine();
                    if (temp.equals("$"))
                        break;
                    temp = temp.replace('^', '\n');
                    Event.descriptionOfEvents.addElement(temp);
                }

                while (sc.hasNextLine()) {
                    temp = sc.nextLine();
                    Reminder.setReminder(temp.substring(0,5),temp.substring(5,15),Integer.parseInt(temp.substring(15)));
                }

                sc.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {
            file.createNewFile(); //creating new file, if any file of given name does not exists
        }
        createUIComponents();
    }

    private void createUIComponents() {
        setTitle("What Time It Is?");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 450);
        //instantiating JPanel,setting its bounds and its background color
        contentPane= new JPanel();
        contentPane.setBounds(100,100,300,400);
        contentPane.setBackground(new Color(204, 255, 204));
        setContentPane(contentPane);

        //instantiating JLabel and setting its bounds
        timeLable =new JLabel("Time: ");
        timeLable.setBounds(30,20, 100,30);
        contentPane.add(timeLable);

        //instantiating JTextField and setting its bounds and can't edited by user.
        forTime =new JTextField("");
        forTime.setBounds(80, 29, 100, 18);
        forTime.setEditable(false);
        contentPane.add(forTime);

        //instantiating JLabel and setting its bounds
        dayLable =new JLabel("Day: ");
        dayLable.setBounds(30,40, 100,30);
        contentPane.add(dayLable);

        //instantiating JTextField and setting its bounds and can't edited by user.
        forDay =new JTextField("");
        forDay.setBounds(80, 49, 100, 18);
        forDay.setEditable(false);
        contentPane.add(forDay);

        //instantiating JLabel and setting its bounds
        dateLable =new JLabel("Date: ");
        dateLable.setBounds(30,60, 100,30);
        contentPane.add(dateLable);

        //instantiating JTextField and setting its bounds and can't edited by user.
        forDate =new JTextField("");
        forDate.setBounds(80, 69, 100, 18);
        forDate.setEditable(false);
        contentPane.add(forDate);

        //instantiating JButton and setting its bounds,its background color
        stopWatch =new JButton("STOPWATCH");
        stopWatch.setBounds(50,100,180,30);
        stopWatch.setBackground(Color.yellow);
        //its actionListener
        stopWatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //only one object of stopwatch can be created
                if(firstPage.flag== 0)
                {
                    StopWatch sW= new StopWatch();
                    sW.start();
                    firstPage.flag= 1;
                }
            }
        });
        contentPane.add(stopWatch);

        //instantiating JButton and setting its bounds,its background color
        alarm =new JButton("ALARM");
        alarm.setBounds(50,150,180,30);
        alarm.setBackground(Color.RED);
        //its actionListener
        alarm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AlarmClock zz = new AlarmClock();
            }
        });
        contentPane.add(alarm);

        //instantiating JButton and setting its bounds,its background color
        timer =new JButton("TIMER");
        timer.setBounds(50,200,180,30);
        timer.setBackground(Color.BLUE);
        //its actionListener
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimerPage.timer();
            }
        });
        contentPane.add(timer);

        //instantiating JButton and setting its bounds,its background color
        events =new JButton("EVENTS");
        events.setBounds(50,250,180,30);
        events.setBackground(Color.GREEN);
        //its actionListener
        events.addActionListener(new ActionListener() {
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
        contentPane.add(events);

        //instantiating JButton and setting its bounds,its background color
        zoneClock =new JButton("Zone Clock");
        zoneClock.setBounds(50,300,180,30);
        zoneClock.setBackground(Color.PINK);
        //its actionListener
        zoneClock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ZoneClock zz= new ZoneClock();
                Thread z =new Thread(zz);
                z.start();
            }
        });
        contentPane.add(zoneClock);

        //instantiating JButton and setting its bounds,its background color
        calender =new JButton("CALENDER");
        calender.setBounds(50,350,180,30);
        calender.setBackground(Color.yellow);
        //its actionListener
        calender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Calender();
            }
        });
        contentPane.add(calender);
        setLayout(null);
    }
}
