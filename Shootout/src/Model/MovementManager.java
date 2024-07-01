package Model;

/**
 * Class responsible for managing the movement of all playable character, whether it be the goalie
 * or the shooter.
 */
public class MovementManager {
  private double xVelocity;
  private double yVelocity;
  private boolean upPressed;
  private boolean downPressed;
  private boolean leftPressed;
  private boolean rightPressed;
  private Player player;

  /**
   * Base constructor for a MovementManager which requires a player to start tracking and updating
   * their movements.
   * @param player player to track and update movements of.
   */
  public MovementManager(Player player) {
    this.xVelocity = 0;
    this.yVelocity = 0;
    this.upPressed = false;
    this.downPressed = false;
    this.leftPressed = false;
    this.rightPressed = false;
    this.player = player;
  }

  /**
   * Alters the state of the player's movement depending on the current state of the
   * player's velocity and user key-presses.
   * @param deltaTime change in time since the last update. Should be time since last frame.
   */
  public void updateMovement(double deltaTime) {
    updateVelocity(deltaTime);
    applyFriction(deltaTime);
    clampVelocity();
    updatePosition(deltaTime);
  }

  private void updateVelocity(double deltaTime) {
    if (upPressed) {
      yVelocity += Physics.ACCELERATION * deltaTime;
    }
    if (downPressed) {
      yVelocity -= Physics.ACCELERATION * deltaTime;
    }
    if (leftPressed) {
      xVelocity -= Physics.ACCELERATION * deltaTime;
    }
    if (rightPressed) {
      xVelocity += Physics.ACCELERATION * deltaTime;
    }
  }

  private void clampVelocity() {
    xVelocity = Math.max(-Physics.MAX_VELO, Math.min(Physics.MAX_VELO, xVelocity));
    yVelocity = Math.max(-Physics.MAX_VELO, Math.min(Physics.MAX_VELO, yVelocity));
  }

  private void applyFriction(double deltaTime) {
    xVelocity -= Physics.FRICTION * xVelocity * deltaTime;
    yVelocity -= Physics.FRICTION * yVelocity * deltaTime;
  }

  private void updatePosition(double deltaTime) {
    double newX = player.getXCoord() + xVelocity * deltaTime;
    double newY = player.getYCoord() + yVelocity * deltaTime;
    player.setPosition(newX, newY);
  }

  /**
   * For a player, will alter the state of pressed keys depending on what movement keys the user
   * is holding down.
   * @param direction the direction matching the associated key pressed.
   * @param pressed   boolean, is this key being pressed or no?
   */
  public void setKeyPressed(Physics.DIRECTION direction, boolean pressed) {
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
   * Returns the current velocity across the x-axis of a player.
   * @return X-velocity.
   */
  public double getXVelocity() {
    return xVelocity;
  }

  /**
   * Returns the current velocity across the y-axis of a player.
   * @return y-velocity.
   */
  public double getYVelocity() {
    return yVelocity;
  }

  /**
   * Alters the X-Velocity of a player to the inputted amount when needed.
   * @param xVelocity double to change player's x-velocity to.
   */
  public void setXVelocity(double xVelocity) {
    this.xVelocity = xVelocity;
  }

  /**
   * Alters the Y-Velocity of a player to the inputted amount when needed.
   * @param yVelocity double to change player's y-velocity to.
   */
  public void setYVelocity(double yVelocity) {
    this.yVelocity = yVelocity;
  }
}
