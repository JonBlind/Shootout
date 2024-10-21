package Model.MovementManagers;

import Model.GameConfig;
import Model.GameSession;
import Model.Net;
import Model.Net.NetSide;
import Model.Player;
import Model.Position;
import Model.Rink;
import java.util.ArrayList;

public class MovementManagerGoalie extends MovementManagerPlayer {


  /**
   * Constructor for a Goalie variation of the MovementManager. Requires initial velocity values.
   * @param initialXVelocity initial X Velocity the goalie should spawn with.
   * @param initialYVelocity initial Y velocity the goalie should spawn with.
   */
  public MovementManagerGoalie(double initialXVelocity, double initialYVelocity) {
    super(initialXVelocity, initialYVelocity);
  }

  /**
   * Default constructor for the goalie variation of the MovementManager.
   * Will spawn in front of net and have 0 velocity.
   */
  public MovementManagerGoalie() {
    super();
  }

  @Override
  public void updateMovement(double deltaTime) {
    updateVelocityFromUserInput(deltaTime);
    calculateNewPosition(deltaTime);
  }

  @Override
  public void handleRinkAndNetReflection(ArrayList<Integer> outputs) {
  }

  @Override
  protected void updateVelocityFromUserInput(double deltaTime) {
    if (upPressed) yVelocity += GameConfig.GOALIE_ACCELERATION * deltaTime;
    if (downPressed) yVelocity -= GameConfig.GOALIE_ACCELERATION * deltaTime;
    if (leftPressed) xVelocity -= GameConfig.GOALIE_ACCELERATION * deltaTime;
    if (rightPressed) xVelocity += GameConfig.GOALIE_ACCELERATION * deltaTime;
  }

  /**
   * Is the given position inside the goalie crease of either net?
   * @param pos Position to compare and see if it overlaps with the goalie crease.
   * @return Boolean, is this position overlapping with the goalie crease?
   */
  private boolean isPositionInsideGoalieCrease(Position pos) {

    for (Net net : GameSession.getInstance().getSessionNets()) {
      if (net.getNetSide() == NetSide.LEFT && net.isThisPositionInsideGoalieCrease(pos)
          && pos.getXCoord() >= net.getPosition().getXCoord() - net.getThickness()) {
        return true;
      }
      if (net.getNetSide() == NetSide.RIGHT && net.isThisPositionInsideGoalieCrease(pos)
          && pos.getXCoord() <= net.getPosition().getXCoord() + net.getThickness()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void calculateNewPosition(double deltaTime) {
    double newX = this.position.getXCoord() + xVelocity * deltaTime;
    double newY = this.position.getYCoord() + yVelocity * deltaTime;
    Position temp = new Position(newX, newY);

    if(isPositionInsideGoalieCrease(temp)) {
      this.position.setXCoord(newX);
      this.position.setYCoord(newY);

      for (Position point : points) {
        double newPointX = point.getXCoord() + xVelocity * deltaTime;
        double newPointY = point.getYCoord() + yVelocity * deltaTime;
        point.setXCoord(newPointX);
        point.setYCoord(newPointY);
      }
    }
  }

}
