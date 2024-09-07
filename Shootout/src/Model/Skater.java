package Model;

import Model.MovementManagers.MovementManagerSkater;

public class Skater extends Player {

  public Skater(String name, TEAM team, double radius) {
    super(name, team, radius);
    this.movementManager = new MovementManagerSkater(0, 0);
    this.angle = 0;
    this.position = new Position(500, 500);
    this.hasPuck = false;
    this.pokeCheck = false;
    this.puck = null;
  }


  /**
   * Method to handle Skater initiating a poke check.
   */
  public void pokeCheck() {

  }

  public void shoot() {}

  /**
   * Method to handle a skater colliding with another skater.
   * @param skater Other skater that this skater is colliding with.
   */
  public void handleSkaterOnSkaterCollision(Skater skater) {

  }

}
