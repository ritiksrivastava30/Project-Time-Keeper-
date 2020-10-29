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
    JTextField monthField, yearField;
    JTextArea calenderArea;
    JButton show, monthUP, yearUP, monthDOWN, yearDOWN;
    JPanel conPane;
    JLabel monthLabel, yearLabel, monthNAME;

    public Calender()
    {
        createUIComponent();

    }

    public void createUIComponent(){
        JFrame j= new JFrame();
        j.setTitle("Calender");
        j.setSize(400, 350);

        conPane= new JPanel();
        conPane.setBackground(new Color(204, 255, 204));
        j.setContentPane(conPane);

        monthLabel =new JLabel("MONTH");
        monthLabel.setBounds(60,20, 60,30);
        conPane.add(monthLabel);

        yearLabel =new JLabel("YEAR");
        yearLabel.setBounds(60,50, 60,30);
        conPane.add(yearLabel);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("MM");
        String formatDateTime1 = now.format(format1);

        monthUP = new JButton("+");
        monthUP.setBounds(155,20, 45,13);
        monthUP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a= Integer.parseInt(monthField.getText());
                if(a<12) {
                    a = a + 1;
                    monthField.setText(a + "");
                }
            }
        });
        conPane.add(monthUP);

        monthField = new JTextField(formatDateTime1);
        monthField.setBounds(110,20, 45,26);
        monthField.setEditable(false);
        conPane.add(monthField);

        monthDOWN = new JButton("-");
        monthDOWN.setBounds(155,33, 45,13);
        monthDOWN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a= Integer.parseInt(monthField.getText());
                if(a>1) {
                    a = a - 1;
                    monthField.setText(a + "");
                }
            }
        });
        conPane.add(monthDOWN);

        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("yyyy");
        String formatDateTime2 = now.format(format2);

        yearUP = new JButton("+");
        yearUP.setBounds(155,52, 45,13);
        yearUP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a= Integer.parseInt(yearField.getText());
                    a= a+1;
                    yearField.setText(a+"");
            }
        });
        conPane.add(yearUP);

        yearField = new JTextField(formatDateTime2);
        yearField.setBounds(110,52, 45,26);
        yearField.setEditable(false);
        conPane.add(yearField);

        yearDOWN = new JButton("-");
        yearDOWN.setBounds(155,65, 45,13);
        yearDOWN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a= Integer.parseInt(yearField.getText());
                    a= a-1;
                    yearField.setText(a+"");
            }
        });
        conPane.add(yearDOWN);

        show = new JButton("SHOW");
        show.setBounds(250,35, 80,25);
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calenderPrinter();
            }
        });
        conPane.add(show);

        calenderArea = new JTextArea();
        calenderArea.setBounds(30,120, 320,150);
        calenderArea.setEditable(false);
        conPane.add(calenderArea);

        monthNAME = new JLabel(Month.of(Integer.parseInt(formatDateTime1)).name());
        monthNAME.setBounds(150,85, 100,40);
        conPane.add(monthNAME);

        calenderPrinter();

        j.setLayout(null);
        j.setVisible(true);
    }

    //*******method for calender of each month**********//
    void calenderPrinter(){
        int days[]= new int[]{1,2,3,4,5,6,7}; //given number for each day of week
        int months[]= new int[]{31,28,31,30,31,30,31,31,30,31,30,31,30};//no of days in each month of year
        int i, dd= 15, mm, yy, pos, j;
        int A[][]= new int[6][7];

        mm= Integer.parseInt(monthField.getText());
        yy= Integer.parseInt(yearField.getText());

        LocalDate localDate = LocalDate.of(yy, mm, dd);
        DayOfWeek dayOfWeek = DayOfWeek.from(localDate);
        pos= dayOfWeek.get(ChronoField.DAY_OF_WEEK) + 1;
        monthNAME.setText(Month.of(mm).name());

        //***********Logic for calender****************//

        //using this loop to find day of week for dd= 1 in each month
        while(dd!=1)
        {
            dd--;
            pos--;
            if(pos<1)
                pos=7;
        }

        //checking each year is leap or not
        if((yy%4==0 && yy%100!= 0)||yy%400==0)
            months[1]= 29;

        //assigning whole month dates [1 - 28/29/30/31] to array
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

        //setting month in calender area
        calenderArea.setText("sun"+"         "+"mon"+"         "+"tue"+"         "+"wed"+"         "+"thurs"+"         "+"fri"+"         "+"sat"+"\n");
        for(i=0;i<6;i++)
        {
            for(j=0;j<7;j++)
            {
                if(A[i][j]>0&&A[i][j]<=months[mm-1]) {
                    if(A[i][j]>0 && A[i][j]<=9)
                        calenderArea.append("  "+ A[i][j] + "            ");
                    else
                        calenderArea.append(A[i][j]+ "            ");
                }
                else
                    calenderArea.append("                ");
            }
            calenderArea.append("\n");
        }
    }
}
