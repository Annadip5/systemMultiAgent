package org.multiagent_city.utils;

import org.multiagent_city.nature.Nature;
import org.multiagent_city.nature.nature_elements.*;

public class NatureMap {

    public static Nature[][] generateNatureMap(int width, int height, FastNoiseLite noise, int blurRadius) {
        Nature[][] map = new Nature[height][width];
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                float noiseValue = noise.GetNoise(x, y);
                map[x][y] = buildNatureFromNoise(noiseValue);
            }
        }
        return applyBlur(map, blurRadius);
    }

    private static Nature buildNatureFromNoise(float noiseValue) {
        if (noiseValue < -0.6) return new Water();
        else if (noiseValue < -0.2) return new Grass();
        else if (noiseValue < 0.2) return new Bush();
        else if (noiseValue < 0.6) return new Tree();
        else return new Rock();
    }

    private static Nature[][] applyBlur(Nature[][] map, int radius) {
        int width = map.length;
        int height = map[0].length;
        Nature[][] blurredMap = new Nature[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                blurredMap[i][j] = getBlurredNature(map, i, j, radius);
            }
        }
        return blurredMap;
    }

    private static Nature getBlurredNature(Nature[][] map, int x, int y, int radius) {
        int[] count = new int[5]; // Assuming we have 5 types of Nature
        int total = 0;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                int nx = x + dx;
                int ny = y + dy;

                if (nx >= 0 && nx < map.length && ny >= 0 && ny < map[0].length) {
                    Nature nature = map[nx][ny];
                    int index = getNatureIndex(nature);
                    count[index]++;
                    total++;
                }
            }
        }

        int maxIndex = 0;
        for (int i = 1; i < count.length; i++) {
            if (count[i] > count[maxIndex]) {
                maxIndex = i;
            }
        }

        return createNatureFromIndex(maxIndex);
    }

    private static int getNatureIndex(Nature nature) {
        if (nature instanceof Bush) return 0;
        if (nature instanceof Grass) return 1;
        if (nature instanceof Rock) return 2;
        if (nature instanceof Tree) return 3;
        if (nature instanceof Water) return 4;
        throw new IllegalArgumentException("Unknown Nature type");
    }

    private static Nature createNatureFromIndex(int index) {
        switch (index) {
            case 0:
                return new Bush();
            case 1:
                return new Grass();
            case 2:
                return new Rock();
            case 3:
                return new Tree();
            case 4:
                return new Water();
            default:
                throw new IllegalStateException("Unexpected value: " + index);
        }
    }
}