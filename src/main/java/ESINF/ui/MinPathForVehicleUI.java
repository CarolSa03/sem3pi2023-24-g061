package ESINF.ui;

import ESINF.controller.MinPathForVehicleController;


public class MinPathForVehicleUI implements Runnable {

    MinPathForVehicleController controller = new MinPathForVehicleController();

    public void run() {
        controller.loadGraph();

        controller.getMinPath();
    }
}
