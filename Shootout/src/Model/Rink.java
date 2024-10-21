package Model;

import Model.MovementManagers.IMovementManageable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the hockey rink (play area). A Real Hockey Rink Is:
 * <ul>
 * <li>200 ft long</li>
 * <li>85 ft in width</li>
 * <li>Neutral ice is 50 ft long, so 25% of the rinks length</li>
 * <li>Each zone is 64 ft long + 11 ft behind the ice. So 37.5% of the rink total.</li>
 * <li>The zone behind the goal line is 5.5% of the total rink (11ft).</li>
 * <li>
 * </ul>
 * With this information, the rink should represent a rectangle split into 5 zones:
 * <ul>
 * <li>1 Zone for neutral ice. (25%) the length of the rink.
 * <li>2 Zones for the attacking/defending zone in front of each net. (32%) of the rink for each.
 * <li>2 Zones for the back of each net, where the rink curves. (5.5%) of the rink for each.
 * </ul>
 * The very end zones will resemble rectangles, but will have their corners removed depending on
 * the radius/length of the back zone.
 */
public class Rink {

  private Zone[] zones;
  private Net[] nets;
  private double length;
  private double height;
  private static Rink instance;

  /**
   * Constructor for testing purposes likely. Requires all fields to be included.
   *
   * @param zones  Array of zones representing the rink when combined in order.
   * @param nets   Array of nets representing the hockey nets. Array of two, indices 0 is the left,
   *               and 1 is the right net.
   * @param length Horizontal size of the rink.
   * @param height Vertical size of the rink.
   */
  private Rink(Zone[] zones, Net[] nets, double length, double height) {
    this.length = length;
    this.height = height;
    this.zones = zones;
    this.nets = nets;
  }

  /**
   * Constructor based on just the dimensions of the rink.
   *
   * @param length Horizontal size of the rink.
   * @param height Vertical size of the rink.
   */
  private Rink(double length, double height) {
    this.length = length;
    this.height = height;
    this.zones = initializeZones();
    this.nets = initializeNets(length, height);
  }


  public static Rink getRinkInstance(double length, double height) {
    if (instance == null) {
      instance = new Rink(length, height);
    }
    return instance;
  }

  /**
   * Method initializes the zones of the rink utilizing its dimensions.
   *
   * @return Array of Zones.
   * @throws IllegalStateException if the zones for some reason to not cover the entire rink
   *                               length.
   */
  private Zone[] initializeZones() throws IllegalStateException {
    double startOfZone1 = 0;
    double endOfZone1 = (0.055 * length);
    double startOfZone2 = endOfZone1;
    double endOfZone2 = startOfZone2 + (0.375 * length);
    double startOfZone3 = endOfZone2;
    double endOfZone3 = startOfZone3 + (0.25 * length);
    double startOfZone4 = endOfZone3;
    double endOfZone4 = startOfZone4 + (0.375 * length);
    double startOfZone5 = endOfZone4;
    double endOfZone5 = startOfZone5 + (0.055 * length);

    Zone[] zones =
        {new Zone(Zone.ZoneType.LEFT_BEHIND_NET, startOfZone1, endOfZone1, 0, height),
            new Zone(Zone.ZoneType.LEFT_ZONE, startOfZone2, endOfZone2, 0, height),
            new Zone(Zone.ZoneType.NEUTRAL_ZONE, startOfZone3, endOfZone3, 0, height),
            new Zone(Zone.ZoneType.RIGHT_ZONE, startOfZone4, endOfZone4, 0, height),
            new Zone(Zone.ZoneType.RIGHT_BEHIND_NET, startOfZone5, endOfZone5, 0, height)};

    if ((endOfZone5 - startOfZone1) != this.length) {
      throw new IllegalStateException("Combined Zone lengths do not match Rink length");
    }

    return zones;
  }

