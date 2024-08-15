package Model;

/**
 * Interface that represents any object located in the game. Any object should have a position
 * on the screen.
 */
public interface IGameObject {

  /**
   * Sets the objects position.
   * @param position x and y doubles that represent position on the screen.
   */
  void setPosition(Position position);

  /**
   * Get the x,y coordinates of a GameObject.
   * @return Position(x,y coordinates) of object.
   */
  Position getPosition();

}
