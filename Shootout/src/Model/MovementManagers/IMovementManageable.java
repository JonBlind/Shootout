package Model.MovementManagers;

import Model.Position;
import java.util.ArrayList;

/**
 * Interface representing the set of classes responsible for managing the movement of Mobile
 * Objects. Any class implementing this is a variation of a MovementManager.
 */
public interface IMovementManageable {

  /**
   * Method to calculate and change the location of a MobileObject given the time passed since the
   * last update.
   * This will also calculate and update the location of all the mobilePoints tracking the object's
   * perimeter.
   * THIS IS THE LAST STEP OF THE CYCLE
   *
   * @param deltaTime change in time since the last update. Should be time since last frame.
   */
  void calculateNewPosition(double deltaTime);


  public Position[] initializePoints();


  /**
   * Given a list of indexes of mobile points that collide with the rink for its argument. It
   * takes into consideration each colliding points angle and manipulates the velocity accordingly.
   * @param outputs list of indexes corresponding to the mobile points that the
   *               Skater object has colliding with the rink.
   */
  void handleRinkAndNetReflection(ArrayList<Integer> outputs);


  /**
   * Returns the current velocity across the x-axis tracked in this MovementManager.
   * @return X-velocity.
   */
  double getXVelocity();

  /**
   * Returns the current velocity across the y-axis tracked in this MovementManager.
   * @return y-velocity.
   */
  double getYVelocity();

  /**
   * Alters the X-Velocity of a mobile object to the inputted amount when needed.
   * @param xVelocity double to change player's x-velocity to.
   */
  void setXVelocity(double xVelocity);

  /**
   * Alters the Y-Velocity of a mobile object to the inputted amount when needed.
   * @param yVelocity double to change player's y-velocity to.
   */
  void setYVelocity(double yVelocity);


  /**
   * Modify the position field of the game object, intakes a Position class to replace the
   * existing one.
   * @param x   The x-value to which the object's position should be modified to. Double input.
   * @param y   The y-value to which the object's position should be modified to. Double input.
   */
  void setPosition(double x, double y);

  /**
   * Get the x,y coordinates of a GameObject.
   * @return Position(x,y coordinates) of object.
   */
  Position getPosition();

  /**
   * Return the series of MobilePoints that this MovementManager has to track. Represents the
   * points on the perimeter of a mobile object. Every 45-degree angle of a circle, starting from
   * the top, is a mobile point. Top is index zero and goes clockwise.
   * @return array of MobilePoints representing the perimeter of a mobile object
   * respective of the manager's object.
   */
  Position[] getPoints();

  /**
   * Method to return the radius of a mobileObject (puck and player), as these are circular
   * objects.
   *
   * @return the radius of the mobileObject.
   */
  double getRadius();

  /**
   * Method to change the radius of a mobileObject (puck and player), as these are circular
   * objects.
   */
  void setRadius(double radius);
}
