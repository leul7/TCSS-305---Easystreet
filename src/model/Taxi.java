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
public class Taxi extends AbstractVehicle {
    
    /** The death time of the Taxi. */
    private static final int DEATH_TIME = 10;
    
    /** Constant 3 for clock counter during cross walk. */
    private static final int MOVE_CLOCK = 3;
    
    /** Clock counter for movement during cross walk red light. */
    private int myClockCounter;
    /**
     * Constructor.
     * @param theX X coordinate.
     * @param theY Y coordinate.
     * @param theDir Direction.
     */
    public Taxi(final int theX, final int theY, 
                 final Direction theDir) {
        super(theX, theY, theDir);
        myDeathTimer = DEATH_TIME;
        myClockCounter = 0;
        myTerrain = new ArrayList<Terrain>();
        myTerrain.add(Terrain.CROSSWALK);
        myTerrain.add(Terrain.STREET);
        myTerrain.add(Terrain.LIGHT);
    }
    
    /**
     * Returns whether or not truck can pass through terrain dependent on light.
     * @param theTerrain Terrain to analyze.
     * @param theLight Whether light is green or red.
     * @return Can vehicle pass. True if so.
     */
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;
        if (myTerrain.contains(theTerrain)) {
            if (theTerrain == Terrain.CROSSWALK
                            && theLight == Light.GREEN) {
              //If cross walk and green
                result = true;
            } else if (theTerrain == Terrain.LIGHT
                            && theLight != Light.RED) {
                //If at light and its green or yellow
                result = true;
            } else if (theTerrain == Terrain.STREET) {
                //if on street
                result = true;
            } else if (theTerrain == Terrain.CROSSWALK
                            && theLight == Light.RED) {
                //If at cross walk red light move clock begins or checks
                if (myClockCounter == MOVE_CLOCK) {
                    result = true; //GO!
                    myClockCounter = 0; //reset clock
                } else {
                    //Increment until it reaches 3
                    myClockCounter++; 
                }
            } 
        }
        return result;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        //FIX - Taxi is turning around instead of going left near cross walk yellows
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
