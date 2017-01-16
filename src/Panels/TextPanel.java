package Panels;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;

public class TextPanel extends JPanel{

    public static String name = "Notes";

    private JTextArea textArea;
    private JScrollPane scrollPane;

    private JButton save;

        public TextPanel(){
        init();
    }

    public void init(){

        this.setLayout(new BorderLayout());

        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), name, TitledBorder.CENTER, TitledBorder.TOP));

        initializeComponents();
        initFunctionality();
        positionComponents();
        fillTextArea();
    }

    public void initializeComponents(){

        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);

        save = new JButton("save");
    }

    public void initFunctionality(){

        textArea.setLineWrap(true);
        this.add(scrollPane);

        save.addActionListener(e -> saveCurrentText());
    }

    public void positionComponents(){

        this.add(save, BorderLayout.SOUTH);
    }

    public void saveCurrentText(){

        try {
            PrintStream ps = new PrintStream(new File(System.getProperty("user.home") + "\\" + "RunescapeTool" + "\\" + "Notes.txt"));
            ps.print(textArea.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillTextArea(){

        String dir = System.getProperty("user.home") + "\\" + "RunescapeTool" + "\\" + "Notes.txt";
        File file = new File(dir);

        if(!file.exists())
            try {
                file.createNewFile();
                System.out.println("Created");
            } catch (IOException e) {
                e.printStackTrace();
            }

        try {
            FileInputStream fr = new FileInputStream(dir);
            BufferedReader br = new BufferedReader(new InputStreamReader(fr));

            String line;

            while((line = br.readLine()) != null){
              textArea.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
