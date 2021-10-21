package userCommand;
import StagGame.StagModel;
import gameObjects.gameAction;
import gameObjects.gameEntity;
import gameObjects.gameLocation;
import java.util.ArrayList;
import java.util.HashMap;

public class specialCMD extends userCMD {
    gameAction action;

    public specialCMD(StagModel gameModel, gameAction userAction) {
        this.model = gameModel;
        this.action = userAction;
    }

    public String executeCMD() {
        useConsumed();
        if (model.getCurrentPlayer().isDead()) {
            dieAndRespawn();
            return "You have died";
        }
        if (!action.getProduced().isEmpty()) {
            produceEntities(action);
        }
        return action.getNarration();
    }


    public void useConsumed() {
        for (String consumed : action.getConsumed()) {
            if (consumed.equals("health"))  model.getCurrentPlayer().decreaseHealth();
            else {
                model.getCurrentPlayer().removeFromInventory(consumed);
                model.getCurrentLocation().removeEntity(consumed);
            }
        }
    }


    private void produceEntities(gameAction action) {
        HashMap<String, gameLocation> locations = model.getLocationList();
        for (String itemProduced : action.getProduced()) {
            if (itemProduced.equals("health")) {
                model.getCurrentPlayer().increaseHealth();
            }
            else if (!produceRoute(itemProduced)) {
                for (gameLocation location : locations.values()) {
                    produceItem(location.getEntityList(), itemProduced);
                }
            }
        }
    }

    private void produceItem(ArrayList<gameEntity> entitiesInLocation, String itemProduced) {
        for (gameEntity entity : entitiesInLocation) {
            if (entity.getName().equals(itemProduced)) {
                produceEntity(entity);
                entitiesInLocation.remove(entity);
                return;
            }
        }
    }

    private boolean produceRoute(String route) {
        if (model.getGameLocation(route) != null) {
            model.getCurrentLocation().addRoute(route);
            return true;
        }
        return false;
    }

    private void produceEntity(gameEntity entity) {
        if (entity.getType().equals("artefacts")) {
            model.getCurrentPlayer().addToInventory(entity);
        }
        else model.getCurrentLocation().addEntity(entity);
    }

    private void dieAndRespawn() {
        ArrayList<gameEntity> inventory = model.getCurrentPlayer().getInventory();
        int invLength = inventory.size();
        for (int i = 0; i < invLength; i++) {
            drop(inventory.get(0));
        }
        model.setCurrentLocation(model.getStartingLocation());
        model.getCurrentPlayer().resetHealth();
    }

}
