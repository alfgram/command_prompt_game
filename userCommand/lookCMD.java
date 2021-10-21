package userCommand;
import StagGame.StagModel;

public class lookCMD extends userCMD {

    public lookCMD(StagModel gameModel) {
        this.model = gameModel;
    }

    public String executeCMD() {
        return printDetailsOfLocation();
    }
}
