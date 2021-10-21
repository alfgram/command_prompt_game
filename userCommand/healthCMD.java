package userCommand;

import StagGame.StagModel;


public class healthCMD extends userCMD {

    public healthCMD(StagModel gameModel) {
        this.model = gameModel;
    }

    public String executeCMD() {
        return "your health is " + model.getCurrentPlayer().getHealth();
    }

}
