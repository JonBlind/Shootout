package Model;

import Model.MovementManagers.MovementManagerPuck;

/**
 * This class represents the mechanics behind the puck, the item the game revolves all around.
 */
public class Puck implements IMobileObject {
  private double radius;
  private double speed;
  private double angle;
  private Position position;
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
    this.speed = 0.0;
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

  /**
   * Handles the process of a skater shooting the puck. Puck will be un-possessed and the movement
   * manager updates accordingly.
   * @param strength Strength of the shot. MUST BE BETWEEN 0.0 and 5.0.
   */
  public void handleShot(double strength) throws IllegalArgumentException {
    if (strength < 0.0 || strength > 5.0) {
      throw new IllegalArgumentException("Invalid Strength! Must be between 0.0 and 5.0!");
    }
    setPossession(null, false);
    this.movementManager.handleShot(angle, strength);


  }


  @Override
  public void setPosition(double x, double y) {
    position.setXCoord(x);
    position.setYCoord(y);
  }

  @Override
  public Position getPosition() {
    return this.position;
  }

  public double getRadius() {
    return this.radius;
  }

  public void setRadius(double radius) {
    this.radius = radius;
  }

}
