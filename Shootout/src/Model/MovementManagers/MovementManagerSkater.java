package Model.MovementManagers;

import Model.GameConfig;
import Model.Position;

public class MovementManagerSkater extends MovementManagerPlayer {

  public MovementManagerSkater(double initialXVelocity, double initialYVelocity) {
    super(initialXVelocity, initialYVelocity);
  }

  @Override
  public Position updateMovement(Position currentPosition, double deltaTime) {
    updateVelocity(deltaTime);
    applyFriction(deltaTime);
    applyDamping();
    clampVelocity(GameConfig.SKATER_MAX_VELOCITY);

    Position newPos = calculateNewPosition(currentPosition, deltaTime);
    return reflectOffRink(newPos);
  }

  @Override
  protected void updateVelocity(double deltaTime) {
    if (upPressed) yVelocity += GameConfig.SKATER_ACCELERATION * deltaTime;
    if (downPressed) yVelocity -= GameConfig.SKATER_ACCELERATION * deltaTime;
    if (leftPressed) xVelocity -= GameConfig.SKATER_ACCELERATION * deltaTime;
    if (rightPressed) xVelocity += GameConfig.SKATER_ACCELERATION * deltaTime;
  }

  /**
   * Applies a damping factor to the velocity to simulate gradual reduction in speed
   * when no forces are applied.
   */
  private void applyDamping() {
    xVelocity *= GameConfig.DAMPING;
    yVelocity *= GameConfig.DAMPING;
  }

}
