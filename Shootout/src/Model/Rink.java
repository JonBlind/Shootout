package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the hockey rink (play area).
 * A Real Hockey Rink Is:
 * <ul>
 * <li>200 ft long</li>
 * <li>85 ft in width</li>
 * <li>Neutral ice is 50 ft long, so 25% of the rinks length</li>
 * <li>Each zone is 64 ft long + 11 ft behind the ice. So 37.5% of the rink total.</li>
 * <li>The zone behind the net is 5.5% of the total rink.
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
  private double length;
  private double height;

  /**
   * Constructor for testing purposes likely. Requires all fields to be included.
   *
   * @param zones  Array of zones representing the rink when combined.
   * @param length Horizontal size of the rink.
   * @param height Vertical size of the rink.
   */
  public Rink(Zone[] zones, double length, double height) {
    this.length = length;
    this.height = height;
    this.zones = zones;
  }

  /**
   * Constructor based on just the dimensions of the rink.
   *
   * @param length Horizontal size of the rink.
   * @param height Vertical size of the rink.
   */
  public Rink(double length, double height) {
    this.length = length;
    this.height = height;
    this.zones = initializeZones();
  }

  /**
   * Method initializes the zones of the rink utilizing its dimensions.
   *
   * @return Array of Zones.
   * @throws IllegalStateException if the zones for some reason to not
   *                               cover the entire rink length.
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
}