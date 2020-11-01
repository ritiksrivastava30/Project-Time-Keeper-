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
import java.util.*;

public class Play extends Thread {

    int a, b ,clicked;
    public static int i=0;
    public static ArrayList<Play> pl=new ArrayList<>();
    private String path;
    private JTextArea labelArea;
    private int score,num1,num2,num3,correct;
    private Random rand;
    private JLabel prob;

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
            if (a1 == hours && b1 == mins && sec == 0  && dayCheck()==1) {
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
        JFrame frame = new JFrame();
        frame.setSize(325, 450);
        frame.setTitle("Alarm");
        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        DecimalFormat form = new DecimalFormat("00");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(Integer.parseInt(String.valueOf(AlarmClock.flags.get(i-1).charAt(7)))==0) {
                    SimpleAudioPlayer.clip.stop();
                    SimpleAudioPlayer.clip.close();
                }
            }
        });
        JLabel lb = new JLabel();
        lb.setBounds(80, 30, 160, 50);
        lb.setText(form.format(a) + ":" + form.format(b));
        lb.setFont(new Font("Serif", Font.BOLD, 45));
        panel.add(lb);

        JLabel l = new JLabel("ALARM");
        l.setBounds(90, 80, 200, 50);
        l.setFont(new Font("Serif", Font.PLAIN, 25));
        panel.add(l);

        JButton stop = new JButton("Ok! I,m Up");
        stop.setBounds(35, 140, 100, 35);
        panel.add(stop);
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    SimpleAudioPlayer.clip.stop();
                    SimpleAudioPlayer.clip.close();
            }
        });

        JLabel m = new JLabel("No more Snoozing allowed");
        m.setBounds(80, 175, 175, 25);
        m.setVisible(false);
        panel.add(m);

        JLabel label = new JLabel("Label:");
        label.setBounds(20, 200, 40, 25);
        panel.add(label);

        labelArea = new JTextArea();
        labelArea.setLineWrap(true);
        labelArea.setEditable(false);
        labelArea.setText(AlarmClock.alarmLabel.get(i - 1));
        labelArea.setBounds(60, 200, 200, 75);
        panel.add(labelArea);

        JButton close=new JButton("Close");
        close.setBounds(85, 355, 75, 30);
        panel.add(close);
        close.setVisible(false);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JButton snooze = new JButton("Snooze");
        snooze.setBounds(135, 140, 100, 35);
        panel.add(snooze);
        clicked = 0;
        snooze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clicked < 3) {
                    SimpleAudioPlayer.clip.stop();
                    frame.setVisible(false);
                    try {
                        pl.get(i - 1).sleep(300000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    SimpleAudioPlayer.vain();
                    frame.setVisible(true);
                    clicked++;
                } else {
                    snooze.setEnabled(false);
                    m.setVisible(true);
                }
            }
        });

        JTextField message=new JTextField("Solve below 3 problems to Stop");
        message.setBounds(35, 140, 200, 35);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(message);
        message.setVisible(false);

        JLabel wrong = new JLabel("Answer is wrong! Try Again");
        wrong.setBounds(90, 390, 200, 25);
        wrong.setVisible(false);

        prob = new JLabel();
        prob.setBounds(60, 310, 150, 25);
        prob.setVisible(false);

        JTextField ans = new JTextField("");
        ans.setBounds(210, 310, 50, 25);
        ans.setVisible(false);

        JButton sub = new JButton("Submit");
        sub.setBounds(160, 355, 125, 30);
        sub.setVisible(false);

        panel.add(prob);
        panel.add(wrong);
        panel.add(ans);
        panel.add(sub);


        frame.setVisible(true);

        if (Integer.parseInt(String.valueOf(AlarmClock.flags.get(i-1).charAt(7)))==1) {
            frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            prob.setVisible(true);
            stop.setVisible(false);
            snooze.setVisible(false);
            ans.setVisible(true);
            sub.setVisible(true);
            message.setVisible(true);
            rand = new Random();
            score = 0;

            correct = generateProb();

            sub.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (ans.getText().equals("") || correct != Integer.parseInt(ans.getText())) {
                        wrong.setVisible(true);
                    } else {
                        wrong.setVisible(false);
                        if (score == 2) {
                            close.setVisible(true);
                            sub.setText("Good Work");
                            SimpleAudioPlayer.clip.stop();
                            SimpleAudioPlayer.clip.close();
                        } else {
                            ans.setText("");
                            score++;
                            correct = generateProb();
                        }
                    }
                }
            });
            if (score == 3) {
                SimpleAudioPlayer.clip.stop();
                SimpleAudioPlayer.clip.close();
            }


        }

    }
    public int generateProb(){
        num1 = rand.nextInt(50);
        num2 = rand.nextInt(20);
        num3 = rand.nextInt(99);
        int correct;
        correct = (num1 * num2) + num3;
        prob.setText("( " + num1 + " X " + num2 + " )" + " + " + num3 + " = ");
        return correct;
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
            fileWriter.write("$\n");
            for (i = 0; i < AlarmClock.alarmLabel.size(); i++) {
                st = AlarmClock.alarmLabel.get(i);
                st = st.replace('\n', '^');
                fileWriter.write(st + "\n");
            }
            fileWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
