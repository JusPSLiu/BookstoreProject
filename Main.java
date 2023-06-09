import java.awt.*;
import java.util.Scanner;
import java.io.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.sound.sampled.*;

/**
 * everything outside the main method is nice and neat i swear
 *
 * @version 3
 * @author jusps
 */
public class Main extends JFrame implements KeyListener {
    private static boolean lightMode = false; //u can customize to start in light mode here
    JLabel label;
    private static final DecimalFormat priceFormat = new DecimalFormat("###,###,##0.00");
    public static Registry registry = new Registry();
    public static Inventory inventory = new Inventory();
    private static byte animation = 3;
    private static byte menu = 0;
    private static byte menuType = 0;
    private static int num2Order = 0;
    private static boolean rightPanelSelected = false;
    private static String[] description;
    private static String receipt;
    private static String[] loadedInfo;
    public static boolean upPress;
    public static boolean dwnPress;
    public static boolean lPress;
    public static boolean rPress;
    public static boolean selectPress;
    public static boolean backPress;
    public static boolean express;
    public static byte measuring = (byte)1;
    public static int[] compareIndex = new int[2];
    public static String nextFile;
    public static final String[][][] asciiArtThings = new String[][][]
                                         {{{" ▐▔▔▔▔▔▌ ", //Books
	                                        " ▐     ▌ ",
	                                        " ▐     ▌ ",
	                                        " ▐▁▁▁▁▁▌ "},
                                           {" ▐▔▔▔▔▌▒▌",
	                                        " ▐    ▌▒▌",
	                                        " ▐    ▌▒▌",
	                                        " ▐▁▁▁▁▌▒▌"},
                                           {" █▀▀▀▀▀█ ",
	                                        " █     █ ",
	                                        " █     █ ",
	                                        " █▄▄▄▄▄█ "},
                                           {" ▐▀▀▀▀█▒▌",
	                                        " ▐    █▒▌",
	                                        " ▐    █▒▌",
	                                        " ▐▄▄▄▄█▒▌"}},
                                          {{" ▄▀▀▀▀▀▄ ", //CDs
	                                        "█   ▄   █",
	                                        "█   ▀   █",
	                                        " ▀▄▄▄▄▄▀ "},
                                           {"  ▄▀▀▀▀▄ ",
	                                        " █  ▄   █",
	                                        " █  ▀   █",
	                                        "  ▀▄▄▄▄▀ "},
                                           {" ▄▀▀▀▀▀▄ ",
	                                        "█   ▄   █",
	                                        "█   ▀   █",
	                                        " ▀▄▄▄▄▄▀ "},
                                           {"  ▄▀▀▀▀▄ ",
	                                        " █  ▄   █",
	                                        " █  ▀   █",
	                                        "  ▀▄▄▄▄▀ "}},
                                          {{" ▄▀▀▀▀▀▄ ", //DVDs
	                                        "█   ▄   █",
	                                        "█   ▀   █",
	                                        " ▀▄▄▄▄▄▀ "},
                                           {"  ▄▀▀▀▀▄ ",
	                                        " █  ▄   █",
	                                        " █  ▀   █",
	                                        "  ▀▄▄▄▄▀ "},
                                           {" ▄▀▀▀▀▀▄ ",
	                                        "█   ▃   █",
	                                        "█   ▔   █",
	                                        " ▀▄▄▄▄▄▀ "},
                                           {"  ▄▀▀▀▀▄ ",
	                                        " █  ▃   █",
	                                        " █  ▔   █",
	                                        "  ▀▄▄▄▄▀ "}},
                                          {{"  ▄▄▀▀   ", //return
	                                        " ▀██▀▀▀▄ ",
	                                        "    ▀▀  █",
	                                        "  ▄▄▄▄▄▀ "},
                                           {"  ▄▄▀▀   ", //return
	                                        " ▀██▀▀▀▄ ",
	                                        "    ▀▀  █",
	                                        "  ▄▄▄▄▄▀ "},
                                           {"","","",""},{"","","",""}}, // waste the rest of the space
                                          {{"    ▄    ", //add new
                                            "  ▄▄█▄▄  ",
                                            "    █    ",
	                                        "         "},
                                           {"    ▄    ",
                                            "  ▄▄█▄▄  ",
                                            "    █    ",
	                                        "         "},
                                           {"","","",""},{"","","",""}}, // waste the rest of the space
                                          {{"    ▄    ", // customer
                                            "  ▄▄▄▄▄  ",
                                            " ▀ ███ ▀ ",
                                            "   █ █   "},
                                           {"    ▄    ",
                                            " ▄▄▄▄▄▄▄ ",
                                            "   ███   ",
                                            "   █ █   "},
                                           {"  $ ▄ $  ",
                                            "  ▄▄▄▄▄  ",
                                            " ▀ ███ ▀ ",
                                            "   █ █   "},
                                           {"  $ ▄ $  ",
                                            " ▄▄▄▄▄▄▄ ",
                                            "   ███   ",
                                            "   █ █   "}},
                                          {{"▄▄▄▄▄▄▄▄▄",
                                            "█▄▀▄▀▄▀▄█",
                                            " █▀▄▀▄▀█ ",
                                            "▄▀█▄█▄█▀▄"},
                                           {"█▀▀▀▀▀▀▀█",
                                            "█▄▄▄▄▄▄▄█",
                                            "▀█▄▀▄▀▄█▀",
                                            "▄█▄█▄█▄█▄"},
                                           {"▄▄▄▄▄▄▄▄▄",
                                            "██▀██▄█▄█",
                                            " █▀██▄▀█ ",
                                            "▄▀█▄█▄█▀▄"},
                                           {"█▀▀▀▀▀▀▀█",
                                            "██▄██▄█▄█",
                                            "▀█▄██▀██▀",
                                            "▄███▄███▄"}},
                                          {{"  ▄▄▄▄▄  ", //compare
                                            " █  █  █ ",
                                            "▀▀▀ █ ▀▀▀",
                                            "  ▄▄█▄▄  "},
                                           {" ▄▀▄▄▄   ",
                                            "▄█▄ █ ▀▄ ",
                                            "    █ ▄█▄",
                                            "  ▄▄█▄▄  "},
                                           {"   ▄▄▄▀▄ ",
                                            " ▄▀ █ ▄█▄",
                                            "▄█▄ █    ",
                                            "  ▄▄█▄▄  "}},
                                          {{"    ▄▄▄▄ ", // file
                                            "   █▒▒█  ",
                                            "   █▒▒█  ",
                                            "  ▀▀▀▀   "},
                                           {"   █▀▀█  ",
                                            "   █░░█  ",
                                            "   █░░█  ",
                                            "   █▄▄█  "},
                                           {"  ▄▀▀▀▄  ",
                                            "     ▄▀  ",
                                            "    █    ",
                                            "    ▄    "}}};

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        //for the JFrame
        new Main();

        
        //runTheOpeningTest
        if (lightMode) {
            System.out.print("\u001B[47m\u001B[30m");
            System.out.println("\u001B[47m\u001B[30m█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\u001B[0m");
            System.out.println("\u001B[47m\u001B[30m█                                                              █\u001B[0m");
            System.out.println("\u001B[47m\u001B[30m█                             (1) click (Open Desktop)———————> █\u001B[0m");
            System.out.println("\u001B[47m\u001B[30m█                                                              █\u001B[0m");
            System.out.println("\u001B[47m\u001B[30m█       (2) activate audio and click the desktop itself        █\u001B[0m");
            System.out.println("\u001B[47m\u001B[30m█                                                              █\u001B[0m");
            System.out.println("\u001B[47m\u001B[30m█        (3) Finally, press \"enter\" / \"spacebar\" to continue   █");
            for (int indx=0;indx<2;indx++) System.out.println("\u001B[47m\u001B[30m█                                                              █\u001B[0m");
            System.out.println("\u001B[47m\u001B[30m█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█\u001B[0m");
        } else {
            System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
            System.out.println("█                                                              █");
            System.out.println("█                             (1) click (Open Desktop)———————> █");
            System.out.println("█                                                              █");
            System.out.println("█       (2) activate audio and click the desktop itself        █");
            System.out.println("█                                                              █");
            System.out.println("█        (3) Finally, press \"enter\" / \"spacebar\" to continue   █");
            for (int indx=0;indx<2;indx++) System.out.println("█                                                              █");
            System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
        }
        
        System.out.println("SETUP: (Make sure the entirety of the rectangle above is visible)");

        selectPress = false;
        while (!selectPress) Thread.sleep(70);
        clearScreen();
        

        String printer = "  _\n  \\\\\n  \\ {    BTC\n   | \\\n   / /\n  /_/\t\tL...";
        for (int i=0;i<printer.length();i++) {
            System.out.print(printer.charAt(i));
            
            //if its a space or something invisible it doesnt wait but if not then thread.sleep
            if (printer.charAt(i) != '\n' && printer.charAt(i) != ' ' && printer.charAt(i) != '\t') {
                //in front of the bite mark put the loading word there, also btc becomes anana ech orp.
                if (printer.charAt(i) == 'L') System.out.print("oading");
                else if (printer.charAt(i) == 'B') System.out.print("anana ");
                else if (printer.charAt(i) == 'T') System.out.print("ech ");
                else if (printer.charAt(i) == 'C') System.out.print("orp.");
                
                //sleeep
                Thread.sleep(70);
            }
        }
        Thread.sleep(2000);




        
        //fill with white screen
        clearScreen();
        for (int j = 0;j<10;j++) {
            for (int i=0;i<64;i++) System.out.print("█");
            System.out.println("\u001B[0m");
            if (lightMode) System.out.print("\u001B[47m\u001B[30m");
        }


        //play the on jingle :D
        playSound("onjingle.wav");
        
        //set screen variables
        String[] faders = {"✖","X","×","·"," "};
        String screen[] = new String[] {"█                                                              █",
                                        "█                                                              █",
                                        "█      ▄     ▄  ▄▄▄▄▄  ▄      ▄▄▄    ▄▄▄    ▄▄ ▄▄   ▄▄▄▄▄      █",
                                        "█      █  █  █  █      █     █   █  █   █  █  █  █  █          █",
                                        "█      █  █  █  █▀▀    █     █      █   █  █  █  █  █▀▀        █",
                                        "█      █  █  █  █      █     █   █  █   █  █  █  █  █          █",
                                        "█       ▀▀ ▀▀   ▀▀▀▀▀  ▀▀▀▀▀  ▀▀▀    ▀▀▀   ▀  ▀  ▀  ▀▀▀▀▀      █",
                                        "█                                                              █"};
        //visible screen is what will be printed after the post processing
        String visibleScreen[] = new String[8];
        //play opening animation
        for (int i=0;i<4;i++) {
            //fill the spaces in the welcome sign with faders
            for (int j=0;j<8;j++) visibleScreen[j] = fillSpace(screen[j], faders[i]);
            //sleep thread for a while
            Thread.sleep(70);
            
            //clear and print screen
            clearScreen();
            if (lightMode) {
                System.out.print("\u001B[47m\u001B[30m");
                System.out.println("\u001B[47m\u001B[30m█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\u001B[0m");
                for (int indx=0;indx<8;indx++) System.out.println("\u001B[47m\u001B[30m"+visibleScreen[indx]+"\u001B[0m");
                System.out.println("\u001B[47m\u001B[30m█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█\u001B[0m");
            } else {
                System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
                for (int indx=0;indx<8;indx++) System.out.println(visibleScreen[indx]);
                System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
            }
        }

        Thread.sleep(70);
        
        clearScreen();
        if (lightMode) {
            System.out.print("\u001B[47m\u001B[30m");
            System.out.println("\u001B[47m\u001B[30m█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\u001B[0m");
            for (int indx=0;indx<8;indx++) System.out.println("\u001B[47m\u001B[30m"+screen[indx]+"\u001B[0m");
            System.out.println("\u001B[47m\u001B[30m█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█\u001B[0m");
        } else {
            System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
            for (int indx=0;indx<8;indx++) System.out.println(screen[indx]);
            System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
        }

        Thread.sleep(1000);
        //          DISPLAY STUFF
        int menuSelection = 0;
        //loadFrame says this is the initial loading frame for this new scene
        boolean loadFrame = true;

        nextFile = "SaveFiles/Day ";
        String selectedFile = "E";
        if (selectedFile.equals("E")) {
            //idk how optimization works but I made this if statement so that this array goes out of the ram once this is done
            String files[] = (new File("SaveFiles")).list();
            String fullFileNames[] = (new File("SaveFiles")).list();

            boolean[] recognized = new boolean[files.length];

            //save names & determine which files are recognized as text files
            for (int i=0;i<files.length;i++) {
                if (files[i].indexOf(".txt") != -1 && files[i].indexOf("Day ") == 0) recognized[i] = true;

                //remove the stuff after the dot in the name
                if (files[i].indexOf(".") != -1) files[i] = files[i].substring(0, files[i].indexOf("."));
            }

            while (selectedFile.equals("E")) {
                //controls
                if (lPress) {
                    if (menuSelection > 0) {
                        playSound("blip.wav");
                        menuSelection--;
                        lPress = false;
                        loadFrame = true;
                    }
                }
                if (rPress) {
                    if (menuSelection < files.length-1) {
                        playSound("blip.wav");
                        menuSelection++;
                        rPress = false;
                        loadFrame = true;
                    }
                }

                //load the frame
                if (loadFrame) {
                    loadFrame = false;            //  Select Save File
                    visibleScreen = new String[]  {"█ Select Save File      ▄▄▄           ▄▄▄                      █","","","","","","",""};
                    //I'm using menuSelection as an index
                    for (int disp=0;disp<5;disp++) {
                        if (disp+menuSelection < 2) for (int i=1;i<6;i++) visibleScreen[i] += "               ";
                        else if (menuSelection+disp-2 < files.length) {
                            if (disp == 2) {
                                if (recognized[menuSelection])
                                    for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[8][1][i]+"     ";
                                else for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[8][2][i]+"     ";
                                visibleScreen[5] += "               ";
                                for (int i=0;i<35-(files[menuSelection-2+disp].length()/2);i++)visibleScreen[6] += " ";
                                visibleScreen[6] += files[menuSelection-2+disp];
                                while (visibleScreen[6].length() < 66) visibleScreen[6]+=" ";
                                visibleScreen[7] = "                                                                      ";
                            } else {
                                if (recognized[disp+menuSelection-2])
                                    for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[8][0][i]+"     ";
                                else for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[8][2][i]+"     ";
                                String thisName = files[disp+menuSelection-2];
                                if (thisName.length() > 12) {
                                    visibleScreen[5] += thisName.substring(0,11)+"…";
                                    while (visibleScreen[5].length() < visibleScreen[4].length()) visibleScreen[5]+=" ";
                                } else {
                                    for (int i=0;i<5-(thisName.length()/2);i++)visibleScreen[5] += " ";
                                    visibleScreen[5] += thisName;
                                    while (visibleScreen[5].length() < visibleScreen[4].length()) visibleScreen[5]+=" ";
                                    // for (int i=0;i<5-((thisName.indexOf(" ")+2)/2);i++)visibleScreen[5] += " ";
                                    // visibleScreen[5] += thisName.substring(0,thisName.indexOf(" ")+2) + ".";
                                    // while (visibleScreen[5].length() < visibleScreen[4].length()) visibleScreen[5]+=" ";
                                }
                            }
                        }
                    }
                    //brute force failsafe
                    while (visibleScreen[7].length() < 66 || visibleScreen[2].length() < 66) for (int i=1;i<8;i++) visibleScreen[i] += "          ";
                    //limit screen to this area                                            4 66
                    for (int i=1;i<5;i++) visibleScreen[i] = "█"+visibleScreen[i].substring(4, 27)+"█"+visibleScreen[i].substring(28, 43)+"█"+visibleScreen[i].substring(44, 66)+"█";
                    visibleScreen[5] = "█"+visibleScreen[5].substring(4, 27)+"▀▀▀   / | \\   ▀▀▀"+visibleScreen[5].substring(44, 66)+"█";
                    for (int i=6;i<8;i++) visibleScreen[i] = "█"+visibleScreen[i].substring(4, 66)+"█";
                }
                //selectpress :D
                if (selectPress) {
                    selectPress = false;
                    playSound("select.wav");
                    selectedFile = fullFileNames[menuSelection];
                    animation = 8; loadFrame = true;
                }


                //display
                clearScreen();
                if (lightMode) {
                    System.out.print("\u001B[47m\u001B[30m");
                    System.out.println("\u001B[47m\u001B[30m█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\u001B[0m");
                    for (int indx=0;indx<8;indx++) System.out.println("\u001B[47m\u001B[30m"+visibleScreen[indx]+"\u001B[0m");
                    System.out.println("\u001B[47m\u001B[30m█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█\u001B[0m");
                } else {
                    System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
                    for (int indx=0;indx<8;indx++) System.out.println(visibleScreen[indx]);
                    System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
                }
                //System.out.println(menuSelection+" | "+selectedFile);//debug
                System.out.print("(arrow keys to move, enter to select, backspace to go back)");

                Thread.sleep(70);
            }

            //check what the next day number will be:
            int dayNum = 0;
            for (String name : files) {
                if (name.indexOf("Day ") != -1 && isInteger(name.substring(name.indexOf(" ")+1))) {
                    int thisNum = Integer.parseInt(name.substring(name.indexOf(" ")+1));
                    if (thisNum > dayNum) dayNum = thisNum;
                }
            }
            nextFile += (1+dayNum)+".txt";
        }
        readSaveFile(selectedFile);


