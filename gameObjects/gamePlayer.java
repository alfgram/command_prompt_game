package gameObjects;
import java.util.ArrayList;

public class gamePlayer {
    private ArrayList<gameEntity> inventory = new ArrayList<>();
    private static final int MAX_HEALTH = 3;
    private int health = MAX_HEALTH;
    private String currentLocation;

    public gamePlayer(String startingLocation) {
        currentLocation = startingLocation;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void resetHealth() {
        health = MAX_HEALTH;
    }

    public void addToInventory(gameEntity entity) {
        inventory.add(entity);
    }

    public void removeFromInventory(String entityName) {
        inventory.removeIf(gameEntity -> gameEntity.getName().equals(entityName));
    }

    public int getHealth() {
        return health;
    }

    public void decreaseHealth() {
        health--;
    }

    public void increaseHealth() {
        health++;
    }

    public boolean isDead() {
        return (health <= 0);
    }

    public ArrayList<gameEntity> getInventory() {
        return inventory;
    }
}
