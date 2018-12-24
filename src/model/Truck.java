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
 * Class for the Truck.
 * 
 * @author leul.
 * @version 02 / 02 / 2018.
 */
public class Truck extends AbstractVehicle {

    /** The death time of the Truck. */
    private static final int DEATH_TIME = 0;
    
    /**
     * Constructor.
     * @param theX X coordinate.
     * @param theY Y coordinate.
     * @param theDir Direction.
     */
    public Truck(final int theX, final int theY, 
                 final Direction theDir) {
        super(theX, theY, theDir);
        myDeathTimer = DEATH_TIME;
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
            if (theTerrain.equals(Terrain.CROSSWALK)
                             && (theLight == Light.RED)) {
                //If at crosswalk and light is red, STOP!
                result = false;
            } else {
                result = true;
            }
        }
        return result;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction resultDir = Direction.random();
        if (canPass(Terrain.STREET, Light.GREEN)) {

            if (truckReverse(theNeighbors)) {
                resultDir = getDirection().reverse();
                
            } else {
                while (theNeighbors.get(resultDir) != Terrain.STREET
                       && theNeighbors.get(resultDir) != Terrain.CROSSWALK
                       && theNeighbors.get(resultDir) != Terrain.LIGHT
                       || resultDir == getDirection().reverse()) {
                    resultDir = Direction.random();
                }
            }
        }

        return resultDir;

    }
    /**
     * Helper method to help truck reverse due to 
     * cyclomatic complexity warnings in chooseDirection.
     * 
     * @param theNeighbors Neighboring Direction,Terrain.
     * @return Whether or not truck can reverse;
     */
    private boolean truckReverse(final Map<Direction, Terrain> theNeighbors) {
        final Direction curDir = getDirection();
        //Checks to make sure it can't go other directions first via terrain.
        return theNeighbors.get(getDirection()) != Terrain.STREET
                        && theNeighbors.get(curDir) != Terrain.LIGHT
                        && theNeighbors.get(curDir.right()) != Terrain.LIGHT
                        && theNeighbors.get(curDir.left()) != Terrain.LIGHT
                        && theNeighbors.get(curDir) != Terrain.CROSSWALK
                        && theNeighbors.get(curDir.right()) != Terrain.CROSSWALK
                        && theNeighbors.get(curDir.left()) != Terrain.CROSSWALK
                        && (theNeighbors.get(curDir.right()) != Terrain.STREET)
                        && (theNeighbors.get(curDir.left()) != Terrain.STREET);
    }

}
