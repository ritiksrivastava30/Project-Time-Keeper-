/*
Flow of Program:
-Event class
-Creating a File Input Stream via Constructor
-openEvent() :public static function(to be called by through Main Page)
-showEventListPage() : this private function creates a Frame that will show List of Events
-createEventForm() : this private function will create a form type template to fill details
-addData() : this private function will add entered details to corresponding Array Lists
-dataUpdate : this private function will update data in the the Local file where input data is Stored
-Creating a File Output Stream to put entered details into a local file
-frame : contains page showing List of Events
-frame2 : contains the Event form Template
-frame3 : shows the details of selected event

 */

package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Event {
    JFrame frame, frame2, frame3;
    JPanel panel, panel2, panel3;
    JButton addEvent, addingEvent, deleteEvent, back31, back10, back21, editEvent;//back31 represents back button to go back from frame 3 to frame 1
    static JList listOfName, listOfSchedule;
    JTextArea descriptionArea, enteredDescription;
    JLabel nameOfEvent, dateLabel, time, description, showDescription, showTime, showDate, showName;
    JTextField nameEntry;
    SpinnerModel spinnerModel1, spinnerModel3, spinnerModel4, spinnerModel5;
    JSpinner spinner, spinner2, spinner3, spinner4, spinner5;
    DecimalFormat form=new DecimalFormat("00");
    static DefaultListModel<String> eventNames = new DefaultListModel<>(); //an Array List to store name of Events
    static DefaultListModel<String> dateOfEvents = new DefaultListModel<>(); //an Array List to store date and time of any Event
    static DefaultListModel<String> descriptionOfEvents = new DefaultListModel<>(); //an Array List to store Description of Events as entered by the user
    int newEventAdditionIndex;
    private static int index;
    private int date,year,hour,minute;
    private String monthName;
    public static JLabel msg;
    public static JButton remind, cancelRemind;
    ArrayList<String> months = new ArrayList<String>(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));

    public static void openEvent() throws IOException {
        Event obj = new Event();
        obj.showEventListPage();
    }

    //function to create all the components of Event
    private void showEventListPage() {
        frame = new JFrame();
        panel = new JPanel();
        frame.setSize(500, 500);
        panel.setLayout(null);
        JScrollPane jsp = new JScrollPane(panel);
        panel.setPreferredSize(new Dimension(250, 3500));
        panel.setLayout(null);
        frame.getContentPane().add(jsp);
        //creating file output stream when close button is clicked i.e. Event frame is closed
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dataUpdate();
            }
        });

        JLabel info = new JLabel("List of Events");
        info.setBounds(175, 15, 300, 50);
        info.setFont(new Font("Serif", Font.PLAIN, 25));
        panel.add(info);

        addEvent = new JButton("Add Event");
        addEvent.setBounds(100, 85, 300, 40);
        panel.add(addEvent);

        back10 = new JButton("Back");
        back10.setBounds(30, 25, 70, 30);
        panel.add(back10);
        back10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataUpdate();
                frame.dispose();
                Main.visibilty(true);
            }
        });

        listOfName = new JList(eventNames);
        listOfName.setBounds(25, 150, 240, 3500);
        listOfName.setFont(new Font("Serif", Font.PLAIN, 26));
        panel.add(listOfName);

        listOfSchedule = new JList(dateOfEvents);
        listOfSchedule.setBounds(265, 150, 180, 3500);
        listOfSchedule.setEnabled(false);
        panel.add(listOfSchedule);
        frame.setVisible(true);

        addEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createEventForm();
            }
        });


        // adding an Action Listener when any List Item on the screen is Clicked
        listOfName.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //index variable will have the index of selected item from the List
                frame.setVisible(false);
                index = listOfName.getSelectedIndex();
                showDetails(index);
            }
            });
        }
        public void showDetails(int index){
                frame3 = new JFrame();
                panel3 = new JPanel();
                frame3.setSize(500, 500);
                //frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                panel3.setLayout(null);
                frame3.add(panel3);
                frame3.setVisible(true);
                frame3.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        if(frame!=null)
                        frame.setVisible(true);
                    }
                });
                showName = new JLabel(eventNames.get(index));
                showName.setHorizontalAlignment(SwingConstants.CENTER);
                showName.setBounds(0, 25, 500, 100);
                showName.setFont(new Font("Serif", Font.PLAIN, 50));
                panel3.add(showName);

                showDate = new JLabel(dateOfEvents.get(2 * index), SwingConstants.CENTER);
                showDate.setBounds(160, 125, 180, 25);
                showDate.setFont(new Font("Serif", Font.PLAIN, 20));
                panel3.add(showDate);

                showTime = new JLabel(dateOfEvents.get(2 * index + 1), SwingConstants.CENTER);
                showTime.setBounds(160, 160, 180, 25);
                showTime.setFont(new Font("Serif", Font.PLAIN, 20));
                panel3.add(showTime);

                showDescription = new JLabel("Description: ");//+descriptionOfEvents.get(index));
                showDescription.setBounds(50, 90, 450, 300);
                showDescription.setFont(new Font("Serif", Font.PLAIN, 20));
                showDescription.setLayout(new FlowLayout());
                panel3.add(showDescription);

                enteredDescription = new JTextArea(descriptionOfEvents.get(index));
                enteredDescription.setEditable(false);
                enteredDescription.setLineWrap(true);
                panel3.add(enteredDescription);

                JScrollPane jsp = new JScrollPane(enteredDescription);
                jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                jsp.setBounds(160, 230, 225, 100);
                panel3.add(jsp);

                int flag = 0,h;
                for (h = 0; h < Reminder.reminders.size(); h++) {
                    if (Integer.parseInt(Reminder.reminders.get(h).substring(15)) == index) {
                        flag = 1;
                        break;
                    }
                }

                int l = dateOfEvents.get(2 * index).length();
                monthName = "";
                //copying date from String Array
                date = Integer.parseInt(dateOfEvents.get(2 * index).substring(3, 5));
                //copying month name from String Array
                monthName = monthName + dateOfEvents.get(2 * index).substring(6, l - 5);
                year = Integer.parseInt(dateOfEvents.get(2 * index).substring(l - 4, l));
                hour = Integer.parseInt(dateOfEvents.get(2 * index + 1).substring(3, 5));
                minute = Integer.parseInt(dateOfEvents.get(2 * index + 1).substring(6, 8));

                editEvent = new JButton("Edit");
                editEvent.setBounds(380, 15, 80, 30);
                panel3.add(editEvent);
                String finalMonthName = monthName;
                editEvent.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame3.dispose();
                        createEventForm();
                        nameEntry.setText(eventNames.get(index));
                        descriptionArea.setText(descriptionOfEvents.get(index));

                        //setting up initial values to all the fields so that user will get the data copied and have to edit in that only
                        spinner.setValue(date);
                        spinner2.setValue(monthName);
                        spinner3.setValue(year);
                        spinner4.setValue(hour);
                        spinner5.setValue(minute);
                        eventNames.remove(index);
                        descriptionOfEvents.remove(index);
                        dateOfEvents.remove(2 * index);
                        dateOfEvents.remove(2 * index);
                        newEventAdditionIndex = index;
                        back21.setText("Discard");
                        addingEvent.setText("Save");
                    }
                });

                msg = new JLabel();
                msg.setBounds(140, 340, 250, 35);
