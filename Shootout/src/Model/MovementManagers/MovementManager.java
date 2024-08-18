package Model.MovementManagers;

import Model.GameConfig;
import Model.IMobileObject;
import Model.Rink;

abstract class MovementManager implements IMovementManageable {
  protected double xVelocity;
  protected double yVelocity;
  protected Rink rink;
  protected IMobileObject mobileObject;

  public MovementManager(IMobileObject mobileObject, Rink rink) {
    this.mobileObject = mobileObject;
    this.rink = rink;
    this.xVelocity = 0;
    this.yVelocity = 0;
  }

  @Override
  public abstract void updateMovement(double deltaTime);

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
   * Method to directly update the velocity of a MobileObject depending on the amount of time that
   * has passed, along with other forces that may be different for each respective object.
   * For example, a Player based class will have its velocity update depend on user inputs.
   * The puck class will depend on the user possessing it or the last applied force.
   *
   * @param deltaTime change in time since the last update. Should be time since last frame.
   */
  protected void updateVelocity(double deltaTime) {
  }


  /**
   * Method to directly update the location of the designated MobileObject of the
   * MovementManager. All MovementManagers have a field for a IMobileObject.
   *
   * @param deltaTime change in time since the last update. Should be time since last frame.
   */
  public void updatePosition(double deltaTime) {
    double newX = mobileObject.getPosition().getXCoord() + xVelocity * deltaTime;
    double newY = mobileObject.getPosition().getYCoord() + yVelocity * deltaTime;
    mobileObject.setPosition(newX, newY);
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