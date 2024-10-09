package Model.MovementManagers;

import Model.Position;
import java.util.ArrayList;

/**
 * Interface representing the set of classes responsible for managing the movement of Mobile
 * Objects. Any class implementing this is a variation of a MovementManager.
 */
public interface IMovementManageable {

  /**
   * Method to return the location of a MobileObject given a Position and using the current state
   * of the Movement Manager's Velocity fields.
   * THIS IS THE LAST STEP OF THE CYCLE
   *
   * @param deltaTime change in time since the last update. Should be time since last frame.
   */
  Position calculateNewPosition(Position currentPosition, double deltaTime);


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
   * Given a list of indexes of mobile points that collide with the rink for its argument. It
   * takes into consideration each colliding points angle and manipulates the velocity accordingly.
   * @param outputs list of indexes corresponding to the mobile points that the
   *               Skater object has colliding with the rink.
   */
  void handleRinkReflection(ArrayList<Integer> outputs);
}
