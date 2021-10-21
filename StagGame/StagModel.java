package StagGame;
import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;

import gameObjects.gameEntity;
import gameObjects.gameLocation;
import gameObjects.gamePlayer;
import gameObjects.gameAction;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class StagModel {
    private String currentPlayer;
    private String startingLocation;
    private HashMap<String, gameLocation> locationList = new HashMap<>();
    private HashMap<String, gamePlayer> playersList = new HashMap<>();
    private ArrayList<gameAction> actionList = new ArrayList<>();

    public StagModel(String entityFilename, String actionFilename) throws IOException, ParseException {
        loadEntityFile(entityFilename);
        loadActionFile(actionFilename);
    }

    public void setCurrentPlayer(String playerName) {
        if (playersList.get(playerName) != null) {
            this.currentPlayer = playerName;
            return;
        }
        addNewPlayer(playerName);
    }

    public void addNewPlayer(String newPlayerName) {
        currentPlayer = newPlayerName;
        gamePlayer newPlayer = new gamePlayer(startingLocation);
        playersList.put(newPlayerName, newPlayer);
        gameEntity playerEntity = new gameEntity(newPlayerName, newPlayerName + " is here", newPlayerName);
        getCurrentLocation().addEntity(playerEntity);
    }

    public gamePlayer getCurrentPlayer() {
        return playersList.get(currentPlayer);
    }

    private void loadEntityFile(String entityFilename) {
        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader(entityFilename);
            parser.parse(reader);
            ArrayList<Graph> graphs = parser.getGraphs();
            ArrayList<Graph> subGraphs = graphs.get(0).getSubgraphs();
            for (Graph g : subGraphs) {
                ArrayList<Graph> subGraphs1 = g.getSubgraphs();
                loadInEntities(subGraphs1);
                loadInRoutes(g);
            }
        } catch (FileNotFoundException | com.alexmerz.graphviz.ParseException fnfe) {
            System.out.println(fnfe);
        }
    }

    private void loadInEntities(ArrayList<Graph> graphs) {
        boolean isFirst = true;
        for (Graph g1 : graphs) {
            ArrayList<Node> nodesLoc = g1.getNodes(false);
            Node nLoc = nodesLoc.get(0);
            if (isFirst) {
                startingLocation = nLoc.getId().getId();
                isFirst = false;
            }
            gameLocation newLocation = new gameLocation(nLoc.getAttribute("description"));
            ArrayList<Graph> subGraphs2 = g1.getSubgraphs();
            for (Graph g2 : subGraphs2) {
                String type = g2.getId().getId();
                ArrayList<Node> nodesEnt = g2.getNodes(false);
                for (Node nEnt : nodesEnt) {
                    gameEntity newEntity = new gameEntity(nEnt.getId().getId(), nEnt.getAttribute("description"), type);
                    newLocation.addEntity(newEntity);
                }
            }
            locationList.put(nLoc.getId().getId(), newLocation);
        }
    }

    private void loadInRoutes(Graph g) {
        ArrayList<Edge> edges = g.getEdges();
        for (Edge e : edges) {
            String routeLocationIsFrom = e.getSource().getNode().getId().getId();
            String routeLocationIsTo = e.getTarget().getNode().getId().getId();
            this.getGameLocation(routeLocationIsFrom).addRoute(routeLocationIsTo);
        }
    }

    public String getCurrentPlayerName() {
        return currentPlayer;
    }

    private void loadActionFile(String actionFilename) throws IOException, ParseException {
        FileReader reader = new FileReader(actionFilename);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        JSONArray actionArray = (JSONArray) jsonObject.get("actions");
        for (Object o : actionArray) {
            gameAction newAction = new gameAction();
            JSONObject actionSet = (JSONObject) o;
            for (attributeType type: attributeType.values()) {
                addAttributes(type, actionSet, newAction);
            }
            actionList.add(newAction);
        }
    }

    private void addAttributes(attributeType type, JSONObject actionToBeAdded, gameAction newAction) {
        Object attribute = actionToBeAdded.get(type.name());
        /* if single element */
        if (attribute instanceof String) {
            newAction.addAttribute(type, (String) attribute);
        /* if json array */
        } else {
            JSONArray actionArray = (JSONArray) attribute;
            for (Object s : actionArray) {
                newAction.addAttribute(type, (String) s);
            }
        }
    }

    public HashMap<String, gameLocation> getLocationList() {
        return locationList;
    }

    public gameLocation getGameLocation(String locationName) {
        return locationList.get(locationName);
    }

    public ArrayList<gameAction> getActionList() {
        return actionList;
    }

    public String getStartingLocation() {
        return startingLocation;
    }

    private gameEntity getCurrentPlayerEntity() {
        for (gameEntity entity: getCurrentLocation().getEntityList()) {
            if (entity.getName().equals(currentPlayer)) return entity;
        }
        return null;
    }

    public void setCurrentLocation(String location) {
        gameEntity playerEntityToMove = getCurrentPlayerEntity();
        getGameLocation(location).addEntity(playerEntityToMove);
        getCurrentLocation().removeEntity(currentPlayer);
        getCurrentPlayer().setCurrentLocation(location);
    }
    public gameLocation getCurrentLocation() {
        return getGameLocation(getCurrentPlayer().getCurrentLocation());
    }
}
