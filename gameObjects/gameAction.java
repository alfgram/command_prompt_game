package gameObjects;
import StagGame.attributeType;

import java.util.ArrayList;

public class gameAction {
    private ArrayList<String> triggers = new ArrayList<>();
    private ArrayList<String> subjects = new ArrayList<>();
    private ArrayList<String> consumed = new ArrayList<>();
    private ArrayList<String> produced = new ArrayList<>();
    private String narration;

    public void addAttribute(attributeType type, String attributeValue) {
        switch (type) {
            case triggers:
                this.triggers.add(attributeValue);
                break;
            case subjects:
                this.subjects.add(attributeValue);
                break;
            case consumed:
                this.consumed.add(attributeValue);
                break;
            case produced:
                this.produced.add(attributeValue);
                break;
            case narration:
                this.narration = attributeValue;
                break;
        }
    }

    public ArrayList<String> getConsumed() {
        return consumed;
    }

    public ArrayList<String> getProduced() {
        return produced;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public ArrayList<String> getTriggers() {
        return triggers;
    }

    public String getNarration() {
        return narration;
    }
}
