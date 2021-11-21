package userInterface;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;

/**
 * Simple class to allow the recovery of a .csv file to use as the configuration
 * file of the simulation
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class ConfigChooser {
    /**
     * Shows the window of selection of data to be plotted
     */
    public static File getFile() {
        // Creates a file chooser
        JFileChooser fc = new JFileChooser("configFiles");
        // adds a filter for .csv files
        fc.addChoosableFileFilter(new FileNameExtensionFilter("*.csv", "csv"));

        // Creates and sets up the window
        JFrame frame = new JFrame("Data to Show");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Validates the value returned by the file chooser
        if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            return null;
        }
    }
}
