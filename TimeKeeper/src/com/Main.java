package com;

import java.io.IOException;

public class Main {
    static firstPage mySw;
    public static void main(String args[]) throws IOException {
        mySw= new firstPage();
        Thread zing =new Thread(mySw);
        zing.start();
        visibilty(true);

    }
    public static void visibilty(boolean b){
        mySw.setVisible(b);
    }
}