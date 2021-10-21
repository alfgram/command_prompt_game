package userCommand;
import StagGame.StagModel;
import gameObjects.gameEntity;

import java.util.ArrayList;

public abstract class userCMD {
    StagModel model;
    ArrayList<String> command;

    public userCMD() {
    }

    public String executeCMD() {
        return "";
    }

    public void drop(gameEntity item) {
        model.getCurrentLocation().addEntity(item);
        model.getCurrentPlayer().removeFromInventory(item.getName());
    }

    public String printDetailsOfLocation() {
        String gameResponse = "You are in " + model.getCurrentLocation().getDescription() + "\n";
        gameResponse += "You can see:\n" + printItemsInLocation();
        gameResponse += "You can access from here:\n" + printRoutesInLocation();
        return gameResponse;
    }

    public String printItemsInLocation() {
        String itemsToBePrinted = "";
        ArrayList<gameEntity> itemsInLocation = model.getCurrentLocation().getEntityList();
        for (gameEntity item : itemsInLocation) {
            if (!item.getName().equals(model.getCurrentPlayerName())) {
                itemsToBePrinted += (item.getDescription() + "\n");
            }
        }
        return itemsToBePrinted;
    }

    public String printRoutesInLocation() {
        String routesToBePrinted = "";
        for (String route : model.getCurrentLocation().getRoutes()) {
            routesToBePrinted += (route + "\n");
        }
        return routesToBePrinted;
    }

}

