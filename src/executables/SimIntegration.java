package executables;

import integration.ControllerIntegration;
import integration.UIModelIntegration;
import integration.UserInterfaceIntegration;

/**
 * Example of integration. It shows the integration of all the extensions
 * 
 * @author DE OLIVEIRA MORENO NEVES, José Afonso
 */
public class SimIntegration {
    public static void main(String[] args) {
        // creates the model
        UIModelIntegration uiModel = new UIModelIntegration();
        // then the user interface
        UserInterfaceIntegration ui = new UserInterfaceIntegration();
        // then the controller with them both
        ControllerIntegration controller = new ControllerIntegration(ui, uiModel);
        // associates the controller and the ui
        ui.setController(controller);
        // shows the ui
        ui.show();
    }
}