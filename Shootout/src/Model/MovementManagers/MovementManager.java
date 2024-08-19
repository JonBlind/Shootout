package Model.MovementManagers;

import Model.GameConfig;
import Model.IMobileObject;
import Model.Position;
import Model.Rink;

abstract class MovementManager implements IMovementManageable {
  protected double xVelocity;
  protected double yVelocity;
  protected Rink rink;


  public MovementManager(double initialXVelocity, double initialYVelocity) {
    this.xVelocity = initialXVelocity;
    this.yVelocity = initialYVelocity;
    this.rink = Rink.getRinkInstance(GameConfig.RINK_LENGTH, GameConfig.RINK_HEIGHT);
  }

  /**
   * This is the main update loop that will be present in every MovementManager.
   * This will update the velocity of the movement manager normally,
   * apply friction, apply any other forces, and then clamp if needed.
   * @param currentPosition Position that is currently being inputted to alter.
   * @param deltaTime difference in time since the last calculation.
   * @return Position of the new location the MobileObject will be.
   */
  public abstract Position updateMovement(Position currentPosition, double deltaTime);

  /**
   * Method to establish a velocity "clamp" Essentially, it ensures that the Mobile Objects
   * velocity will not exceed the argument.
   *
   * @param maxVelocity the maximum velocity that the mobile object can undergo. Any attempts to
   *                    update velocity past the maximum or its negation will be voided.
   */
  void clampVelocity(double maxVelocity) {
    xVelocity = Math.max(-maxVelocity, Math.min(maxVelocity, xVelocity));
    yVelocity = Math.max(-maxVelocity, Math.min(maxVelocity, yVelocity));
  }

  /**
   * Method to slowly bring the velocity of an object closer to zero over time if no force is being
   * applied to it.
   *
   * @param deltaTime change in time since the last update. Should be time since last frame.
   */
  protected void applyFriction(double deltaTime) {
    xVelocity *= (1 - GameConfig.FRICTION * deltaTime);
    yVelocity *= (1 - GameConfig.FRICTION * deltaTime);
  }

  /**
   * Method to directly update the velocity tracked in the MovementManager depending on the amount
   * of time that has passed, along with other forces that may be different for each respective
   * object. For example, a Player based class will have its velocity update depend on user inputs.
   * The puck class will depend on the user possessing it or the last applied force.
   * THIS IS THE FIRST STEP OF THE UPDATE CYCLE FOR MOVEMENT MANAGERS.
   *
   * @param deltaTime change in time since the last update. Should be time since last frame.
   */
  protected void updateVelocity(double deltaTime) {
  }


  @Override
  public Position calculateNewPosition(Position currentPosition, double deltaTime) {
    double newX = currentPosition.getXCoord() + xVelocity * deltaTime;
    double newY = currentPosition.getYCoord() + yVelocity * deltaTime;
    return new Position(newX, newY);
  }

  /**
   * Reflect the velocities for an object that collides with the rink.
   * @param position Position at which the object is, if they are past or in contact with the wall,
   *                 they reflect.
   * @return New position that the user is after reflecting, will likely be the location
   * of contact.
   */
  protected Position adjustForBoundaries(Position position) {
    double x = position.getXCoord();
    double y = position.getYCoord();

    // Check for boundary interactions and apply the law of reflection
    if (x < rink.getLeftX() || x > rink.getRightX()) {
      xVelocity = -xVelocity; // Reflect horizontally
      x = Math.max(rink.getLeftX(), Math.min(x, rink.getRightX()));
    }

    if (y < rink.getBottomY() || y > rink.getTopY()) {
      yVelocity = -yVelocity; // Reflect vertically
      y = Math.max(rink.getBottomY(), Math.min(y, rink.getTopY()));
    }

    return new Position(x, y);
  }

  @Override
  public double getXVelocity() {
    return xVelocity;
  }

  @Override
  public double getYVelocity() {
    return yVelocity;
  }

  @Override
  public void setXVelocity(double xVelocity) {
    this.xVelocity = xVelocity;
  }

  @Override
  public void setYVelocity(double yVelocity) {
    this.yVelocity = yVelocity;
  }


}