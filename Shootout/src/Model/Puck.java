package Model;

import Model.MovementManagers.MovementManagerPuck;
import java.util.ArrayList;

/**
 * This class represents the mechanics behind the puck, the item the game revolves all around.
 */
public class Puck extends MobileObject {
  private boolean possessed;
  private Player possessor;
  private MovementManagerPuck movementManager;

  /**
   * Basic constructor for a puck using only coordinates.
   * @param position In charge of tracking the puck's coordinates.
   */
  public Puck(Position position) {
    this.radius = GameConfig.PUCK_RADIUS;
    this.position = position;
    this.points = initializePoints();
    this.angle = 0.0;
    this.possessed = false;
    this.possessor = null;
    this.movementManager = new MovementManagerPuck(0, 0);
  }

  /**
   * Constructor for puck if you want to customize size, with specified location.
   * @param radius number of units representing the pucks radius.
   * @param position In charge of tracking the puck's coordinates.
   */
  public Puck(double radius, Position position) {
    this.radius = radius;
    this.position = position;
  }



  @Override
  public void update(double deltaTime) {

  }

  /**
   * Change the possession related fields in this Puck class.
   * @param possessor the Player object that possesses this Puck. Could be null if no one.
   * @param isPossessed Boolean, is this puck possessed? If true, possessor must be an object.
   */
  public void setPossession(Player possessor, boolean isPossessed)
          throws IllegalArgumentException {

    if (possessor != null && isPossessed) {
      this.possessor = possessor;
      this.possessed = isPossessed;
    }
    else if (possessor == null && !isPossessed) {
      this.possessor = possessor;
      this.possessed = isPossessed;
    }
    else throw new IllegalArgumentException("Puck's possessor and IsPossessed conflict!");

  }

}
