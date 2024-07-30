package Model;

/**
 * Interface that represents an object capable of motion. These objects should have location
 * tracking (given by the GameObject interface), collision based-physics incorporated
 * into their design, and movement capabilities.
 */
public interface MobileObject extends GameObject {

  /**
   * Obtains the Position coordinates that the object has in its direct fields.
   * @return Position. A set of coordinate points representing X and Y placement respectively.
   */
  Position getPosition();

  /**
   * Modify the position field of the mobile object, intakes a Position class to replace the
   * existing one.
   * @param x   The x-value to which the object's position should be modified to. Double input.
   * @param y   The y-value to which the object's position should be modified to. Double input.
   */
  void setPosition(double x, double y);

  /**
   * Method that represents the update loop for the mobile object. Essentially at every difference
   * of time between each frame (or whenever the code is run), update the objects properties
   * depending on their prior state or interactions since then.
   * Ideally, only objects in motion should be in the update loop since static objects stay static.
   * @param deltaTime Difference in time from the current calculation to the prior.
   */
  void update(double deltaTime);


}
