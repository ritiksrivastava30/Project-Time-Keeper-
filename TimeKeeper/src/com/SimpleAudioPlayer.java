package com;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SimpleAudioPlayer
{

    public static long currentFrame;
    // to store current position
    static Clip clip;

    AudioInputStream audioInputStream;
    static String filePath;//=new File("C:\\Users\\RITIK SRIVASTAVA\\Documents\\Tracks\\Track 3.wav");

    // constructor to initialize streams and clip
    public SimpleAudioPlayer()
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        // create AudioInputStream object
//        System.out.println(firstPage);
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(String.valueOf(filePath)).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    public static void vain()
    {
        try
        {
            SimpleAudioPlayer audioPlayer =
                    new SimpleAudioPlayer();

            audioPlayer.play();
            Scanner sc = new Scanner(System.in);
            sc.close();
        }

        catch (Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
    }
    // Method to play the audio
    public static void setFilePath(String nameOfSong){
        filePath = nameOfSong;
    }
    public void play()
    {
        //start the clip
        clip.start();
    }


}