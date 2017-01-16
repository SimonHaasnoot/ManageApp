import Panels.TimerPanel;
import Panels.ClickerPanel;
import Panels.TextPanel;
import Panels.ScriptPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame{

    private TimerPanel timerPanel;
    private ClickerPanel clickerPanel;
    private ScriptPanel scriptPanel;

    public MainFrame(){
        init();
    }

    public void init(){

        checkDir();

        this.setMinimumSize(new Dimension(800, 600));
        this.setSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initGui();
        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


    public void initGui(){

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 4, 4));

        timerPanel = new TimerPanel(true);
        panel.add(timerPanel);

        clickerPanel = new ClickerPanel(true);
        panel.add(clickerPanel);

        panel.add(new TextPanel());

        scriptPanel = new ScriptPanel();
        panel.add(scriptPanel);

        this.add(panel);
    }

    public void update(){

        this.setTitle(App.APPNAME + " | " + "Mouse X-Location: "  + MouseInfo.getPointerInfo().getLocation().getX() + " | " + "Mouse Y-Location: " + MouseInfo.getPointerInfo().getLocation().getY());
        timerPanel.update();
        clickerPanel.update();
        scriptPanel.update();
    }

    public void checkDir(){

        File theDir = new File(System.getProperty("user.home") + "\\" + "RunescapeTool");

        if (!theDir.exists()) {
            System.out.println("creating directory: " + "RunescapeTool.");
            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            }
            catch(SecurityException se){

            }
            if(result) {
                System.out.println("Directory created.");
            }
        }
    }
}

