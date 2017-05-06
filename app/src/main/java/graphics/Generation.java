package graphics;

import hacktech.youniversity.Coordinate;
import hacktech.youniversity.Gameplay;

/**
 * Created by Derek on 5/5/2017.
 *
 * Creates a tile map for new levels
 */

public class Generation {

    /* Proportion of tiles that are dirt */
    private static final double DIRT_CHANCE = 0.10;

    /* Proportion of tiles that are trees */
    private static final double TREE_CHANCE = 0.15;

    /* Proportion of tiles that are water */
    private static final double WATER_CHANCE = 0.002;

    /* Decreasing chance for lengthening the pond
     * Higher values = smaller ponds */
    private static final double POND_DELTA = 0.45;

    /**
     * Creates a tile map for randomly generated tiles
     *
     * @param width - number of columns
     * @param height - number of rows
     * @return the tile map
     */
    public static Tile[] generate(Gameplay game, int width, int height) {

        // tile types
        int[] types = new int[width * height];

        // list of water tile offsets
        int[] water = new int[width * height];

        // index of next water tile
        int waterIndex = 0;

        // get an outline for each tile type
        for (int i = 0; i < types.length; i++) {

            // randomly generate a percentage
            double num = Math.random();

            // assort based on percentage
            if (num < DIRT_CHANCE) {
                types[i] = Tile.DIRT;
            } else if (num < TREE_CHANCE + DIRT_CHANCE) {
                types[i] = Tile.TREE;
            } else if (num < WATER_CHANCE + TREE_CHANCE + DIRT_CHANCE) {
                types[i] = Tile.WATER;
                water[waterIndex++] = i;
            } else {
                types[i] = Tile.GRASS;
            }

        }

        // smooth over for water
        for (int i = 0; i < water.length; i++) {
            generatePond(types, width, water[i] % width, water[i] / width, 1);
        }

        // the tile map
        Tile[] tiles = new Tile[width * height];

        // initialize the tile map
        for (int i = 0; i < tiles.length; i++) {

            // initialize the tile
            tiles[i] = new Tile(game, new Coordinate(i % width, i / width), types[i]);
        }

        // return the tile array
        return tiles;
    }

    /**
     * Recursively generates a radial pool of water
     *
     * @param tiles - tiles to modify
     * @param width - number of columns
     * @param x - x coord
     * @param y - y coord
     * @param chance - chance of water generation
     */
    private static void generatePond(int tiles[], int width, int x, int y, double chance) {

            // top
            int offset = x + (y - 1) * width;

            // make sure top is valid
            if (offset >=0 && tiles[offset] != Tile.WATER) {

                // generate a new water tile
                if (Math.random() < chance) {
                    tiles[offset] = Tile.WATER;

                    // extend the length in this direction
                    generatePond(tiles, width, x, y-1, chance - POND_DELTA);
                } else {
                    tiles[offset] = Tile.SAND;
                }

            }

        // bottom
        offset = x + (y + 1) * width;

        // make sure bottom is valid
        if (offset < tiles.length && tiles[offset] != Tile.WATER) {

            // generate a new water tile
            if (Math.random() < chance) {
                tiles[offset] = Tile.WATER;

                // extend the length in this direction
                generatePond(tiles, width, x, y+1, chance - POND_DELTA);
            }else {
                tiles[offset] = Tile.SAND;
            }

        }

        // left
        offset = (x - 1) + y * width;

        // make sure left is valid
        if (offset >= 0 && tiles[offset] != Tile.WATER) {

            // generate a new water tile
            if (Math.random() < chance) {
                tiles[offset] = Tile.WATER;

                // extend the length in this direction
               generatePond(tiles, width, x - 1, y, chance - POND_DELTA);
            }else {
                tiles[offset] = Tile.SAND;
            }

        }

        // right
        offset = (x + 1) + y * width;

        // make sure right is valid
        if (offset < tiles.length && tiles[offset] != Tile.WATER) {

            // generate a new water tile
            if (Math.random() < chance) {
                tiles[offset] = Tile.WATER;

                // extend the length in this direction
                generatePond(tiles, width, x + 1, y, chance - POND_DELTA);
            }else {
                tiles[offset] = Tile.SAND;
            }

        }
    }
}
