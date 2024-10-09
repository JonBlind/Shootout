package Model.MovementManagers;

import Model.GameConfig;
import Model.Position;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
    return null;
  }

  @Override
  public void handleRinkReflection(ArrayList<Integer> outputs) {
    double xReflectFactor = 0;
    double yReflectFactor = 0;
    for (Integer output : outputs) {
      switch (output) {
        case 0:
          yReflectFactor -= 1.0;
          break;
        case 1:
          xReflectFactor -= Math.sqrt(2) / 2;
          yReflectFactor -= Math.sqrt(2) / 2;
          break;
        case 2:
          xReflectFactor -= 1.0;
          break;
        case 3:
          xReflectFactor -= Math.sqrt(2) / 2;
          yReflectFactor += Math.sqrt(2) / 2;
        case 4:
          yReflectFactor += 1.0;
        case 5:
          xReflectFactor += Math.sqrt(2) / 2;
          yReflectFactor += Math.sqrt(2) / 2;
        case 6:
          xReflectFactor += 1.0;
        case 7:
          xReflectFactor += Math.sqrt(2) / 2;
          yReflectFactor -= Math.sqrt(2) / 2;
        default:
          throw new IllegalArgumentException("List of MobileObject Colliding Mobile Points"
              + "is Out Of Possible Range.");
      }
      double magnitude =
          Math.sqrt(xReflectFactor * xReflectFactor + yReflectFactor * yReflectFactor);

      xReflectFactor /= magnitude;
      yReflectFactor /= magnitude;

      xVelocity = xVelocity * xReflectFactor;
      yVelocity = yVelocity * yReflectFactor;
    }
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
