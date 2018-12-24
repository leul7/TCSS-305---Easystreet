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
 * Class for the Bicycle.
 * 
 * @author leul.
 * @version 02 / 02 / 2018.
 */

public class Bicycle extends AbstractVehicle {
    
    /** The death time of the bicycle. */
    private static final int DEATH_TIME = 20;

    /**
     * Constructor.
     * @param theX X coordinate.
     * @param theY Y coordinate.
     * @param theDir Direction.
     */
    public Bicycle(final int theX, final int theY, 
                 final Direction theDir) {
        super(theX, theY, theDir);
        myDeathTimer = DEATH_TIME;
        myTerrain = new ArrayList<Terrain>();
        myTerrain.add(Terrain.CROSSWALK);
        myTerrain.add(Terrain.LIGHT);
        myTerrain.add(Terrain.STREET);
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
            if ((theTerrain == Terrain.LIGHT
                            || theTerrain == Terrain.CROSSWALK)
                            && theLight != Light.GREEN) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction result = getDirection();

        if (theNeighbors.get(result) == Terrain.STREET
            || theNeighbors.get(result) == Terrain.LIGHT
            || theNeighbors.get(result) == Terrain.CROSSWALK) {

            result = moveToTrail(theNeighbors);

        } else if (theNeighbors.get(result.right()) == Terrain.STREET
                 || theNeighbors.get(result.left()) == Terrain.STREET
                 || theNeighbors.get(result.right()) == Terrain.CROSSWALK
                 || theNeighbors.get(result.left()) == Terrain.CROSSWALK) {
           
            result = result.left();
            
        } else if (theNeighbors.get(result) == Terrain.STREET
                 && theNeighbors.get(result) != Terrain.CROSSWALK) {
            
            result = result.right();

        }

        return result;
    }
    
    /**
     * Helps chooseDirection by making the trails a priority.
     * 
     * @param theNeighbors Map containing Direction, Terrain
     * @return Direction
     * 
     */
    private Direction moveToTrail(final Map<Direction, Terrain> theNeighbors) {
        Direction result = getDirection();

        if (theNeighbors.get(result.right()) == Terrain.TRAIL) {
            result = result.right();

        } else if (theNeighbors.get(result.left()) == Terrain.TRAIL) {
            result = result.left();
        }
        return result;

    }

}
