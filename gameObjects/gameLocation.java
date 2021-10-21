package gameObjects;
import java.util.ArrayList;

public class gameLocation {
    private String description;
    private ArrayList<gameEntity> entityList = new ArrayList<>();
    private ArrayList<String> routes = new ArrayList<>();

    public gameLocation(String description) {
        this.description = description;
    }

    public void addEntity(gameEntity newEntity) {
        entityList.add(newEntity);
    }

    public void removeEntity(String entityName) {
        entityList.removeIf(gameEntity -> gameEntity.getName().equals(entityName));
    }

    public ArrayList<String> getRoutes() {
        return routes;
    }

    public String getDescription() {
        return description;
    }

    public void addRoute(String route) {
        routes.add(route);
    }

    public ArrayList<gameEntity> getEntityList() {
        return entityList;
    }

}
