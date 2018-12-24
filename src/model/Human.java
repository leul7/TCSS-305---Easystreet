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
 * Class for the Human.
 * 
 * @author leul.
 * @version 02 / 02 / 2018.
 */
public class Human extends AbstractVehicle {

    /** The death time of the Human. */
    private static final int DEATH_TIME = 25;
    
    /**
     * Constructor.
     * @param theX X coordinate.
     * @param theY Y coordinate.
     * @param theDir Direction.
     */
    public Human(final int theX, final int theY, 
                 final Direction theDir) {
        super(theX, theY, theDir);
        myDeathTimer = DEATH_TIME;
        myTerrain = new ArrayList<Terrain>();
        myTerrain.add(Terrain.CROSSWALK);
        myTerrain.add(Terrain.GRASS);
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
            if (theTerrain == Terrain.CROSSWALK
                            && theLight == Light.GREEN) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction result = Direction.random();
        result = moveToCross(theNeighbors);
        if (canPass(Terrain.GRASS, Light.YELLOW)) {
            
            if (reverseCheck(theNeighbors)) {
                result = getDirection().reverse();

            } else {
                while (theNeighbors.get(result) != Terrain.GRASS
                       && theNeighbors.get(result) != Terrain.CROSSWALK
                       || result == getDirection().reverse()) {
                    result = Direction.random();
                }
            }
        }

        return result;
    } 
    
    /**
     * Helps chooseDirection by making cross walks a priority.
     * 
     * @param theNeighbors Map containing Direction, Terrain.
     * @return Direction.
     */
    private Direction moveToCross(final Map<Direction, Terrain> theNeighbors) {
        final Direction result = getDirection();
        Direction rand = Direction.random();

        if (theNeighbors.get(result.right()) == Terrain.CROSSWALK) {
            rand = result.right();
        } else if (theNeighbors.get(result.left()) == Terrain.CROSSWALK) {
            rand = result.left();
        } else if (theNeighbors.get(result) == Terrain.CROSSWALK) {
            rand = getDirection();
        } 
        return rand;
    }

    /**
     * Helps choose direction and makes reverse last priority.
     * 
     * @param theNeighbors Map containing Direction, Terrain.
     * @return True if reverse false otherwise.
     */
    private boolean reverseCheck(final Map<Direction, Terrain> theNeighbors) {
        final Direction result = getDirection();
      
        return theNeighbors.get(getDirection()) != Terrain.GRASS
               && theNeighbors.get(result.right()) != Terrain.GRASS
               && theNeighbors.get(result.left()) != Terrain.GRASS
               && theNeighbors.get(result) != Terrain.CROSSWALK
               && theNeighbors.get(result.right()) != Terrain.CROSSWALK
               && theNeighbors.get(result.left()) != Terrain.CROSSWALK;
    }
}