        boolean running = true; //this boolean will become false when the user exits
        while (running) {
            Thread.sleep(70);
            if (menu == 0 || menu == 4 || menu == 9 || menu == 10) animation++;

            //MENU, -1 = System(store stats, new month, save), 0=main, 1=Customers, 2=CustomerDetails, 3=AddCustomer
            //      4=Inventory, 5=InventoryPurchaseMenu, 6=InventoryPurchase, 7=InventoryRestockMenu, 8=InventoryRestock
            // 9=InventorySelection;Purchase, 10=InventorySelection;restock
            switch (menu) {
                case -1:
                //I actually made this last so I gave up on using loading, the screen and visibleScreen and am only using visibleScreen
                    visibleScreen[0] = "█                          SYSTEM                            ";

                    //in this scenario, the animation variable is used for scrolling up and down
                    if (animation < 1) {
                        animation = 0;
                        visibleScreen[0] += "∧ █";
                    } else visibleScreen[0] += "▲ █";
                    if (animation > 17) {
                        animation = 17;
                    }

                    for (int i=1;i<8;i++) {
                        switch(animation+i) {
                            case 1: visibleScreen[i] = "█   STORE STATS:                                             "; break;
                            case 2: visibleScreen[i] = "█      Total Funds: $"+(priceFormat.format(Bookstore.getTotalFunds()*0.01)+"                                      ").substring(0,40); break;
                            case 3: visibleScreen[i] = "█         Products: "+((inventory.getProductStock((byte)0)+inventory.getProductStock((byte)1)+inventory.getProductStock((byte)2))+" (worth $"+priceFormat.format(inventory.inventoryValue())+")                                   ").substring(0,41); break;
                            case 4: visibleScreen[i] = "█              ( Books: "+(inventory.getProductStock((byte)0)+",  CDs: "+inventory.getProductStock((byte)1)+",  DVDs: "+inventory.getProductStock((byte)2)+" )                         ").substring(0,37); break;
                            case 5: visibleScreen[i] = "█         Customers: "+(registry.numMem()+"                                       ").substring(0,40); break;
                            case 6: visibleScreen[i] = "█              ( Premium: "+registry.numPremMem()+",  Regular: "+registry.numRegMem()+" )                   "; break;
                            case 7: case 11: case 16: case 20: visibleScreen[i] = "█——————————————————————————————————————————————————————————— "; break;
                            case 8: if (menuSelection == 1) visibleScreen[i]="█                       █▀▀       ▀▀█                        ";
                                else visibleScreen[i] = "█                                                            ";
                                break;
                            case 9: if (menuSelection == 1) visibleScreen[i]="█                       █ NEW MONTH █                        ";
                                else visibleScreen[i] = "█                         NEW MONTH                          ";
                                break;
                            case 10: if (menuSelection == 1) visibleScreen[i]="█                       █▄▄       ▄▄█                        ";
                                else visibleScreen[i] = "█                                                            ";
                                break;
                            case 12: if (menuSelection == 2) visibleScreen[i]="█                      █▀▀          ▀▀█                      ";
                                else visibleScreen[i] ="█                                                            ";
                                break;
                            case 13: if (menuSelection == 2) visibleScreen[i]="█                      █ MANUAL  SAVE █                      ";
                                                                            //"█                       █ DARK  MODE █                       "
                                else visibleScreen[i]="█                        MANUAL  SAVE                        ";
                                break;
                            case 14: if (menuSelection == 2) visibleScreen[i]="█                      █▄▄          ▄▄█                      ";
                                else visibleScreen[i]="█ ( Auto-saving is automatic but in case I missed something )";
                                break;
                            case 15: if (menuSelection == 2) visibleScreen[i]="█ ( Auto-saving is automatic but in case I missed something )";
                                else visibleScreen[i]= "█                                                            ";
                                break;
                            case 17: if (menuSelection == 3) visibleScreen[i]="█                       █▀▀        ▀▀█                       ";
                            	else visibleScreen[i] ="█                                                            ";
                            	break;
	                        case 18:
                                if (menuSelection == 3) {
                                    if (lightMode) visibleScreen[i]="█                       █ DARK  MODE █                       ";
                                    else visibleScreen[i]="█                       █ LIGHT MODE █                       ";
                              // DARK MODE
                                } else {
                                    if (lightMode) visibleScreen[i]="█                         DARK  MODE                         ";
                                    else visibleScreen[i]="█                         LIGHT MODE                         ";
                                }
	                            break;
	                        case 19: if (menuSelection == 3) visibleScreen[i]="█                       █▄▄        ▄▄█                       ";
	                            else visibleScreen[i]="█                                                            ";
	                            break;
                            case 21: if (menuSelection == 4) visibleScreen[i]="█                      █▀▀         ▀▀█                       ";
                            	else visibleScreen[i] ="█                                                            ";
                            	break;
	                        case 22: if (menuSelection == 4) visibleScreen[i]="█                      █ SAVE & EXIT █                       ";
	                            else visibleScreen[i]="█                        SAVE & EXIT                         ";
	                            break;
	                        case 23: if (menuSelection == 4) visibleScreen[i]="█                      █▄▄         ▄▄█                       ";
	                            else visibleScreen[i]="█                                                            ";
	                            break;
	                        default: visibleScreen[i] ="█                                                            ";break;
	                    }
                        if (i == 7) {
                            if (animation == 17) visibleScreen[i] += "∨ █";
                            else visibleScreen[i] += "▼ █";
                        } else visibleScreen[i] += "  █";
                    }
                    break;
                case 0:
                if (loadFrame)
                screen = new String[]  {"█ Funds: $"+(priceFormat.format(Bookstore.getTotalFunds()*0.01)+"              ").substring(0,15)+" MAIN MENU                            █",
                                        "█         ░░░░    ░░░   ░     ░   ░░░   ░     ░   ░░░          █",
                                        "█         ░   ░  ░   ░  ░░Customers  ░  ░░    ░  ░   ░         █",
                                        "█         ░   ░  ░   ░  ░ ░   ░  ░   ░  ░ ░   ░  ░   ░         █",
                                        "█         ░░░░   ░░░░░  ░  Products░░░  ░  ░  ░  ░░░░░         █",
                                        "█         ░   ░  ░   ░  ░   ░ ░  ░   ░  ░   ░ ░  ░   ░         █",
                                        "█         ░   ░  ░   ░  ░   System   ░  ░    ░░  ░   ░         █",
                                        "█         ░░░░   ░   ░  ░     ░  ░   ░  ░     ░  ░   ░         █"};

                if (menuSelection == 0) {
                    visibleScreen[0] = screen[0];
                    visibleScreen[4] = screen[4];
                    visibleScreen[5] = screen[5];
                    visibleScreen[6] = screen[6];
                    visibleScreen[7] = screen[7];
                    if (animation == 4 || animation == 0) {
                        visibleScreen[1] = new String("█         ░░░░    ░░░   ░ ▄ ▄ ▄ ▄ ▄░░   ░     ░   ░░░          █");
                        visibleScreen[2] = new String("█         ░   ░  ░   ░  ░▀Customers▀ ░  ░░    ░  ░   ░         █");
                        visibleScreen[3] = new String("█         ░   ░  ░   ░  ░▀░▀ ▀ ▀ ▀ ▀ ░  ░ ░   ░  ░   ░         █");
                    } else if (animation >=8) {
                        visibleScreen[1] = new String("█         ░░░░    ░░░   ░▄ ▄ ▄ ▄ ▄ ▄░   ░     ░   ░░░          █");
                        visibleScreen[2] = new String("█         ░   ░  ░   ░  ░▄Customers▄ ░  ░░    ░  ░   ░         █");
                        visibleScreen[3] = new String("█         ░   ░  ░   ░  ░ ▀ ▀ ▀ ▀ ▀  ░  ░ ░   ░  ░   ░         █");
                        animation = 0;
                    }
                } else if (menuSelection == 1) {
                    visibleScreen[0] = screen[0];
                    visibleScreen[1] = screen[1];
                    visibleScreen[2] = screen[2];
                    visibleScreen[6] = screen[6];
                    visibleScreen[7] = screen[7];
                    if (animation == 4) {
                        visibleScreen[3] = new String("█         ░   ░  ░   ░  ░ ░▄ ▄░▄ ▄ ▄ ░  ░ ░   ░  ░   ░         █");
                        visibleScreen[4] = new String("█         ░░░░   ░░░░░  ░ ▀Products▄░░  ░  ░  ░  ░░░░░         █");
                        visibleScreen[5] = new String("█         ░   ░  ░   ░  ░ ▀ ▀ ▀ ▀░▀  ░  ░   ░ ░  ░   ░         █");
                    } else if (animation >=8) {
                        visibleScreen[3] = new String("█         ░   ░  ░   ░  ░ ▄ ▄ ▄ ▄░▄  ░  ░ ░   ░  ░   ░         █");
                        visibleScreen[4] = new String("█         ░░░░   ░░░░░  ░ ▄Products▀░░  ░  ░  ░  ░░░░░         █");
                        visibleScreen[5] = new String("█         ░   ░  ░   ░  ░  ▀░▀░▀ ▀ ▀ ░  ░   ░ ░  ░   ░         █");
                        animation = 0;
                    }
                } else if (menuSelection == 2) {
                    visibleScreen[0] = screen[0];
                    visibleScreen[1] = screen[1];
                    visibleScreen[2] = screen[2];
                    visibleScreen[3] = screen[3];
                    visibleScreen[4] = screen[4];
                    if (animation == 4) {
                        visibleScreen[5] = new String("█         ░   ░  ░   ░  ░   ▄ ▄ ▄░▄  ░  ░   ░ ░  ░   ░         █");
                        visibleScreen[6] = new String("█         ░   ░  ░   ░  ░  ▀System▄  ░  ░    ░░  ░   ░         █");
                        visibleScreen[7] = new String("█         ░░░░   ░   ░  ░  ▀ ▀░▀ ▀   ░  ░     ░  ░   ░         █");
                    } else if (animation >=8) {
                        visibleScreen[5] = new String("█         ░   ░  ░   ░  ░  ▄░▄░▄ ▄   ░  ░   ░ ░  ░   ░         █");
                        visibleScreen[6] = new String("█         ░   ░  ░   ░  ░  ▄System▀  ░  ░    ░░  ░   ░         █");
                        visibleScreen[7] = new String("█         ░░░░   ░   ░  ░   ▀ ▀ ▀░▀  ░  ░     ░  ░   ░         █");
                        animation = 0;
                    }
                }
                break;

                //the customerList
                case 1:
                    if (loadFrame) {
                        if (rightPanelSelected)
                            visibleScreen = new String[]  {"█ Under Whose Account is▄▄This Order? ▄▄▄                      █","","","","","","",""};
                        else
                            visibleScreen = new String[]  {"█ Customers             ▄▄▄           ▄▄▄                      █","","","","","","",""};
                        
                        //I'm using menuSelection as an index
                        for (int disp=0;disp<5;disp++) {
                            //menuSelection 0 == disp at 2
                            if (2-menuSelection > disp) for (int i=1;i<6;i++) visibleScreen[i] += "               ";
                            else if (2-menuSelection == disp) {
                                for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[3][0][i]+"     ";
                                if (disp == 2) {visibleScreen[5] += "               ";visibleScreen[6] += "                                 Return                          ";}
                                else visibleScreen[5] += "   Return      ";
                            } else if (3-menuSelection == disp) {
                                for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[4][0][i]+"     ";
                                if (disp == 2) {visibleScreen[5] += "               ";visibleScreen[6] += "                                  Add                            ";}
                                else visibleScreen[5] += "    Add        ";
                            } else if (registry.numMem()+4-menuSelection > disp) {
                                if (disp == 2) {
                                    String infoStuff = registry.nameMem()[menuSelection-4+disp]+", "+registry.getAge(menuSelection-4+disp)+", "+registry.getPaymentType(menuSelection-4+disp);
                                    if (registry.isPremium(menuSelection-4+disp)[0])
                                        for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[5][3][i]+"     ";
                                    else for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[5][1][i]+"     ";
                                    visibleScreen[5] += "               ";
                                    for (int i=0;i<35-(infoStuff.length()/2);i++)visibleScreen[6] += " ";
                                    visibleScreen[6] += infoStuff;
                                    while (visibleScreen[6].length() < 66) visibleScreen[6]+=" ";
                                    boolean[] paidThisMonth = registry.isPremium(menuSelection-4+disp);
                                    if (paidThisMonth[0]) {
                                        if (paidThisMonth[1]) {
                                            visibleScreen[7] =    "                             Premium (paid)                           ";
                                        } else visibleScreen[7] = "                            Premium (unpaid)                          ";
                                    } else     visibleScreen[7] = "                                Regular                               ";
                                } else {
                                    if (registry.isPremium(menuSelection-4+disp)[0])
                                        for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[5][2][i]+"     ";
                                    else for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[5][0][i]+"     ";
                                    String thisName = registry.nameMem()[menuSelection-4+disp];
                                    if (thisName.indexOf(" ") > 9) {
                                        visibleScreen[5] += "    "+thisName.substring(0,1)+"."+thisName.substring(thisName.indexOf(" ")+1,thisName.indexOf(" ")+2)+".  ";
                                        while (visibleScreen[5].length() < visibleScreen[4].length()) visibleScreen[5]+=" ";
                                    } else {
                                        for (int i=0;i<5-((thisName.indexOf(" ")+2)/2);i++)visibleScreen[5] += " ";
                                        visibleScreen[5] += thisName.substring(0,thisName.indexOf(" ")+2) + ".";
                                        while (visibleScreen[5].length() < visibleScreen[4].length()) visibleScreen[5]+=" ";
                                    }
                                }
                            }
                        }
                        //brute force failsafe
                        while (visibleScreen[7].length() < 66 || visibleScreen[2].length() < 66) for (int i=1;i<8;i++) visibleScreen[i] += "          ";
                        //limit screen to this area                                            4 66
                        for (int i=1;i<5;i++) visibleScreen[i] = "█"+visibleScreen[i].substring(4, 27)+"█"+visibleScreen[i].substring(28, 43)+"█"+visibleScreen[i].substring(44, 66)+"█";
                        visibleScreen[5] = "█"+visibleScreen[5].substring(4, 27)+"▀▀▀   / | \\   ▀▀▀"+visibleScreen[5].substring(44, 66)+"█";
                        for (int i=6;i<8;i++) visibleScreen[i] = "█"+visibleScreen[i].substring(4, 66)+"█";

                    }
                    break;

                //The customer info
                case 2:
                    if (loadFrame){
                        screen = new String[] {"█________________________CUSTOMER_INFO_________________________█",
                                            "█","█","█","█","█","█","█"};
                        loadedInfo = new String[] { registry.nameMem()[menuSelection-2]+", "+registry.getAge(menuSelection-2), registry.getAddress(menuSelection-2), "$"+priceFormat.format(registry.getMoneySpent(menuSelection-2)), registry.getPaymentType(menuSelection-2), registry.getPrivateDetails(menuSelection-2), "" };
                        if (registry.isPremium(menuSelection-2)[0]) {
                            if (registry.isPremium(menuSelection-2)[1]) {
                                loadedInfo[5] += " (Premium, paid this month)";
                            } else loadedInfo[5] += " (Premium, not paid this month)";
                        } else loadedInfo[5] += " (Regular)";

                        screen[1] = "NAME: "+loadedInfo[0];
                        while (screen[1].length() < 62) screen[1] = " "+screen[1]+" ";
                        screen[1] = "█"+screen[1].substring(0,62)+"█";

                        screen[2] = loadedInfo[5];
                        while (screen[2].length() < 62) screen[2] = " "+screen[2]+" ";
                        screen[2] = "█"+screen[2].substring(0,62)+"█";

                        screen[3] = "ADDRESS: "+loadedInfo[1];
                        while (screen[3].length() < 62) screen[3] = " "+screen[3]+" ";
                        screen[3] = "█"+screen[3].substring(0,62)+"█";

                        screen[4] = "HAS SPENT: "+loadedInfo[2];
                        while (screen[4].length() < 62) screen[4] = " "+screen[4]+" ";
                        screen[4] = "█"+screen[4].substring(0,62)+"█";

                        screen[5] = "PAYMENT METHOD: "+loadedInfo[3];
                        while (screen[5].length() < 62) screen[5] = " "+screen[5]+" ";
                        screen[5] = "█"+screen[5].substring(0,62)+"█";

                        screen[6] = "PAYMENT INFO: "+loadedInfo[4];
                        while (screen[6].length() < 62) screen[6] = " "+screen[6]+" ";
                        screen[6] = "█"+screen[6].substring(0,62)+"█";

                        screen[7] = "█    P R E S S   X   T O   S E L L   I N F O   O N L I N E     █";
                    }
                    visibleScreen = screen;

                    break;

                //Inventory
                case 4:
                    if (loadFrame)
                    screen = new String[]  {"█                          PRODUCTS                            █",
                                            "█         ░░░░    ░░░   ░     ░   ░░░   ░     ░   ░░░          █",
                                            "█         ░   ░  ░   ░  ░░    ░  ░   ░  ░░    ░  ░   ░         █",
                                            "█         ░   ░  ░   ░  ░ ░   ░  ░   ░  ░ ░   ░  ░   ░         █",
                                            "█         ░░░░   ░░░░Purchase ░  ░Restock  ░  ░  ░░░░░         █",
                                            "█         ░   ░  ░   ░  ░   ░ ░  ░   ░  ░   ░ ░  ░   ░         █",
                                            "█         ░   ░  ░   ░  ░    Back░   ░  ░    ░░  ░   ░         █",
                                            "█         ░░░░   ░   ░  ░     ░  ░   ░  ░     ░  ░   ░         █"};
                    if (menuSelection == 0) {
                        visibleScreen[0] = screen[0];
                        visibleScreen[1] = screen[1];
                        visibleScreen[2] = screen[2];
                        visibleScreen[6] = screen[6];
                        visibleScreen[7] = screen[7];
                        if (animation == 4 || animation == 0) {
                            visibleScreen[3] = new String("█         ░   ░  ░  █▀  ░ ░ ▀█░  ░   ░  ░ ░   ░  ░   ░         █");
                            visibleScreen[4] = new String("█         ░░░░   ░░░█Purchase█░  ░Restock  ░  ░  ░░░░░         █");
                            visibleScreen[5] = new String("█         ░   ░  ░  ▀▀  ░   ▀▀░  ░   ░  ░   ░ ░  ░   ░         █");
                        } else if (animation >=8) {
                            visibleScreen[3] = new String("█         ░   ░  ░  ▄▄  ░ ░ ▄▄░  ░   ░  ░ ░   ░  ░   ░         █");
                            visibleScreen[4] = new String("█         ░░░░   ░░░█Purchase█░  ░Restock  ░  ░  ░░░░░         █");
                            visibleScreen[5] = new String("█         ░   ░  ░  █▄  ░   ▄█░  ░   ░  ░   ░ ░  ░   ░         █");
                            animation = 0;
                        }
                    } else if (menuSelection == 1) {
                        visibleScreen[0] = screen[0];
                        visibleScreen[1] = screen[1];
                        visibleScreen[2] = screen[2];
                        visibleScreen[6] = screen[6];
                        visibleScreen[7] = screen[7];
                        if (animation == 4) {
                            visibleScreen[3] = new String("█         ░   ░  ░   ░  ░ ░   ░  █▀  ░  ▀█░   ░  ░   ░         █");
                            visibleScreen[4] = new String("█         ░░░░   ░░░░Purchase ░  █Restock█ ░  ░  ░░░░░         █");
                            visibleScreen[5] = new String("█         ░   ░  ░   ░  ░   ░ ░  ▀▀  ░  ▀▀  ░ ░  ░   ░         █");
                        } else if (animation >=8) {
                            visibleScreen[3] = new String("█         ░   ░  ░   ░  ░ ░   ░  ▄▄  ░  ▄▄░   ░  ░   ░         █");
                            visibleScreen[4] = new String("█         ░░░░   ░░░░Purchase ░  █Restock█ ░  ░  ░░░░░         █");
                            visibleScreen[5] = new String("█         ░   ░  ░   ░  ░   ░ ░  █▄  ░  ▄█  ░ ░  ░   ░         █");
                            animation = 0;
                        }
                    } else if (menuSelection == 2) {
                        visibleScreen[0] = screen[0];
                        visibleScreen[1] = screen[1];
                        visibleScreen[2] = screen[2];
                        visibleScreen[3] = screen[3];
                        visibleScreen[4] = screen[4];
                        if (animation == 4) {
                            visibleScreen[5] = new String("█         ░   ░  ░   ░  ░   █▀░ ▀█   ░  ░   ░ ░  ░   ░         █");
                            visibleScreen[6] = new String("█         ░   ░  ░   ░  ░   █Back█   ░  ░    ░░  ░   ░         █");
                            visibleScreen[7] = new String("█         ░░░░   ░   ░  ░   ▀▀░ ▀▀   ░  ░     ░  ░   ░         █");
                        } else if (animation >=8) {
                            visibleScreen[5] = new String("█         ░   ░  ░   ░  ░   ▄▄░ ▄▄   ░  ░   ░ ░  ░   ░         █");
                            visibleScreen[6] = new String("█         ░   ░  ░   ░  ░   █Back█   ░  ░    ░░  ░   ░         █");
                            visibleScreen[7] = new String("█         ░░░░   ░   ░  ░   █▄░ ▄█   ░  ░     ░  ░   ░         █");
                            animation = 0;
                        }
                    }
                    break;

                //the inventory
                case 5: case 7:
                measuring++; if (measuring > 31) measuring = 2;
                
                if (loadFrame || (menuSelection == -1 && measuring % 8 == 2)) {
                    String topLeft = "";
                    switch(menu) {
                        case 5: topLeft += "Purchase ";break;
                        case 7: topLeft += " Restock ";break;
                    }
                    switch(menuType) {
                        case 0: topLeft += "Books";break;
                        case 1: topLeft += "CDs  ";break;
                        case 2: topLeft += "DVDs ";break;
                    }
                    if (inventory.getUniqueProductStock(menuType)-menuSelection > -1 && menuSelection > 0)
                        visibleScreen = new String[]  {"█ "+topLeft+"        ▄▄▄   $","","","","","","",""};
                    else visibleScreen = new String[]  {"█ "+topLeft+"        ▄▄▄           ▄▄▄                      █","","","","","","",""};

                    //I'm using menuSelection as an index
                    for (int disp=0;disp<5;disp++) {
                        //menuSelection 0 == disp at 2
                        if (0-menuSelection > disp) for (int i=1;i<6;i++) visibleScreen[i] += "               ";
                        else if (-menuSelection == disp) {
                            for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[3][0][i]+"     ";
                            if (disp == 2) {visibleScreen[5] += "               ";visibleScreen[6] += "                                 Return                             ";}
                            else visibleScreen[5] += "   Return      ";
                        } else if (1-menuSelection == disp) {
                            if (disp == 2) {
                                if (measuring > 0 && measuring < 8)
                                    for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[7][0][i]+"     ";
                                else if (measuring > 7 && measuring < 16)
                                    for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[7][1][i]+"     ";
                                else if (measuring > 15 && measuring < 24)
                                    for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[7][0][i]+"     ";
                                else
                                    for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[7][2][i]+"     ";
                                visibleScreen[5] += "               ";
                                visibleScreen[6] += "                                Compare                             ";
                                visibleScreen[7] += "                               ( Prices )                           ";
                            } else {
                                for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[7][0][i]+"     ";
                                visibleScreen[5] += "  Compare      ";
                            }
                        } else if (2-menuSelection == disp) {
                            if (menu == 5) {
                                if (disp == 2) {
                                    if (Bookstore.getCartSize() > 0)
                                        for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[6][3][i]+"     ";
                                    else for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[6][1][i]+"     ";
                                    visibleScreen[5] += "               ";
                                    visibleScreen[6] += "                                  Cart                              ";}
                                else {
                                    visibleScreen[5] += "    Cart       ";
                                    if (Bookstore.getCartSize() > 0)
                                        for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[6][2][i]+"     ";
                                    else for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[6][0][i]+"     ";
                                }
                            } else {
                                if (disp == 2) {
                                    for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[4][1][i]+"     ";
                                    visibleScreen[5] += "               ";
                                    visibleScreen[6] += "                                  Add                            ";}
                                else {
                                    visibleScreen[5] += "    Add        ";
                                    for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[4][0][i]+"     ";
                                }
                            }
                        } else if (inventory.getUniqueProductStock(menuType)+3-menuSelection > disp) {
                            if (disp == 2) {
                                if (menu==5) visibleScreen[0] += (inventory.getPrice(menuType, (menuSelection-3+disp))+".99   ").substring(0,7)+"▄▄▄                      █";
                                else {
                                    double price = (inventory.getPrice(menuType, (menuSelection-3+disp))*100+99)*0.004;
                                    visibleScreen[0] += (priceFormat.format(price)+"       ").substring(0,7)+"▄▄▄                      █";
                                }
                                if (inventory.isSpecial(menuType, (menuSelection-3+disp)))
                                    for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[menuType][3][i]+"     ";
                                else for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[menuType][1][i]+"     ";
                                visibleScreen[5] += "               ";
                                if (inventory.isSpecial(menuType, (menuSelection-3+disp)))
                                    switch(menuType) {
                                        case 0:
                                            for (int i=0;i<35-(inventory.getName(menuType, (menuSelection-3+disp)).length()/2+6);i++)visibleScreen[6] += " ";
                                            visibleScreen[6] += inventory.getName(menuType, (menuSelection-3+disp))+" (hardcover)";
                                            break;
                                        case 1:
                                            for (int i=0;i<35-((inventory.getName(menuType, (menuSelection-3+disp)).length()+37)/2);i++)visibleScreen[6] += " ";
                                            visibleScreen[6] += inventory.getName(menuType, (menuSelection-3+disp))+" (acually CD ROM of E.T. pc port)";
                                            break;
                                        case 2:
                                            for (int i=0;i<35-((inventory.getName(menuType, (menuSelection-3+disp)).length()+9)/2);i++)visibleScreen[6] += " ";
                                            visibleScreen[6] += inventory.getName(menuType, (menuSelection-3+disp))+" (BluRay)";
                                    }
                                else {
                                    for (int i=0;i<35-(inventory.getName(menuType, (menuSelection-3+disp)).length()/2);i++)visibleScreen[6] += " ";
                                    visibleScreen[6] += inventory.getName(menuType, (menuSelection-3+disp));
                                }
                                while (visibleScreen[6].length() < 66) visibleScreen[6]+=" ";

                                if (menu==5) {
                                    int stokk = inventory.getProductStock(menuType, menuSelection-1);
                                    if (stokk > 0) visibleScreen[7] = "    ("+stokk+" in stock, "+Bookstore.numInCart(menuType, inventory.getName(menuType, (menuSelection-3+disp)))+" in cart)";
                                    else visibleScreen[7] = "    OUT OF STOCK";
                                } else visibleScreen[7] = "    ("+inventory.getProductStock(menuType, menuSelection-1)+" in stock)";

                                while (visibleScreen[7].length() < 66) {visibleScreen[7] = " "+visibleScreen[7];if (visibleScreen[7].length() < 66) visibleScreen[7] += " ";}
                            } else {
                                if (inventory.isSpecial(menuType, (menuSelection-3+disp)))
                                    for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[menuType][2][i]+"     ";
                                else for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[menuType][0][i]+"     ";
                                if (inventory.getName(menuType, (menuSelection-3+disp)).length() > 11) visibleScreen[5] += inventory.getName(menuType, (menuSelection-3+disp)).substring(0,11)+"…   ";
                                else {
                                    for (int i=0;i<5-(inventory.getName(menuType, (menuSelection-3+disp)).length()/2);i++)visibleScreen[5] += " ";
                                    visibleScreen[5] += inventory.getName(menuType, (menuSelection-3+disp));
                                    while (visibleScreen[5].length() < visibleScreen[4].length()) visibleScreen[5]+=" ";
                                }
                            }
                        }
                    }
                    //im lazy so im brute forcing a failsafe
                    for (int i=1;i<8;i++) visibleScreen[i] += "                                                                  ";
                    //limit screen to this area                                            4 66
                    for (int i=1;i<5;i++) visibleScreen[i] = "█"+visibleScreen[i].substring(4, 27)+"█"+visibleScreen[i].substring(28, 43)+"█"+visibleScreen[i].substring(44, 66)+"█";
                    visibleScreen[5] = "█"+visibleScreen[5].substring(4, 27)+"▀▀▀   / | \\   ▀▀▀"+visibleScreen[5].substring(44, 66)+"█";
                    for (int i=6;i<8;i++) visibleScreen[i] = "█"+visibleScreen[i].substring(4, 66)+"█";
                }
                break;

                //inventory purchase screen
                case 6:
                    if (rightPanelSelected) {
                        if (animation > 0 && !loadFrame) {animation--;loadFrame = true;if (animation > 3) animation = 3;}
                        if (loadFrame) {
                            //limit num2Order to the boundary
                            if (num2Order < 0) num2Order = 0; else if (num2Order > inventory.getProductStock(menuType, menuSelection-1)) num2Order = inventory.getProductStock(menuType, menuSelection-1);
                            //find the price
                            int cartPrice = Bookstore.getTotalPrice();

                            //create the screen object
                            visibleScreen=new String[] {"█ Cart: ",
                                                        "█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█░∨░▒     # to order: ",
                                                        "█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓                 ",
                                                        "█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓",
                                                        "",
                                                        "█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓     # in stock: ",
                                                        "█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓                            █",
                                                        "█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓(press enter to add to cart)█"};
                            if (Bookstore.getTotalPrice() == 0) visibleScreen[0] += "0 ($0.00)             █░∧░▒                 ";
                            else visibleScreen[0] += (Bookstore.getCartSize()+" ($"+priceFormat.format(cartPrice*0.01)+")                     ").substring(0,22)+"█░∧░▒                 ";
                            if (animation == 0) {
                                visibleScreen[2] = "█░░Title:░"+(inventory.getName(menuType, menuSelection-1)+"░░░░░░░░░░░░░░░░░░░░░░░").substring(0,24)+"▒                 ";
                                visibleScreen[3] = "█░Description:░░░░░░░░░░░░░░░░░░░░▒";
                            } else if (animation == 1) visibleScreen[2] = "█░Description:░░░░░░░░░░░░░░░░░░░░▒                 ";
                            
                            visibleScreen[1] += (num2Order+"           ").substring(0,11)+"█";

                            if (num2Order < 1) visibleScreen[2] += "∨          █";
                            else visibleScreen[2] += "▼          █";

                            if (num2Order >= inventory.getProductStock(menuType, menuSelection-1)) visibleScreen[0] += "∧          █";
                            else visibleScreen[0] += "▲          █";

                            visibleScreen[4] = "($"+priceFormat.format((inventory.getPrice(menuType, menuSelection-1)+0.99)*num2Order);
                            if (visibleScreen[4].indexOf(".") > visibleScreen[4].length()-3) visibleScreen[4] += "0)";
                            else if (visibleScreen[4].indexOf(".") == -1) visibleScreen[4] += ".00)";
                            else visibleScreen[4] += ")";
                            while (visibleScreen[4].length() < 28) visibleScreen[4] = " "+visibleScreen[4]+" ";
                            visibleScreen[3] += visibleScreen[4].substring(0,28)+"█";

                            visibleScreen[4] = visibleScreen[6];
                            visibleScreen[5] += (inventory.getProductStock(menuType, menuSelection-1)+"          ").substring(0,11)+"█";
                        }
                    } else {
                        if (loadFrame) {
                            //assign description variable
                            description = new String[(inventory.getDescription(menuType, menuSelection-1).length()/40)+1];

                            if (inventory.getDescription(menuType, menuSelection-1).charAt(0) == 'Ω') {
                                //SPECIAL CASE FOR NOT A WAKE, USING OMEGA AS NEXT LINE
                                int count = 0;
                                for (int i = 1;i<inventory.getDescription(menuType, menuSelection-1).length();i++) {
                                    if (inventory.getDescription(menuType, menuSelection-1).charAt(i) == 'Ω') count++;
                                }
                                description = new String[count+1];
                                //for some reason its set to null so this fixes that
                                for (int i=0;i<=count;i++) description[i] = "";
                                count = 0;
                                for (int j=1;j<inventory.getDescription(menuType, menuSelection-1).length();j++)
                                    if (inventory.getDescription(menuType, menuSelection-1).charAt(j) == 'Ω') {
                                        description[count] = (description[count]+"                                        ").substring(0,40);
                                        count++;
                                    } else description[count] += inventory.getDescription(menuType, menuSelection-1).charAt(j);
                            } else for (int i=0;i<description.length;i++) description[i] = (inventory.getDescription(menuType, menuSelection-1)+"                                        ").substring(i*40,(i+1)*40);
                            screen = new String[]  {"█ ",
                                                    "█▄▄▄▄▄▄▄▄▄▄▄▄█                         ",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    ""};
                            
                            //limit animation to where the stuff is now
                            if (animation > description.length-4) {animation = (byte)(description.length-4);if (animation <= 0) animation = 0;}
                            if (animation <= 0) animation = 0;

                            if (animation < description.length-4) screen[1] += "▼ ";
                            else screen[1] += "∨ ";

                            if (animation < 1) switch(menuType) {
                                case 0: screen[0] += "Book Info  █                         ∧ ";break;
                                case 1: screen[0] += "  CD Info  █                         ∧ ";break;
                                case 2: screen[0] += " DVD Info  █                         ∧ ";break;
                            } else switch(menuType) {
                                case 0: screen[0] += "Book Info  █                         ▲ ";break;
                                case 1: screen[0] += "  CD Info  █                         ▲ ";break;
                                case 2: screen[0] += " DVD Info  █                         ▲ ";break;
                            }
                            if (animation == 0) {
                                screen[2] = ("█  Title: "+inventory.getName(menuType, menuSelection-1)+"                             ").substring(0,41);
                                screen[3] = "█ Description:                           ";
                                for (int i=0;i<4;i++) if (description.length > i) screen[i+4] = "█"+description[i];
                                                        else screen[i+4] = "█                                        ";
                            } else if (animation == 1) {
                                screen[2] = "█ Description:                           ";
                                for (int i=0;i<5;i++) if (description.length > i) screen[i+3] = "█"+description[i];
                                                        else screen[i+3] = "█                                        ";
                            } else for (int i=0;i<6;i++) if (description.length > i+animation-2) screen[i+2] = "█"+description[i+animation-2];
                                                        else screen[i+2] = "█                                        ";

                            visibleScreen = screen;
                            visibleScreen[0] += " ▒░░░░░░░░░░░░░░░░∧░░░█";
                            visibleScreen[1] += " ▒░░░░░#░to░order░"+(Integer.toString(num2Order)+"░░░").substring(0,4)+"█";
                            visibleScreen[2] += " ▒░░░░░░░░░░░░░░░░∨░░░█";
                            visibleScreen[3] += " ▒░░░░░░░░░░░░░░░░░░░░█";
                            visibleScreen[4] += " ▒░░░░░░░░░░░░░░░░░░░░█";
                            visibleScreen[5] += " ▒░░░░░#░in░stock░"+(inventory.getProductStock(menuType, menuSelection-1)+"░░░░").substring(0,4)+"█";
                            visibleScreen[6] += " ▒░░░░░░░░░░░░░░░░░░░░█";
                            visibleScreen[7] += " ▒░░░░░░░░░░░░░░░░░░░░█";
                        }
                    }
                
                    break;

                //inventory restock screen
                case 8:
                    if (rightPanelSelected) {
                        if (animation > 0 && !loadFrame) {animation--;loadFrame = true;if (animation > 3) animation = 3;}

                        //actual price is 40% of selling price
                        //find the price
                        double itemPrice = ((int)((inventory.getPrice(menuType, menuSelection-1)*100)+99)*0.004);
                        if (loadFrame) {

                            //create the screen object
                            visibleScreen=new String[] {"█ Shop Funds: $",
                                                        "█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█░∨░▒     # to order: ",
                                                        "█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓                 ",
                                                        "█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓",
                                                        "",
                                                        "█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓     # in stock: ",
                                                        "█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓                            █",
                                                        "█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓  (press enter to restock)  █"};

                            if (Bookstore.getTotalFunds() == 0) visibleScreen[0] += "0.00)          █░∧░▒                 ";
                            else visibleScreen[0] += ((long)(Bookstore.getTotalFunds()*0.01)+"."+(Long.toString(Bookstore.getTotalFunds()).substring(Long.toString(Bookstore.getTotalFunds()).length()-2))+"              ").substring(0,15)+"█░∧░▒                 ";
                            if (animation == 0) {
                                visibleScreen[2] = "█░░Title:░"+(inventory.getName(menuType, menuSelection-1)+"░░░░░░░░░░░░░░░░░░░░░░░").substring(0,24)+"▒                 ";
                                visibleScreen[3] = "█░Description:░░░░░░░░░░░░░░░░░░░░▒";
                            } else if (animation == 1) visibleScreen[2] = "█░Description:░░░░░░░░░░░░░░░░░░░░▒                 ";

                            //set the stuff and also limit num2Order to boundary
                            if (num2Order < 1) {
                                num2Order = 0;
                                visibleScreen[2] += "∨          █";
                            } else visibleScreen[2] += "▼          █";

                            if (num2Order >= (int)((Bookstore.getTotalFunds()*0.01)/(itemPrice))) {
                                visibleScreen[0] += "∧          █";
                                num2Order = (int)((Bookstore.getTotalFunds()*0.01)/(itemPrice));
                            } else visibleScreen[0] += "▲          █";

                            //display num2Order
                            visibleScreen[1] += (num2Order+"           ").substring(0,11)+"█";

                            visibleScreen[4] = "($"+priceFormat.format((itemPrice)*num2Order);
                            if (visibleScreen[4].indexOf(".") > visibleScreen[4].length()-3) visibleScreen[4] += "0)";
                            else if (visibleScreen[4].indexOf(".") == -1) visibleScreen[4] += ".00)";
                            else visibleScreen[4] += ")";
                            while (visibleScreen[4].length() < 28) visibleScreen[4] = " "+visibleScreen[4]+" ";
                            visibleScreen[3] += visibleScreen[4].substring(0,28)+"█";

                            visibleScreen[4] = visibleScreen[6];
                            visibleScreen[5] += (inventory.getProductStock(menuType, menuSelection-1)+"          ").substring(0,11)+"█";
                        }
                    } else {
                        if (loadFrame) {
                            //assign description variable
                            description = new String[(inventory.getDescription(menuType, menuSelection-1).length()/40)+1];

                            if (inventory.getDescription(menuType, menuSelection-1).charAt(0) == 'Ω') {
                                //SPECIAL CASE FOR NOT A WAKE, USING OMEGA AS NEXT LINE
                                int count = 0;
                                for (int i = 1;i<inventory.getDescription(menuType, menuSelection-1).length();i++) {
                                    if (inventory.getDescription(menuType, menuSelection-1).charAt(i) == 'Ω') count++;
                                }
                                description = new String[count+1];
                                //for some reason its set to null so this fixes that
                                for (int i=0;i<=count;i++) description[i] = "";
                                count = 0;
                                for (int j=1;j<inventory.getDescription(menuType, menuSelection-1).length();j++)
                                    if (inventory.getDescription(menuType, menuSelection-1).charAt(j) == 'Ω') {
                                        description[count] = (description[count]+"                                        ").substring(0,40);
                                        count++;
                                    } else description[count] += inventory.getDescription(menuType, menuSelection-1).charAt(j);
                            } else for (int i=0;i<description.length;i++) description[i] = (inventory.getDescription(menuType, menuSelection-1)+"                                        ").substring(i*40,(i+1)*40);
                            screen = new String[]  {"█ ",
                                                    "█▄▄▄▄▄▄▄▄▄▄▄▄█                         ",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    "",
                                                    ""};


                            //limit animation to where the stuff is now
                            if (animation > description.length-4) {animation = (byte)(description.length-4);if (animation <= 0) animation = 0;}
                            if (animation <= 0) animation = 0;

                            if (animation < description.length-6) screen[1] += "▼ ";
                            else screen[1] += "∨ ";

                            if (animation < 1) switch(menuType) {
                                case 0: screen[0] += "Book Info  █                         ∧ ";break;
                                case 1: screen[0] += "  CD Info  █                         ∧ ";break;
                                case 2: screen[0] += " DVD Info  █                         ∧ ";break;
                            } else switch(menuType) {
                                case 0: screen[0] += "Book Info  █                         ▲ ";break;
                                case 1: screen[0] += "  CD Info  █                         ▲ ";break;
                                case 2: screen[0] += " DVD Info  █                         ▲ ";break;
                            }
                            if (animation <= 0) animation = 0;
                            else if (animation > description.length-6) {animation = (byte)(description.length-6);if (animation <= 0) animation = 0;}
                            if (animation == 0) {
                                screen[2] = ("█  Title: "+inventory.getName(menuType, menuSelection-1)+"                             ").substring(0,41);
                                screen[3] = "█ Description:                           ";
                                for (int i=0;i<4;i++) if (description.length > i+animation) screen[i+4] = "█"+description[i+animation];
                                                        else screen[i+4] = "█                                        ";
                            } else if (animation == 1) {
                                screen[2] = "█ Description:                           ";
                                for (int i=0;i<5;i++) if (description.length > i+animation) screen[i+3] = "█"+description[i+animation];
                                                        else screen[i+3] = "█                                        ";
                            } else for (int i=0;i<6;i++) if (description.length > i+animation) screen[i+2] = "█"+description[i+animation];
                                                        else screen[i+2] = "█                                        ";

                            visibleScreen = screen;
                            visibleScreen[0] += " ▒░░░░░░░░░░░░░░░░∧░░░█";
                            visibleScreen[1] += " ▒░░░░░#░to░order░"+(Integer.toString(num2Order)+"░░░").substring(0,4)+"█";
                            visibleScreen[2] += " ▒░░░░░░░░░░░░░░░░∨░░░█";
                            visibleScreen[3] += " ▒░░░░░░░░░░░░░░░░░░░░█";
                            visibleScreen[4] += " ▒░░░░░░░░░░░░░░░░░░░░█";
                            visibleScreen[5] += " ▒░░░░░#░in░stock░"+(inventory.getProductStock(menuType, menuSelection-1)+"░░░░").substring(0,4)+"█";
                            visibleScreen[6] += " ▒░░░░░░░░░░░░░░░░░░░░█";
                            visibleScreen[7] += " ▒░░░░░░░░░░░░░░░░░░░░█";
                        }
                    }
                
                    break;

                // InventorySelectionType
                case 9: case 10:
                    if (loadFrame)
                    screen = new String[]  {"█                      Inventory Select                        █",
                                            "█         ░░░░    ░░░   ░     ░   ░░░   ░     ░   ░░░          █",
                                            "█         ░   ░  ░   ░  ░░    ░  ░   ░  ░░    ░  ░   ░         █",
                                            "█         ░   ░  ░   ░  ░ ░   ░  ░   ░  ░ ░   ░  ░   ░         █",
                                            "█         ░░░░   ░░░░BOOK  ░  CD ░░░░░DVD  ░  ░  ░░░░░         █",
                                            "█         ░   ░  ░   ░  ░   ░ ░  ░   ░  ░   ░ ░  ░   ░         █",
                                            "█         ░   ░  ░   ░  ░    Back░   ░  ░    ░░  ░   ░         █",
                                            "█         ░░░░   ░   ░  ░     ░  ░   ░  ░     ░  ░   ░         █"};
                    if (menuSelection == 0) {
                        visibleScreen[0] = screen[0];
                        visibleScreen[1] = screen[1];
                        visibleScreen[2] = screen[2];
                        visibleScreen[6] = screen[6];
                        visibleScreen[7] = screen[7];
                        if (animation == 4 || animation == 0) {
                            visibleScreen[3] = new String("█         ░   ░  ░ █▀░  ░▀█   ░  ░   ░  ░ ░   ░  ░   ░         █");
                            visibleScreen[4] = new String("█         ░░░░   ░░█░BOOK █░  CD ░░░░░DVD  ░  ░  ░░░░░         █");
                            visibleScreen[5] = new String("█         ░   ░  ░ ▀▀░  ░▀▀ ░ ░  ░   ░  ░   ░ ░  ░   ░         █");
                        } else if (animation >=8) {
                            visibleScreen[3] = new String("█         ░   ░  ░ ▄▄░  ░▄▄   ░  ░   ░  ░ ░   ░  ░   ░         █");
                            visibleScreen[4] = new String("█         ░░░░   ░░█░BOOK █░  CD ░░░░░DVD  ░  ░  ░░░░░         █");
                            visibleScreen[5] = new String("█         ░   ░  ░ █▄░  ░▄█ ░ ░  ░   ░  ░   ░ ░  ░   ░         █");
                            animation = 0;
                        }
                    } else if (menuSelection == 1) {
                        visibleScreen[0] = screen[0];
                        visibleScreen[1] = screen[1];
                        visibleScreen[2] = screen[2];
                        visibleScreen[6] = screen[6];
                        visibleScreen[7] = screen[7];
                        if (animation == 4) {
                            visibleScreen[3] = new String("█         ░   ░  ░   ░  ░ ░ █▀░ ▀█   ░  ░ ░   ░  ░   ░         █");
                            visibleScreen[4] = new String("█         ░░░░   ░░░░BOOK  ░█ CD █░░░░DVD  ░  ░  ░░░░░         █");
                            visibleScreen[5] = new String("█         ░   ░  ░   ░  ░   ▀▀░ ▀▀   ░  ░   ░ ░  ░   ░         █");
                        } else if (animation >=8) {
                            visibleScreen[3] = new String("█         ░   ░  ░   ░  ░ ░ ▄▄░ ▄▄   ░  ░ ░   ░  ░   ░         █");
                            visibleScreen[4] = new String("█         ░░░░   ░░░░BOOK  ░█ CD █░░░░DVD  ░  ░  ░░░░░         █");
                            visibleScreen[5] = new String("█         ░   ░  ░   ░  ░   █▄░ ▄█   ░  ░   ░ ░  ░   ░         █");
                            animation = 0;
                        }
                    } else if (menuSelection == 2) {
                        visibleScreen[0] = screen[0];
                        visibleScreen[1] = screen[1];
                        visibleScreen[2] = screen[2];
                        visibleScreen[6] = screen[6];
                        visibleScreen[7] = screen[7];
                        if (animation == 4) {
                            visibleScreen[3] = new String("█         ░   ░  ░   ░  ░ ░   ░  ░  █▀  ░▀█   ░  ░   ░         █");
                            visibleScreen[4] = new String("█         ░░░░   ░░░░BOOK  ░  CD ░░░█░DVD █░  ░  ░░░░░         █");
                            visibleScreen[5] = new String("█         ░   ░  ░   ░  ░   ░ ░  ░  ▀▀  ░▀▀ ░ ░  ░   ░         █");
                        } else if (animation >=8) {
                            visibleScreen[3] = new String("█         ░   ░  ░   ░  ░ ░   ░  ░  ▄▄  ░▄▄   ░  ░   ░         █");
                            visibleScreen[4] = new String("█         ░░░░   ░░░░BOOK  ░  CD ░░░█░DVD █░  ░  ░░░░░         █");
                            visibleScreen[5] = new String("█         ░   ░  ░   ░  ░   ░ ░  ░  █▄  ░▄█ ░ ░  ░   ░         █");
                            animation = 0;
                        }
                    } else if (menuSelection == 3) {
                        visibleScreen[0] = screen[0];
                        visibleScreen[1] = screen[1];
                        visibleScreen[2] = screen[2];
                        visibleScreen[3] = screen[3];
                        visibleScreen[4] = screen[4];
                        if (animation == 4) {
                            visibleScreen[5] = new String("█         ░   ░  ░   ░  ░   █▀░ ▀█   ░  ░   ░ ░  ░   ░         █");
                            visibleScreen[6] = new String("█         ░   ░  ░   ░  ░   █Back█   ░  ░    ░░  ░   ░         █");
                            visibleScreen[7] = new String("█         ░░░░   ░   ░  ░   ▀▀░ ▀▀   ░  ░     ░  ░   ░         █");
                        } else if (animation >=8) {
                            visibleScreen[5] = new String("█         ░   ░  ░   ░  ░   ▄▄░ ▄▄   ░  ░   ░ ░  ░   ░         █");
                            visibleScreen[6] = new String("█         ░   ░  ░   ░  ░   █Back█   ░  ░    ░░  ░   ░         █");
                            visibleScreen[7] = new String("█         ░░░░   ░   ░  ░   █▄░ ▄█   ░  ░     ░  ░   ░         █");
                            animation = 0;
                        }
                    }
                    break;
                case 11:
                    if (loadFrame) {
                        if (Bookstore.getUniqueCartSize() == 0) {
                            animation = 0;
                            visibleScreen = new String[]  {"█___________________________C_A_R_T____________________________█",
                                                           "█                                                              █",
                                                           "█                                                              █",
                                                           "█                      The cart is empty.                      █",
                                                           "█                                                              █",
                                                           "█                                                              █",
                                                           "█                                                              █",
                                                           "█                                                              █"};
                        } else {
                            visibleScreen = new String[]  {"█___________________________C_A_R_T____________________________█",
                                                           "█",
                                                           "█",
                                                           "█",
                                                           "█",
                                                           "█",
                                                           "█",
                                                           "█"};
                            if (animation < 0 || (animation > 0 && Bookstore.getUniqueCartSize() < 4)) animation = 0;
                            else if (animation > 0 && animation > Bookstore.getUniqueCartSize()-4) animation = (byte)(Bookstore.getUniqueCartSize()-4);

                            for (int i=0;i<7;i++) {
                                if (i+animation<Bookstore.getUniqueCartSize()) {
                                    visibleScreen[i+1] += (" "+Bookstore.numInCart(i+animation)+", "+Bookstore.getName(i+animation)+" ($"+priceFormat.format(Bookstore.getPrice(i+animation)*0.01)+")                                                    ").substring(0,60);
                                } else if (i+animation<Bookstore.getUniqueCartSize()+3) {
                                    if (rightPanelSelected) {
                                        switch(i+animation-Bookstore.getUniqueCartSize()) {
                                            case 0:
                                                visibleScreen[i+1] += " Total: $"+(priceFormat.format(Bookstore.getTotalPrice()*0.01)+"        ").substring(0,10)+"█▀▀                 ▀▀█                  ";break;
                                            case 1:
                                                visibleScreen[i+1] += "                   █ Proceed to Purchase █                  ";break;
                                            case 2:
                                                visibleScreen[i+1] += "                   █▄▄                 ▄▄█                  ";break;
                                        }
                                    } else switch(i+animation-Bookstore.getUniqueCartSize()) {
                                            case 0:
                                                visibleScreen[i+1] += " Total: $"+priceFormat.format(Bookstore.getTotalPrice()*0.01);
                                                for (int j=visibleScreen[i+1].length();j<61;j++) visibleScreen[i+1] += " ";
                                                break;
                                            case 1:
                                                visibleScreen[i+1] += "                     Proceed to Purchase                    ";break;
                                            case 2:
                                                visibleScreen[i+1] += "                   (press down to select)                   ";break;
                                        }
                                } else visibleScreen[i+1] += "                                                            ";


                                if (i==0) {
                                    if (animation == 0) visibleScreen[1] += "∧ █";
                                    else visibleScreen[1] += "▲ █";
                                } else if (i==6) {
                                    if (Bookstore.getUniqueCartSize()-animation < 5) visibleScreen[7] += "∨ █";
                                    else visibleScreen[7] += "▼ █";
                                } else visibleScreen[i+1] += "  █";
                            }
                        }// end else
                    }// end if loadFrame
                    break;
                case 12:
                    if (loadFrame) {
                        screen = new String[]  {"█________________________R_E_C_E_I_P_T_________________________█",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                ""};
                        animation = 0;
                        int count = 1;
                        receipt = Bookstore.buyNow(menuSelection-2);
                        for (int i = 1;i<receipt.length();i++) {
                            if (receipt.charAt(i) == '▓') count++;
                        }
                        description = new String[count];
                        //for some reason its set to null so this fixes that
                        for (int i=0;i<count;i++) description[i] = "";
                        count = 0;
                        for (int j=1;j<receipt.length();j++)
                            if (receipt.charAt(j) == '▓') {
                                description[count] = (description[count]+"                                                    ").substring(0,60);
                                count++;
                            } else description[count] += receipt.charAt(j);
                        description[description.length-1] = (description[description.length-1]+"                                                         ").substring(0,60);
                    }
                    if (animation > description.length-6) animation = (byte)(description.length-6);
                    if (animation <= 0) animation = 0;
                    
                    visibleScreen = screen;
                    if (animation < 1) visibleScreen[1] = "█"+description[animation]+"∧ █";
                    else if (animation < description.length && animation > -1) visibleScreen[1] = "█"+description[animation]+"▲ █"; else visibleScreen[1] = "█                                                            ▲ █";
                    for (int i=1;i<6;i++) if (i+animation < description.length && i+animation > -1) visibleScreen[i+1] = "█"+description[i+animation]+"  █"; else visibleScreen[i+1] = "█                                                              █";
                    if (6+animation < description.length && 6+animation > -1) visibleScreen[7] = "█"+description[6+animation]+"▼ █"; else visibleScreen[6+1] = "█                                                            ∨ █";
                    break;
                //the inventory comparison screen
                case 15: case 17:
                if (loadFrame || menuSelection == -1) {
                    String topLeft = "";
                    switch(menuType) {
                        case 0: topLeft += "Books";break;
                        case 1: topLeft += "CDs  ";break;
                        case 2: topLeft += "DVDs ";break;
                    }
                    if (inventory.getUniqueProductStock(menuType)-menuSelection > -1 && menuSelection > 0)
                        visibleScreen = new String[]  {"█ Compare "+topLeft+"         ▄▄▄   $","","","","","","",""};
                    else visibleScreen = new String[]  {"█ Compare "+topLeft+"         ▄▄▄           ▄▄▄                      █","","","","","","",""};

                    //I'm using menuSelection as an index
                    for (int disp=0;disp<5;disp++) {
                        //menuSelection 0 == disp at 2
                        if (2-menuSelection > disp) for (int i=1;i<6;i++) visibleScreen[i] += "               ";
                        else if (2-menuSelection == disp) {
                            for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[3][0][i]+"     ";
                            if (disp == 2) {visibleScreen[5] += "               ";visibleScreen[6] += "                                 Return                             ";}
                            else visibleScreen[5] += "   Return      ";
                        } else if (inventory.getUniqueProductStock(menuType)+3-menuSelection > disp) {
                            if (disp == 2) {
                                if (menu==15) visibleScreen[0] += (inventory.getPrice(menuType, (menuSelection-3+disp))+".99   ").substring(0,7)+"▄▄▄                      █";
                                else {
                                    double price = (inventory.getPrice(menuType, (menuSelection-3+disp))*100+99)*0.004;
                                    visibleScreen[0] += (priceFormat.format(price)+"       ").substring(0,7)+"▄▄▄                      █";
                                }
                                if (inventory.isSpecial(menuType, (menuSelection-3+disp))) {
                                    if (compareIndex[0]+1 == menuSelection)
                                        for (int i=0;i<4;i++) if (i==2) visibleScreen[i+1] += "A"+asciiArtThings[menuType][3][i]+"A    "; else visibleScreen[i+1] += "░"+asciiArtThings[menuType][3][i]+"░    ";
                                    else if (compareIndex[1]+1 == menuSelection)
                                        for (int i=0;i<4;i++) if (i==2) visibleScreen[i+1] += "B"+asciiArtThings[menuType][3][i]+"B    "; else visibleScreen[i+1] += "░"+asciiArtThings[menuType][3][i]+"░    ";
                                    else for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[menuType][3][i]+"     ";
                                } else {
                                    if (compareIndex[0]+1 == menuSelection)
                                        for (int i=0;i<4;i++) if (i==2) visibleScreen[i+1] += "A"+asciiArtThings[menuType][1][i]+"A    "; else visibleScreen[i+1] += "░"+asciiArtThings[menuType][1][i]+"░    ";
                                    else if (compareIndex[1]+1 == menuSelection)
                                        for (int i=0;i<4;i++) if (i==2) visibleScreen[i+1] += "B"+asciiArtThings[menuType][1][i]+"B    "; else visibleScreen[i+1] += "░"+asciiArtThings[menuType][1][i]+"░    ";
                                    else
                                        for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[menuType][1][i]+"     ";
                                }
                                visibleScreen[5] += "               ";
                                if (inventory.isSpecial(menuType, (menuSelection-3+disp)))
                                    switch(menuType) {
                                        case 0:
                                            for (int i=0;i<35-(inventory.getName(menuType, (menuSelection-3+disp)).length()/2+6);i++)visibleScreen[6] += " ";
                                            visibleScreen[6] += inventory.getName(menuType, (menuSelection-3+disp))+" (hardcover)";
                                            break;
                                        case 1:
                                            for (int i=0;i<35-((inventory.getName(menuType, (menuSelection-3+disp)).length()+37)/2);i++)visibleScreen[6] += " ";
                                            visibleScreen[6] += inventory.getName(menuType, (menuSelection-3+disp))+" (acually CD ROM of E.T. pc port)";
                                            break;
                                        case 2:
                                            for (int i=0;i<35-((inventory.getName(menuType, (menuSelection-3+disp)).length()+9)/2);i++)visibleScreen[6] += " ";
                                            visibleScreen[6] += inventory.getName(menuType, (menuSelection-3+disp))+" (BluRay)";
                                    }
                                else {
                                    for (int i=0;i<35-(inventory.getName(menuType, (menuSelection-3+disp)).length()/2);i++)visibleScreen[6] += " ";
                                    visibleScreen[6] += inventory.getName(menuType, (menuSelection-3+disp));
                                }
                                while (visibleScreen[6].length() < 66) visibleScreen[6]+=" ";

                                if (compareIndex[0] > -1 && compareIndex[1] > -1) {
                                    int comparison = inventory.compare(menuType, compareIndex[0], compareIndex[1]);
                                    if (menu == 17) comparison *= 0.4;
                                    if (comparison > 0) visibleScreen[7] = "    Comparison: item A ("+inventory.getName(menuType, compareIndex[0]).substring(0,3)+") > item B ("+inventory.getName(menuType, compareIndex[1]).substring(0,3)+"); Difference = "+comparison;
                                    else if (comparison < 0) visibleScreen[7] = "    Comparison: item A ("+inventory.getName(menuType, compareIndex[0]).substring(0,3)+") < item B ("+inventory.getName(menuType, compareIndex[1]).substring(0,3)+"); Difference = "+comparison;
                                    else visibleScreen[7] = "    Comparison: item A ("+inventory.getName(menuType, compareIndex[0]).substring(0,3)+") = item B ("+inventory.getName(menuType, compareIndex[1]).substring(0,3)+"); Difference = 0";
                                } else visibleScreen[7] = "    Please select 2 items to compare.";

                                while (visibleScreen[7].length() < 66) {visibleScreen[7] = " "+visibleScreen[7];if (visibleScreen[7].length() < 66) visibleScreen[7] += " ";}
                            } else {
                                if (inventory.isSpecial(menuType, (menuSelection-3+disp))) {

                                    if (compareIndex[0]+3 == menuSelection+disp)
                                        for (int i=0;i<4;i++) if (i==2) visibleScreen[i+1] += "A"+asciiArtThings[menuType][3][i]+"A    "; else visibleScreen[i+1] += "░"+asciiArtThings[menuType][3][i]+"░    ";
                                    else if (compareIndex[1]+3 == menuSelection+disp)
                                        for (int i=0;i<4;i++) if (i==2) visibleScreen[i+1] += "B"+asciiArtThings[menuType][3][i]+"B    "; else visibleScreen[i+1] += "░"+asciiArtThings[menuType][3][i]+"░    ";
                                    else for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[menuType][2][i]+"     ";

                                } else {
                                    if (compareIndex[0]+3 == menuSelection+disp)
                                        for (int i=0;i<4;i++) if (i==2) visibleScreen[i+1] += "A"+asciiArtThings[menuType][1][i]+"A    "; else visibleScreen[i+1] += "░"+asciiArtThings[menuType][1][i]+"░    ";
                                    else if (compareIndex[1]+3 == menuSelection+disp)
                                        for (int i=0;i<4;i++) if (i==2) visibleScreen[i+1] += "B"+asciiArtThings[menuType][1][i]+"B    "; else visibleScreen[i+1] += "░"+asciiArtThings[menuType][1][i]+"░    ";
                                    else for (int i=0;i<4;i++) visibleScreen[i+1] += " "+asciiArtThings[menuType][0][i]+"     ";
                                }
                                if (inventory.getName(menuType, (menuSelection-3+disp)).length() > 11) visibleScreen[5] += inventory.getName(menuType, (menuSelection-3+disp)).substring(0,11)+"…   ";
                                else {
                                    for (int i=0;i<5-(inventory.getName(menuType, (menuSelection-3+disp)).length()/2);i++)visibleScreen[5] += " ";
                                    visibleScreen[5] += inventory.getName(menuType, (menuSelection-3+disp));
                                    while (visibleScreen[5].length() < visibleScreen[4].length()) visibleScreen[5]+=" ";
                                }
                            }
                        }
                    }
                    //im lazy so im brute forcing a failsafe
                    for (int i=1;i<8;i++) visibleScreen[i] += "                                                                  ";
                    //limit screen to this area                                            4 66
                    for (int i=1;i<5;i++) visibleScreen[i] = "█"+visibleScreen[i].substring(4, 27)+"█"+visibleScreen[i].substring(28, 43)+"█"+visibleScreen[i].substring(44, 66)+"█";
                    visibleScreen[5] = "█"+visibleScreen[5].substring(4, 27)+"▀▀▀   / | \\   ▀▀▀"+visibleScreen[5].substring(44, 66)+"█";
                    for (int i=6;i<8;i++) visibleScreen[i] = "█"+visibleScreen[i].substring(4, 66)+"█";
                }   
            } // end switch(menu)

            //make loadFrame false because the frame has ended and its abt to check if its a new loadframe
            loadFrame = false;

            //check if keys were pressed
            switch(menu) {
                case -1:
                    //up and down while viewing the details
                    if (upPress) { if (animation==8||animation==12||animation==16)playSound("blip.wav"); animation--;upPress = false; }
                    if (dwnPress) {if(animation < 17)animation++;if (animation==4||animation==8||animation==12||animation==16)playSound("blip.wav");dwnPress=false;}
                    if (backPress) {menu=0;loadFrame = true;playSound("back.wav");backPress = false;animation=8;}

                    if (selectPress) {
                        switch(menuSelection) {
                            case 1: newMonth(); break;
                            case 2: writeSaveFile(); break;
                            case 3: lightMode = !lightMode; playSound("cartadd.wav"); break;
                            case 4: writeSaveFile(); RecordKeeper.writeReport(); running = false;
                        }
                        selectPress = false;
                    }

                  //I'm putting the logic here for which thing is selected in the systems menu
                    if (animation > 3) {
                        if (animation < 8) menuSelection = 1;
                        else if (animation < 12) menuSelection = 2;
                        else if (animation < 16) menuSelection = 3;
                        else menuSelection = 4;
                    } else menuSelection = 0;// this one is to make sure nothing is selected

                    break;
                case 0:
                    if (upPress) {menuSelection--;playSound("blip.wav");upPress = false;animation=8;}
                    if (dwnPress) {menuSelection++;playSound("blip.wav");dwnPress = false;animation=8;}
                    if (rPress) {menuSelection++;playSound("blip.wav");rPress = false;animation=8;}
                    if (lPress) {menuSelection--;playSound("blip.wav");lPress = false;animation=8;}
                    break;
                case 1:
                    //1 menu is customer list that doesn't loop around
                    if (lPress && menuSelection != 0) {menuSelection--;playSound("blip.wav");lPress = false;loadFrame=true;}
                    if (rPress && menuSelection != registry.numMem()+1) {menuSelection++;playSound("blip.wav");rPress = false;loadFrame=true;}
                    if (backPress) {menu=0;loadFrame = true;if(rightPanelSelected)menu=11;else{menuSelection=0;animation=3;}playSound("back.wav");backPress = false;}
                    break;
                case 2:
                    //selected customer menu
                    if (express) {express = false; playSound("cartadd.wav"); HappyMessage(); }
                    if (backPress) {menu=1;animation=3;loadFrame = true;playSound("back.wav");backPress = false;}
                    break;
                case 3:
                    //add customer menu . . .  yeah i didn't actually end up using this
                    
                    if (backPress) {menu=1;loadFrame = true;playSound("back.wav");backPress = false;}
                    break;
                case 4:
                    //inventory select screen because bottom says back but top has 2 options
                    if (dwnPress || upPress) { if(menuSelection == 2)menuSelection=0; else menuSelection=2; playSound("blip.wav");dwnPress = false;upPress = false;animation=8;}
                    if (rPress) {menuSelection++;playSound("blip.wav");rPress = false;animation=8;}
                    if (lPress) {menuSelection--;playSound("blip.wav");lPress = false;animation=8;}
                    if (backPress) { menu=0; loadFrame = true;animation=3;menuSelection=1; playSound("back.wav"); backPress = false; }
                    break;
                case 5:
                    //5 and 7 are inventory menus that don't loop around
                    if (lPress && menuSelection != -2) {menuSelection--;playSound("blip.wav");lPress = false;loadFrame=true;}
                    if (rPress && menuSelection != inventory.getUniqueProductStock(menuType)) {menuSelection++;playSound("blip.wav");rPress = false;loadFrame=true;}
                    if (backPress) { menu=9;animation=3;menuSelection = menuType; loadFrame = true; playSound("back.wav"); backPress = false; }
                    break;
                case 6: case 8: 
                    //up and down while viewing the details
                    if (upPress) {
                        if (rightPanelSelected) num2Order++; else animation--;
                        loadFrame = true;upPress = false;
                    }
                    if (dwnPress) {
                        if (rightPanelSelected) num2Order--; else animation++;
                        loadFrame = true;dwnPress = false;
                    }
                    if (rPress) { rightPanelSelected = true; playSound("blip.wav"); loadFrame = true; rPress = false; }
                    if (lPress) { rightPanelSelected = false; playSound("blip.wav"); loadFrame = true; lPress = false; }
                    if (backPress) {menu--;loadFrame = true;playSound("back.wav");backPress = false;}
                    break;
                case 7:
                    if (lPress && menuSelection != -2) {menuSelection--;playSound("blip.wav");lPress = false;loadFrame=true;}
                    if (rPress && menuSelection != inventory.getUniqueProductStock(menuType)) {menuSelection++;playSound("blip.wav");rPress = false;loadFrame=true;}
                    if (backPress) { menu=10; animation=3; menuSelection = menuType; loadFrame = true; playSound("back.wav"); backPress = false; }
                    break;
                case 9: case 10:
                    //inventory select screen because bottom says back but top has 3 options
                    if (dwnPress || upPress) { if(menuSelection == 3)menuSelection=1; else menuSelection=3; playSound("blip.wav");dwnPress = false;upPress = false;animation=8;}
                    if (rPress) {menuSelection++;playSound("blip.wav");rPress = false;animation=8;}
                    if (lPress) {menuSelection--;playSound("blip.wav");lPress = false;animation=8;}
                    if (backPress) { menuSelection=menu-9; menu = 4; loadFrame = true; playSound("back.wav"); backPress = false; animation = 8; }
                    break;
                case 11:
                    //up and down while viewing the details
                    if (upPress) { animation--;loadFrame = true;upPress = false;rightPanelSelected=false; }
                    if (dwnPress) {if(Bookstore.getUniqueCartSize()-animation < 6&&Bookstore.getUniqueCartSize()>0){rightPanelSelected=true;playSound("blip.wav");}animation++;loadFrame=true;dwnPress=false;}
                    if (backPress) {menu=5;loadFrame = true;playSound("back.wav");backPress = false;}
                    break;
                case 12:
                    if (upPress) { animation--;upPress = false; }
                    if (dwnPress) {animation++;dwnPress=false;}
                    if (backPress) { menu = 0;playSound("back.wav"); loadFrame = true; writeSaveFile(); animation = 8; }
                    break;
                case 15:
                    //15 and 17 are inventory menus that don't loop around
                    if (lPress && menuSelection != 0) {menuSelection--;playSound("blip.wav");lPress = false;loadFrame=true;}
                    if (rPress && menuSelection != inventory.getUniqueProductStock(menuType)) {menuSelection++;playSound("blip.wav");rPress = false;loadFrame=true;}
                    if (backPress) { menu = 5;  playSound("back.wav"); menuSelection = -1; backPress = false; }
                    break;
                case 17:
                    //15 and 17 are inventory menus that don't loop around
                    if (lPress && menuSelection != 0) {menuSelection--;playSound("blip.wav");lPress = false;loadFrame=true;}
                    if (rPress && menuSelection != inventory.getUniqueProductStock(menuType)) {menuSelection++;playSound("blip.wav");rPress = false;loadFrame=true;}
                    if (backPress) { menu = 7; playSound("back.wav"); menuSelection = -1; backPress = false; }
            }
            
            //check if select pressed, switch depending on menuSelection and menu
            //MENU, -1=system, 0=main, 1=Customers, 2=CustomerDetails, 3=AddCustomer
            //4=Inventory, 5=InventoryPurchaseMenu, 6=InventoryPurchase, 7=InventoryRestockMenu, 8=InventoryRestock
            // 9=InventorySelection;Purchase, 10=InventorySelection;restock, 11=Cart, 12=receipt
            if (selectPress) {
                loadFrame = true;
                switch(menu) {
                    //main menu
                    case 0:
                        playSound("select.wav");
                        switch(menuSelection) {
                            case 0: menu = 1; rightPanelSelected = false; break;
                            case 1: menu = 4; animation=0; menuType = 0; break;
                            case 2: menu = -1; break;
                        }
                        menuSelection=0;
                        break;
                    //customers view menu
                    case 1:
                        //rightPanelSelected version goes through to select someone after pressing the purchase button in the cart
                        if (rightPanelSelected) {
                            if (menuSelection == 0) { playSound("back.wav"); menuSelection=0; menu = 11; }
                            else if (menuSelection == 1) { playSound("select.wav"); menuSelection=0; newCustomer(); backPress=false; }
                            else { playSound("select.wav"); menu = 12; }
                        } else {
                            //otherwise go through normally
                            if (menuSelection == 0) { playSound("back.wav"); menuSelection=0; menu = 0; }
                            else if (menuSelection == 1) { playSound("select.wav"); menuSelection=0; newCustomer(); }
                            else { playSound("select.wav"); menu = 2; }
                        }
                        break;
                    //customer details menu
                    case 2:
                        selectPress = false; editCustomer(menuSelection-2);
                        selectPress=false; backPress=false;
                        writeSaveFile();
                        break;
                    //Inventory menu
                    case 4:
                        switch(menuSelection) {
                            case 0: playSound("select.wav"); menuSelection=0; menu = 9; break;
                            case 1: playSound("select.wav"); menuSelection=0; menu = 10; break;
                            case 2: playSound("back.wav"); menuSelection=1; menu = 0; break;
                        }
                        break;
                    //Inventory exploring purchasing menu
                    case 5:
                        if (menuSelection == -2) { menu = 9; menuSelection=menuType; playSound("back.wav"); }
                        else if (menuSelection == -1) { menu = 15; menuSelection=0; playSound("select.wav"); compareIndex[0] = -1; compareIndex[1] = -1; }
                        else if (menuSelection == 0) { rightPanelSelected=false;menu=11;menuSelection=0;animation = 0;playSound("select.wav"); }
                        else {
                            menu=6;
                            playSound("select.wav");
                            rightPanelSelected = false;
                            num2Order=Bookstore.numInCart(menuType, inventory.getName(menuType, menuSelection-1));
                        }
                        break;
                    //Inventory purchasing screen
                    case 6:
                        if (rightPanelSelected && num2Order != Bookstore.numInCart(menuType, inventory.getName(menuType, menuSelection-1))) {
                            playSound("cartadd.wav");
                            int cartNum = Bookstore.numInCart(menuType, inventory.getName(menuType, menuSelection-1));
                            Bookstore.addToCart(menuType, inventory.getName(menuType, menuSelection-1), num2Order, inventory.isSpecial(menuType, menuSelection-1));
                            rightPanelSelected = false;
                        }
                        break;
                    //inventory exploring restocking menu
                    case 7:
                        if (menuSelection == -2) { menu = 10; menuSelection=menuType; playSound("back.wav"); }
                        else if (menuSelection == -1) { menu = 17; menuSelection=0; playSound("select.wav"); compareIndex[0] = -1; compareIndex[1] = -1; }
                        else if (menuSelection == 0) {newItem(); selectPress=false; backPress=false; writeSaveFile(); }
                        else { menu = 8; playSound("select.wav"); num2Order = 0; rightPanelSelected = false;selectPress = false;}
                        break;
                    //order restocks
                    case 8:
                        if (rightPanelSelected) {
                            if (num2Order > 0) {
                                int itemPrice = ((int)((inventory.getPrice(menuType, menuSelection-1)*100)+99)*4/10);
                                Bookstore.setTotalFunds(Bookstore.getTotalFunds()-(itemPrice*num2Order));
                                inventory.restockProduct(menuType, menuSelection-1, num2Order);
                                //also add it to the record
                                RecordKeeper.addRestock(menuType, menuSelection-1, num2Order);
                                playSound("cartadd.wav");
                                num2Order = 0;
                                writeSaveFile();
                            }
                        } else {
                            editItem(menuType, menuSelection-1);
                            writeSaveFile();
                            selectPress=false; backPress=false;
                        }
                        break;
                    //purchaseSelectType
                    case 9:
                        //animation = -1;
                    //type 0=book, 1=cd, 2=dvd
                        switch(menuSelection) {
                            case 0: menu = 5; playSound("select.wav"); menuType = 0; break;
                            case 1: menu = 5; playSound("select.wav"); menuType = 1; break;
                            case 2: menu = 5; playSound("select.wav"); menuType = 2; break;
                            case 3: menu = 4; playSound("back.wav"); break;
                        }
                        menuSelection=0;
                        break;
                    case 10: //restockSelectType
                    //type 0=book, 1=cd, 2=dvd
                        switch(menuSelection) {
                            case 0: menu = 7; playSound("select.wav"); menuType = 0; break;
                            case 1: menu = 7; playSound("select.wav"); menuType = 1; break;
                            case 2: menu = 7; playSound("select.wav"); menuType = 2; break;
                            case 3: menu = 4; playSound("back.wav");
                        }
                        menuSelection=1;
                        break;
                    case 11: //cart menu, if selected then go ask which customer is buying
                        if (rightPanelSelected) menu = 1;
                        break;
                    case 12: //receipt
                        menu = 0;playSound("back.wav");writeSaveFile(); animation = 8;
                    case 15:
                    case 17:
                        if (menuSelection == 0) { menu -= 10;  playSound("back.wav"); menuSelection = -1; }
                        else {
                            if (compareIndex[0] == menuSelection-1) { compareIndex[0] = -1; playSound("back.wav"); }
                            else if (compareIndex[1] == menuSelection-1) { compareIndex[1] = -1; playSound("back.wav"); }
                            else if (compareIndex[0] == -1) { compareIndex[0] = menuSelection-1; playSound("select.wav"); }
                            else if (compareIndex[1] == -1) { compareIndex[1] = menuSelection-1; playSound("select.wav"); }
                        }
                        break;
                }
                //unless menu 6, 8, or 11, set selectPress to false and reset menuSelection and animation to their default values
                if (menu == 6 || menu == 8 || menu == 11 || menu == -1) animation = 0;
                else {
                    selectPress = false;
                    animation = 8;
                }
            } // end if

            //loop menu selection around if it goes too far
            switch(menu) {
                //MENU, 0=main, 1=Customers, 2=CustomerDetails, 3=AddCustomer
                //      4=Inventory, 5=InventoryPurchaseMenu, 6=InventoryPurchase, 7=InventoryRestockMenu, 8=InventoryRestock
                // 9=InventorySelection;Purchase, 10=InventorySelection;restock
                case 0: case 4:
                    if (menuSelection < 0) menuSelection = 2;
                    else if (menuSelection > 2) menuSelection = 0;
                    break;
                case 1:
                    if (menuSelection < 0) menuSelection = 0;
                    if (menuSelection > registry.numMem()+1) menuSelection = registry.numMem()+1;
                    break;
                case 5:case 7:
                    if (menuSelection < -2) menuSelection = -2;
                    else if (menuSelection > inventory.getUniqueProductStock(menuType)) menuSelection = inventory.getUniqueProductStock(menuType);
                    break;
                case 9: case 10:
                    if (menuSelection < 0) menuSelection = 3;
                    else if (menuSelection > 3) menuSelection = 0;
                    break;
            }

            clearScreen();
            if (lightMode) {
                System.out.println("\u001B[47m\u001B[30m█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
                for (int indx=0;indx<8;indx++) System.out.println(visibleScreen[indx]);
                System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█\u001B[0m");
            } else {
                System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
                for (int indx=0;indx<8;indx++) System.out.println(visibleScreen[indx]);
                System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
            }
            //System.out.println(menuSelection + " | "+menu+" | "+menuType+" | "+animation+" | "+rightPanelSelected+" | "+Bookstore.getUniqueCartSize()+" | "+measuring+" | "+nextFile);//debug
            if (menu==2||menu==8) System.out.print("(arrow keys to move, enter to edit, backspace to go back)");
            else System.out.print("(arrow keys to move, enter to select, backspace to go back)");
        }
        
        //save the visibleScreen to the screen, because the system menu is only set to render as a post processing effect
        for (int i=0;i<8;i++) screen[i] = visibleScreen[i];

        //play exit animation, beginning with fading to white
        for (int i=3;i>-1;i--) {
            //fill the spaces in the welcome sign with faders
            for (int j=0;j<8;j++) visibleScreen[j] = fillSpace(screen[j], faders[i]);
            //sleep thread for a while
            Thread.sleep(70);
            
            //clear and print screen
            clearScreen();
            if (lightMode) {
                System.out.println("\u001B[47m\u001B[30m█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
                for (int indx=0;indx<8;indx++) System.out.println(visibleScreen[indx]);
                System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█\u001B[0m");
            } else {
                System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
                for (int indx=0;indx<8;indx++) System.out.println(visibleScreen[indx]);
                System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
            }
        }

        //white screen
        clearScreen();
        if (lightMode) for (int indx=0;indx<10;indx++) System.out.println("\u001B[30m████████████████████████████████████████████████████████████████");
        else for (int indx=0;indx<10;indx++) System.out.println("████████████████████████████████████████████████████████████████");
        Thread.sleep(1000);

        //exit screen string
        screen = new String[] {"█                                                              █",
                               "█                                                              █",
                               "█       ▄▄▄▄   ▄▄▄    ▄▄▄   ▄▄▄▄   ▄▄▄▄   ▄   ▄  ▄▄▄▄▄  ▄      █",
                               "█      █      █   █  █   █  █   █  █   █  █   █  █      █      █",
                               "█      █  ▄▄  █   █  █   █  █   █  █▀▀▀▄   █ █   █▀▀    █      █",
                               "█      █   █  █   █  █   █  █   █  █   █    █    █      ▀      █",
                               "█       ▀▀▀▀   ▀▀▀    ▀▀▀   ▀▀▀▀   ▀▀▀▀     ▀    ▀▀▀▀▀  ▀      █",
                               "█                                                              █"};
        //play exit animation
        for (int i=0;i<5;i++) {
            //fill the spaces in the welcome sign with faders
            for (int j=0;j<8;j++) visibleScreen[j] = fillSpace(screen[j], faders[i]);
            //sleep thread for a while
            Thread.sleep(70);
            
            //clear and print screen
            clearScreen();
            if (lightMode) {
                System.out.println("\u001B[47m\u001B[30m█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
                for (int indx=0;indx<8;indx++) System.out.println(visibleScreen[indx]);
                System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█\u001B[0m");
            } else {
                System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
                for (int indx=0;indx<8;indx++) System.out.println(visibleScreen[indx]);
                System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
            }
        }
        Thread.sleep(1000);
        for (int i=3;i>-1;i--) {
            //fill the spaces in the welcome sign with faders
            for (int j=0;j<8;j++) visibleScreen[j] = fillSpace(screen[j], faders[i]);
            //sleep thread for a while
            Thread.sleep(70);
            
            //clear and print screen
            clearScreen();
            if (lightMode) {
                System.out.println("\u001B[47m\u001B[30m█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
                for (int indx=0;indx<8;indx++) System.out.println(visibleScreen[indx]);
                System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█\u001B[0m");
            } else {
                System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
                for (int indx=0;indx<8;indx++) System.out.println(visibleScreen[indx]);
                System.out.println("█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
            }
        }
        
        //white screen
        clearScreen();
        if (lightMode) for (int indx=0;indx<10;indx++) System.out.println("\u001B[30m████████████████████████████████████████████████████████████████");
        else for (int indx=0;indx<10;indx++) System.out.println("████████████████████████████████████████████████████████████████");
        Thread.sleep(500);
        //cool delete effect
        for (int spd=0, i=640;i>0;i-=spd,spd+=10) {
            clearScreen();
            for (int j=1;j<i;j++) {
                if (j>1 && j%64==0) System.out.println("█");
                else System.out.print("█");
            }
            Thread.sleep(70);
        }
        clearScreen();

        System.exit(0);
    } // end main



    ///      METHODS
    
    private static String fillSpace(String input, String replacer) {
        String result = "";
        for (int i=0;i<input.length();i++) {
            if (input.charAt(i) == ' ') {
                result += replacer;
            } else result += input.substring(i,i+1);
        }
        return result;
    }

    private static void clearScreen() {
        // backslash n just in case
        System.out.println("\n");
        
        //clear screen, usable in Codingrooms and Command Prompt
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        //clear screen, usable in NetBeans
        // try {
        //     Robot robo = new Robot();
        //     robo.keyPress(17);
        //     robo.keyPress(76);
        //     robo.keyRelease(17);
        //     robo.keyRelease(76);
        // } catch (AWTException e) {}
    }

    private static void editItem(byte type, int selection) throws InterruptedException {
        playSound("select.wav");
        selectPress = false;
        //wait until either selectPress or backPress
        clearScreen();
        if (lightMode) System.out.print("\u001B[47m\u001B[30m");
        System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\n"+
                            "█                                                              █\n"+
                            "█                Are you sure you would like to                █\n"+
                            "█                      modify this item?                       █\n"+
                            "█                                                              █\n"+
                            "█                           Press 'enter' to proceed           █\n"+
                            "█                                                              █\n"+
                            "█          Press 'backspace' to go back                        █\n"+
                            "█                                                              █\n"+
                            "█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
        while (!selectPress && !backPress) {
            Thread.sleep(100);
        }
        //as long as they didn't say 'ono go bac' then go ahead
        if (selectPress) {
            try {
                playSound("select.wav");
                Scanner scan = new Scanner(System.in);
                String input = "";
                clearScreen();
                System.out.println("I'm not writing a whole typing system so click the console for this part.\n");
                Thread.sleep(2000);
                switch(menuType) {
                    case 0:
                        if (inventory.isSpecial(type, selection)) System.out.print("Would you like to remove and toss out the hardcovers,\nand replace them with shoddy sheets of paper? (Y/N)\n\t");
                        else System.out.print("Would you like to remove and toss out the paper covers,\nand replace them with cardboard and duct tape DIY hardcovers? (Y/N)\n\t");
                        break;
                    case 1:
                        if (inventory.isSpecial(type, selection)) System.out.print("Would you like to replace this CD (that's definitely not\na ROM of a custom made pc port of ET for the Atari 2600)? (Y/N)\n\t");
                        else System.out.print("Would you like to replace this CD with a custom made pc port\nET for the Atari 2600 that you have lying in the dumpster? (Y/N)\n\t");
                        break;
                    case 2:
                        if (inventory.isSpecial(type, selection)) System.out.print("Would you like to remove the BluRay label? (Y/N)\n\t");
                        else System.out.print("Studies have shown that when any DVDs are labeled BluRay, people tend to buy them.\nWould you like to change the label of this ordinary DVD? (Y/N)\n\t");
                        break;
                }
                input = scan.nextLine();
                if (input.length() > 0) input = input.substring(0,1);
                while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    System.out.print("\tSorry, I didn't get that. Could you please input a 'Y' or an 'N'?\n\t");
                    input = scan.nextLine();
                    if (input.length() > 0) input = input.substring(0,1);
                }
                if (input.equalsIgnoreCase("y")) {
                    //ok then i'll make the change
                    inventory.invertSpecial(type, selection);

                    //print out that the change is being made
                    switch(menuType) {
                    case 0:
                        if (inventory.isSpecial(type, selection)) System.out.println("The soft paper covers have been expunged, eliminated, tossed aside into the garbage, for they must make way for the new, handcrafted hardcovers.");
                        else System.out.println("The hardcovers have been tossed into the garbage and replaced with shoddy sheets of paper.");
                        break;
                    case 1:
                        if (inventory.isSpecial(type, selection)) System.out.println("\n\tYou go outside into the empty lot out back, full of grime and dirt. You look around for the dumpster, but no matter where you look, it doesn't seem to appear. \"Why can't I find this simple, stupid dumpster?!\" You plea toward the heavens, \"I finally find a use for all my CD ROMs, and this is what happens?!?\" Then you notice a noise. It sounds... like an engine. Then you realize the horrid stench entering your nostrils. You turn around and look over your shoulder to find a large, green, greasy dump truck full of trash. But what's this? Something strange is on its side, obscured on the other side of the truck. The truck begins to turn, and you see that it is carrying the dumpster. Your wonderful, beautiful, beloved dumpster, is being held in the air by this horrible truck. And... Oh no! It's getting away!\n\tYou give chase, running after it with all your strength, and finally catch up to it, but unfortunately... You have arrived at the junkyard. The horrible stench has become far stronger now, and you see your beloved dumpster off in the distance, placed down on its side, its lid still open. You see this horrid sight, and freeze in your tracks. The truck has vanished. You stare for a moment at the defiled corpse of your beloved, trying to hold back your tears, as reality sets in.\n\tOne tear, somehow, barely manages to escape. Followed by another. And then you realize it's hopeless to even try to hold back. You turn toward the sky, shouting at the top of your lungs. You then sink to the ground, sobbing and shouting, \"WHO DID THIS TO YOU!?!?\" You bring your hands up to your face, wiping the tears from your eyes, hoping to wipe the devastating image from your mind. \"WHAT HORRIBLE.. HORRIBLE-\" You begin choking on your words, \"MONSTER OF A PERSON, COULD HAVE-\" You gasp for air, breathing in the fetid gases. \"COULD HAVE, DONE THIS?\" You inhale the putrid atmosphere yet again, \"I swear... I SWEAR.. That by Grabthar's Hammer... you shall be, avenged.\"\n\tRemembering your mission, you regretfully get back up to your feet, and walk over to the junkyard, hoping to find some solace in that you could at least save something to remember your beloved dumpster by. You enter the dump, finding yourself unable to look at the deceased. Off into the distance, you see a large, odd pile of disks. You walk over and pick one up, all covered in grime. Wiping your last tear from your eye, you wash off a layer of dirt, seeing two letters drawn in sharpie: \"ET.\" Memories come back from your experiences with your beloved, and all you have gone through. But now... They're gone. And you still remain. At least you can find some solace in the fact that something, some remnant of the past you shared, still remains. You grab a handful and take them back to the shop.\n\tUpon your return, everything looks the same, and smells the same, but something feels different. It's... empty. You suddenly feel an overwhelming sense of dread, that you must live out an empty, hollow experience from now on. Trying not to linger on that, you go back inside, and go back to work, putting the ET disks all back into place in the boxes labeled, "+inventory.getName((byte)1, selection)+", and resume work, trying to forget all that happened, but still lingering on the events. You think to yourself, \"Who did that to my beloved dumpster? I have to find out exactly what happened, so I can exact my vengeance.\"\n\tAll of these questions nag at you for the rest of the day; however, none would remain unanswered for long.");
                        else System.out.println("Feeling down, you go over to the closet to get the old CDs back out, to swap the ET disks back from the boxes. You think about what happened last time you changed these out, vividly recalling all the horrid details. You shed a single tear, and you pick up a box full of the CDs. The tear travels down your face, and gets deflected off your nose. It goes down, and down, to the bottom of your chin, where it simply remains. You put the box down upon your desk, and reach to open it up, when a familiar scent sends shivers through your body. Everything beings to feel numb, as you hear a motor in the distance. The motor of a truck. You rech under your desk and grab your rifle, determined to take revenge for everything that was lost. For who you lost.\n\tYou run out the store doors, past all the frightened customers. You search around the parking lot, searching for the one that took everything from you. And then you see it. Stopped next to the telephone pole acoss the parking lot. You charge as fast as you can, with your thoughts racing through your head, \"could this be it? Am I really about to find out who did this, and why? This could be my last chance, I can't mess this up!\"\n\tApproaching the truck, you lift the shotgun up, holding it between your face and the truck, your finger on the trigger. You walk over to the door, looking up from below, where you see a strange shiny object, reflecting the sun's light onto the ceiling of the truck. It appears to be the top of some kind of ball. As you open the door, the ball begins to move, and you realize it wasn't a ball at all, but a bald man's head. \"What're you doing over there? I'm on break. Let me go back to sleep.\" You lift the gun and point it at his head, pulling back the forestock. \"Woah! Come on, take it easy!\" the stranger pleas, raising his hands into the air, shaking. \"I- I don't have much on me right now. I just now realized I forgot my wallet at home. I... I-I've got some spare change in the glove compartment. How much do you want?\" You keep the weapon high, pointing it directly at his forehead, replying, \"No amount of money could ever replace what you took from me.\" \"What? What do you mean? I'm just- I'm just a dump truck driver\" \"You took my dumpster.\" \"Wh- what?\" \"YOU HEARD ME!!\" \"Wait... oh... oh crop... so- so that's what that sound was...\" \"And now you will pay\" \"I'm truly sorry for your loss. I- I'll make it up to you, please. Please. I just want to see my beloved family one more time.\" \"You killed my beloved, so I will kill you. Goodbye, sir.\" \"NO WAIT!!!! PLEASE!!! IT WAS AN HONEST MISTAKE!!! PLEASE, I DON'T WANT TO DIE!!!!!\" You bring barrel of the gun inches from his face, your finger beginning to tighten on the trigger. Your mind races with thoughts of revenge, but something feels, empty. Suddenly, you feel the air getting cooler around your ankles. Looking down, a strange glowing fog has set in. Behind you, a cloud of fog has appeared. the edges slowly turn from rounded to sharp, as if it were wrapping around some kind of box shaped object. Then you realize, it has taken the shape of a familiar friend. No, more than a friend. It is your beloved dumpster. Mesmerized, you stare at it, lowering your gun. Then, the odd cloud begins to move, opening it lid, and it seems to be... speaking. \"Please, don't do this for me. I don't want revenge. This was a simple misunderstanding. You know what? I fought all the rubbish debins to make it out of the great junkyard so I could give you this message. I might as well stay.\" Then, the fog at your feet begins to fade, and the fog shaped like your beloved dumpster has begun to darken, seemingly condensing. The fog then faded away, giving way to your great beloved underneath. You drop your gun on the ground, transfixed by all these events\n\t\"WHAT JUST HAPPENED!?!?!\" The bald man shouts. You ignore him and run over to your dumpster, embracing your beloved in your arms. Tears begin flooding down your face, as you have finally reunited with your dumpster\n\tAfter a while, you walk back into your shop, feeling renewed. You walk in, and remember what you were doing before all this. You then look at the box on your desk, and carefully replace all the copies of E.T. for the Atari 2600 with the original "+inventory.getName((byte)1, selection)+" CD. Closing the box, and putting it away, you feel like you just got a fresh start in the world, revitalized and ready to begin anew.");
                        break;
                    case 2:
                        if (inventory.isSpecial(type, selection)) System.out.println("You grab a sharpie and write the word \"Blueray\" and now wait for customers to flock over to buy every last copy.");
                        else System.out.println("You grab the BluRay label and rip it off.");
                        break;
                    }
                }
                input = "";

                System.out.print("Would you like to alter the price? (Y/N) The current price is $"+inventory.getPrice(type, selection)+".99\n\t");
                input = scan.nextLine();
                if (input.length() > 0) input = input.substring(0,1);
                while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    System.out.print("\tSorry, I didn't get that. Could you please input a 'Y' or an 'N'?\n\t");
                    input = scan.nextLine();
                    if (input.length() > 0) input = input.substring(0,1);
                }
                if (input.equalsIgnoreCase("y")) {
                    input = "";
                    //PRICE LOOP
                    while (!input.equalsIgnoreCase("Y")) {
                        //ask 4 pric
                        System.out.print("Enter the price of "+inventory.getName(type, selection)+"(as a whole number, because 99₵ will be added automatically):\n\t");
                        input = scan.nextLine();

                        //if that was an integer then keep going, or else just go back to earlier 
                        if (isInteger(input) && (input.length() < 10 || (input.length() < 11 && (input.charAt(0) == '0' || input.charAt(0) == '1' || input.charAt(0) == '2')))) {
                            //set pric
                            int price = Integer.parseInt(input);

                            //confrim pric
                            System.out.print("Are you sure about this price?: $"+price+".99 (Y/N)\n\t");
                            input = scan.nextLine();
                            if (input.length()> 0) input = input.substring(0,1).toUpperCase();

                            //if input invalid then enters loop
                            while (!input.equals("Y") && !input.equals("N")) {
                                System.out.print("    Sorry, I, uh, didn't get that. Please answer with a \"Y\" or an \"N\"\n\t");
                                input = scan.nextLine();
                                if (input.length()> 0) input = input.substring(0,1).toUpperCase();
                            }

                            //if the input was yes then yaaay
                            if (input.equals("Y")) {
                                inventory.setPrice(type, selection, price);
                                System.out.println("Successfully changed price to $"+inventory.getPrice(type, selection)+".99");
                            }
                        } else {
                            System.out.print("    ERROR invalid input. Please try again.\n\t");
                            //failsafe in case "y" was the response
                            input = "e";
                        }
                    }
                }
                
                System.out.print("Would you like to modify the description? (Y/N)\n\t");
                input = scan.nextLine().toUpperCase();
                if (input.length() > 0) input = input.substring(0,1);
                while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    System.out.print("\tSorry, I didn't get that. Could you please input a 'Y' or an 'N'?\n\t");
                    input = scan.nextLine().toUpperCase();
                    if (input.length() > 0) input = input.substring(0,1);
                }

                //DESCRIPTION LOOP
                if (input.equalsIgnoreCase("Y")) {
                    input = "";
                    System.out.println("Here is the previous description:\n\n"+inventory.getDescription(type, selection));
                    Thread.sleep(2000);
                    System.out.println("\nPlease type out the new description:\n(for slight modifications, you may copy and paste the previous description)");
                    String desc = scan.nextLine();
                    System.out.print("\nIs this the entirety of the new description? (Y/N)\n"+desc+"\n\t");
                    input = scan.nextLine().toUpperCase();
                    if (input.length() > 0) input = input.substring(0,1);
                    while (!input.equals("Y") && !input.equals("N")) {
                        System.out.print("\tSorry, I didn't get that. Could you please input a 'Y' or an 'N'?\n\t");
                        input = scan.nextLine().toUpperCase();
                        if (input.length() > 0) input = input.substring(0,1);
                    }
                    while (!input.equalsIgnoreCase("Y")) {
                        System.out.println("Ok. Keep editing then.");
                        desc = scan.nextLine();
                        System.out.print("Is this the entirety of the new description? (Y/N)\n"+desc+"\n\t");
                        input = scan.nextLine().toUpperCase();
                        if (input.length() > 0) input = input.substring(0,1);
                        while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                            System.out.print("\tSorry, I didn't get that. Could you please input a 'Y' or an 'N'?\n\t");
                            input = scan.nextLine().toUpperCase();
                            if (input.length() > 0) input = input.substring(0,1);
                        }
                    }
                    inventory.setDescription(type, selection, desc);
                    System.out.println("Successfully changed description.");
                }
            } catch (Exception e) {
                System.out.println("ERROR something went wrong :(");
            }
            System.out.println("Click back on the desktop window and press 'enter' or 'backspace' to exit.\u001B[0m");
            while (!selectPress && !backPress) {
                Thread.sleep(100);
            }
        }
    }

    private static void newItem() throws InterruptedException {
        playSound("select.wav");
        selectPress = false; backPress = false;
        clearScreen();
        if (lightMode) System.out.print("\u001B[47m\u001B[30m");
        System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\n"+
                            "█                                                              █\n"+
                            "█                Are you sure you would like to                █\n"+
                            "█                       add a new item?                        █\n"+
                            "█                                                              █\n"+
                            "█                           Press 'enter' to proceed           █\n"+
                            "█                                                              █\n"+
                            "█          Press 'backspace' to go back                        █\n"+
                            "█                                                              █\n"+
                            "█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
        while (!selectPress && !backPress) {
            Thread.sleep(70);
        }
        if (selectPress) {
            try {
                playSound("select.wav");
                clearScreen();
                System.out.println("Okay, I'm not writing a whole typing system so click the console for this part.\n");

                //sleep a little
                Thread.sleep(2000);

                //make a scanner and input variable
                Scanner scanny = new Scanner(System.in);
                String input = "▓";

                //create string type that stores a string saying whether this is a book, CD or DVD
                String type = "";
                switch(menuType) {
                    //0=book, 1=CD, 2=DVD
                    case 0: type = "book"; break;
                    case 1: type = "CD"; break;
                    case 2: type = "DVD"; break;
                }

                //make variables to store the name, description, price and amount to order
                String name = "";
                String description = "";
                int price = 0;

                //NAME LOOP
                while (!input.equals("Y")) {
                    //ask 4 name
                    System.out.print("Enter the name of the "+type+" you would like to add:\n\t");
                    name = scanny.nextLine();

                    //confrim name
                    System.out.print("Are you sure you would like to name the book: "+name+"? (Y/N)\n\t");
                    input = scanny.nextLine();//input
                    if (input.length()> 0) input = input.substring(0,1).toUpperCase();

                    //if input invalid then enters loop
                    while (!input.equals("Y") && !input.equals("N")) {
                        System.out.print("    Sorry, I, uh, didn't get that. Please answer with a \"Y\" or an \"N\"\n\t");
                        input = scanny.nextLine();
                        if (input.length()> 0) input = input.substring(0,1).toUpperCase();
                    }
                }
                //reset input for the next loop
                input = "";

                //DESCRIPTION LOOP
                while (!input.equals("Y")) {
                    //ask 4 descrip
                    System.out.print("Enter the description of "+name+":\n\t");
                    description = scanny.nextLine();

                    //confrim descrip
                    System.out.print("Is this description finished? (Y/N)\n\t");
                    input = scanny.nextLine();
                    if (input.length()> 0) input = input.substring(0,1).toUpperCase();

                    //if input invalid then enters loop
                    while (!input.equals("Y") && !input.equals("N")) {
                        System.out.print("    Sorry, I, uh, didn't get that. Please answer with a \"Y\" or an \"N\"\n\t");
                        input = scanny.nextLine();
                        if (input.length()> 0) input = input.substring(0,1).toUpperCase();
                    }
                }
                //reset input for the next loop
                input = "";

                //PRICE LOOP
                while (!input.equals("Y")) {
                    //ask 4 pric
                    System.out.print("Enter the price of "+name+"(as a whole number, because 99₵ will be added automatically):\n\t");
                    input = scanny.nextLine();

                    //if that was an integer then keep going, or else just go back to earlier 
                    if (isInteger(input)) {
                        //set pric
                        price = Integer.parseInt(input);

                        //confrim pric
                        System.out.print("Are you sure about this price?: $"+price+".99 (Y/N)\n\t");
                        input = scanny.nextLine();
                        if (input.length()> 0) input = input.substring(0,1).toUpperCase();

                        //if input invalid then enters loop
                        while (!input.equals("Y") && !input.equals("N")) {
                            System.out.print("    Sorry, I, uh, didn't get that. Please answer with a \"Y\" or an \"N\"\n\t");
                            input = scanny.nextLine();
                            if (input.length()> 0) input = input.substring(0,1).toUpperCase();
                        }
                    } else {
                        System.out.print("    ERROR invalid input. Please try again.\n\t");
                        //failsafe in case "y" was the response
                        input = "e";
                    }
                }

                //add the item to the collection
                switch(menuType) {
                    case 0:
                        inventory.restockBook(name, 5, false);
                        inventory.setDescription((byte)0, inventory.findIndex((byte)0, name), description);
                        inventory.setPrice((byte)0, inventory.findIndex((byte)0, name), price);
                        break;
                    case 1:
                        inventory.restockCD(name, 5, false);
                        inventory.setDescription((byte)1, inventory.findIndex((byte)1, name), description);
                        inventory.setPrice((byte)1, inventory.findIndex((byte)1, name), price);
                        break;
                    case 2:
                        inventory.restockDVD(name, 5, false);
                        inventory.setDescription((byte)2, inventory.findIndex((byte)2, name), description);
                        inventory.setPrice((byte)2, inventory.findIndex((byte)2, name), price);
                        break;
                }

                Thread.sleep(500);
                System.out.println("\nSuddenly, at the door, somebody arrives with five free copies of "+name+".\nTo get more, you will have to pay for them.\n\n");
            } catch (Exception e) {
                System.out.println("ERROR something went wrong :(");
            }

            Thread.sleep(1000);
            System.out.println("Please click the deskop again and press \"enter\" to continue.\u001B[0m");

            while (!selectPress) Thread.sleep(100);
            playSound("select.wav");
            selectPress = false;
        }
    }
    
    private static void newMonth() throws InterruptedException {
        playSound("select.wav");
        selectPress = false; backPress = false;
        clearScreen();
        if (lightMode) System.out.print("\u001B[47m\u001B[30m");
        System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\n"+
                        "█                                                              █\n"+
                        "█        Are you sure you would like to reset the month?       █\n"+
                        "█  This will reset all customer memberships from Paid Premium  █\n"+
                        "█     to Unpaid Premium and from Unpaid Premium to Regular.    █\n"+
                        "█                                                              █\n"+
                        "█                    Press 'enter' to proceed                  █\n"+
                        "█                  Press 'backspace' to go back                █\n"+
                        "█                                                              █\n"+
                        "█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█\u001B[0m");
        while (!selectPress && !backPress) {
            Thread.sleep(70);
        }
        if (selectPress) {
            playSound("select.wav");
            selectPress = false; backPress = false;
            registry.lowerMembershipStatus();
            clearScreen();
            if (lightMode) System.out.print("\u001B[47m\u001B[30m");
            System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\n"+
                            "█                                                              █\n"+
                            "█                The month has been changed                    █\n"+
                            "█      and all memberships have been adjusted accordingly.     █\n"+
                            "█                                                              █\n"+
                            "█                                                              █\n"+
                            "█           Press 'enter' or 'backspace' to finish.            █\n"+
                            "█                                                              █\n"+
                            "█                                                              █\n"+
                            "█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█\u001B[0m");
            while (!selectPress && !backPress) {
                Thread.sleep(70);
            }
        } else {playSound("back.wav");backPress = false;}
    }

    private static void readSaveFile(String file) {
        try {
            String name = "";
            String description = "";
            int number = 0;
            int price = 0;
            double num = 0.0;
            long storeFunds = 0;

            FileInputStream inputStream = new FileInputStream("SaveFiles/"+file);
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            int character = reader.read();

            //STORE FUNDS LOOP
            while ((char)character != '▓' &&  character != -1) {
                storeFunds = storeFunds*10+Character.getNumericValue((char)character);
                character = reader.read();
            } System.out.println("poo");
            //skip past the funny bars
            character = reader.read();
            Bookstore.setTotalFunds(storeFunds);
            
            //BOOK LOOP
            while ((char)character != '▓' &&  character != -1) {
                //reset the variables
                name = "";
                description = "";
                number = 0;
                price = 0;
                boolean hardCover = true;

                //build the string name
                while ((char)character != '·' &&  character != -1) {
                    name += (char)character;
                    character = reader.read();
                }
                //skip past the dot
                character = reader.read();

                //build the int number
                while ((char)character != '·' &&  character != -1) {
                    number = number*10+Character.getNumericValue((char)character);
                    character = reader.read();
                }
                //skip past the dot
                character = reader.read();

                //build the int price
                while ((char)character != '·' &&  character != -1) {
                    price = price*10+Character.getNumericValue((char)character);
                    character = reader.read();
                }
                //skip past the dot
                character = reader.read();

                //check if hardcover
                if ((char)character == 'f') hardCover = false;

                //skip past the dot
                reader.read();
                character = reader.read();

                
                //build the string description
                while ((char)character != '▒' && (char)character != '▓' &&  character != -1) {
                    description += (char)character;
                    character = reader.read();
                }
                
                //add new book to list
                inventory.restockBook(name, number, hardCover);
                inventory.setDescription((byte)0, inventory.getUniqueProductStock((byte)0)-1, description);
                inventory.setPrice((byte)0, inventory.getUniqueProductStock((byte)0)-1, price);

                //if this segment doesn't end yet then skip the weird brick thing
                if ((char)character == '▒') character = reader.read();
            }

            //skip past the bar
            character = reader.read();

            //CD LOOP
            while ((char)character != '▓' &&  character != -1) {
                //reset the variables
                name = "";
                description = "";
                number = 0;
                price = 0;
                boolean rom = false;

                //build the string name
                while ((char)character != '·' &&  character != -1) {
                    name += (char)character;
                    character = reader.read();
                }
                //skip past the dot
                character = reader.read();

                //build the int number
                while ((char)character != '·' &&  character != -1) {
                    number = number*10+Character.getNumericValue((char)character);
                    character = reader.read();
                }
                //skip past the dot
                character = reader.read();

                //build the int price
                while ((char)character != '·' &&  character != -1) {
                    price = price*10+Character.getNumericValue((char)character);
                    character = reader.read();
                }
                //skip past the dot
                character = reader.read();

                //check if rom
                if ((char)character == 't') rom = true;

                //skip past the dot
                reader.read();
                character = reader.read();

                //build the string description
                while ((char)character != '▒' && (char)character != '▓' &&  character != -1) {
                    description += (char)character;
                    character = reader.read();
                }
                
                //add new cd to list
                inventory.restockCD(name, number, rom);
                inventory.setDescription((byte)1, inventory.getUniqueProductStock((byte)1)-1, description);
                inventory.setPrice((byte)1, inventory.getUniqueProductStock((byte)1)-1, price);

                //if this segment doesn't end yet then skip the weird brick thing
                if ((char)character == '▒') character = reader.read();
            }

            //skip past the bar
            character = reader.read();

            //DVD LOOP
            while ((char)character != '▓' &&  character != -1) {
                //reset the variables
                name = "";
                description = "";
                number = 0;
                price = 0;
                boolean bluRay = false;

                //build the string name
                while ((char)character != '·' &&  character != -1) {
                    name += (char)character;
                    character = reader.read();
                }
                //skip past the dot
                character = reader.read();

                //build the int number
                while ((char)character != '·' &&  character != -1) {
                    number = number*10+Character.getNumericValue((char)character);
                    character = reader.read();
                }
                //skip past the dot
                character = reader.read();

                //build the int price
                while ((char)character != '·' &&  character != -1) {
                    price = price*10+Character.getNumericValue((char)character);
                    character = reader.read();
                }
                //skip past the dot
                character = reader.read();

                //check if bluray
                if ((char)character == 't') bluRay = true;

                //skip past the dot
                reader.read();
                character = reader.read();

                //build the string description
                while ((char)character != '▒' && (char)character != '▓' &&  character != -1) {
                    description += (char)character;
                    character = reader.read();
                }
                
                //add new dvd to list
                inventory.restockDVD(name, number, bluRay);
                inventory.setDescription((byte)2, inventory.getUniqueProductStock((byte)2)-1, description);
                inventory.setPrice((byte)2, inventory.getUniqueProductStock((byte)2)-1, price);

                //if this segment doesn't end yet then skip the weird brick thing
                if ((char)character == '▒') character = reader.read();
            }

            //skip past the bar
            character = reader.read();

            //CUSTOMER LOOP
            while ((char)character != '▓' &&  character != -1) {
                //reset the variables
                name = "";
                description = "";
                String address = "";
                String payInfo = "";
                boolean[] paid  = new boolean[]{false, false};
                number = 0;
                price = 0;
                num = 0.0;

                //build the string name
                while ((char)character != '·' &&  character != -1) {
                    name += (char)character;
                    character = reader.read();
                }

                //skip past the dot
                character = reader.read();

                //build the string payment info in the description spot
                while ((char)character != '·' &&  character != -1) {
                    description += (char)character;
                    character = reader.read();
                }

            //Tentacles·Debit Card·f·8.99·Address: Bikini Bottom·Acct

                //skip past the dot
                character = reader.read();

                if ((char) character == 't') paid[0] = true;
                else if ((char) character == 'T') {paid[0] = true; paid[1] = true;}

                //skip past t and the dot
                reader.read();
                character = reader.read();

                //build the double number
                while ((char)character != '·' &&  character != -1) {
                    payInfo += (char)character;
                    character = reader.read();
                }
                //offload stuff into the double and reset payInfo
                num = Double.parseDouble(payInfo); payInfo = "";

                //skip past the dot
                character = reader.read();

                //build the int number (age)
                while ((char)character != '·' &&  character != -1) {
                    number = number*10+Character.getNumericValue((char)character);
                    character = reader.read();
                }

                //skip past the dot
                character = reader.read();

                //build the address info in the description spot
                while ((char)character != '·' &&  character != -1) {
                    address += (char)character;
                    character = reader.read();
                }

                //skip past the dot
                character = reader.read();

                //build the string payment info in the description spot
                while ((char)character != '▒' && (char)character != '▓' &&  character != -1) {
                    payInfo += (char)character;
                    character = reader.read();
                }
                
                //add new member to list
                registry.addMember(name, description, number, paid[0], paid[1], address, payInfo);
                registry.addMoneySpent(registry.numMem()-1, num);

                //if this segment doesn't end yet then skip the weird brick thing
                if ((char)character == '▒') character = reader.read();
            }
            
            
            reader.close();
 
        } catch (FileNotFoundException e) {
            System.out.println("ERROR 404 file not found");
        } catch (IOException e) {
            System.out.print("ERROR something went wrong!");
        }
    } // end readSaveFile()

    private static boolean writeSaveFile() {
        try {
            FileWriter writer = new FileWriter(nextFile);

            writer.write(Bookstore.getTotalFunds()+"▓");

            //save all the products
            for (byte type = 0;type < 3; type++) {
                for (int i=0;i<inventory.getUniqueProductStock(type);i++) {
                    //bars between the products
                    if (i != 0) writer.write("▒");

                    //write all the info separated by dots
                    if (inventory.isSpecial(type, i)) writer.write(inventory.getName(type, i)+"·"+inventory.getProductStock(type, i)+"·"+inventory.getPrice(type, i)+"·t·"+inventory.getDescription(type, i));
                    else writer.write(inventory.getName(type, i)+"·"+inventory.getProductStock(type, i)+"·"+inventory.getPrice(type, i)+"·f·"+inventory.getDescription(type, i));
                } //end for and write the big block
                writer.write("▓");
            }

            //save the customer data
            String[] names = registry.nameMem();
            for (int i=0;i<registry.numMem();i++) {
                //bars between the people
                if (i != 0) writer.write("▒");

                //the if statements are to change how I store a boolean value
                if (registry.isPremium(i)[0]) {
                    if (registry.isPremium(i)[1]) writer.write(names[i]+"·"+registry.getPaymentType(i)+"·T·"+registry.getMoneySpent(i)+"·"+registry.getAge(i)+"·"+registry.getAddress(i)+"·"+registry.getPrivateDetails(i));
                    else writer.write(names[i]+"·"+registry.getPaymentType(i)+"·t·"+registry.getMoneySpent(i)+"·"+registry.getAge(i)+"·"+registry.getAddress(i)+"·"+registry.getPrivateDetails(i));
                } else writer.write(names[i]+"·"+registry.getPaymentType(i)+"·f·"+registry.getMoneySpent(i)+"·"+registry.getAge(i)+"·"+registry.getAddress(i)+"·"+registry.getPrivateDetails(i));
            } //end for and write the big block
            writer.write("▓");
            
            //end the method now
            writer.close();
            return true;
        } catch (IOException e) {return false;}
    }

    private static void newCustomer() throws InterruptedException {
        playSound("select.wav");
        selectPress = false;
        //wait until either selectPress or backPress
        clearScreen();
        if (lightMode) System.out.print("\u001B[47m\u001B[30m");
        System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\n"+
                            "█                                                              █\n"+
                            "█                Are you sure you would like to                █\n"+
                            "█              add a new customer to the registry?             █\n"+
                            "█                                                              █\n"+
                            "█                           Press 'enter' to proceed           █\n"+
                            "█                                                              █\n"+
                            "█          Press 'backspace' to go back                        █\n"+
                            "█                                                              █\n"+
                            "█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
        while (!selectPress && !backPress) {
            Thread.sleep(100);
        }
        if (selectPress) {
            try {
                playSound("select.wav");
                //for some reason it gave me an error saying the string wasnt initialized so I manually initialized it
                String info[] = new String[]{"", "", "", "", ""};//0==name, 1==payType, 2==accountInfo, 3==address, 4==premium
                int age = 0;
                Scanner scan = new Scanner(System.in);
                clearScreen();

                System.out.println("I'm lazy, so this is the best UI you're gonna get for this sequence.\n");
                Thread.sleep(1000);

                System.out.println("I'm using Scanner this time so you'll have to click in the console window to type here.\n");
                Thread.sleep(1000);

                System.out.print("Enter the name (eg: \"First Last\" no middle name) of the new customer:\n\t");
                info[0] = scan.nextLine();
                while (info[0].indexOf(" ") == -1 || info[0].substring(info[0].indexOf(" ")+1).indexOf(" ") != -1) {
                    if (info[0].indexOf(" ") == -1) {
                        System.out.print("    Sorry, you have to have both a first and a last name separated by a single spacebar:\n\t");
                        info[0] = scan.nextLine();
                    } else if (info[0].substring(info[0].indexOf(" ")).indexOf(" ")+1 != info[0].substring(info[0].indexOf(" ")).length()) {
                        System.out.print("    Sorry, middle names/extra spaces ruin the system I set up. Please only provide the first and last name:\n\t");
                        info[0] = scan.nextLine();
                    } else info[0] = info[0].substring(0,info[0].length()-1);
                }
                //CAPITALIZATION (first letter of first and last name always capitalized)
                info[0] = info[0].substring(0,1).toUpperCase()+info[0].substring(1,info[0].indexOf(" "))+info[0].substring(info[0].indexOf(" "),info[0].indexOf(" ")+2).toUpperCase()+info[0].substring(info[0].indexOf(" ")+2);
                Thread.sleep(400);

                System.out.print("In years, how old is "+info[0]+"?\n  (eg: \"24\")\n\t");
                info[1] = scan.nextLine();
                while (!isInteger(info[1])) {
                    System.out.print("    Please input a number for the age:\n\t");
                    info[1] = scan.nextLine();
                }
                age = Integer.parseInt(info[1]);
                Thread.sleep(400);

                System.out.print("Enter the payment method for "+info[0]+":\n  (eg: \"Credit Card\")\n\t");
                info[1] = scan.nextLine();
                Thread.sleep(400);

                System.out.print("Enter additional payment account information for "+info[0]+": \n  (eg: \"Card PIN: 1234-5678-9012-2345, Security Code: 123\")\n\t");
                info[2] = scan.nextLine();
                Thread.sleep(400);

                System.out.print("Enter the address of the new customer:\n  (eg: 9201 University City Blvd, Charlotte, NC 28223)\n\t");
                info[3] = scan.nextLine();
                Thread.sleep(400);

                System.out.print("Would "+info[0]+" like to get a $32.99 premium membership?(Y/N)\n\t(The cost will be automatically added to the receipt)\n\t");
                info[4] = scan.nextLine();
                if (info[4].length()> 0) info[4] = info[4].substring(0,1).toUpperCase();
                while (!info[4].equals("Y") && !info[4].equals("N")) {
                    System.out.print("    Sorry, please input either \"Y\" or \"N\" or words beginning with those letters");
                    info[4] = scan.nextLine();
                    if (info[4].length()> 0) info[4] = info[4].substring(0,1).toUpperCase();
                }
                Thread.sleep(400);
                
                //add to record
                RecordKeeper.addRegistered(info[0], age, info[3], (info[4].equals("Y")));
                if (info[4].equals("Y")) RecordKeeper.addToDailyRevenue(32.99);

                //add member
                registry.addMember(info[0], info[1], age, (info[4].equals("Y")), info[4].equals("Y"), info[3], info[2]);
                System.out.println("\nSuccessfully added customer "+info[0]+" to registry\n");
            } catch (Exception e) {
                System.out.println("ERROR something went wrong :(");
            }

            Thread.sleep(1000);
            System.out.println("Please click the deskop again and press \"enter\" to continue.\u001B[0m");

            while (!selectPress) Thread.sleep(100);
            playSound("select.wav");
            selectPress = false;
        } else playSound("back.wav");
    }

    private static void editCustomer(int index) throws InterruptedException {
        playSound("select.wav");
        selectPress = false;
        //wait until either selectPress or backPress
        clearScreen();
        if (lightMode) System.out.print("\u001B[47m\u001B[30m");
        System.out.println("█▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\n"+
                            "█                                                              █\n"+
                            "█                Are you sure you would like to                █\n"+
                            "█             modify the profile of this customer?             █\n"+
                            "█                                                              █\n"+
                            "█                           Press 'enter' to proceed           █\n"+
                            "█                                                              █\n"+
                            "█          Press 'backspace' to go back                        █\n"+
                            "█                                                              █\n"+
                            "█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
        while (!selectPress && !backPress) {
            Thread.sleep(100);
        }
        if (selectPress) {
            try {
                playSound("select.wav");
                String info[] = new String[]{registry.nameMem()[index], registry.getPaymentType(index), registry.getPrivateDetails(index), registry.getAddress(index), ""};//0==name, 1==payType, 2==accountInfo, 3==address
                boolean[] premiumStatus = registry.isPremium(index);
                int age = registry.getAge(index);

                String response = "";

                Scanner scan = new Scanner(System.in);
                clearScreen();

                System.out.println("I'm lazy, so this is the best UI you're gonna get for this sequence.\n");
                Thread.sleep(1000);

                System.out.println("I'm using Scanner this time so you'll have to click in the console window to type here.\n");
                Thread.sleep(1000);

                System.out.print("Would you like to modify the name, "+info[0]+"? (Y/N)\n\t");
                response = scan.nextLine();
                if (response.length() > 0) response = response.substring(0,1);
                while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
                    System.out.print("    Sorry, I don't understand. Please answer with either a \"Y\" or \"N\"\n\t");
                    response = scan.nextLine();
                    if (response.length() > 0) response = response.substring(0,1);
                }

                if (response.equalsIgnoreCase("y")) {
                    System.out.println("The previous name was "+info[0]);
                    System.out.print("Enter the new name (eg: \"First Last\" no middle name) of the customer:\n\t");
                    info[0] = scan.nextLine();
                    while (info[0].indexOf(" ") == -1 || info[0].substring(info[0].indexOf(" ")+1).indexOf(" ") != -1) {
                        if (info[0].indexOf(" ") == -1) {
                            System.out.print("    Sorry, you have to have both a first and a last name separated by a single spacebar:\n\t");
                            info[0] = scan.nextLine();
                        } else if (info[0].substring(info[0].indexOf(" ")).indexOf(" ")+1 != info[0].substring(info[0].indexOf(" ")).length()) {
                            System.out.print("    Sorry, middle names/extra spaces ruin the system I set up. Please only provide the first and last name:\n\t");
                            info[0] = scan.nextLine();
                        } else info[0] = info[0].substring(0,info[0].length()-1);
                    }
                    //CAPITALIZATION (first letter of first and last name always capitalized)
                    info[0] = info[0].substring(0,1).toUpperCase()+info[0].substring(1,info[0].indexOf(" "))+info[0].substring(info[0].indexOf(" "),info[0].indexOf(" ")+2).toUpperCase()+info[0].substring(info[0].indexOf(" ")+2);

                    //add to record
                    RecordKeeper.registryEdit("Modified customer's name from "+registry.nameMem()[index]+" to "+info[0]);

                    //set it
                    registry.changeName(index, info[0]);

                    Thread.sleep(400);
                }

                //check if want to modify age
                System.out.print("Would you like to modify the age ("+age+") of "+info[0]+"? (Y/N)\n\t");
                response = scan.nextLine();
                if (response.length() > 0) response = response.substring(0,1);
                while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
                    System.out.print("    Sorry, I don't understand. Please answer with either a \"Y\" or \"N\"\n\t");
                    response = scan.nextLine();
                    if (response.length() > 0) response = response.substring(0,1);
                }
                if (response.equalsIgnoreCase("y")) {
                    System.out.println("The previous age on file was "+age);
                    System.out.print("In years, how old is "+info[0]+"?\n  (eg: \"24\")\n\t");
                    info[4] = scan.nextLine();
                    while (!isInteger(info[4])) {
                        System.out.print("    Please input a number for the age:\n\t");
                        info[4] = scan.nextLine();
                    }
                    //add to record
                    RecordKeeper.registryEdit("Modified customer "+info[0]+"'s age from "+registry.getAge(index)+" to "+info[4]);
                    //change it
                    registry.changeAge(index, Integer.parseInt(info[4]));
                    /*i dont rember if i need this empty but just in case*/ info[4] = "";
                    Thread.sleep(400);
                }

                //check if want to modify pay method
                System.out.print("Would you like to modify the payment method for "+info[0]+"? (Y/N)\n\t");
                response = scan.nextLine();
                if (response.length() > 0) response = response.substring(0,1);
                while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
                    System.out.print("    Sorry, I don't understand. Please answer with either a \"Y\" or \"N\"\n\t");
                    response = scan.nextLine();
                    if (response.length() > 0) response = response.substring(0,1);
                }
                if (response.equalsIgnoreCase("y")) {
                    System.out.println("The previous payment information on file was "+info[1]);
                    System.out.print("Enter the payment method for "+info[0]+":\n  (eg: \"Credit Card\")\n\t");
                    info[1] = scan.nextLine();

                    //add to record
                    RecordKeeper.registryEdit("Modified customer "+info[0]+"'s pay method from "+registry.getPaymentType(index)+" to "+info[1]);
                    //change it
                    registry.changePaymentType(index, info[1]);
                    Thread.sleep(400);
                }

                //check if want to modify additional pay info
                System.out.print("Would you like to modify the additional payment information for "+info[0]+"? (Y/N)\n\t");
                response = scan.nextLine();
                if (response.length() > 0) response = response.substring(0,1);
                while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
                    System.out.print("    Sorry, I don't understand. Please answer with either a \"Y\" or \"N\"\n\t");
                    response = scan.nextLine();
                    if (response.length() > 0) response = response.substring(0,1);
                }
                if (response.equalsIgnoreCase("y")) {
                    System.out.println("The previous payment information on file was "+info[2]);
                    System.out.print("Enter additional payment account information for "+info[0]+": \n  (eg: \"Card PIN: 1234-5678-9012-2345, Security Code: 123\")\n\t");
                    info[2] = scan.nextLine();

                    //add to record
                    RecordKeeper.registryEdit("Modified customer "+info[0]+"'s additional pay info to "+info[2]);
                    //change it
                    registry.changeInfo(index, info[2]);
                    Thread.sleep(400);
                }

                //check if want to modify address
                System.out.print("Would you like to modify the address on file for "+info[0]+"? (Y/N)\n\t");
                response = scan.nextLine();
                if (response.length() > 0) response = response.substring(0,1);
                while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
                    System.out.print("    Sorry, I don't understand. Please answer with either a \"Y\" or \"N\"\n\t");
                    response = scan.nextLine();
                    if (response.length() > 0) response = response.substring(0,1);
                }
                if (response.equalsIgnoreCase("y")) {
                    System.out.println("The previous address on file was "+info[3]);
                    System.out.print("Enter the address of the customer:\n  (eg: 9201 University City Blvd, Charlotte, NC 28223)\n\t");
                    info[3] = scan.nextLine();

                    //add to record
                    RecordKeeper.registryEdit("Modified customer "+info[0]+"'s address to "+info[3]);
                    //change it
                    registry.changeAddress(index, info[3]);
                    Thread.sleep(400);
                }

                //only do this part if not paid premium
                if (!premiumStatus[1]) {
                    if (premiumStatus[0]) System.out.print("Would "+info[0]+" like to get renew their $32.99 premium membership?(Y/N)\n\t(The cost will be automatically applied to the account)\n\t");
                    else System.out.print("Would "+info[0]+" like to get a $32.99 premium membership?(Y/N)\n\t(The cost will be automatically applied to the account)\n\t");
                    info[4] = scan.nextLine();
                    if (info[4].length()> 0) info[4] = info[4].substring(0,1).toUpperCase();
                    while (!info[4].equals("Y") && !info[4].equals("N")) {
                        System.out.print("    Sorry, please input either \"Y\" or \"N\" or words beginning with those letters\n\t");
                        info[4] = scan.nextLine();
                        if (info[4].length()> 0) info[4] = info[4].substring(0,1).toUpperCase();
                    }
                    if (info[4].equals("Y")) {
                        //add to record
                        RecordKeeper.registryEdit("Modified customer "+info[0]+"'s profile to premium, paid");
                        RecordKeeper.addToDailyRevenue(32.99);
                        //change it
                        registry.setPremMem(index, true, true);
                        registry.addMoneySpent(index, 32.99);
                        Bookstore.setTotalFunds(Bookstore.getTotalFunds()+3299);
                    }

                    Thread.sleep(400);
                }

                System.out.println("\nAll requested edits have been completed\n");
            } catch (Exception e) {
                System.out.println("ERROR something went wrong :(");
            }

