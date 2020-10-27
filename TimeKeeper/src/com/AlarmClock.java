package com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class AlarmClock {
    private JSpinner s1;
    private JSpinner s2;
    private JTextField t1;
    private JTextField t2;
    private JButton b1;
    private JLabel l1;
    private JLabel l2;
    private JPanel Content;
    private JButton b2;
    private JTextField t4;
    private JLabel l3;
    private JButton b3;
    private JButton b5;
    private JFrame f,fr,fr2;
    private JPanel panel,panel2;
    private JButton addAlarm;
    private JList list;
    public static int index;
    private JLabel[] day=new JLabel[8];
    private JButton[] off=new JButton[8];
    private JButton[] on=new JButton[8];
    public int p1, p2,h;
    public static DefaultListModel<String> ls=new DefaultListModel<>();
    DecimalFormat form = new DecimalFormat("00");
    public String[] days={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    static ArrayList<String> flags=new ArrayList<>();
    public AlarmClock() { createUIComponents();
    }


    private void createUIComponents() {
        fr = new JFrame();
        fr.setSize(500, 500);
        panel = new JPanel();
        panel.setLayout(null);
        fr.add(panel);

        JLabel info = new JLabel("ALARMS");
        info.setBounds(200, 15, 300, 50);
        info.setFont(new Font("Serif", Font.PLAIN, 25));
        panel.add(info);

        addAlarm = new JButton("Add Alarm");
        addAlarm.setBounds(100, 85, 300, 40);
        panel.add(addAlarm);

        list = new JList(ls);
        list.setBounds(75, 150, 335, 2000);
        list.setFont(new Font("Serif", Font.PLAIN, 26));
        panel.add(list);

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ArrayList<Integer> fl=new ArrayList<>(7);
                index = list.getSelectedIndex();
                //fr.setVisible(false);
                for(int y=0;y<=6;y++)
                    fl.add(Integer.parseInt(String.valueOf(flags.get(index).charAt(y))));
                fr2=new JFrame();
                fr2.setSize(315,520);
                panel2=new JPanel();
                panel2.setLayout(null);
                fr2.add(panel2);


                JLabel lb = new JLabel(ls.get(index).substring(2,8));
                lb.setBounds(110, 30, 200, 40);
                lb.setFont(new Font("Serif", Font.BOLD, 45));
                panel2.add(lb);

                day[0] = new JLabel("Status: ");
                day[0].setBounds(25, 100, 100, 30);
                day[0].setFont(new Font("Serif", Font.PLAIN, 25));
                panel2.add(day[0]);

                on[0]=new JButton("ON");
                on[0].setBounds(120,100,80,30);
                panel2.add(on[0]);
                on[0].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        on[0].setEnabled(false);
                        ls.set(index,ls.get(index).replaceAll("OFF","ON"));
                        Play.pl.get(index).resume();
                        off[0].setEnabled(true);
                    }
                });

                off[0]=new JButton("OFF");
                off[0].setBounds(200,100,80,30);
                panel2.add(off[0]);
                off[0].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ls.set(index,ls.get(index).replaceAll("ON","OFF"));
                        Play.pl.get(index).suspend();
                        on[0].setEnabled(true);
                        off[0].setEnabled(false);
                    }
                });

                JButton save=new JButton("Save");
                save.setBounds(60,400,180,30);
                save.setEnabled(false);
                panel2.add(save);
                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s="";
                        for(int p=0;p<7;p++){
                            s=s+fl.get(p).toString();
                        }
                        flags.set(index,s);
                        save.setEnabled(false);
                    }
                });

                int temp=145;
                for(h=1;h<8;h++) {
                    int x=h;//bcoz h was read as 8 in click on button
                    day[x] = new JLabel(days[x - 1] + ":");
                    day[x].setBounds(25, temp, 100, 30);
                    day[x].setFont(new Font("Serif", Font.PLAIN, 18));
                    panel2.add(day[x]);

                    on[x]=new JButton("ON");
                    on[x].setBounds(140,temp,60,30);
                    panel2.add(on[x]);

                    off[x]=new JButton("OFF");
                    off[x].setBounds(200,temp,60,30);
                    panel2.add(off[x]);

                    on[x].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            save.setEnabled(true);
                            on[x].setEnabled(false);
                            off[x].setEnabled(true);
                            fl.set(x-1,1);
                        }
                    });

                    off[x].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            save.setEnabled(true);
                            on[x].setEnabled(true);
                            off[x].setEnabled(false);
                            fl.set(x-1,0);
                        }
                    });

                    if (fl.get(x - 1) == 1) {
                        on[x].setEnabled(false);
                        off[x].setEnabled(true);
                    }
                    else{
                        off[x].setEnabled(false);
                        on[x].setEnabled(true);
                    }

                    temp=temp+35;
                }


                if(ls.get(index).substring(ls.get(index).length()-2,ls.get(index).length()).equals("ON"))
                    on[0].setEnabled(false);
                else
                    off[0].setEnabled(false);



                JButton delete=new JButton("Delete Alarm");
                delete.setBounds(60,440,180,30);
                panel2.add(delete);
                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ls.remove(index);
                        fr2.dispose();
                        flags.remove(index);
                        Play.pl.get(index).stop();
                        Play.pl.remove(index);
                        Play.i--;
                    }
                });

                fr2.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        if(save.isEnabled()) {
                            JFrame close = new JFrame();
                            close.setSize(250, 200);
                            close.setTitle("Alarm");
                            JPanel cpanel = new JPanel();
                            cpanel.setLayout(null);
                            close.add(cpanel);

                            JLabel l = new JLabel("Do you want to save changes?");
                            l.setBounds(0, 35, 250, 50);
                            l.setHorizontalAlignment(SwingConstants.CENTER);
                            l.setFont(new Font("Serif", Font.PLAIN, 15));
                            cpanel.add(l);

                            JButton yes = new JButton("YES");
                            yes.setBounds(25, 100, 60, 35);
                            cpanel.add(yes);
                            yes.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String s = "";
                                    for (int p = 0; p < 7; p++) {
                                        s = s + fl.get(p).toString();
                                    }
                                    flags.set(index, s);
                                    close.dispose();
                                }
                            });

                            JButton no = new JButton("NO");
                            no.setBounds(125, 100, 60, 35);
                            cpanel.add(no);
                            no.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    close.dispose();
                                }
                            });

                            close.setVisible(true);
                        }
                    }
                });

                fr2.setVisible(true);
                list.clearSelection();
            }


        });


        addAlarm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alarmDetails();
            }
        });
        fr.setVisible(true);
        fr.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dataUpdate();
            }
        });
    }
    private void alarmDetails(){
        f = new JFrame();
        f.setTitle("Set Alarm");
        f.setSize(300, 400);
        Content = new JPanel();
        Content.setBounds(100, 100, 300, 400);
        Content.setBackground(new Color(204, 255, 204));
        l1 = new JLabel("hr: ");
        l1.setBounds(50, 90, 50, 30);
        // s1=new JSpinner();
        SpinnerModel value = new SpinnerNumberModel(0, 0, 23, 1);
        s1 = new JSpinner(value);
        s1.setBounds(50, 120, 50, 30);
        t1 = new JTextField("");
        t1.setBounds(50, 70, 50, 25);
        t1.setEditable(false);
        b1 = new JButton("Set hr");
        b1.setBounds(35, 170, 80, 30);
        l2 = new JLabel("min: ");
        l2.setBounds(200, 90, 50, 30);
        // s2=new JSpinner();
        SpinnerModel va = new SpinnerNumberModel(0, 0, 59, 1);
        s2 = new JSpinner(va);
        s2.setBounds(200, 120, 50, 30);
        t2 = new JTextField("");
        t2.setBounds(200, 70, 50, 25);
        t2.setEditable(false);
        b2 = new JButton("Set min");
        b2.setBounds(185, 170, 80, 30);
        b3 = new JButton("Set Alarm");
        b3.setBounds(100, 215, 100, 30);
        l3 = new JLabel("Alarm set at : ");
        l3.setBounds(30, 20, 100, 30);
        t4 = new JTextField("");
        t4.setBounds(110, 29, 80, 25);
        t4.setEditable(false);
        b5 = new JButton("Choose Audio");
        b5.setBounds(90, 300, 120, 30);



        Content.add(l1);
        Content.add(l2);
        Content.add(t1);
        Content.add(t2);
        Content.add(s1);
        Content.add(s2);
        Content.add(b1);
        Content.add(b2);
        Content.add(l3);
        Content.add(t4);
        Content.add(b3);
        Content.add(b5);
        f.setContentPane(Content);
        f.setLayout(null);
        f.setVisible(true);


        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t1.setText(s1.getValue().toString());
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t2.setText(s2.getValue().toString());
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flags.add("1111111");
                ls.addElement("  "+form.format(Integer.parseInt(t1.getText())) + ":" + form.format(Integer.parseInt(t2.getText()))+"                Status: ON");
                t4.setText(form.format(Integer.parseInt(t1.getText())) + ":" + form.format(Integer.parseInt(t2.getText())));
                p1 = (int) s1.getValue();
                p2 = (int) s2.getValue();
                Play.setAlarm(p1,p2);
                //dataUpdate();
            }

        });

        SimpleAudioPlayer.filePath = new File("src\\com\\sim.wav");
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fc.showDialog(f, "Select");
                if (fc.getSelectedFile() != null) {
                    SimpleAudioPlayer.filePath= fc.getSelectedFile();
                }

            }

        });

    }
    private void dataUpdate(){
        //creating file output stream
        String st,st2;
        int i;
        try {
            FileWriter fileWriter = new FileWriter("./alarms.txt", false);
            for (i = 0; i < ls.size(); i++) {
                st = ls.get(i);
                st2=flags.get(i);
                fileWriter.write(st2 + st + "\n");
            }
            fileWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}