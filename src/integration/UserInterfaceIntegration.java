package integration;

import extension3.UserInterface;
import extension3.VerticalToolbar;

/**
 * Class to allow the integration of different extensions with the user
 * interface extension. It extends the UserInterface extension so that it is
 * easy to do the integration
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class UserInterfaceIntegration extends UserInterface {
    /**
     * In fact the only difference between both is the fact that this one will have
     * more buttons in the toolbar so it changes the toolbar to a new version with
     * all the buttons needed
     * 
     * @return toolbar with buttons to use every other extension
     */
    @Override
    protected VerticalToolbar createToolbar() {
        VerticalToolbar toolbar = new VerticalToolbar(0.05, 1);
        toolbar.addButton("Load", "Loads a new configuration file");
        toolbar.addButton("Data to plot", "Selects new data to plot");
        toolbar.addButton("Simulation type", "Selects the type of simulation to perform");
        toolbar.addButton("Losses", "Computes the losses");

        return toolbar;
    }
}
