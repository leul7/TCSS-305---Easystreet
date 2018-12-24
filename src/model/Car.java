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
 * Class for the  Car.
 * 
 * @author leul.
 * @version 02 / 02 / 2018.
 */
public class Car extends AbstractVehicle {
    
    /** The death time of the car. */
    private static final int DEATH_TIME = 5;
    
    /**
     * Constructor.
     * @param theX X coordinate.
     * @param theY Y coordinate.
     * @param theDir Direction.
     */
    public Car(final int theX, final int theY, 
                 final Direction theDir) {
        super(theX, theY, theDir);
        myDeathTimer = DEATH_TIME;
        myTerrain = new ArrayList<Terrain>();
        myTerrain.add(Terrain.CROSSWALK);
        myTerrain.add(Terrain.STREET);
        myTerrain.add(Terrain.LIGHT);
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
            if (theTerrain == Terrain.CROSSWALK
                            && theLight == Light.GREEN) {
                result = true;
            } else if (theTerrain == Terrain.LIGHT
                            && (theLight == Light.GREEN 
                            || theLight == Light.YELLOW)) {
                result = true;
            } else if (theTerrain == Terrain.STREET) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        if ((theNeighbors.get(getDirection()) == Terrain.STREET) 
                        || (theNeighbors.get(getDirection()) == Terrain.LIGHT)) {
            myDirection = getDirection();
        } else if ((theNeighbors.get(getDirection().left()) == Terrain.STREET) 
                        || (theNeighbors.get(getDirection().left()) == Terrain.LIGHT)) {
            myDirection = getDirection().left();
        } else if ((theNeighbors.get(getDirection().right()) == Terrain.STREET) 
                        || (theNeighbors.get(getDirection().right()) == Terrain.LIGHT)) {
            myDirection = getDirection().right();
        } else {
            myDirection = getDirection().reverse();
        }
      
        return myDirection;
    }

}
