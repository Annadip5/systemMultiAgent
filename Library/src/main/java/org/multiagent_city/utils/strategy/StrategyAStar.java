package org.multiagent_city.utils.strategy;

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
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        /*A MODIFIER*/
        Position start = map.getTownHall().getPosition();
        Position goal = findConstructibleAdjacentPosition(map, start, infrastructure);
        if (goal == null) {
            System.out.println("No constructible position adjacent to a road found.");
            return null;
        }
        Node startNode = new Node(start, null, 0, Position.heuristic(start, goal));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            // Trouver le nœud avec le coût total le plus bas
            Node currentNode = openList.stream().min(Comparator.comparingDouble(n -> n.fCost)).orElse(null);

            // Position finale atteinte
            if (currentNode.position.equals(goal)) {
                return reconstructPath(currentNode);
            }

            // Déplacer le nœud courant de la liste ouverte à la liste fermée
            openList.remove(currentNode);
            closedList.add(currentNode);

            // Générer les nœuds voisins
            for (Position neighborPos : getNeighbors(currentNode.position, map.getZones())) {
                if (isInList(closedList, neighborPos)) {
                    continue;  // Ignorez les voisins déjà explorés
                }

                double tentativeGCost = currentNode.gCost + Position.distance(currentNode.position, neighborPos);
                Node neighborNode = new Node(neighborPos, currentNode, tentativeGCost, Position.heuristic(neighborPos, goal));

                if (!isInList(openList, neighborPos)) {
                    openList.add(neighborNode);
                } else {
                    Node existingNode = getNodeFromList(openList, neighborPos);
                    if (tentativeGCost < existingNode.gCost) {
                        existingNode.gCost = tentativeGCost;
                        existingNode.fCost = tentativeGCost + existingNode.hCost;
                        existingNode.parent = currentNode;
                    }
                }
            }
        }
        return null; // Si aucun chemin n'a été trouvé
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
        if (map.getZones()[start.getX()][start.getY()].getInfrastructure() instanceof Road
                || map.getZones()[start.getX()][start.getY()].getInfrastructure() instanceof TownHall) {
            for (Position neighbor : getNeighbors(start, map.getZones())) {
                if (infrastructure.checkBuildRule(map, neighbor)) {
                    return neighbor;
                }
            }
        }
        return null;
    }

    private Position reconstructPath(Node currentNode) {
        // Recréez le chemin en remontant les parents des nœuds
        List<Position> path = new ArrayList<>();
        while (currentNode != null) {
            path.add(currentNode.position);
            currentNode = currentNode.parent;
        }
        Collections.reverse(path);
        path.forEach(System.out::println);
        return path.isEmpty() ? null : path.get(path.size() - 1);
    }
}
