package Model;

/**
 * Interface that represents an object capable of motion. These objects should have location
 * tracking (given by the GameObject interface), collision based-physics incorporated
 * into their design, and movement capabilities.
 */
public interface IMobileObject extends IGameObject {

  /**
   * Method that represents the update loop for the mobile object. Essentially at every difference
   * of time between each frame (or whenever the code is run), update the objects properties
   * depending on their prior state or interactions since then.
   * Ideally, only objects in motion should be in the update loop since static objects stay static.
   * @param deltaTime Difference in time from the current calculation to the prior.
   */
  void update(double deltaTime);

  /**
   * Method to return the radius of a mobileObject (puck and player), as these are circular
   * objects.
   * @return the radius of the mobileObject.
   */
  double getRadius();

  /**
   * Method to change the radius of a mobileObject (puck and player), as these are circular
   * objects.
   */
  void setRadius(double radius);

}
