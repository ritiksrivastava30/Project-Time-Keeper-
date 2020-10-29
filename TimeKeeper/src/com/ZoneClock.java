package com;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.*;
import static java.lang.Thread.sleep;


public class ZoneClock implements Runnable{
    private JPanel contPane;
    private JTextField selectedZoneTab;
    private JTextField dateTimeSelectedZoneTab;
    private JButton setButton;
    private JButton addButton;
    private JButton removeButton;
    private JTextArea addedClockArea;
    public static ArrayList<ZoneId> selectedZoneList =new ArrayList<>();
    private DefaultListModel<String> localZoneList;
    private JList<String> zoneListUI;

    public ZoneClock(){
        JFrame f= new JFrame();
        f.setTitle("ZoneClock");
        f.setSize(500, 500);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                updateData();
            }
        });

        contPane= new JPanel();
        contPane.setBounds(100,100,500,500);
        contPane.setBackground(new Color(204, 255, 204));
        f.setContentPane(contPane);

        this.implementingJList();

        selectedZoneTab =new JTextField("Selected ZoneId");
        selectedZoneTab.setBounds(150, 29, 200, 20);
        selectedZoneTab.setEditable(false);
        contPane.add(selectedZoneTab);

        dateTimeSelectedZoneTab =new JTextField("Date And Time");
        dateTimeSelectedZoneTab.setBounds(150, 89, 240, 20);
        dateTimeSelectedZoneTab.setEditable(false);
        contPane.add(dateTimeSelectedZoneTab);

        //adding set button
        setButton = new JButton("Set");
        setButton.setBounds(400,320,80,30);
        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(zoneListUI.getSelectedIndex()!= -1) {
                    firstPage.zone = ZoneId.of(zoneListUI.getSelectedValue());
                    selectedZoneTab.setText("Selected ZoneId");
                    dateTimeSelectedZoneTab.setText("Date And Time");
                }
            }
        });
        contPane.add(setButton);

        //adding add button
        addedClockArea= new JTextArea();
        addButton = new JButton("Add");
        addButton.setBounds(400, 360, 80, 30);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(zoneListUI.getSelectedIndex()!= -1 && !selectedZoneTab.getText().equals("Selected ZoneId")) {
                    selectedZoneList.add(ZoneId.of(zoneListUI.getSelectedValue()));
                    selectedZoneTab.setText("Selected ZoneId");
                    dateTimeSelectedZoneTab.setText("Date And Time");
                }
            }
        });
        contPane.add(addButton);

        //adding remove button
        removeButton = new JButton("Remove");
        removeButton.setBounds(400, 400, 80, 30);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedZoneList.size()!=0){
                    selectedZoneList.remove(selectedZoneList.size()-1);
                }
            }
        });
        contPane.add(removeButton);

        f.setLayout(null);
        f.setVisible(true);
    }

    private void implementingJList(){
        DefaultListModel<String> localZoneList = new DefaultListModel<>();
        //adding all available zone in localZoneList
        localZoneList.addElement("Asia/Karachi");
        localZoneList.addElement("Europe/Paris");
        localZoneList.addElement("Africa/Harare");
        localZoneList.addElement("Australia/Darwin");
        localZoneList.addElement("Pacific/Apia");
        localZoneList.addElement("Pacific/Guadalcanal");
        localZoneList.addElement("Asia/Ho_Chi_Minh");
        localZoneList.addElement("America/Puerto_Rico");
        localZoneList.addElement("America/Los_Angeles");
        localZoneList.addElement("Pacific/Auckland");
        localZoneList.addElement("Asia/Tokyo");
        localZoneList.addElement("Asia/Kolkata");
        localZoneList.addElement("Asia/Shanghai");
        localZoneList.addElement("Africa/Cairo");
        //instantiating JList,setting its bounds
        zoneListUI = new JList<>(localZoneList);
        zoneListUI.setBounds(0,2, 140,500);
        //its action listener
        zoneListUI.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //getting zoneId of selected zone
                ZoneId localSelectedZoneID = ZoneId.of(zoneListUI.getSelectedValue());
                //getting Date and time of selected zone
                LocalDateTime localSelectedZoneDate = LocalDateTime.now(localSelectedZoneID);
                //converting it in dd-mm-yyyy format
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String inDateFormat = localSelectedZoneDate.format(dateFormat);
                //converting it in hh:mm:ss format
                DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
                String inTimeFormat = localSelectedZoneDate.format(timeFormat);
                //setting selected zone and its time and date in textField
                selectedZoneTab.setText(zoneListUI.getSelectedValue());
                dateTimeSelectedZoneTab.setText(inDateFormat +"  "+ inTimeFormat);
            }
        });
        contPane.add(zoneListUI);
    }

    //thread function
    @Override
    public void run() {
        JScrollPane scrollableAddedClockTextArea = new JScrollPane(addedClockArea);
        scrollableAddedClockTextArea.setBounds(145, 170, 250, 260);
        contPane.add(scrollableAddedClockTextArea);

        while(true) {
            addedClockArea.setText("Total added clock: " + selectedZoneList.size()+ "\n");

            if (selectedZoneList.size()!= 0) {
                String addedClockLocalString= "";

                for(int i = 0; i < selectedZoneList.size(); i++) {
                    LocalDateTime localSelectedZoneClock = LocalDateTime.now(selectedZoneList.get(i));
                    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String inTimeFormat = localSelectedZoneClock.format(timeFormat);
                    addedClockLocalString = addedClockLocalString +"Clock:"+(i+1)+"   "+ selectedZoneList.get(i)+"  "+ inTimeFormat + "\n";
                }
                addedClockArea.append(addedClockLocalString);

                try {
                    sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //for Writing data in Zonedata file
    private void updateData(){
        ZoneId local;
        int counter;
        try {
            FileWriter fileWriter = new FileWriter("./Zonedata.txt", false);

            for (counter = 0; counter < selectedZoneList.size(); counter++) {
                local = selectedZoneList.get(counter);
                fileWriter.write(local + "\n");
            }

            fileWriter.write("$\n");
            local= firstPage.zone;
            fileWriter.write(local + "\n");
            fileWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}