//                if (flag == 1)
//                    msg.setVisible(true);
//                else
//                    msg.setVisible(false);
                panel3.add(msg);

                remind = new JButton("Set Reminder");
                remind.setBounds(160, 372, 140, 35);
                panel3.add(remind);
                if (flag == 1) {
                    remind.setVisible(false);
                    msg.setText("Reminder is enabled on "+ Reminder.reminders.get(h).substring(5,15)+" at "+ Reminder.reminders.get(h).substring(0,5));
                }
                else {
                    remind.setVisible(true);
                    msg.setText("");
                }
                remind.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int month = months.indexOf(monthName) + 1;
                        new ReminderUI(hour,minute,date,month,year);
//                        String remDate = form.format(date) + "-" + form.format(month) + "-" + form.format(year);
//                        String remTime = form.format(hour) + ":" + form.format(minute);
//                        Reminder.setReminder(remTime, remDate, index);
//                        msg.setVisible(true);
//                        remind.setVisible(false);
//                        cancelRemind.setVisible(true);
                    }
                });

                cancelRemind = new JButton("Cancel Reminder");
                cancelRemind.setBounds(160, 375, 140, 35);
                if (flag == 1)
                    cancelRemind.setVisible(true);
                else
                    cancelRemind.setVisible(false);
                panel3.add(cancelRemind);
                cancelRemind.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Reminder.remObj[index].stop();
                        Reminder.deleteReminder(index);
                        cancelRemind.setVisible(false);
                        remind.setVisible(true);
