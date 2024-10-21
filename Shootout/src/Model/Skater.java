package Model;

import Model.MovementManagers.MovementManagerSkater;
import java.util.ArrayList;
import Model.Rink;

public class Skater extends Player {

  public Skater(String name, TEAM team) {
    super(name, team);
    this.movementManager = new MovementManagerSkater(0, 0);
    this.angle = 0;
    this.hasPuck = false;
    this.pokeCheck = false;
    this.puck = null;
  }

  public Skater() {
    this.name = "Artemi";
    this.team = TEAM.BLUE;
    this.movementManager = new MovementManagerSkater(0, 0);
    this.angle = 0;
    this.hasPuck = false;
    this.pokeCheck = false;
    this.puck = null;
  }


  @Override
  public void update(double deltaTime) {
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
