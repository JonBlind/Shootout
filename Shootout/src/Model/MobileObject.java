package Model;

import Model.MovementManagers.IMovementManageable;
import java.util.ArrayList;

public abstract class MobileObject implements IMobileObject {

  protected double radius;
  protected double angle;
  protected Position position;
  protected Position[] points;
  protected IMovementManageable movementManager;

  //Likely planning on skipping the use of a constructor since each subclass will utilize
  //its own unique initialization. Up for change/debate.



  @Override
  public Position[] initializePoints() {
    double objX = getPosition().getXCoord();
    double objY = getPosition().getYCoord();
    Position pointUp = new Position(objX, objY + radius);
    Position pointUpRight = new Position((radius * Math.cos(Math.PI / 4)) + objX,
        radius * Math.cos(Math.PI / 4) + objY);
    Position pointRight = new Position(objX + radius, objY);
    Position pointDownRight = new Position(objX + (radius * Math.cos(Math.PI / 4)),
        objY - radius * Math.cos(Math.PI / 4));
    Position pointDown = new Position(objX, objY - radius);
    Position pointDownLeft = new Position(objX - (radius * Math.cos(Math.PI / 4)),
        objY - (radius * Math.cos(Math.PI / 4)));
    Position pointLeft = new Position(objX - radius, objY);
    Position pointUpLeft = new Position(objX - (radius * Math.cos(Math.PI / 4)),
        objY + (radius * Math.cos(Math.PI / 4)));

    return new Position[]
        {pointUp, pointUpRight, pointRight, pointDownRight, pointDown, pointDownLeft, pointLeft,
            pointUpLeft};
  }

  /**
   * Grab the status of mobile points for this MobileObject, and create an ArrayList. The ArrayList
   * holds only the indexes that returned true from the method:
   * .isMobileObjectTouchingBoards.
   * Essentially, every returned number represents what 45 degree point of this object has breached
   * or made contact with the boards of the rink, where the top-most point is the number 0, and
   * it increments clockwise.
   * @return ArrayList of integers where each integer represents what mobile point has breached or
   * made contact with the boards. Top most point is 0 and increments clockwise. If the output
   * is simply an empty ArrayList, then nothing has made contact.
   */
  public ArrayList<Integer> grabStatusOfMobilePointsInRink() {
    //If the rink detects that the skater is conflicting with the boards.
    if (Rink.getRinkInstance(GameConfig.RINK_LENGTH, GameConfig.RINK_HEIGHT)
        .isMobileObjectTouchingBoards(this)) {

      //Grab the status of each mobile point of this skater
      Boolean[] status = Rink.getRinkInstance(GameConfig.RINK_LENGTH, GameConfig.RINK_HEIGHT)
          .locationStatusOfAllMobilePoints(this);
      ArrayList<Integer> output = new ArrayList<Integer>();

      //For the output, an arrayList of integers, append the index of each status point that is
      //conflicting with the boards.
      for (int i = 0; i < status.length; i++) {
        if (status[i] = true) {
          output.add(i);
        }
      }
      return output;
    }
    else {
      return new ArrayList<Integer>();
    }
  }

  /**
   * Method to call to constantly check if the Mobile Object is found to have been colliding with
   * the boards.
   */
  protected void handleBoardReflection() {
    ArrayList<Integer> pointStatus = grabStatusOfMobilePointsInRink();
    if (!pointStatus.isEmpty()) {
      this.movementManager.handleRinkReflection(pointStatus);
    }
  }

  @Override
  public double getRadius() {
    return radius;
  }

  @Override
  public void setRadius(double radius) {
    this.radius = radius;
  }

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
    return position;
  }

  @Override
  public void setPosition(double x, double y) {
    position.setXCoord(x);
    position.setYCoord(y);
  }


  /**
   * Obtain the MovementManager for this Player object.
   * @return this Player's MovementManager.
   */
  private IMovementManageable getMovementManager() {
    return movementManager;
  }

}