            Thread.sleep(1000);
            System.out.println("Please click the deskop again and press \"enter\" to continue.\u001B[0m");

            selectPress = false;
            while (!selectPress) Thread.sleep(70);
            playSound("select.wav");
            selectPress = false;
        } else playSound("back.wav");
    }// end editCustomer

    private static void HappyMessage() throws InterruptedException {
        clearScreen();
        if (lightMode) System.out.println("\u001B[47m\u001B[30m█▀▀██▀▀▀██▀▀▀▀▀██▀▀▀██▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\u001B[0m\n\u001B[47m\u001B[30m█ █▄▀▄██         █ ▄▀▄█                                        █\u001B[0m\n\u001B[47m\u001B[30m█   ▀▄█           █▄▀  t h a n k   y o u   f o r   y o u r     █\u001B[0m\n\u001B[47m\u001B[30m█     █ ██▄   ▄██ █         s  e  r  v  i  c  e  s             █\u001B[0m\n\u001B[47m\u001B[30m█     █     █     █                                            █\u001B[0m\n\u001B[47m\u001B[30m█     ▄██▄     ▄██▄                                            █\u001B[0m\n\u001B[47m\u001B[30m█  ▄▄▀▄█▀█▄█▄█▄█▀█▄▀▄▄                                         █\u001B[0m\n\u001B[47m\u001B[30m█  ▀▄█  ▀▀▄█▄█▄▀▀  █▄▀                                         █\u001B[0m\n\u001B[47m\u001B[30m█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█\u001B[0m");
        else System.out.println("█▀▀██▀▀▀██▀▀▀▀▀██▀▀▀██▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\n█ █▄▀▄██         █ ▄▀▄█                                        █\n█   ▀▄█           █▄▀  t h a n k   y o u   f o r   y o u r     █\n█     █ ██▄   ▄██ █         s  e  r  v  i  c  e  s             █\n█     █     █     █                                            █\n█     ▄██▄     ▄██▄                                            █\n█  ▄▄▀▄█▀█▄█▄█▄█▀█▄▀▄▄                                         █\n█  ▀▄█  ▀▀▄█▄█▄▀▀  █▄▀                                         █\n█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
        Thread.sleep(5000);
        clearScreen();
        if (lightMode) System.out.println("\u001B[47m\u001B[30m█▀▀██▀▀▀██▀▀▀▀▀██▀▀▀██▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\u001B[0m\n\u001B[47m\u001B[30m█ █▄▀▄██         █ ▄▀▄█                                        █\u001B[0m\n\u001B[47m\u001B[30m█   ▀▄█           █▄▀                                          █\u001B[0m\n\u001B[47m\u001B[30m█     █ ██▄   ▄██ █ t h e   l i v e   s e r v i c e   m o d e l█\u001B[0m\n\u001B[47m\u001B[30m█     █     █     █      i s   b e s t   f o r   t h i s       █\u001B[0m\n\u001B[47m\u001B[30m█     ▄██▄     ▄██▄          l i n e   o f   w o r k           █\u001B[0m\n\u001B[47m\u001B[30m█  ▄▄▀▄█▀█▄█▄█▄█▀█▄▀▄▄                                         █\u001B[0m\n\u001B[47m\u001B[30m█  ▀▄█  ▀▀▄█▄█▄▀▀  █▄▀                                         █\u001B[0m\n\u001B[47m\u001B[30m█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█\u001B[0m");
        else System.out.println("█▀▀██▀▀▀██▀▀▀▀▀██▀▀▀██▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█\n█ █▄▀▄██         █ ▄▀▄█                                        █\n█   ▀▄█           █▄▀                                          █\n█     █ ██▄   ▄██ █ t h e   l i v e   s e r v i c e   m o d e l█\n█     █     █     █      i s   b e s t   f o r   t h i s       █\n█     ▄██▄     ▄██▄          l i n e   o f   w o r k           █\n█  ▄▄▀▄█▀█▄█▄█▄█▀█▄▀▄▄                                         █\n█  ▀▄█  ▀▀▄█▄█▄▀▀  █▄▀                                         █\n█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
        Bookstore.setTotalFunds(Bookstore.getTotalFunds()+5000);
        RecordKeeper.addToDailyRevenue(50.0);
        Thread.sleep(10000);
    }

    //I took this from StackOverflow
    private static boolean isInteger(String str) {
        if (str == null) return false;
        int length = str.length();
        if (length == 0) return false;
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) return false;
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }


    //KeyListener JFrame stuff
    Main() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1920,1080);
		this.addKeyListener(this);
		this.getContentPane().setBackground(Color.black);
		this.setVisible(true);
	} //end object

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
            case 38: case 87:
                upPress = true;
                break;
            case 40: case 83:
                dwnPress = true;
                break;
            case 37: case 65:
                lPress = true;
                break;
            case 39: case 68:
                rPress = true;
                break;
            case 88:
                express = true;
                break;
            case 32: case 10:
                selectPress = true;
                break;
            case 8:
                backPress = true;
                break;
	    }

	}

    //I'm not sure if I actually used this in the program but just in case
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
            case 38: case 87:
                upPress = false;
                break;
            case 40: case 83:
                dwnPress = false;
                break;
            case 37: case 65:
                lPress = false;
                break;
            case 39: case 68:
                rPress = false;
                break;
            case 32: case 10:
                selectPress = false;
                break;
            case 8:
                backPress = false;
                break;
	    }
	}// end keyReleased

    //sound
    public static synchronized void playSound(String url) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                Main.class.getResourceAsStream("Sounds/"+url));
            clip.open(inputStream);
            clip.start(); 
        } catch (Exception e) {
            //System.out.print("\t(sound's stopped working again");
        }
    }
}