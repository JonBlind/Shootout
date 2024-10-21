package Model.MovementManagers;

import Model.GameConfig;
import Model.GameSession;
import Model.Net;
import Model.Position;
import Model.Rink;
import java.util.ArrayList;

abstract class MovementManager implements IMovementManageable {
  protected Position position;
  protected double radius;
  protected Position[] points;
  protected double xVelocity;
  protected double yVelocity;
  protected Rink rink;


  public MovementManager(Position position, double initialXVelocity, double initialYVelocity) {
    this.position = new Position(500, 500);
    this.xVelocity = initialXVelocity;
    this.yVelocity = initialYVelocity;
    this.radius = 5;
    this.rink = Rink.getRinkInstance(GameConfig.RINK_LENGTH, GameConfig.RINK_HEIGHT);
    this.points = initializePoints();
  }

  public MovementManager(double initialXVelocity, double initialYVelocity) {
    this.position = new Position(500, 500);
    this.xVelocity = initialXVelocity;
    this.yVelocity = initialYVelocity;
    this.radius = 5;
    this.rink = Rink.getRinkInstance(GameConfig.RINK_LENGTH, GameConfig.RINK_HEIGHT);
    this.points = initializePoints();
  }

  /**
   * Constructor for the abstract class MovementManager. Should never be used directly outside of
   * being supered.
   */
  public MovementManager() {
    this.position = new Position(500, 500);
    this.xVelocity = 0;
    this.yVelocity = 0;
    this.radius = 5;
    this.rink = Rink.getRinkInstance(GameConfig.RINK_LENGTH, GameConfig.RINK_HEIGHT);
    this.points = initializePoints();
  }

  ;

  /**
   * Method to establish a velocity "clamp" Essentially, it ensures that the Mobile Objects
   * velocity will not exceed the argument.
   *
   * @param maxVelocity the maximum velocity that the mobile object can undergo. Any attempts to
   *                    update velocity past the maximum or its negation will be voided.
   */
  void clampVelocity(double maxVelocity) {
    xVelocity = Math.max(-maxVelocity, Math.min(maxVelocity, xVelocity));
    yVelocity = Math.max(-maxVelocity, Math.min(maxVelocity, yVelocity));
  }

  /**
   * Method to slowly bring the velocity of an object closer to zero over time if no force is being
   * applied to it.
   *
   * @param deltaTime change in time since the last update. Should be time since last frame.
   */
  protected void applyFriction(double deltaTime) {
    xVelocity *= (1 - GameConfig.FRICTION * deltaTime);
    yVelocity *= (1 - GameConfig.FRICTION * deltaTime);
  }

  /**
   * Method to directly update the velocity tracked in the MovementManager depending on the amount
   * of time that has passed, along with other forces that may be different for each respective
   * object. For example, a Player based class will have its velocity update depend on user inputs.
   * The puck class will depend on the user possessing it or the last applied force.
   * THIS IS THE FIRST STEP OF THE UPDATE CYCLE FOR MOVEMENT MANAGERS.
   *
   * @param deltaTime change in time since the last update. Should be time since last frame.
   */
  protected void updateMovement(double deltaTime) {
  }


  @Override
  public void calculateNewPosition(double deltaTime) {
    double newX = this.position.getXCoord() + xVelocity * deltaTime;
    double newY = this.position.getYCoord() + yVelocity * deltaTime;
    this.position.setXCoord(newX);
    this.position.setYCoord(newY);

    for (Position point : points) {
      double newPointX = point.getXCoord() + xVelocity * deltaTime;
      double newPointY = point.getYCoord() + yVelocity * deltaTime;
      point.setXCoord(newPointX);
      point.setYCoord(newPointY);
    }
  }

  @Override
  public abstract void handleRinkAndNetReflection(ArrayList<Integer> outputs);

  /**
   * Create the Mobile Points that will track the perimeter of the object this MovementManager is
   * supposed to track. Each mobile point will be positioned at every 45 degree point on the
   * mobileObject's perimeter, with the first index starting at the very top, and each consecutive
   * index is the next point going clockwise.
   * @return array of Positions representing a mobile object's list of MobilePoints.
   */
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
  public ArrayList<Integer> grabBoardCollisionStatusOfMobilePointsInRink() {
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
   * Grab the net collision status of mobile points for this MobileObject, and create an ArrayList.
   * The ArrayList holds only the indexes that returned true from the method:
   * .isMobileObjectTouchingNet.
   * Essentially, every returned number represents what 45 degree point of this object has breached
   * or made contact with the posts or front of the net, where the top-most point is the number 0,
   * and it increments clockwise.
   * @return ArrayList of integers where each integer represents what mobile point has breached or
   * made contact with the boards. Top most point is 0 and increments clockwise. If the output
   * is simply an empty ArrayList, then nothing has made contact.
   */
  public ArrayList<Integer> grabNetCollisionStatusOfMobilePoints() {

    Net[] nets = GameSession.getInstance().getSessionNets();

    //If the net detects that the skater is conflicting with the posts/front.
    if (nets[0].isTheMobileObjectTouchingNet(this)) {


      //Grab the status of each mobile point of this skater
      Boolean[] status = nets[0].netCollisionStatusOfAllMobilePoints(this);
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

    if (nets[1].isTheMobileObjectTouchingNet(this)) {


      //Grab the status of each mobile point of this skater
      Boolean[] status = nets[1].netCollisionStatusOfAllMobilePoints(this);
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
    ArrayList<Integer> pointStatus = grabBoardCollisionStatusOfMobilePointsInRink();
    if (!pointStatus.isEmpty()) {
      this.handleRinkAndNetReflection(pointStatus);
    }
  }

  /**
   * Method to call to constantly check if the Mobile Object is found to have been colliding with
   * the nets.
   */
  protected void handleNetReflection() {
    ArrayList<Integer> pointStatus = grabNetCollisionStatusOfMobilePoints();
    if (!pointStatus.isEmpty()) {
      this.handleRinkAndNetReflection(pointStatus);
    }
  }


  @Override
  public double getXVelocity() {
    return xVelocity;
  }

  @Override
  public double getYVelocity() {
    return yVelocity;
  }

  @Override
  public void setXVelocity(double xVelocity) {
    this.xVelocity = xVelocity;
  }

  @Override
  public void setYVelocity(double yVelocity) {
    this.yVelocity = yVelocity;
  }

  @Override
  public Position getPosition() {
    return this.position;
  }

  @Override
  public void setPosition(double x, double y) {
    position.setXCoord(x);
    position.setYCoord(y);
  }

  @Override
  public Position[] getPoints() {
    return this.points;
  }

  @Override
  public double getRadius() {
    return radius;
  }

  @Override
  public void setRadius(double radius) {
    this.radius = radius;
  }





}