package userCommand;

import StagGame.StagModel;
import gameObjects.gameEntity;

import java.util.ArrayList;

public class inventoryCMD extends userCMD {

    public inventoryCMD(StagModel gameModel) {
        this.model = gameModel;
    }

    public String executeCMD() {
        ArrayList<gameEntity> inventory = model.getCurrentPlayer().getInventory();
        if (inventory.isEmpty()) return "You inventory is empty";
        return printInventory(inventory);
    }

    private String printInventory(ArrayList<gameEntity> inv) {
        String gameResponse = "";
        for (gameEntity item : inv) {
            gameResponse += (item.getDescription() + "\n");
        }
        return gameResponse;
    }
}
