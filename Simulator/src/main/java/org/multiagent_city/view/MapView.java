package org.multiagent_city.view;

import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.agents.buildings.TownHall;
import org.multiagent_city.environment.IObserver;
import org.multiagent_city.environment.Map;
import org.multiagent_city.model.MapCellUI;
import org.multiagent_city.nature.Nature;
import org.multiagent_city.utils.Position;
import org.multiagent_city.zonestate.InConstructionState;
import org.multiagent_city.zonestate.LockedState;
import org.multiagent_city.zonestate.PruningState;
import org.multiagent_city.zonestate.ZoneState;

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

    public void setMap(Map map) {
        this.map = map;
    }
    public void setUiMap(MapCellUI[][] uiMap) {
        this.uiMap = uiMap;
    }

    @Override
    public void update(Map map, Position updatedPosition) {
        if(updatedPosition != null) {
            //System.out.println("Zones have been updated at " + updatedPosition);
            // Update the specific area
            int positionX = updatedPosition.getX();
            int positionY = updatedPosition.getY();
            this.updateUiMap(map, positionX, positionY);
            return;
        }
        // Update the all map
        int mapHeight = map.getHeight();
        int mapWidth = map.getWidth();

        // Display the colors
        this.uiMap = new MapCellUI[mapHeight][mapWidth];
        for (int x = 0; x < mapHeight; x++) {
            for (int y = 0; y < mapWidth; y++) {
                this.updateUiMap(map, x, y);
            }
        }
    }

    private void updateUiMap(Map map, int x, int y) {
        TownHall townHall = map.getTownHall();
        if (townHall != null && townHall.getPosition().isEqual(x, y)) {
            this.uiMap[x][y] = new MapCellUI(townHall.getType().getColor(), townHall.getType().getTexture());
        }
        Infrastructure infra = map.getZones()[x][y].getInfrastructure();
        if (infra != null) {
            ZoneState zoneState = map.getZones()[x][y].getZoneState();
            // Check the state
            if (zoneState instanceof LockedState || zoneState instanceof PruningState || zoneState instanceof InConstructionState){
                this.uiMap[x][y] = new MapCellUI(infra.getType().getColor(), zoneState.getTexture());
            } else {
                this.uiMap[x][y] = new MapCellUI(infra.getType().getColor(), infra.getType().getTexture());
            }
        } else {
            Nature nature = map.getZones()[x][y].getNature();
            this.uiMap[x][y] = new MapCellUI(nature.getColor(), nature.getTexture());
        }
    }
}
