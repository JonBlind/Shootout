package Model;

import Model.MovementManagers.IMovementManageable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a net in the hockey game. Each net has a position, established size including
 * the length of the net opening, as well as the length of the sides that run parallel on the goal
 * to track for collision.
 * **POSITION TRACKS THE VERY BOTTOM OF THE POST AT THE OPENING OF THE NET.**
 */
public class Net {
  private final Position position;
  private final double length;
  private final double sideLength;
  private final double thickness;
  private Player.TEAM color;
  private final GoalieCrease crease;
  boolean goal;
  private NetSide netSide;

  /**
   * Enumeration representing the side of the rink the net is placed.
   * So NetSide.LEFT has a net on the left side, which means the net opens up towards the right.
   */
  public enum NetSide {
    LEFT, RIGHT
  }

  public Net(Position position, Player.TEAM color, NetSide netSide) {
    this.position = position;
    this.color = color;
    this.length = GameConfig.NET_LENGTH;
    this.sideLength = GameConfig.NET_SIDE_LENGTH;
    this.thickness = GameConfig.NET_THICKNESS;
    this.goal = false;
    this.netSide = netSide;
    this.crease = initalizeCrease(GameConfig.GOALIE_CREASE_RADIUS);
  }

  private GoalieCrease initalizeCrease(double radius) {
    double x = position.getXCoord();
    double y = position.getYCoord() + thickness + (length / 2.0);
    return new GoalieCrease(x, y, radius, netSide);

  }



  /**
   * Method to identify if a puck is inside the goal, determining if the net should signal that
   * there is a goal or not.
   * @param puck The puck in the game to see if its position is inside the goals area.
   * @return is there a puck inside the net, signaling a goal?
   */
  private boolean checkGoal(Puck puck) {
    double puckX = puck.getPosition().getXCoord();
    double puckY = puck.getPosition().getYCoord();
    boolean isGoal = false;

    if (netSide == NetSide.RIGHT) {
      isGoal = puckY > position.getYCoord() &&
               puckY < position.getYCoord() + length &&
               puckX > position.getXCoord() &&
               puckX < position.getXCoord() + sideLength;
    } else {
      isGoal = puckY > position.getYCoord() &&
               puckY < position.getYCoord() + length &&
               puckX < position.getXCoord() &&
               puckX > position.getXCoord() - sideLength;
    }

    //I Would normally just do goal = isGoal; however, I want to account for the fact that
    //the puck could always bounce back out, and to ignore any bugs that relate to that, it's good
    //to not allow isGoal to freely change without me directly demanding it to be set back to
    //false.
    if (isGoal) {
      goal = true;
    }
    return isGoal;
  }

  /**
   * Method to check if the inputted mobileObject is on the same y-levels as the net.
   * @param obj MobileObject to compare y-levels to the net.
   * @return boolean, is this mobileObject sharing a y-level as this net?
   */
  private boolean isWithinYBoundary(IMobileObject obj) {
    double objY = position.getYCoord();
    double bottom = position.getYCoord();
    double top = position.getYCoord() + length + (thickness * 2);

    return objY >= bottom && objY <= top;
  }

  /**
   * Method to check if the inputted mobileObject is on the same x-levels as the net.
   * @param obj MobileObject to compare x-levels to the net.
   * @return boolean, is this mobileObject sharing an x-level as this net?
   */
  private boolean isWithinXBoundary(IMobileObject obj) {
    double objX = getPosition().getXCoord();
    if (netSide == NetSide.RIGHT) {
      double leftend = position.getXCoord();
      double rightend = position.getXCoord() + sideLength;
      return objX >= leftend && objX <= rightend;
    }
    else {
      double rightend = position.getXCoord();
      double leftend = position.getXCoord() - sideLength;
      return objX >= leftend && objX <= rightend;
    }
  }

  /**
   * Is this position conflicting or colliding with the top post of this net?
   * @param pos the Position to compare to the net's placement and determine if it overlaps with
   *            the area taken up by the top post.
   * @return Does this position overlap with a point in the area taken up by the top post?
   */
  private boolean isThisPositionTouchingTopPost(Position pos) {
    double posX = this.position.getXCoord();
    double posY = this.position.getYCoord();
    double netX = this.position.getXCoord();
    double netY = this.position.getYCoord();

    if (netSide == NetSide.LEFT) {
      return (posX >= netX - sideLength && posX <= netX && posY >= netY + thickness + length
              && posY <= netY + length + (thickness * 2));
    }
    else {
      return (posX >= netX && posX <= netX + sideLength && posY >= netY + thickness + length
              && posY <= netY + length + (thickness * 2));
    }
  }

  /**
   * Is this position conflicting or colliding with the bottom post of this net?
   * @param pos the Position to compare to the net's placement and determine if it overlaps with
   *            the area taken up by the bottom post.
   * @return Does this position overlap with a point in the area taken up by the bottom post?
   */
  private boolean isThisPositionTouchingBottomPost(Position pos) {
    double posX = this.position.getXCoord();
    double posY = this.position.getYCoord();
    double netX = this.position.getXCoord();
    double netY = this.position.getYCoord();

    if (netSide == NetSide.LEFT) {
      return (posX >= netX - sideLength && posX <= netX && posY >= netY
          && posY <= netY + thickness);
    }
    else {
      return (posX >= netX && posX <= netX + sideLength && posY >= netY
          && posY <= netY + thickness);
    }
  }

