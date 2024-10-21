package Model.MovementManagers;

import static Model.GameConfig.*;

import Model.GameConfig;
import Model.Player;
import Model.Position;

public abstract class MovementManagerPlayer extends MovementManager {
  protected boolean upPressed;
  protected boolean downPressed;
  protected boolean leftPressed;
  protected boolean rightPressed;

  public MovementManagerPlayer(Position position, double initialXVelocity, double initialYVelocity) {
    super(position, initialXVelocity, initialYVelocity);
    upPressed = false;
    downPressed = false;
    leftPressed = false;
    rightPressed = false;
  }

  public MovementManagerPlayer(double initialXVelocity, double initialYVelocity) {
    super(initialXVelocity, initialYVelocity);
    this.position = new Position(500, 500);
    upPressed = false;
    downPressed = false;
    leftPressed = false;
    rightPressed = false;
  }

  public MovementManagerPlayer() {
    this.position = new Position(500, 500);
    this.radius = SKATER_RADIUS;
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

  /**
   * Method to update the velocity as a result of user inputs.
   * @param deltaTime Change in time since the last update.
   */
  abstract protected void updateVelocityFromUserInput(double deltaTime);
}
