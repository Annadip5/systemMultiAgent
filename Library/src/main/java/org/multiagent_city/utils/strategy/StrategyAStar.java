package org.multiagent_city.utils.strategy;

import org.multiagent_city.agents.Infrastructure;
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
    public void execute(Map map, Infrastructure infrastructure ) {
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        /*A MODIFIER*/
        Position start = map.getTownHall().getPosition();
        Position goal = map.getTownHall().getPosition();/*position qui n est pas deja construit et qui peut l'être*/;

        Node startNode = new Node(start, null, 0, heuristic(start, goal));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            // Trouver le nœud avec le coût total le plus bas
            Node currentNode = openList.stream().min(Comparator.comparingDouble(n -> n.fCost)).orElse(null);

            if (currentNode == null) {
                break;
            }

            // Si nous avons atteint la position du goal, terminez
            if (currentNode.position.equals(goal)) {
                reconstructPath(currentNode);
                return;
            }

            // Déplacer le nœud courant de la liste ouverte à la liste fermée
            openList.remove(currentNode);
            closedList.add(currentNode);

            // Générer les nœuds voisins
            for (Position neighborPos : getNeighbors(currentNode.position, map.getZones())) {
                if (isInList(closedList, neighborPos)) {
                    continue;  // Ignorez les voisins déjà explorés
                }

                double tentativeGCost = currentNode.gCost + distance(currentNode.position, neighborPos);
                Node neighborNode = new Node(neighborPos, currentNode, tentativeGCost, heuristic(neighborPos, goal));

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
    }

    private double heuristic(Position a, Position b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private double distance(Position a, Position b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private List<Position> getNeighbors(Position pos, Zone[][] zones) {
        List<Position> neighbors = new ArrayList<>();
        // Ajoutez la logique pour obtenir les voisins d'une position donnée
        return neighbors;
    }

    private boolean isInList(List<Node> list, Position pos) {
        return list.stream().anyMatch(node -> node.position.equals(pos));
    }

    private Node getNodeFromList(List<Node> list, Position pos) {
        return list.stream().filter(node -> node.position.equals(pos)).findFirst().orElse(null);
    }

    private void reconstructPath(Node currentNode) {
        // Recréez le chemin en remontant les parents des nœuds
        List<Position> path = new ArrayList<>();
        while (currentNode != null) {
            path.add(currentNode.position);
            currentNode = currentNode.parent;
        }
        Collections.reverse(path);
        // Afficher ou utiliser le chemin
        path.forEach(System.out::println);
    }
}