  /**
   * Creates appropriate nets depending on the rink dimensions, for now has hard-coded teams, will
   * need to alter to allow access to future team selection implementation.
   *
   * @param length length of the rink.
   * @param height height of the rink.
   * @return Array of Nets with a length of 2. Index 0 is the left net, Index 1 is the right net.
   */
  private Net[] initializeNets(double length, double height) {
    nets[0] = new Net(new Position(GameConfig.LEFT_GOAL_LINE_X,
        (GameConfig.RINK_HEIGHT / 2) - (GameConfig.NET_LENGTH / 2)),
        Player.TEAM.BLUE, Net.NetSide.LEFT);

    nets[1] = new Net(new Position(GameConfig.RIGHT_GOAL_LINE_X,
        (GameConfig.RINK_HEIGHT / 2) - (GameConfig.NET_LENGTH / 2)),
        Player.TEAM.RED, Net.NetSide.RIGHT);

    return nets;
  }

  /**
   * Returns boolean indicating if the position argument is present within the boundaries of the
   * rink. This is done by iterating through each zone and seeing if it returns true for any. If any
   * zone returns true, the method return true. If no zone returns true, the method returns false.
   *
   * @param position X, Y coordinates to observe.
   * @return boolean identifying if the Position argument is in the rink or not.
   */
  public boolean isInsideRink(Position position) {
    for (Zone zone : zones) {
      if (zone.isInsideZone(position)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Grab the status (boolean flag) representing if each mobile point of the IMobileObject is inside
   * the rink. REMINDER: Mobile Points are the 45 degree markers around every mobile object to track
   * position as every object is a circle.
   *
   * @param movementmanager movementmanager to examine if all of its Mobile points are inside the
   *                        Rink.
   * @return Boolean array showing the status of every Mobile Point of the inputted mobile object.
   * Index 0 Should have the top-most point, and each consecutive should be the next 45 degree point
   * in clockwise rotation.
   */
  public Boolean[] locationStatusOfAllMobilePoints(IMovementManageable movementmanager) {
    List<Boolean> boolList = new ArrayList<Boolean>();
    double objX = movementmanager.getPosition().getXCoord();
    double objY = movementmanager.getPosition().getYCoord();
    double radii = movementmanager.getRadius();
    Position[] points = movementmanager.initializePoints();

    for (Position point : points) {
      boolList.add(isInsideRink(point));
    }

    Boolean[] output = boolList.toArray(new Boolean[0]);
    return output;
  }

  /**
   * Method to identify if the inputted mobile object is completely inside the rink. Passes object
   * to check status of each mobile point, and iterates through each point's status. If a single
   * point is not inside the rink, the method returns a true flag, indicating the object is most
   * likely touching the boards.
   *
   * @param movementmanager Movement Manager, to check if the object is currently touching the
   *                        boards or has a part located outside the rink.
   * @return Boolean value, is this mobile object touching the boards or has a mobile point located
   * outside the rink?>
   */
  public boolean isMobileObjectTouchingBoards(IMovementManageable movementmanager) {
    movementmanager.getPoints();
    Boolean[] boolArr = locationStatusOfAllMobilePoints(movementmanager);

    for (Boolean bool : boolArr) {
      if (!bool) {
        return true;
      }
    }
    return false;
  }

  /**
   * Method to return the X-Value of the left end of the rink This method is being made just in case
   * I need to extract this value and change it in the future once we actually place this on a
   * visual.
   *
   * @return X-Value of the left end of the rink.
   */
  public double getLeftX() {
    return 0;
  }

  /**
   * Method to return the X-Value of the right end of the rink This method is being made just in
   * case I need to extract this value and change it in the future once we actually place this on a
   * visual.
   *
   * @return X-Value of the right end of the rink.
   */
  public double getRightX() {
    return getLeftX() + length;
  }

  /**
   * Method to return the Y-Value of the Bottom end of the rink This method is being made just in
   * case I need to extract this value and change it in the future once we actually place this on a
   * visual.
   *
   * @return Y-Value of the Bottom end of the rink.
   */
  public double getBottomY() {
    return 0;
  }

  /**
   * Method to return the Y-Value of the Top end of the rink This method is being made just in case
   * I need to extract this value and change it in the future once we actually place this on a
   * visual.
   *
   * @return Y-Value of the Top end of the rink.
   */
  public double getTopY() {
    return getBottomY() + height;
  }

  /**
   * Method to return the array of nets.
   *
   * @return array of nets that exist in this rink instance.
   */
  public Net[] getNets() {
    return nets;
  }
}