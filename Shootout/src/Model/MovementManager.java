package Model;

//TODO: Contemplate the use of 4 boolean fields to track movement keys or
//      some variation of EnumMap.

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

  public MovementManager() {
    this.xVelocity = 0;
    this.yVelocity = 0;
    this.upPressed = false;
    this.downPressed = false;
    this.leftPressed = false;
    this.rightPressed = false;
  }

  public void updateVelocity(double deltaTime, boolean upPressed, boolean downPressed,
                             boolean leftPressed, boolean rightPressed) {
    // Adjust velocities based on input
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

    // Apply friction
    applyFriction(deltaTime);
  }

  private void applyFriction(double deltaTime) {
    xVelocity -= Physics.FRICTION * xVelocity * deltaTime;
    yVelocity -= Physics.FRICTION * yVelocity * deltaTime;
  }

  public double getXVelocity() {
    return xVelocity;
  }

  public double getYVelocity() {
    return yVelocity;
  }
}
