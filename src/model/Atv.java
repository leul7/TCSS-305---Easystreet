/*
 * Leul Amare 
 * TCSS 305 
 * Assignment 3 - easystreet.
 * 
 */

package model;

import java.util.ArrayList;
import java.util.Map;


/**
 * Class for the Atv.
 * 
 * @author leul.
 * @version 02 / 02 / 2018.
 */

public class Atv extends AbstractVehicle {
    
    /** The death time of the atv. */
    private static final int DEATH_TIME = 15;
    /**
     * Constructor.
     * @param theX X coordinate.
     * @param theY Y coordinate.
     * @param theDir Direction.
     */
    public Atv(final int theX, final int theY, 
                 final Direction theDir) {
        super(theX, theY, theDir);
        myDeathTimer = DEATH_TIME;
        myTerrain = new ArrayList<Terrain>();
        myTerrain.add(Terrain.CROSSWALK);
        myTerrain.add(Terrain.STREET);
        myTerrain.add(Terrain.LIGHT);
        myTerrain.add(Terrain.GRASS);
        myTerrain.add(Terrain.TRAIL);
    }
    
    /**
     * Returns whether or not truck can pass through terrain dependant on light.
     * @param theTerrain Terrain to analyze.
     * @param theLight Whether light is green or red.
     * @return Can vehicle pass. True if so.
     */
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;
        if (myTerrain.contains(theTerrain)) {
            result = true;
        }
        return result;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction result = Direction.random();
        if (canPass(Terrain.STREET, Light.GREEN)) {
            while (theNeighbors.get(result) == Terrain.WALL
                   || result == getDirection().reverse()) {
                result = Direction.random();
            }

        } else {
            result = getDirection().reverse();
        }
        return result;
    }
   
}