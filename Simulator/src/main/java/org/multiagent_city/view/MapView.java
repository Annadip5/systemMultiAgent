package org.multiagent_city.view;

import org.multiagent_city.agents.buildings.TownHall;
import org.multiagent_city.environment.IObserver;
import org.multiagent_city.environment.Map;
import org.multiagent_city.model.MapCellUI;
import org.multiagent_city.utils.Position;

import java.awt.*;

public class MapView implements IObserver {
    private Map map;
    private MapCellUI[][] uiMap;

    public MapView(Map map) {
        this.map = map;
        this.uiMap = new MapCellUI[map.getHeight()][map.getWidth()];
    }

    public Map getMap() {
        return map;
    }

    public MapCellUI[][] getUiMap() {
        return uiMap;
    }

    public void setUiMap(Map map) {
        this.map = map;
    }
    public void setUiMap(MapCellUI[][] uiMap) {
        this.uiMap = uiMap;
    }

    @Override
    public void update(Map map, Position updatedPosition) {
        if(updatedPosition != null) {
            System.out.println("Zones have been updated at " + updatedPosition);
            // Update the specific area
            int positionX = updatedPosition.getX();
            int positionY = updatedPosition.getY();
            this.uiMap[positionX][positionY].setColor(this.getZoneColor(map, positionX, positionY));
            return;
        }
        // Update the all map
        int mapHeight = map.getHeight();
        int mapWidth = map.getWidth();

        // Display the colors
        this.uiMap = new MapCellUI[mapHeight][mapWidth];
        for (int x = 0; x < mapHeight; x++) {
            for (int y = 0; y < mapWidth; y++) {
                this.uiMap[x][y] = new MapCellUI(this.getZoneColor(map, x, y), map.getZones()[x][y].getNature().getTexture());
            }
        }
    }

    private Color getZoneColor(Map map, int x, int y) {
        TownHall townHall = map.getTownHall();
        if (townHall != null && townHall.getPosition().isEqual(x, y)) {
            return townHall.getType().getColor();
        }
        if (map.getZones()[x][y].getInfrastructure() != null) {
            return map.getZones()[x][y].getInfrastructure().getType().getColor();
        }
        return map.getZones()[x][y].getNature().getColor();
    }
}