  /**
   * Is this position conflicting or colliding with the back post of this net?
   * @param pos the Position to compare to the net's placement and determine if it overlaps with
   *            the area taken up by the back post.
   * @return Does this position overlap with a point in the area taken up by the back post?
   */
  private boolean isThisPositionTouchingBackPost(Position pos) {
    double posX = this.position.getXCoord();
    double posY = this.position.getYCoord();
    double netX = this.position.getXCoord();
    double netY = this.position.getYCoord();

    if (netSide == NetSide.LEFT) {
      return (posX >= netX - sideLength && posX <= netX - sideLength + thickness
              && posY >= netY && posY <= netY + length + (thickness * 2));
    }
    else {
      return (posX <= netX + sideLength && posX >= netX + sideLength - thickness
          && posY >= netY && posY <= netY + length + (thickness * 2));
    }
  }

  /**
   * Is this position conflicting or colliding with the front of this net, excluding the posts?
   * @param pos the Position to compare to the net's placement and determine if it overlaps with
   *            the area taken up by the net opening.
   * @return Does this position overlap with a point in the area taken up by the net opening?
   */
  private boolean isThisPositionTouchingFrontNet(Position pos) {
    double posX = this.position.getXCoord();
    double posY = this.position.getYCoord();
    double netX = this.position.getXCoord();
    double netY = this.position.getYCoord();

    if (netSide == NetSide.LEFT) {
      return (posX <= netX && posX >= netX - thickness && posY >= netY - thickness &&
              posY <= netY + thickness + length);
    }
    else {
      return (posX >= netX && posX <= netX + thickness && posY >= netY + thickness &&
          posY <= netY + thickness + length);
    }
  }

  /**
   * Given a MovementManager, identify if the object this manager is responsible for is in contact
   * with the posts of the net.
   * @param movementmanager movementmanager to identify if it's respective object is in contatct
   *                        with this net.
   * @return is the given Movement Manager's object in contact with the posts of the net?
   */
  public boolean isTheMobileObjectTouchingNet(IMovementManageable movementmanager) {
    for (Position point : movementmanager.getPoints()) {
      if (isThisPointTouchingNet(point)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Given a MovementManagerPuck, identify if the object this manager is responsible for is in
   * contact with the posts of the net.
   * This requires a unique variation because it should not bounce off the front of the net.
   * @param movementmanager movementmanager to identify if it's respective object is in contatct
   *                        with this net.
   * @return is the given Movement Manager's object in contact with the posts of the net?
   */
  public boolean isThePuckTouchingNet(IMovementManageable movementmanager) {
    for (Position point : movementmanager.getPoints()) {
      if (isThisPointTouchingNetExceptFront(point)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is the given position touching any post of the net, or even the opening?
   * @param pos Position to compare to the net and see if it is interacting with the posts.
   * @return is the given position colliding with any of the net posts or its opening?
   */
  private boolean isThisPointTouchingNet(Position pos) {
    return isThisPositionTouchingTopPost(pos) || isThisPositionTouchingBackPost(pos)
        || isThisPositionTouchingFrontNet(pos) || isThisPositionTouchingBottomPost(pos);
  }

  /**
   * Is the given position touching any post of the net, EXCLUDING the opening?
   * @param pos Position to compare to the net and see if it is interacting with the posts.
   * @return is the given position colliding with any of the net posts BUT NOT its opening?
   */
  private boolean isThisPointTouchingNetExceptFront(Position pos) {
    return isThisPositionTouchingTopPost(pos) || isThisPositionTouchingBackPost(pos)
        || !isThisPositionTouchingFrontNet(pos) || isThisPositionTouchingBottomPost(pos);
  }

  /**
   * Method to check the status of every mobile point in the movement manager, returning an array
   * of booleans checking if each mobile point is colliding with this net.
   * @param movementmanager MovementManager tracking position and Mobile Points of an object, to
   *                        see if any of its mobile points are touching the net.
   * @return Return a boolean for every Mobile Point tracked by the given MovementManager.
   *         For every corresponding mobile point: Does it collide with the net posts or opening?
   */
  public Boolean[] netCollisionStatusOfAllMobilePoints(IMovementManageable movementmanager) {
    List<Boolean> boolList = new ArrayList<Boolean>();
    Position[] points = movementmanager.initializePoints();

    for (Position point : points) {
      boolList.add(isThisPointTouchingNet(point));
    }

    Boolean[] output = boolList.toArray(new Boolean[0]);
    return output;
  }

  /**
   * Method to identify if the given Position is inside the goalie crease related to this net.
   * @param pos Position to see if it overlaps with the goalie crease area.
   * @return Is the given position inside this net's goalie crease?
   */
  public boolean isThisPositionInsideGoalieCrease(Position pos) {
    return crease.isInsideCrease(pos);
  }

  /**
   * Get this net's starting position
   * @return Start Position.
   */
  public Position getPosition() {
    return this.position;
  }

  /**
   * Get the side of the rink that this net is positioned at.
   * @return the side of the rink this net is placed.
   */
  public NetSide getNetSide() {
    return this.netSide;
  }

  /**
   * Get the length of the sides of this net, from the opening to the back.
   * @return length of the net's sides.
   */
  public double getSideLength() {
    return this.sideLength;
  }

  /**
   * Get the length of the opening of this net.
   * @return length of the net's opening.
   */
  public double getLength() {
    return this.length;
  }

  /**
   * Get the thickness of the perimeter of this net.
   * @return width of the net's post/perimeter.
   */
  public double getThickness() {
    return this.thickness;
  }

  /**
   * Method to set the team of the net.
   * @param color team color.
   */
  public void setTeam(Player.TEAM color) {
    this.color = color;
  }

  /**
   * Method to return the goal status of the current net.
   * @return is this a goal? Boolean value of the goal field.
   */
  public boolean getGoal() {
    return goal;
  }


}
