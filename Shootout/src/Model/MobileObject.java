package Model;

import Model.MovementManagers.IMovementManageable;
import java.util.ArrayList;

public abstract class MobileObject implements IMobileObject {

  protected double angle;
  protected IMovementManageable movementManager;

  //Likely planning on skipping the use of a constructor since each subclass will utilize
  //its own unique initialization. Up for change/debate.

  @Override
  public double getAngle() {
    return this.angle;
  }

  @Override
  public void setAngle(double degree) {
    this.angle = degree;
  }

  @Override
  public Position getPosition() {
    return this.movementManager.getPosition();
  }

  @Override
  public void setPosition(double x, double y) {
    this.movementManager.setPosition(x, y);
  }

  @Override
  public double getRadius() {
    return this.movementManager.getRadius();
  }

  @Override
  public Position[] getPoints() {
    return movementManager.getPoints();
  }

  /**
   * Obtain the MovementManager for this Player object.
   * @return this Player's MovementManager.
   */
  private IMovementManageable getMovementManager() {
    return movementManager;
  }

}
