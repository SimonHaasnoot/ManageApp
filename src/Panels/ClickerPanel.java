package Panels;

import Util.Timer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.InputEvent;

public class ClickerPanel extends JPanel {

    public static String name = "Clicker";

    private JTextField randomIntervalField, timeIntervalField, clickAmountField;
    private JLabel randomIntervalFieldLabel, timeIntervalFieldLabel, clickAmountFieldLabel;
    private JLabel clickCountLabel, randomIntervalLabel, timeIntervalLabel;


    private JButton start, stop;

    private int randomInterval, timeInterval, clickAmount, countedTime, clickCount;

    private Robot robot;
    private Util.Timer timer;

    private boolean isRunning = false;

    public ClickerPanel(boolean visible){

        if(visible)
        init();
    }

    public void init(){

        this.setLayout(new BorderLayout());

        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), name, TitledBorder.CENTER, TitledBorder.TOP));

        initComponents();
        positionComponents();
        initFunctionality();
    }

    public void positionComponents(){

        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        //position time interval textfield
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 8, 8, 8);
        panel.add(timeIntervalField, c);

        // position time interval label
        c.gridx = 0;
        panel.add(timeIntervalFieldLabel, c);

        //position random interval textfield
        c.gridx = 1;
        c.insets = new Insets(0, 8, 8, 8);
        c.gridy = 1;
        panel.add(randomIntervalField, c);

        //position the random interval label
        c.gridx = 0;
        panel.add(randomIntervalFieldLabel, c);

        // position the click amount textfield
        c.gridx = 1;
        c.insets = new Insets(0, 8, 0, 8);
        c.gridy = 2;
        panel.add(clickAmountField, c);

        //position the click amount label
        c.gridx = 0;
        panel.add(clickAmountFieldLabel, c);

        //position the start button
        c.insets = new Insets(8, 8, 0, 8);
        c.gridy = 3;
        panel.add(start, c);

        // position the stop button
        c.gridx = 1;
        panel.add(stop, c);

        //position the click count label
        c.gridy = 4;
        c.gridx = 0;
        panel.add(clickCountLabel, c);

        //position the time interval label
        c.gridy = 5;
        c.gridx = 0;
        panel.add(timeIntervalLabel, c);

        //position the random time interval label
        c.gridy = 6;
        c.gridx = 0;
        panel.add(randomIntervalLabel, c);

        this.add(panel);
    }

    public void initComponents(){

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();}

        timer = new Timer();

        randomIntervalField = new JTextField();
        timeIntervalField = new JTextField();
        clickAmountField = new JTextField();

        randomIntervalFieldLabel = new JLabel("Random interval(ms): ");
        timeIntervalFieldLabel = new JLabel("Time interval(ms): ");
        clickAmountFieldLabel = new JLabel("Click amount: ");
        clickCountLabel = new JLabel("Count = 0");

        randomIntervalLabel = new JLabel("Next click: ");
        timeIntervalLabel = new JLabel("Elapsed: ");


        start = new JButton("Start");
        stop = new JButton("Stop");
    }

    public void initFunctionality(){


        start.addActionListener(e -> {

                getValues();
                countedTime = timeInterval;
                timer.start();

                isRunning = true;
        });

        stop.addActionListener(e -> stopClicking());
    }

    public void getValues(){
        randomInterval = Integer.parseInt(randomIntervalField.getText());
        timeInterval = Integer.parseInt(timeIntervalField.getText());
        clickAmount = Integer.parseInt(clickAmountField.getText());
    }

    public void stopClicking(){
        System.out.println("Stopped!");
        isRunning = false;
        clickCount = 0;
        timer.stop();
        timer.reset();
    }

    public void update(){

        if(isRunning){

            if(timer.getElapsedMillis() > countedTime){

                //press left mouse button
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);

                int random = (int) ((Math.random()*randomInterval)+1);
                countedTime = countedTime + (timeInterval + random);

                clickCount++;

                clickCountLabel.setText("Count = " + Integer.toString(clickCount));

                System.out.println(countedTime);
            }

            if(clickCount == clickAmount){
                System.out.println("Click amount reached!");
                stopClicking();
            }
        }

        timeIntervalLabel.setText("Elapsed = " + Long.toString(timer.getElapsedMillis()));
        randomIntervalLabel.setText("Next click = " + countedTime);
    }
}
