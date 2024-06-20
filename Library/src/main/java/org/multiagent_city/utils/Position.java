package org.multiagent_city.utils;

import java.util.ArrayList;

public class Position {
    private int x;
    private int y;

    public Position() {
    }
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean isEqual(int x, int y) {
        return this.x == x && this.y == y;
    }
    public boolean isEqual(Position position) {
        return this.x == position.x && this.y == position.y;
    }
    public boolean isContained(ArrayList<Position> positions){
        for(int i =0; i< positions.size();i++){
            if (positions.get(i).isEqual(this)){
                return true;
            }
        }
        return false;
    }
    private static double heuristic(Position a, Position b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private static double distance(Position a, Position b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
