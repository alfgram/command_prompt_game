package StagGame;
import gameObjects.gameAction;
import userCommand.*;
import java.util.ArrayList;
import gameObjects.gameEntity;

public class StagController {
    private StagModel model;

    public StagController(StagModel model) {
        this.model = model;
    }

    public String processCommand(ArrayList<String> command) {
        userCMD playerCommand;
        model.setCurrentPlayer(command.get(0));
        command.remove(0);

        if (command.contains("inventory") || command.contains("inv")) playerCommand = new inventoryCMD(model);
        else if (command.contains("health")) playerCommand = new healthCMD(model);
        else if (command.contains("look")) playerCommand = new lookCMD(model);
        else if (command.contains("goto")) playerCommand = new gotoCMD(model, command);
        else if (command.contains("get")) playerCommand = new getCMD(model, command);
        else if (command.contains("drop")) playerCommand = new dropCMD(model, command);
        else {
            gameAction specialAction = verifySpecialCommand(command);
            if (specialAction != null)  playerCommand = new specialCMD(model, specialAction);
            else return "Invalid command";
        }

        return playerCommand.executeCMD();
    }

    private gameAction verifySpecialCommand(ArrayList<String> userCommand) {
        ArrayList<gameAction> actionList = model.getActionList();
            for (gameAction action : actionList) {
                if (verifyTriggers(action, userCommand) && verifySubjects(action, userCommand)) return action;
            }
        return null;
    }

    private boolean verifyTriggers(gameAction action, ArrayList<String> userCommand) {
        for (String trigger : action.getTriggers()) {
            if (userCommand.contains(trigger)) {
                return true;
            }
        }
        return false;
    }

    private boolean verifySubjects(gameAction action, ArrayList<String> userCommand) {
        ArrayList<String> entities = getEntitiesInLocationAndInv();
        for (String subject : action.getSubjects()) {
            if (userCommand.contains(subject)) return entities.containsAll(action.getSubjects());
        }
        return false;
    }

    private ArrayList<String> getEntitiesInLocationAndInv() {
        ArrayList<String> entitiesInLocationAndInv = new ArrayList<>();
        for (gameEntity entity : model.getCurrentPlayer().getInventory()) {
            entitiesInLocationAndInv.add(entity.getName());
        }
        for (gameEntity entity : model.getCurrentLocation().getEntityList()) {
            entitiesInLocationAndInv.add(entity.getName());
        }
        return entitiesInLocationAndInv;
    }
}
