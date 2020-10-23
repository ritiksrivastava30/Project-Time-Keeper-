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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Event {
    JFrame frame, frame2, frame3;
    JPanel panel, panel2, panel3;
    JButton addEvent, addingEvent, deleteEvent, setReminder, back31, back10, back21, editEvent;//back31 represents back button to go back from frame 3 to frame 1
    JList listOfName, listOfSchedule;
    JTextArea descriptionArea, enteredDescription;
    JLabel nameOfEvent, date, time, description, showDescription, showTime, showDate, showName;
    JTextField nameEntry;
    SpinnerModel spinnerModel1, spinnerModel3, spinnerModel4, spinnerModel5;
    JSpinner spinner, spinner2, spinner3, spinner4, spinner5;
    DecimalFormat form;
    ArrayList<String> eventNames = new ArrayList<String>(); //an Array List to store name of Events
    ArrayList<String> dateOfEvents = new ArrayList<String>(); //an Array List to store date and time of any Event
    ArrayList<String> descriptionOfEvents = new ArrayList<String>(); //an Array List to store Description of Events as entered by the user
    int newEventAdditionIndex;
    File file;

    public Event() throws IOException {
        file = new File("./data.txt");
        String temp;
        if (file.exists()) {
            Scanner sc = new Scanner(file); //generating an input stream
            try {
                while (sc.hasNextLine()) {
                    temp = sc.nextLine();
                    if (temp.equals("$"))
                        break;
                    eventNames.add(temp);
                }
                while (sc.hasNextLine()) {
                    temp = sc.nextLine();
                    if (temp.equals("$"))
                        break;
                    dateOfEvents.add(temp);
                }
                while (sc.hasNextLine()) {
                    temp = sc.nextLine();
                    if (temp.equals("$"))
                        break;
                    temp = temp.replace('^', '\n');
                    descriptionOfEvents.add(temp);
                }
                sc.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            file.createNewFile(); //creating new file, if any file of given name does not exists
        }
    }

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
                frame.dispose();
                Main.visibilty(true);
            }
        });

        //converting Array List to array, so that it could be passed as an argument to a JList
        Object[] str = eventNames.toArray();
        listOfName = new JList(str);
        listOfName.setBounds(25, 150, 240, 3500);
        listOfName.setFont(new Font("Serif", Font.PLAIN, 26));
        panel.add(listOfName);

        Object[] ptr = dateOfEvents.toArray();
        listOfSchedule = new JList(ptr);
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
        listOfName.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //index variable will have the index of selected item from the List
                int index = listOfName.getSelectedIndex();
                frame3 = new JFrame();
                panel3 = new JPanel();
                frame3.setSize(500, 500);
                frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                panel3.setLayout(null);
                frame3.add(panel3);
                frame.dispose();
                frame3.setVisible(true);

                showName = new JLabel(eventNames.get(index));
                showName.setBounds(150, 20, 400, 100);
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

                setReminder = new JButton("Set Reminder");
                setReminder.setBounds(160, 350, 125, 35);
                panel3.add(setReminder);

                editEvent=new JButton("Edit");
                editEvent.setBounds(380, 15, 80, 30);
                panel3.add(editEvent);
                editEvent.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame3.dispose();
                        createEventForm();
                        nameEntry.setText(eventNames.get(index));
                        descriptionArea.setText(descriptionOfEvents.get(index));
                        int l=dateOfEvents.get(2*index).length(),x=0,i=3;
                        String monthName="";
                        //copying date from String Array
                        while((int)dateOfEvents.get(2*index).charAt(i)>48 && (int)dateOfEvents.get(2*index).charAt(i)<58){
                            x=x*10;
                            x=x+((int)dateOfEvents.get(2*index).charAt(i)-48);
                            i++;
                        }
                        //copying month name from String Array
                        for(int j=i+1;j<l-5;j++){
                            monthName=monthName+dateOfEvents.get(2*index).charAt(j);
                        }
                        //setting up initial values to all the fields so that user will get the data copied and have to edit in that only
                        spinner.setValue(x);
                        spinner2.setValue(monthName);
                        spinner3.setValue(Integer.parseInt(dateOfEvents.get(2*index).substring(l-4,l)));
                        spinner4.setValue(Integer.parseInt(dateOfEvents.get(2*index+1).substring(3,5)));
                        spinner5.setValue(Integer.parseInt(dateOfEvents.get(2*index+1).substring(6,8)));
                        eventNames.remove(index);
                        descriptionOfEvents.remove(index);
                        dateOfEvents.remove(2*index);
                        dateOfEvents.remove(2*index);
                        newEventAdditionIndex=index;
                        back21.setText("Discard");
                        addingEvent.setText("Save");
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
                deleteEvent.setBounds(160, 400, 125, 35);
                panel3.add(deleteEvent);

                deleteEvent.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        eventNames.remove(index);
                        descriptionOfEvents.remove(index);
                        dateOfEvents.remove(2 * index);
                        dateOfEvents.remove(2 * index);
                        frame3.dispose();
                        frame.dispose();
                        showEventListPage();
                        dataUpdate();
                    }
                });
            }
        });
    }

    //function that will create a form type template to fill details
    private void createEventForm() {
        frame2 = new JFrame();
        panel2 = new JPanel();
        frame2.setSize(500, 500);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel2.setLayout(null);
        frame2.add(panel2);
        frame.setVisible(false);
        frame2.setVisible(true);

        nameOfEvent = new JLabel("Name of Event : ");
        nameOfEvent.setBounds(50, 50, 180, 25);
        nameOfEvent.setFont(new Font("Serif", Font.PLAIN, 15));
        panel2.add(nameOfEvent);

        date = new JLabel("Enter Date(dd/mm/yyyy) : ");
        date.setBounds(50, 100, 180, 25);
        date.setFont(new Font("Serif", Font.PLAIN, 15));
        panel2.add(date);

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
        form = new DecimalFormat("00");
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
        dateOfEvents.add(2*index,"on " + spinner.getValue().toString() + " " + spinner2.getValue().toString() + " " + spinner3.getValue().toString());
        //adding TIME to dateOfEvents ArrayList
        dateOfEvents.add(2*index+1,"at " + form.format(Integer.parseInt(spinner4.getValue().toString())) + ":" + form.format(Integer.parseInt(spinner5.getValue().toString())));
        //adding entered Description to descriptionOfEvents ArrayList
        descriptionOfEvents.add(index,descriptionArea.getText());
        frame2.dispose();
        frame.dispose();
        showEventListPage();
        dataUpdate();
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
            fileWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}



