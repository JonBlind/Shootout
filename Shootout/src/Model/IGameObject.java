package Model;

/**
 * Interface that represents any object located in the game. Any object should have a position
 * on the screen.
 */
public interface IGameObject {

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

}
