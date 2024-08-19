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
    clampVelocity(GameConfig.SKATER_MAX_VELOCITY);
    return calculateNewPosition(currentPosition, deltaTime);
  }

  @Override
  protected void updateVelocity(double deltaTime) {
    if (upPressed) yVelocity += GameConfig.SKATER_ACCELERATION * deltaTime;
    if (downPressed) yVelocity -= GameConfig.SKATER_ACCELERATION * deltaTime;
    if (leftPressed) xVelocity -= GameConfig.SKATER_ACCELERATION * deltaTime;
    if (rightPressed) xVelocity += GameConfig.SKATER_ACCELERATION * deltaTime;
  }
}
