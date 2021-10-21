package gameObjects;

public class gameEntity {
    private String name;
    private String description;
    private String type;

    public gameEntity(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getType() {
        return this.type;
    }
}
