package userCommand;

import StagGame.StagModel;
import java.util.ArrayList;

public class gotoCMD extends userCMD {

    public gotoCMD(StagModel gameModel, ArrayList<String> command) {
        this.model = gameModel;
        this.command = command;
    }

    public String executeCMD() {
        for (String route : model.getCurrentLocation().getRoutes()) {
            if (command.contains(route)) {
                model.setCurrentLocation(route);
                return printDetailsOfLocation();
            }
        }
        return "No route to this location";
    }
}
