package Model;

/**
 * This is a foundational class that is used to construct the 5 different zones needed to
 * combine and create a functional rink. The zones include:
 * <ul>
 * <li> Left_Behind_Net: Behind the net on the left side.
 * <li> Left_Zone: The entirety of the left-zone with the exception of behind the net.
 * <li> Neutral_Zone: All of neutral ice.
 * <li> Right_Zone: The entirety of the right-zone with the exception of behind the net.
 * <li> Left_Behind_Net: Behind the net on the right side.
 * </ul>
 */
public class Zone {

  public enum ZoneType {
    LEFT_BEHIND_NET, LEFT_ZONE, RIGHT_BEHIND_NET, RIGHT_ZONE, NEUTRAL_ZONE
  }

  private final ZoneType zoneType;
  private final double startX;
  private final double startY;
  private final double endX;
  private final double endY;
  private final double cornerRadius;

  /**
   * Basic constructor for a Zone. Should be used if the specified zone is NOT behind any net.
   *
   * @param zoneType ZoneType that represents what this zone is.
   * @param startX   The left-most x-coordinate of the zone.
   * @param startY   The top-most y-coordinate of the zone.
   * @param endX     The right-most x-coordinate of the zone.
   * @param endY     The bottom-most y-coordinate of the zone.
   */
  public Zone(ZoneType zoneType, double startX, double startY, double endX, double endY) {
    this.zoneType = zoneType;
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
    this.cornerRadius = 0;

    if (zoneType == ZoneType.LEFT_BEHIND_NET || zoneType == ZoneType.RIGHT_BEHIND_NET) {
      throw new IllegalArgumentException("Zones That Are Behind The Net CANNOT Have A Radius of"
              + " 0!");
    }
  }

  /**
   * Constructor for a Zone that supports end-zones behind the net. Contains radius field.
   *
   * @param zoneType     ZoneType that represents what this zone is.
   * @param startX       The left-most x-coordinate of the zone.
   * @param startY       The top-most y-coordinate of the zone.
   * @param endX         The right-most x-coordinate of the zone.
   * @param endY         The bottom-most y-coordinate of the zone.
   * @param cornerRadius The radius of the circle that represents the rounded corners of the rink.
   */
  public Zone(ZoneType zoneType, double startX, double startY, double endX, double endY,
              double cornerRadius) {
    this.zoneType = zoneType;
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
    this.cornerRadius = cornerRadius;

    if ((zoneType == ZoneType.LEFT_BEHIND_NET || zoneType == ZoneType.RIGHT_BEHIND_NET)
            && (cornerRadius == 0)) {

      throw new IllegalArgumentException("Zones That Are Behind The Net CANNOT Have A Radius of"
              + " 0!");
    }
  }

  /**
   * Method to determine if the given object position is inside the available play area.
   *
   * @param position position of the player or game object.
   * @return is this position within any zone?
   */
  public boolean contains(Position position) {
    double currentX = position.getXCoord();
    double currentY = position.getYCoord();

    //If the zone is not at the ends, just do a simple check based on rectangular math.
    if (zoneType != ZoneType.LEFT_BEHIND_NET && zoneType != ZoneType.RIGHT_BEHIND_NET) {
      return currentX > startX
              && currentX <= endX
              && currentY > startY
              && currentY <= endY;
    } else {
      return isInLeftBehindNetZone(position) || isInRightBehindNetZone(position);
    }
  }

  private boolean isInLeftBehindNetZone(Position position) {
    double currentX = position.getXCoord();
    double currentY = position.getYCoord();

    // Calculating corner coordinates for the left side
    double leftTopCornerX = startX;
    double leftTopCornerY = startY + cornerRadius;
    double leftBottomCornerX = startX;
    double leftBottomCornerY = endY - cornerRadius;

    // Distance calculations for the left corners
    double topDistance = Math.sqrt(Math.pow(currentX - leftTopCornerX, 2)
            + Math.pow(currentY - leftTopCornerY, 2));
    double bottomDistance = Math.sqrt(Math.pow(currentX - leftBottomCornerX, 2)
            + Math.pow(currentY - leftBottomCornerY, 2));

    // Check for containment within the quarter-circles or the rectangle

    return (topDistance <= cornerRadius                                 //Checks Top-Left Circle.
            && currentX <= leftTopCornerX + cornerRadius) ||
            (bottomDistance <= cornerRadius                             //Checks Bottom-left circle.
                    && currentX <= leftBottomCornerX + cornerRadius) ||
            (currentX >= startX + cornerRadius                          //Checks Rectangle b/w
                    && currentX <= endX                                 //the two circles.
                    && currentY >= startY
                    && currentY <= endY);
  }


  private boolean isInRightBehindNetZone(Position position) {
    double currentX = position.getXCoord();
    double currentY = position.getYCoord();

    // Calculating corner coordinates for the right side
    double rightTopCornerX = endX;
    double rightTopCornerY = startY + cornerRadius;
    double rightBottomCornerX = endX;
    double rightBottomCornerY = endY - cornerRadius;

    // Distance calculations for the right corners
    double topDistance = Math.sqrt(Math.pow(currentX - rightTopCornerX, 2)
            + Math.pow(currentY - rightTopCornerY, 2));
    double bottomDistance = Math.sqrt(Math.pow(currentX - rightBottomCornerX, 2)
            + Math.pow(currentY - rightBottomCornerY, 2));

    // Check for containment within the quarter-circles or the rectangle

    return (topDistance <= cornerRadius                                 //Checks Top-Left Circle.
            && currentX >= rightTopCornerX - cornerRadius) ||
            (bottomDistance <= cornerRadius                             //Checks Bottom-left circle.
                    && currentX >= rightBottomCornerX - cornerRadius) ||
            (currentX >= startX                                         //Checks Rectangle b/w
                    && currentX <= endX - cornerRadius                  //the two circles.
                    && currentY >= startY
                    && currentY <= endY);
  }
}
