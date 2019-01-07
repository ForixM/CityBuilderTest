package ma.forix.citybuilder;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try
        {
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.err.println("Could not set Look And Feel");
        }
        Fenetre fenetre = new Fenetre();
    }
}