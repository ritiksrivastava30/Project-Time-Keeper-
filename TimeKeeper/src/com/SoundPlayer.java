package com;
// Java program to play an Audio
// file using Clip Object
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {
    static Clip clip;
    AudioInputStream audioInputStream;
    static String filePath="src\\com\\Track 1 (default).wav";
    static boolean status=false;
    // constructor to initialize streams and clip
    public SoundPlayer() throws UnsupportedAudioFileException,IOException, LineUnavailableException {
        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        // create clip reference
        clip = AudioSystem.getClip();
        // open audioInputStream to the clip
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void vain() {
        try {
            SoundPlayer audioPlayer = new SoundPlayer();

            audioPlayer.play();

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
    }
    public static void setFilePath(String nameOfSong){
        filePath = nameOfSong;
    }
    public static void play()
    {
        //start the clip
        if(!status) {
            clip.start();
            status=true;
        }
    }

    public static void stop() {
        if(clip!=null) {
            status=false;
            clip.stop();
            clip.close();
        }
    }
}
