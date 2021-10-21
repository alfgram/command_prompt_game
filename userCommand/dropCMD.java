package userCommand;

import StagGame.StagModel;
import gameObjects.gameEntity;
import java.util.ArrayList;

public class dropCMD extends userCMD {

    public dropCMD(StagModel gameModel, ArrayList<String> command) {
        this.model = gameModel;
        this.command = command;
    }

    public String executeCMD() {
        for (gameEntity item : model.getCurrentPlayer().getInventory()) {
            if (command.contains(item.getName())) {
                drop(item);
                return "You dropped a " + item.getName();
            }
        }
        return "You don't have this item";
    }
}
