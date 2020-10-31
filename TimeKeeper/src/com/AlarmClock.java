package com;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
public class AlarmClock {
    private JTextField hourField;
    private JTextField minField;
    private JButton minusHour;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3,l4;
    private JPanel Content;
    private JButton minusMin;
    private JButton setAlarm;
    private JButton plusMin;
    private JButton chooseFile;
    private JButton plusHour;
    private JFrame f,fr,fr2;
    private JPanel panel,panel2;
    private JButton addAlarm;
    private JList list;
    public static int index;
    private int m,h;
    private JLabel[] day=new JLabel[8];
    private JButton[] off=new JButton[8];
    private JButton[] on=new JButton[8];
    public int p1, p2;
    private String localPath;
    private JTextArea labelArea;
    public static DefaultListModel<String> ls=new DefaultListModel<>();//this will hold the list of all the alarms
    DecimalFormat form = new DecimalFormat("00");
    public String[] days={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    static ArrayList<String> flags=new ArrayList<>();
    public static ArrayList<String> alarmLabel=new ArrayList<>();
    public AlarmClock() { createUIComponents();
    }


    private void createUIComponents() {
        //creating a frame that will show the list of all the Alarms and provide click listeners for further modification
        fr = new JFrame();
        panel = new JPanel();
        fr.setSize(500, 500);
        panel.setLayout(null);
        JScrollPane jsp = new JScrollPane(panel);
        panel.setPreferredSize(new Dimension(250, 3500));
        panel.setLayout(null);
        fr.getContentPane().add(jsp);

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

        //adding listener to list
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ArrayList<Integer> fl=new ArrayList<>(7);
                index = list.getSelectedIndex();
                //converting String of binary sequence representing activeness to alarm on a particular day of week into an int array so that it could be modified easily
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

                //button to save the changes
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

                //creating 7 buttons using a single loop
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
                        alarmLabel.remove(index);
                        Play.i--;
                    }
                });

                //making a dialogue box to appear on clicking close button without saving changes
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

                            //this button will save changes
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

                            //this button will discard changes
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

        //when user confirms to set alarm at entered time
        addAlarm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // Play.path="src\\com\\sim.wav";
                alarmDetails();
            }
        });
        fr.setVisible(true);
        //calling a function that creates output stream to write data to the local disk file
        fr.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Play.dataUpdate();
            }
        });
    }
    private void alarmDetails(){
        m=0;//m represents count for minutes
        h=0;//h represents count for hours
        f = new JFrame();
        f.setTitle("Set Alarm");
        f.setSize(300, 475);
        Content = new JPanel();
        Content.setBounds(100, 100, 300, 400);
        Content.setBackground(new Color(204, 255, 204));
        l1 = new JLabel("Hour");
        l1.setBounds(60, 70, 50, 30);
        hourField = new JTextField(String.valueOf(h));
        hourField.setBounds(50, 130, 50, 30);
        hourField.setEditable(false);
        minusHour = new JButton("-");
        minusHour.setBounds(50, 160, 50, 30);
        plusHour = new JButton("+");
        plusHour.setBounds(50, 100, 50, 30);
        l2 = new JLabel("Minute");
        l2.setBounds(203, 70, 50, 30);
        minField = new JTextField(String.valueOf(m));
        minField.setBounds(200, 130, 50, 30);
        minField.setEditable(false);
        minusMin = new JButton("-");
        minusMin.setBounds(200, 160, 50, 30);
        plusMin = new JButton("+");
        plusMin.setBounds(200, 100, 50, 30);
        setAlarm = new JButton("Set Alarm");
        setAlarm.setBounds(100, 245, 100, 30);
        l3=new JLabel("ALARM");
        l3.setBounds(95, 29, 120, 25);
        l3.setFont(new Font("Serif", Font.PLAIN, 30));
        l4= new JLabel();
        l4.setBounds(79,210,180,25);
        chooseFile = new JButton("Choose Audio");
        chooseFile.setBounds(90, 285, 120, 30);
        hourField.setHorizontalAlignment(SwingConstants.CENTER);
        minField.setHorizontalAlignment(SwingConstants.CENTER);
        Content.add(l1);
        Content.add(l2);
        Content.add(l3);
        Content.add(l4);
        Content.add(hourField);
        Content.add(minField);
        Content.add(minusHour);
        Content.add(minusMin);
        Content.add(setAlarm);
        Content.add(chooseFile);
        Content.add(plusMin);
        Content.add(plusHour);
        f.setContentPane(Content);
        f.setLayout(null);
        f.setVisible(true);


        minusHour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h=h-1;
                if(h<0)
                    h=23;
                hourField.setText(String.valueOf(h));

            }
        });
        minusMin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {m=m-1;
                if(m<0)
                    m=59;
                minField.setText(String.valueOf(m));
            }
        });
        plusMin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m=m+1;
                if(m>59)
                    m=0;
                minField.setText(String.valueOf(m));
            }
        });
        plusHour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h=h+1;
                if(h>23)
                    h=0;
                hourField.setText(String.valueOf(h));
            }
        });

        JLabel lb=new JLabel("Label:");
        lb.setBounds(20,335,50,25);
        Content.add(lb);

        //adding text area where we can add label to an alarm
        labelArea = new JTextArea();
        labelArea.setLineWrap(true);
        labelArea.setBounds(70, 335, 200, 75);
        Content.add(labelArea);

        localPath= "src\\com\\sim.wav";
        songFilename(localPath);
        setAlarm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flags.add("1111111");
                ls.addElement("  "+form.format(Integer.parseInt(hourField.getText())) + ":" + form.format(Integer.parseInt(minField.getText()))+"                Status: ON");
                p1 = Integer.parseInt(hourField.getText());
                p2 = Integer.parseInt(minField.getText());
                alarmLabel.add(labelArea.getText());
                //calling set alarm function by of Play class by passing hour and minute as argument
                Play.setAlarm(p1,p2,localPath);
                f.dispose();
            }

        });

        //SimpleAudioPlayer.filePath = new File("src\\com\\sim.wav");
        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fc.addChoosableFileFilter(new FileNameExtensionFilter(".wav Audio files", "wav"));
                fc.setAcceptAllFileFilterUsed(true);
                fc.showDialog(f, "Select");
                if (fc.getSelectedFile() != null) {
                    localPath =fc.getSelectedFile().getAbsolutePath();
                    songFilename(localPath);
                    //b5.setToolTipText(localPath);
                }
            }

        });


    }
    private void songFilename(String locPath){
        String p= null;
        Scanner sc= new Scanner(locPath);
        sc.useDelimiter("\\\\");
        while(sc.hasNext())
             p = sc.next();
        l4.setText("Current Audio: "+ p);

    }
}