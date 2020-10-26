package com;
import java.time.*;
import java.time.temporal.ChronoField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
public class Calender {
    //JSpinner sp1, sp2;
    JTextField tf1, tf2;
    JTextArea ta1;
    JButton b1,u1,u2,d1,d2;
    JPanel conPane;
    JLabel l1, l2, l3;
    //SpinnerModel spinnerModel1,spinnerModel2;
    public Calender(){
        createUIComponent();
    }
    public void createUIComponent(){
        JFrame j= new JFrame();
        j.setTitle("Calender");
        j.setSize(400, 350);
        conPane= new JPanel();
        conPane.setBackground(new Color(204, 255, 204));
        l1=new JLabel("MONTH");
        l1.setBounds(60,20, 60,30);
        l2=new JLabel("YEAR");
        l2.setBounds(60,50, 60,30);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("MM");
        String formatDateTime1 = now.format(format1);
        u1= new JButton("+");
        u1.setBounds(155,20, 45,13);
        u1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a= Integer.parseInt(tf1.getText());
                if(a<12) {
                    a = a + 1;
                    tf1.setText(a + "");
                }
            }
        });
        tf1= new JTextField(formatDateTime1);
        tf1.setBounds(110,20, 45,26);
        tf1.setEditable(false);
        d1= new JButton("-");
        d1.setBounds(155,33, 45,13);
        d1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a= Integer.parseInt(tf1.getText());
                if(a>1) {
                    a = a - 1;
                    tf1.setText(a + "");
                }
            }
        });
        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("yyyy");
        String formatDateTime2 = now.format(format2);
        u2= new JButton("+");
        u2.setBounds(155,52, 45,13);
        u2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a= Integer.parseInt(tf2.getText());
                    a= a+1;
                    tf2.setText(a+"");
            }
        });
        tf2= new JTextField(formatDateTime2);
        tf2.setBounds(110,52, 45,26);
        tf2.setEditable(false);
        d2= new JButton("-");
        d2.setBounds(155,65, 45,13);
        d2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a= Integer.parseInt(tf2.getText());
                    a= a-1;
                    tf2.setText(a+"");
            }
        });
        b1= new JButton("SHOW");
        b1.setBounds(250,35, 80,25);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calenderPrinter();
            }
        });
        ta1= new JTextArea();
        ta1.setBounds(30,120, 320,150);
        ta1.setEditable(false);
        l3= new JLabel(Month.of(Integer.parseInt(formatDateTime1)).name());
        l3.setBounds(150,85, 100,40);
        conPane.add(l3);
        calenderPrinter();
        conPane.add(l1);
        conPane.add(l2);
        conPane.add(tf1);
        conPane.add(tf2);
        conPane.add(b1);
        conPane.add(ta1);
        conPane.add(u1);
        conPane.add(d1);
        conPane.add(u2);
        conPane.add(d2);
        j.setContentPane(conPane);
        j.setLayout(null);
        j.setVisible(true);
    }
    void calenderPrinter(){
        int days[]= new int[]{1,2,3,4,5,6,7};
        int months[]= new int[]{31,28,31,30,31,30,31,31,30,31,30,31,30};
        int i, dd, mm, yy, pos, day, j;
        int A[][]= new int[6][7];
        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd");
//        String DD = now.format(format1);
//        dd= Integer.parseInt(DD);
        dd= 15;
        mm= Integer.parseInt(tf1.getText());
        yy= Integer.parseInt(tf2.getText());
        LocalDate localDate = LocalDate.of(yy, mm, dd);
        DayOfWeek dayOfWeek = DayOfWeek.from(localDate);
        pos= dayOfWeek.get(ChronoField.DAY_OF_WEEK) + 1;
//        Month month = Month.from(localDate);
//        l3= new JLabel(month.name()+"");
//        l3.setBounds(150,85, 100,40);
//        conPane.add(l3);
        l3.setText(Month.of(mm).name());
        while(dd!=1)
        { dd--;
            pos--;
            if(pos<1)
                pos=7;
        }
        if((yy%4==0 && yy%100!= 0)||yy%400==0)
            months[1]= 29;
        for(i=0;i<6;i++)
            for(j=0;j<7;j++)
            {
                if(i==0&&j>=pos-1)
                {
                    A[0][j]=dd;
                    dd++;
                }
                if(i>=1&&dd<=months[mm-1])
                {
                    A[i][j]=dd;
                    dd++;
                }
            }
        ta1.setText("sun"+"         "+"mon"+"         "+"tue"+"         "+"wed"+"         "+"thurs"+"         "+"fri"+"         "+"sat"+"\n");
        for(i=0;i<6;i++)
        {
            for(j=0;j<7;j++)
            {
                //if(j>=0&&j<=pos-1)
                  //  ta1.append(" ");
                if(A[i][j]>0&&A[i][j]<=months[mm-1]) {
                    if(A[i][j]>0 && A[i][j]<=9)
                        ta1.append("  "+ A[i][j] + "            ");
                    else
                        ta1.append(A[i][j]+ "            ");
                }
                else
                    ta1.append("                ");
            }
            ta1.append("\n");
        }

    }
}
