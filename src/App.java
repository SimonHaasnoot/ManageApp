import javax.swing.*;

public class App {


    public static final String VERSION = "0.10";

    public static final String APPNAME = "Runescape Tool";

    public static void main(String[] args) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e2){
            System.out.println("failed");
        }


        MainFrame frame = new MainFrame();

        while(true){
            try {
                frame.update();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
