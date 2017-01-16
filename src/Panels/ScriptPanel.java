package Panels;

import Util.Timer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.util.ArrayList;

public class ScriptPanel extends JPanel{

    public static String name = "Scripts";

    private JButton startFiremakingScriptButton, saveFiremakingButton;

    private boolean fmScriptIsRunning = false, isStartup = false;

    private Util.Timer timer;

    private Robot robot;

    private int countedTime, tinderboxX, tinderboxY, xPosition, yPosition, xPixelGap = 42, yPixelGap = 35, count = 0;

    private JTextField xTextfieldPos, yTextfieldPos;

    private ArrayList <String> fileArray;


    public ScriptPanel(){
    init();
    }

    public void init(){

        this.setLayout(new BorderLayout());

        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), name, TitledBorder.CENTER, TitledBorder.TOP));

        initializeInterface();
        positionInterface();
        initializeFunctionality();
        readValues();
    }

    public void initializeInterface(){

        startFiremakingScriptButton = new JButton("Start Firemaking script");
        saveFiremakingButton = new JButton("Save Firemaking axis");

        fileArray = new ArrayList<>();

        timer = new Timer();

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        xTextfieldPos = new JTextField();
        yTextfieldPos = new JTextField();
    }

    public void readValues(){

        try {
            String homeDir = System.getProperty("user.home") + "\\" + "RunescapeTool" + "\\" + "Values.txt";
            BufferedReader br = new BufferedReader(new FileReader(homeDir));

            String strLine;

            while((strLine = br.readLine()) != null){
                fileArray.add(strLine);
            }

            xTextfieldPos.setText(fileArray.get(0));
            yTextfieldPos.setText(fileArray.get(1));

        } catch (Exception e) {
            System.out.println("Er is nog geen file aangemaakt. --- line 91");
        }
    }

    public void positionInterface(){

        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(startFiremakingScriptButton, c);

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(saveFiremakingButton, c);

        c.fill = GridBagConstraints.CENTER;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(new JLabel("X position:"), c);

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(xTextfieldPos, c);

        // position y axis JLabel
        c.fill = GridBagConstraints.CENTER;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(new JLabel("Y position:"), c);

        // position y axis textfield
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(yTextfieldPos, c);

        // create 3 rows, to place different script panels in it
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 1, 8, 8));

        gridPanel.add(panel);
        this.add(gridPanel);
    }

    public void initializeFunctionality() {

            startFiremakingScriptButton.addActionListener(e -> {

                if(!fmScriptIsRunning) {
                    tinderboxX = Integer.parseInt(xTextfieldPos.getText());
                    tinderboxY = Integer.parseInt(yTextfieldPos.getText());

                    xPosition = tinderboxX;
                    yPosition = tinderboxY;

                    fmScriptIsRunning = true;

                    timer.start();
                    startFiremakingScriptButton.setText("Stop Firemaking Script");
                }

                else{
                    resetScript();
                    startFiremakingScriptButton.setText("Start Firemaking Script");
                }
            });

        saveFiremakingButton.addActionListener(e -> {

            String x = xTextfieldPos.getText();
            String y = yTextfieldPos.getText();

            PrintStream fileStream = null;
            boolean result = false;
            try {

                String homeDir = System.getProperty("user.home") +  "\\" + "RunescapeTool" + "\\" + "Values.txt";
                fileStream = new PrintStream(new File(homeDir));
                fileStream.println(x);
                fileStream.println(y);
                result = true;

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            if(result)
                System.out.println("Successfully saved parameters.");
        });
    }

    public void update() {

        if(fmScriptIsRunning){

            if(timer.getElapsedMillis() > countedTime){

                // if count is even select the tinderbox(must be in left-top corner)
                if ((count & 1) == 0){
                    selectTinderbox();
                    mouseClick();

                    if(!isStartup)
                    countedTime += 1210;

                    //needed for the first log , takes a longer time to lighten
                    if(isStartup)
                        countedTime += 2200;
                }

                // else select the log, based on x/y
                else{
                    xPosition += xPixelGap;
                    robot.mouseMove(xPosition, yPosition);
                    mouseClick();

                    countedTime += 1210;

                }

                 count++;
                checkCount(count);
                System.out.println(count);
            }
        }
    }

    public void mouseClick(){
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void selectTinderbox(){
        robot.mouseMove(tinderboxX, tinderboxY);
    }

    public void checkCount(int count){

        int c = count;

        if(c == 2)
            isStartup = true;

        if(c == 3)
            isStartup = false;

        if(c == 6 || c == 14 || c == 22 || c == 30 || c == 38 || c == 46){
            xPosition = tinderboxX - xPixelGap;
            yPosition += yPixelGap;
        }

        if(c > 53){
            resetScript();
        }
    }

    public void resetScript(){
        fmScriptIsRunning = false;
        timer.reset();
        count = 0;
        countedTime = 0;
    }
}
