package Model.MovementManagers;

import Model.GameConfig;
import Model.Player;
import Model.Rink;

import static Model.Physics.MAX_VELO;

public class MovementManagerPlayer extends MovementManager {
  private boolean upPressed;
  private boolean downPressed;
  private boolean leftPressed;
  private boolean rightPressed;

  public MovementManagerPlayer(Player player, Rink rink) {
    super(player, rink);
    upPressed = false;
    downPressed = false;
    leftPressed = false;
    rightPressed = false;
  }

  @Override
  public void updateMovement(double deltaTime) {
    updateVelocity(deltaTime);
    applyFriction(deltaTime);
    clampVelocity(GameConfig.MAX_VELOCITY);
    updatePosition(deltaTime);
  }

  @Override
  public void updateVelocity(double deltaTime) {
    if (upPressed) {
      yVelocity += GameConfig.ACCELERATION * deltaTime;
    }
    if (downPressed) {
      yVelocity -= GameConfig.ACCELERATION * deltaTime;
    }
    if (leftPressed) {
      xVelocity -= GameConfig.ACCELERATION * deltaTime;
    }
    if (rightPressed) {
      xVelocity += GameConfig.ACCELERATION * deltaTime;
    }
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
