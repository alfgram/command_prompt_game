package userCommand;

import StagGame.StagModel;
import gameObjects.gameEntity;
import java.util.ArrayList;

public class getCMD extends userCMD {

    public getCMD(StagModel gameModel, ArrayList<String> command) {
        this.model = gameModel;
        this.command = command;
    }

    public String executeCMD() {
        for (gameEntity item : model.getCurrentLocation().getEntityList()) {
            if (command.contains(item.getName())) {
                return pickUp(item);
            }
        }
        return "This item does not exist";
    }

    private String pickUp(gameEntity item) {
        if (!item.getType().equals("artefacts")) {
            return "You can't pick this up";
        }
        model.getCurrentPlayer().addToInventory(item);
        model.getCurrentLocation().removeEntity(item.getName());
        return "You picked up a " + item.getName();
    }
}
