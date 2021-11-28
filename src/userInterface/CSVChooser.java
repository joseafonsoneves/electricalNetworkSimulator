package userInterface;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;

/**
 * Simple class to allow the recovery of a .csv file
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class CSVChooser {
    /**
     * Shows the window of selection of data to be plotted
     */
    public static File getFile(JFrame frame, String initialFolderPath) {
        // Creates a file chooser
        JFileChooser fc = new JFileChooser(initialFolderPath);
        // adds a filter for .csv files
        fc.setFileFilter(new FileNameExtensionFilter("CSV files", "csv"));

        // Validates the value returned by the file chooser
        if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            return null;
        }
    }
}
