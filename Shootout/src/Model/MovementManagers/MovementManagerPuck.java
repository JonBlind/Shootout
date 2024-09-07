package Model.MovementManagers;

import Model.GameConfig;
import Model.Net;
import Model.Position;

/**
 * Class that represents and handles the movement of the Puck class.
 */
public class MovementManagerPuck extends MovementManager {
  private double angle;

  /**
   * Constructor to initialize the movement manager for a Puck.
   * @param initialXVelo initial x velocity that the Puck spawns with.
   * @param initialYVelo initial y velocity that the Puck spawns with.
   */
  public MovementManagerPuck(double initialXVelo, double initialYVelo) {
    super(initialXVelo, initialYVelo);
    this.angle = 0;
  }

  @Override
  public Position updateMovement(Position currentPosition, double deltaTime) {
    applyFriction(deltaTime);
    clampVelocity(GameConfig.PUCK_MAX_VELOCITY);
    Position updatedPos = calculateNewPosition(currentPosition, deltaTime);
    return reflectOffRink(updatedPos);
  }


  /**
   * Set the velocity for the puck after the shot.
   * The velocities will scale depending on the angle and strength of the shot.
   * @param angle The angle, in degrees, at which the puck was shot from.
   * @param strength The Strength of the shot. Decides how much more velocity the puck has.
   *                 Strength is done on a scale of 1 - 5.
   */
  public void handleShot(double angle, double strength) throws IllegalArgumentException {
    if (strength < 0.0 ||strength > 5.0) {
      throw new IllegalArgumentException("Strength should be less than 5.0!");
    }
    double force = strength * GameConfig.SHOT_STRENGTH_FACTOR;
    double xFactor = force * Math.cos(angle);
    double yFactor = force * Math.sin(angle);

    this.angle = angle;
    this.xVelocity += xFactor;
    this.yVelocity += yFactor;

    clampVelocity(GameConfig.PUCK_MAX_VELOCITY);
  }

  /**
   * Alter the movement of the puck as a result of being poke checked.
   * @param angle The angle in degrees from which the stick that poke checked the puck came from.
   */
  public void handlePokeCheck(double angle) {

    double force = GameConfig.SHOT_STRENGTH_FACTOR;
    double xFactor = force * Math.cos(angle);
    double yFactor = force * Math.sin(angle);

    this.angle = angle;
    this.xVelocity += xFactor;
    this.yVelocity += yFactor;

    clampVelocity(GameConfig.PUCK_MAX_VELOCITY);
  }

  /**
   * Reflect the velocities for a puck that collides with the net.
   * @param position Position at which the object is, if they are past or in contact with the side,
   *                 back, or post of the net, they reflect.
   * @return New position that the mobile object is after reflecting, will likely be the location
   * of contact.
   */
  protected Position reflectOffNet(Position position) {

  }

  /**
   * Set the angle of this puck. (Degrees).
   * @param angle the double to represent this puck's new angle.
   */
  public void setAngle(double angle) {
    this.angle = angle;
  }

  /**
   * Retrieve the angle of this puck. (Degrees).
   * @return double representing the angle of this puck.
   */
  public double getAngle() {
    return angle;
  }
}
