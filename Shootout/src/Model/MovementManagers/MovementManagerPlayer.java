package Model.MovementManagers;

import Model.GameConfig;
import Model.Player;
import Model.Position;

public abstract class MovementManagerPlayer extends MovementManager {
  protected boolean upPressed;
  protected boolean downPressed;
  protected boolean leftPressed;
  protected boolean rightPressed;

  public MovementManagerPlayer(double initialXVelocity, double initialYVelocity) {
    super(initialXVelocity, initialYVelocity);
    upPressed = false;
    downPressed = false;
    leftPressed = false;
    rightPressed = false;
  }

  /**
   * For a player, will alter the state of pressed keys depending on what movement keys the user
   * is holding down.
   * @param direction the direction matching the associated key pressed.
   * @param pressed   boolean, is this key being pressed or no?
   */
  public void setKeyPressed(GameConfig.DIRECTION direction, boolean pressed) {
    switch (direction) {
      case UP:
        upPressed = pressed;
        break;
      case DOWN:
        downPressed = pressed;
        break;
      case LEFT:
        leftPressed = pressed;
        break;
      case RIGHT:
        rightPressed = pressed;
        break;
    }
  }
}
