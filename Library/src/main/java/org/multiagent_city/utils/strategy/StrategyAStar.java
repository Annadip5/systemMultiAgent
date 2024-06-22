package org.multiagent_city.utils.strategy;

import org.multiagent_city.agents.Building;
import org.multiagent_city.agents.Infrastructure;
import org.multiagent_city.agents.Road;
import org.multiagent_city.agents.buildings.TownHall;
import org.multiagent_city.environment.Map;
import org.multiagent_city.environment.Zone;
import org.multiagent_city.utils.Position;

import java.util.*;

public class StrategyAStar implements IStrategy {

    class Node {
        Position position;
        Node parent;
        double gCost;
        double hCost;
        double fCost;

        public Node(Position position, Node parent, double gCost, double hCost) {
            this.position = position;
            this.parent = parent;
            this.gCost = gCost;
            this.hCost = hCost;
            this.fCost = gCost + hCost;
        }
    }

    @Override
    public Position execute(Map map, Infrastructure infrastructure) {
        Position townHallPosition = map.getTownHall().getPosition();
        PriorityQueue<Position> positionQueue = new PriorityQueue<>(Comparator.comparingDouble(pos -> Position.distance(pos, townHallPosition)));

        if (map.getRoads().isEmpty()) {
            System.out.println("No existing roads found. Starting road construction from Town Hall.");
            Position goal = findConstructibleAdjacentPosition(map, townHallPosition, infrastructure);
            if (goal != null) {
                Position newRoadPosition = findPath(map, infrastructure, townHallPosition, goal);
                if (newRoadPosition != null) {
                    positionQueue.add(newRoadPosition);
                    return newRoadPosition;
                }
            }
        } else {
            for (Road road : map.getRoads()) {
                positionQueue.add(road.getPosition());
            }
        }

        while (!positionQueue.isEmpty()) {
            Position currentPos = positionQueue.poll();
            Position goal = findConstructibleAdjacentPosition(map, currentPos, infrastructure);
            if (goal != null) {
                System.out.println("Start Node: " + currentPos);
                System.out.println("Goal Node: " + goal);
                Position newRoadPosition = findPath(map, infrastructure, currentPos, goal);
                if (newRoadPosition != null && infrastructure.checkBuildRule(map, newRoadPosition)) {
                    positionQueue.add(newRoadPosition);
                    return newRoadPosition;
                }
            }
        }

        System.out.println("No more constructible positions adjacent to any road found.");
        return null;
    }

    private Position findPath(Map map, Infrastructure infrastructure, Position start, Position goal) {
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        Node startNode = new Node(start, null, 0, Position.heuristic(start, goal));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.stream().min(Comparator.comparingDouble(n -> n.fCost)).orElse(null);
            //System.out.println("Current Node: " + currentNode.position);

            if (currentNode.position.isEqual(goal)) {
                //System.out.println("Goal reached at: " + currentNode.position);
                return reconstructPath(currentNode);
            }

            openList.remove(currentNode);
            closedList.add(currentNode);

            for (Position neighborPos : getNeighbors(currentNode.position, map.getZones())) {
                if (isInList(closedList, neighborPos)) {
                    continue;
                }

                double tentativeGCost = currentNode.gCost + Position.distance(currentNode.position, neighborPos);
                Node neighborNode = new Node(neighborPos, currentNode, tentativeGCost, Position.heuristic(neighborPos, goal));

                if (!isInList(openList, neighborPos)) {
                    openList.add(neighborNode);
                    //System.out.println("Added to openList: " + neighborPos);
                } else {
                    Node existingNode = getNodeFromList(openList, neighborPos);
                    if (tentativeGCost < existingNode.gCost) {
                        existingNode.gCost = tentativeGCost;
                        existingNode.fCost = tentativeGCost + existingNode.hCost;
                        existingNode.parent = currentNode;
                        //System.out.println("Updated node in openList: " + neighborPos);
                    }
                }
            }
        }

        return null;
    }

    private List<Position> getNeighbors(Position pos, Zone[][] zones) {
        List<Position> neighbors = new ArrayList<>();
        int x = pos.getX();
        int y = pos.getY();

        if (x > 0) neighbors.add(new Position(x - 1, y));
        if (x < zones.length - 1) neighbors.add(new Position(x + 1, y));
        if (y > 0) neighbors.add(new Position(x, y - 1));
        if (y < zones[0].length - 1) neighbors.add(new Position(x, y + 1));

        return neighbors;
    }

    private boolean isInList(List<Node> list, Position pos) {
        return list.stream().anyMatch(node -> node.position.equals(pos));
    }

    private Node getNodeFromList(List<Node> list, Position pos) {
        return list.stream().filter(node -> node.position.equals(pos)).findFirst().orElse(null);
    }

    private Position findConstructibleAdjacentPosition(Map map, Position start, Infrastructure infrastructure) {
        List<Position> neighbors = getNeighbors(start, map.getZones());
        for (Position neighbor : neighbors) {
            Zone zone = map.getZones()[neighbor.getX()][neighbor.getY()];
            if (zone.getInfrastructure() == null || !(zone.getInfrastructure() instanceof Road)) {
                if (infrastructure.checkBuildRule(map, neighbor)) {
                    return neighbor;
                }
            }
        }
        //System.out.println("No constructible position adjacent to a road found from position: " + start);
        return null;
    }

    private Position reconstructPath(Node currentNode) {
        // Recreate the path by going back through the parent nodes
        List<Position> path = new ArrayList<>();
        while (currentNode != null) {
            path.add(currentNode.position);
            currentNode = currentNode.parent;
        }
        Collections.reverse(path);
        //path.forEach(System.out::println);
        return path.isEmpty() ? null : path.get(path.size() - 1);
    }
    public void checkAndRemoveInaccessibleRoadsAndBuildings(Map map, Infrastructure infrastructure) {
        // Supprimer les routes inaccessibles
        List<Road> roadsToRemove = new ArrayList<>();
        for (Road road : map.getRoads()) {
            if (!isReachable(map, road.getPosition(), map.getTownHall().getPosition())) {
                roadsToRemove.add(road);
            }
        }
        for (Road road : roadsToRemove) {
            map.removeRoad(road);
        }

        // Supprimer les bâtiments inaccessibles
        List<Building> buildingsToRemove = new ArrayList<>();
        for (Building building : map.getBuildings()) {
            if (!hasAccessibleRoad(map, building.getPosition())) {
                buildingsToRemove.add(building);
            }
        }
        for (Building building : buildingsToRemove) {
            map.removeBuilding(building);
        }
    }

    private boolean isReachable(Map map, Position start, Position goal) {
        return findPath(map, null, start, goal) != null;
    }

    private boolean hasAccessibleRoad(Map map, Position buildingPosition) {
        for (Position neighbor : getNeighbors(buildingPosition, map.getZones())) {
            Zone zone = map.getZones()[neighbor.getX()][neighbor.getY()];
            if (zone.getInfrastructure() instanceof Road) {
                if (isReachable(map, neighbor, map.getTownHall().getPosition())) {
                    return true;
                }
            }
        }
        return false;
    }
}
