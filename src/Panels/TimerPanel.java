package Panels;


import Util.Timer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TimerPanel extends JPanel {

    public static String name = "Timer";

    private JLabel timeLabel, minutesLabel, hoursLabel;
    private Util.Timer stopWatch;

    private JButton startButton, resetButton, pauseButton;

    private ImageIcon colorIcon;
    private JLabel colorLabel;


    public TimerPanel(boolean visible){
        if(visible)
        init();
    }

    public void init(){

        //initiate the Timer
        stopWatch = new Timer();

        this.setLayout(new GridLayout(2, 1, 8, 8));

        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), name, TitledBorder.CENTER, TitledBorder.TOP));

        // create and initiate the GUI
        createGUI();

        // position the components made in the createGUI method
        positionNorthComponents();
        positionSouthComponents();
    }

    public void positionNorthComponents(){

        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        c.insets = new Insets(40, 0,0, 0);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;

        // add component to the panel made in this method
        panel.add(timeLabel, c);

        // add the panel to the main class panel
        this.add(panel);
    }

    public void positionSouthComponents(){

        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // add component to the panel made in this method
        c.fill = GridBagConstraints.CENTER;
        c.anchor = GridBagConstraints.NORTH;

        c.gridx = 0;
        c.insets = new Insets(0, 0, 0, 8);
        panel.add(colorLabel, c);

        c.gridx = 1;
        c.insets = new Insets(0, 0, 0, 8);
        panel.add(startButton, c);

        c.gridx = 2;
        c.insets = new Insets(0, 0, 0, 0);
        panel.add(pauseButton, c);

        c.gridx = 3;
        c.insets = new Insets(0, 8, 0, 0);
        panel.add(resetButton, c);

        // add the panel to the class panel
        this.add(panel);
    }

    public void createGUI(){

        //initiate labels
        timeLabel = new JLabel("0");
        minutesLabel = new JLabel("0");
        hoursLabel = new JLabel("0");

        colorIcon = new ImageIcon();
        colorLabel = new JLabel(colorIcon);

        // initiate Start button
        startButton = new JButton("Start");
        startButton.addActionListener(e -> {
        stopWatch.start();
        changeIcon(true);
        });

        // initiate Pause button
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> {
            stopWatch.stop();
            changeIcon(false);
        });

        // initiate Reset button
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            stopWatch.reset();
            changeIcon(false);
        });

        // set the font of the label timer text
        timeLabel.setFont(new Font("Calibri", Font.PLAIN, 60));
    }

    public void changeIcon(boolean b){
        if(!b)
            colorIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/red.png"));
        else{
            colorIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/green.png"));
        }
        colorLabel.setIcon(colorIcon);
    }

    public void update(){

        int totalSecs = stopWatch.getElapsedSeconds();
        int hours = totalSecs/3600;
        int minutes = (totalSecs%3600)/60;
        int seconds = totalSecs % 60;
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeLabel.setText(timeString);
    }
}
