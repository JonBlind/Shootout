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
 *
 */
public class Rink {
  private List<Zone> zones;
  private double length;
  private double height;

  public Rink(List<Zone> zones, double length, double height) {
    this.zones = new ArrayList<>();
    this.length = length;
    this.height = height;
  }

}