package Model;

/**
 * Interface that represents an object capable of motion. These objects should have location
 * tracking (given by the GameObject interface), collision based-physics incorporated into their
 * design, and movement capabilities.
 */
public interface IMobileObject extends IGameObject {


  /**
   * Method that represents the update loop for the mobile object. Essentially at every difference
   * of time between each frame (or whenever the code is run), update the objects properties
   * depending on their prior state or interactions since then. Ideally, only objects in motion
   * should be in the update loop since static objects stay static.
   *
   * @param deltaTime Difference in time from the current calculation to the prior.
   */
  void update(double deltaTime);

  /**
   * Method to alter the degree which a Mobile Object is oriented.
   * For Players, this also correlates to the direction of their stick.
   * For the Puck, this is simply the last applied direction of force or the same angle as
   * the player possessing it, if possessed.
   * @param degree degree which the MobileObject is facing.
   */
  public void setAngle(double degree);

  /**
   * Method to get the current angle that the Mobile Object is oriented.
   */
  public double getAngle();

  /**
   * Get the radius value being tracked in the object's movement manager.
   * @return double value representing the radius of this object.
   */
  double getRadius();


  /**
   * Return the series of MobilePoints that this Mobile Object's MovementManager has to track.
   * Represents the points on the perimeter of a mobile object. Every 45-degree angle of a circle,
   * starting from the top, is a mobile point. Top is index zero and goes clockwise.
   * @return array of MobilePoints representing the perimeter of a mobile object
   * respective of the manager's object.
   */
  Position[] getPoints();

}
