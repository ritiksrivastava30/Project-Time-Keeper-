package com;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Play implements Runnable{
    public void run(){
        int a =AlarmClock.p1;
        int b =AlarmClock.p2;
        int w1=0;
        while (w1 == 0) {
            LocalDateTime now1 = LocalDateTime.now();
            DateTimeFormatter format2 = DateTimeFormatter.ofPattern("HH");
            String formatDateTime2 = now1.format(format2);
            int hours = Integer.parseInt(formatDateTime2);
            DateTimeFormatter format1 = DateTimeFormatter.ofPattern("mm");
            String formatDateTime1 = now1.format(format1);
            int mins = Integer.parseInt(formatDateTime1);
            if (a == hours && b == mins) {
                SimpleAudioPlayer.vain();
                break;
            }
        }


    }
//    Thread t = new Thread() {
//        public void run() {
//            int w1 = 0;
//            while (w1 == 0) {
//                Calendar c = new GregorianCalendar();
//                int hours = c.get(Calendar.HOUR);
//                int mins = c.get(Calendar.MINUTE);
//                if (i == hours && i1 == mins) {
//                    System.out.println("The alarm is working");
//                    break;
//                }
//            }
//        }
//    };
}
