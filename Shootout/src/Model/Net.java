package Model;

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
   * Method to grab the closest position of the net to the object if the object's y-level is within
   * the bounds of the net's y-span, but this case shouldn't hold for the x-values.
   * THIS SHOULD NOT BE CALLED IF THAT REQUIREMENT IS NOT MET.
   * @param obj MobileObject to compare to the net.
   * @return Position of the closest part of the net to the MobileObject(obj) parameter.
   */
  private Position handleSameYDifferentX(IMobileObject obj) {
    double objX = obj.getPosition().getXCoord();
    double objY = obj.getPosition().getYCoord();

    //Right net, player in front of net. Return player's y, net's starting X.
    if (netSide == NetSide.RIGHT && objX < position.getXCoord()) {
      return new Position(position.getXCoord(), objY);
    }

    //Right net, player behind the net. Return player's y, net's starting X + side length.
    else if (netSide == NetSide.RIGHT && objX > position.getXCoord() + sideLength) {
      return new Position(position.getXCoord() + sideLength, objY);
    }

    //Left net, player in front of net. Return player's Y, net's starting X.
    else if (netSide == NetSide.LEFT && objX > position.getXCoord()) {
      return new Position(position.getXCoord(), objY);
    }

    //Left net, player behind net. Return Player's Y, net's starting X - side length.
    //This method should not be called unless the Y matches, so the else case should be ok.
    else {
      return new Position(position.getXCoord() - sideLength, objY);
    }
  }

  /**
   * Method to grab the closest position of the net to the object if the object's x-level is within
   * the bounds of the net's x-span but not the y-values.
   * THIS SHOULD NOT BE CALLED IF THAT REQUIREMENT IS NOT MET.
   * @param obj MobileObject to compare to the net.
   * @return Position of the closest part of the net to the MobileObject(obj) parameter.
   */
  private Position handleDifferentYSameX(IMobileObject obj) {
    double objX = obj.getPosition().getXCoord();
    double objY = obj.getPosition().getYCoord();

    //If the object is below the net.
    if (objY < position.getYCoord()) {
      return new Position(objX, position.getYCoord());
    }
    //Else it should be higher.
    else {
      return new Position(objX, position.getYCoord() - sideLength);
    }
  }

  /**
   * Method to grab the closest position of the net to the object if neither the object's x or y
   * coordinates are within the span/range that matches this net.
   * THIS SHOULD NOT BE CALLED IF THAT REQUIREMENT IS NOT MET.
   * @param obj MobileObject to compare to the net.
   * @return Position of the closest part of the net to the MobileObject(obj) parameter.
   */
  private Position handleDifferentYDifferentX(IMobileObject obj) {
    double objX = obj.getPosition().getXCoord();
    double objY = obj.getPosition().getYCoord();


    //Should not have to compare it to actual position and only starting position because
    //intermediate space should be handled prior.
    if (netSide == NetSide.RIGHT) {

      //Right side net, behind and above it.
      if (objX > position.getXCoord() && objY > position.getYCoord()) {
        return new Position(position.getXCoord() + sideLength,
                position.getYCoord() + length + (thickness * 2));
      }

      //Right side net, behind and below it.
      else if (objX > position.getXCoord() && objY < position.getYCoord()) {
        return new Position(position.getXCoord() + sideLength, position.getYCoord());
      }

      //Right side net, front and above it.
      else if (objX < position.getXCoord() && objY > position.getYCoord()) {
        return new Position(position.getXCoord(), position.getYCoord() + length + (thickness * 2));
      }

      //Right side net, front and below it.
      else {
        return position;
      }
    }

    else {
      //Left side net, behind and above it.
      if (objX < position.getXCoord() && objY > position.getYCoord()) {
        return new Position(position.getXCoord() - sideLength,
                position.getYCoord() + length + (thickness * 2));
      }
      //Left side net, behind and below it.
      else if(objX < position.getXCoord() && objY < position.getYCoord()) {
        return new Position(position.getXCoord() - sideLength, position.getYCoord());
      }
      //Left side net, in front of and above it.
      else if(objX > position.getXCoord() && objY > position.getYCoord()) {
        return new Position(position.getXCoord(), position.getYCoord() + length + (thickness * 2));
      }
      //Left side net, in front of and below it.
      else {
        return position;
      }
    }
  }

  /**
   * Return the closest point of a net to the mobileObject.
   * If an object is on the same y-level as the net, it grabs the closest position of the net at
   * that y-level.
   * If an object is on the same x-level as the net, it grabs the closest position of the net at
   * that y-level.
   * If the x and y values are both off, the closest point will be the closest corner of the net
   * to the mobile object. So we grab the position of that.
   * @param obj The mobile object to compare its distance from the net.
   * @return the Position of the closest point on the net.
   */
  private Position getClosestPointOfNet (IMobileObject obj) {
    double objX = obj.getPosition().getXCoord();
    double objY = obj.getPosition().getYCoord();

    //Essentially inside net, just return the same position. This will likely never be utilized.
    if (isWithinXBoundary(obj) && isWithinYBoundary(obj)) {
      return new Position(objX, objY);
    }

    //Object is on the same y-levels but differs in x. So find the closest point on either the
    //front or back of the net.
    if (!isWithinXBoundary(obj) && isWithinYBoundary(obj)) {
      return handleSameYDifferentX(obj);
    }

    //Object is on the same x-levels but differs in y, so grab the nearest side post and the x
    //value of the object.
    else if (isWithinXBoundary(obj) && !isWithinYBoundary(obj)) {
      return handleDifferentYSameX(obj);
    }

    else {
      return handleDifferentYDifferentX(obj);
    }
  }

  /**
   * Returns the distance to the closest position of the net relative to the inputted mobileObject.
   * If an object is on the same y-level as the net, it grabs the closest position of the net at
   * that y-level and finds the displacement,
   * If an object is on the same x-level as the net, it grabs the closest position of the net at
   * that y-level and finds the displacement.
   * If the x and y values are both off, the closest point will be the closest corner of the net
   * to the mobile object. So we grab the displacement to that.
   * @param obj The mobile object to compare its distance from the net.
   * @return the double value of the distance from the mobile object to the net.
   */
  private double computeDistanceToNet(IMobileObject obj) {
    Position netPos = getClosestPointOfNet(obj);

    return Math.sqrt(Math.pow((netPos.getXCoord() - obj.getPosition().getXCoord()), 2) +
            Math.pow((netPos.getYCoord() - obj.getPosition().getYCoord()),2));
  }

  /**
   * Inputting a MobileObject, this method determines if said MobileObject touches/interacts with
   * the front OPENING of the net. This excludes the post.
   * @param obj A MobileObject, to determine if the net is being touched.
   * @return is the back post of either net being touched?
   */
  private boolean isOpeningBeingTouched(IMobileObject obj) {
    double netX = getClosestPointOfNet(obj).getXCoord();
    double netY = getClosestPointOfNet(obj).getYCoord();
    double openingX = position.getXCoord();
    double bottomY = position.getYCoord() + thickness;
    double topY = position.getYCoord() + thickness + length;

    return netY > bottomY && netY < topY && openingX == netX
              && computeDistanceToNet(obj) < obj.getRadius();
  }

  /**
   * Is this MobileObject that is being inputted touching/interacting with the posts of the net?
   * Done to create collision detection.
   * @param obj the mobile object to see if 
   * @return boolean, is the given MobileObject touching the
   */
  public boolean isNetBeingTouched(IMobileObject obj) {
    return obj.getRadius() > computeDistanceToNet(obj);

  }

  /**
   * Method to see if a puck is interacting with the net for collision purposes. This might seem
   * redundant; however, the puck must be allowed through the front opening of the net, and all
   * other objects should not be.
   * @param Puck The puck in the match to detect if it is touching the net.
   * @return boolean value, is the inputted puck touching the net posts?
   */
  public boolean isPuckTouchingNet(Puck Puck) {
    return isNetBeingTouched(Puck) && !isOpeningBeingTouched(Puck);
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
