package Model;

/**
 * This class represents the goalie crease that is always present in front of each net. This is
 * the blue semicircle. In the context of this game, the goalie crease is also the boundary
 * and habitat that the goalie cannot leave from. It is their encasement.
 */
public class GoalieCrease {
  private final double centerX;
  private final double centerY;
  private final double radius;
  private final Net.NetSide netSide;

  /**
   * Public constructor that initializes a goalie crease
   * @param centerX the x-coordinate of the center. This is the center point of the
   *                semicircle that is in contact with the goal line.
   * @param centerY the y-coordinate of the center. This is the center point of the semicircle
   *                that is in contact with the goal line.
   * @param radius  the radius of the semicircle.
   */
  public GoalieCrease(double centerX, double centerY, double radius, Net.NetSide netSide) {
    this.centerX = centerX;
    this.centerY = centerY;
    this.radius = radius;
    this.netSide = netSide;
  }

  /**
   * Method to identify if a given position is within the boundaries of this GoalieCrease.
   * @param position position to evaluate if it's inside the goalie crease.
   * @return boolean, is this position inside the goalie crease?
   */
  public boolean isInsideCrease(Position position) {
    double x = position.getXCoord();
    double y = position.getYCoord();
    double distance = Math.sqrt(Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2));

    if (distance > radius) {
      return false;
    }

    if (netSide == Net.NetSide.RIGHT) {
      return x <= centerX;
    } else {
      return x >= centerX;
    }
  }

}