//                        msg.setVisible(false);
                        msg.setText("");
                    }
                });

                back31 = new JButton("Back");
                back31.setBounds(15, 15, 80, 30);
                panel3.add(back31);
                back31.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame3.dispose();
                        frame.setVisible(true);

                    }
                });

                deleteEvent = new JButton("Delete Event");
                deleteEvent.setBounds(170, 415, 125, 35);
                panel3.add(deleteEvent);

                deleteEvent.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame3.dispose();
                        frame.setVisible(true);
                        eventNames.remove(index);
                        descriptionOfEvents.remove(index);
                        dateOfEvents.remove(2 * index);
                        dateOfEvents.remove(2 * index);
                        Reminder.remObj[index].stop();
                        Reminder.deleteReminder(index);
                        //dataUpdate();
                    }
                });

            }
    //function that will create a form type template to fill details
    private void createEventForm() {
        frame2 = new JFrame();
        panel2 = new JPanel();
        frame2.setSize(500, 500);
        //frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel2.setLayout(null);
        frame2.add(panel2);
        frame.setVisible(false);
        frame2.setVisible(true);
        frame2.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(true);
            }
        });

        nameOfEvent = new JLabel("Name of Event : ");
        nameOfEvent.setBounds(50, 50, 180, 25);
        nameOfEvent.setFont(new Font("Serif", Font.PLAIN, 15));
        panel2.add(nameOfEvent);

        dateLabel = new JLabel("Enter Date(dd/mm/yyyy) : ");
        dateLabel.setBounds(50, 100, 180, 25);
        dateLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        panel2.add(dateLabel);

        time = new JLabel("Enter Time(hh/mm) : ");
        time.setBounds(50, 150, 180, 25);
        time.setFont(new Font("Serif", Font.PLAIN, 15));
        panel2.add(time);

        description = new JLabel("Description : ");
        description.setBounds(50, 225, 180, 25);
        description.setFont(new Font("Serif", Font.PLAIN, 15));
        panel2.add(description);

        // text field where user can enter Name of the event
        nameEntry = new JTextField();
        nameEntry.setBounds(175, 50, 225, 25);
        panel2.add(nameEntry);

        // text area where user can add Description of the event
        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setBounds(170, 200, 225, 75);
        panel2.add(descriptionArea);

        JScrollPane jsp2 = new JScrollPane(descriptionArea);
        jsp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jsp2.setBounds(170, 225, 225, 100);
        panel2.add(jsp2);

        //spinner for years
        spinnerModel3 = new SpinnerNumberModel(2020, 2020, 2030, 1);
        spinner3 = new JSpinner(spinnerModel3);
        spinner3.setBounds(335, 100, 60, 25);
        panel2.add(spinner3);

        //spinner for months
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        SpinnerListModel spinnerList = new SpinnerListModel(months);
        spinner2 = new JSpinner(spinnerList);
        spinner2.setBounds(250, 100, 80, 25);
        panel2.add(spinner2);

        //spinner for day
        spinnerModel1 = new SpinnerNumberModel(1, 1, 31, 1);
        spinner = new JSpinner(spinnerModel1);
        spinner.setBounds(210, 100, 35, 25);
        panel2.add(spinner);

        //spinner for hour
        spinnerModel4 = new SpinnerNumberModel(0, 0, 23, 1);
        spinner4 = new JSpinner(spinnerModel4);
        spinner4.setBounds(220, 150, 35, 25);
        panel2.add(spinner4);

        //spinner for minutes
        spinnerModel5 = new SpinnerNumberModel(0, 0, 59, 1);
        spinner5 = new JSpinner(spinnerModel5);
        spinner5.setBounds(270, 150, 35, 25);
        panel2.add(spinner5);

        newEventAdditionIndex=eventNames.size();
        back21= new JButton("Back");
        back21.setBounds(200,400,80,35);
        panel2.add(back21);
        back21.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame2.dispose();
                frame.setVisible(true);
            }
        });

        addingEvent = new JButton("Add");
        addingEvent.setBounds(200, 350, 80, 35);
        panel2.add(addingEvent);
        //on click of this button, the entered data will be taken from the input fields and new event will be created
        addingEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addData(newEventAdditionIndex);
            }
        });

    }

    private void addData(int index) {
        //adding entered name to the eventNames ArrayList
        eventNames.add(index,nameEntry.getText());
        //adding DATE to dateOfEvents ArrayList
        dateOfEvents.add(2*index,"on " + form.format(Integer.parseInt(spinner.getValue().toString())) + " " + spinner2.getValue().toString() + " " + spinner3.getValue().toString());
        //adding TIME to dateOfEvents ArrayList
        dateOfEvents.add(2*index+1,"at " + form.format(Integer.parseInt(spinner4.getValue().toString())) + ":" + form.format(Integer.parseInt(spinner5.getValue().toString())));
        //adding entered Description to descriptionOfEvents ArrayList
        descriptionOfEvents.add(index,descriptionArea.getText());
        frame2.dispose();
        frame.setVisible(true);
        //dataUpdate();
    }

    public static int getIndex() {
        return index;
    }

    private void dataUpdate(){
        //creating file output stream
        String st;
        int i;
        try {
            FileWriter fileWriter = new FileWriter("./data.txt", false);
            for (i = 0; i < eventNames.size(); i++) {
                st = eventNames.get(i);
                fileWriter.write(st + "\n");
            }
            fileWriter.write("$\n");
            for (i = 0; i < dateOfEvents.size(); i++) {
                st = dateOfEvents.get(i);
                fileWriter.write(st + "\n");
            }
            fileWriter.write("$\n");
            for (i = 0; i < descriptionOfEvents.size(); i++) {
                st = descriptionOfEvents.get(i);
                st = st.replace('\n', '^');
                fileWriter.write(st + "\n");
            }
            fileWriter.write("$\n");
            for (i = 0; i < Reminder.reminders.size(); i++) {
                st = Reminder.reminders.get(i);
                fileWriter.write(st + "\n");
            }
            fileWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}



