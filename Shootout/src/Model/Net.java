package Model;

/**
 * Class representing a net in the hockey game. Each net has a position, established size including
 * the length of the net opening, as well as the length of the sides that run parallel on the goal
 * to track for collision.
 * **POSITION TRACKS THE VERY BOTTOM OF THE GOAL LINE, NOT POST.**
 */
public class Net {
  private final Position position;
  private final double length;
  private final double sides;
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
    this.sides = GameConfig.NET_SIDE_LENGTH;
    this.thickness = GameConfig.NET_THICKNESS;
    this.goal = false;
    this.netSide = netSide;
    this.crease = initalizeCrease(GameConfig.GOALIE_CREASE_RADIUS);
  }

  private GoalieCrease initalizeCrease(double radius) {
    double x = position.getXCoord();
    double y = position.getYCoord() + (length / 2.0);
    return new GoalieCrease(x, y, radius, netSide);

  }

  public void setTeam(Player.TEAM color) {
    this.color = color;
  }

  public boolean getGoal() {
    return goal;
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
               puckX < position.getXCoord() + sides;
    } else {
      isGoal = puckY > position.getYCoord() &&
               puckY < position.getYCoord() + length &&
               puckX < position.getXCoord() &&
               puckX > position.getXCoord() - sides;
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


}
