package Model;

/**
 * Class representing a net in the hockey game. Each net has a position, established size including
 * the length of the net opening, as well as the length of the sides that run parallel on the goal
 * to track for collision.
 * **POSITION TRACKS THE VERY BOTTOM LEFT OF THE GOAL LINE, NOT POST.**
 */
public class Net {
  private Position position;
  private int length;
  private int sides;
  private int thickness;
  private Player.TEAM_COLOR color;
  boolean goal;

  /**
   * Constructs a net given a team color to represent and a position.
   */
  public Net(Position position, Player.TEAM_COLOR color) {
    this.position = position;
    this.color = color;
    this.length = 50;
    this.sides = 30;
    this.thickness = 5;
    this.goal = false;
  }

  public void setTeam(Player.TEAM_COLOR color) {
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
  public boolean checkGoal(Puck puck) {
    double puckX = puck.getPosition().getXCoord();
    double puckY = puck.getPosition().getYCoord();

    if (puckY > position.getYCoord()
            && puckY < position.getYCoord() + length
            && puckX < position.getXCoord() + sides
            && puckX > position.getXCoord()) {

      goal = true;
      return true;
    }

    return false;
  }


}
