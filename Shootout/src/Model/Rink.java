package Model;

import java.awt.geom.RoundRectangle2D;

public class Rink {
  private RoundRectangle2D.Double playArea;
  private int width;
  private int height;
  private int cornerRadius;

  public Rink(int width, int height, int cornerRadius) {
    this.width = width;
    this.height = height;
    this.cornerRadius = cornerRadius;
    this.playArea = new RoundRectangle2D.Double(0, 0, width, height, cornerRadius, cornerRadius);
  }

  /**
   * Method to identify if an object is present inside the play area of the rink.
   * @param position Position of the object
   * @return Is this Position inside the rink?
   */
  public boolean isWithinBounds(Position position) {
    return playArea.contains(position.getXCoord(), position.getYCoord());
  }

  // Method to check if an object collides with the boundary of the rink
  public boolean checkCollision(Position position) {
    return !isWithinBounds(position);
  }

  /**
   * Gets the width of the ice rink.
   * @return integer representing rink width.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets the height of the ice rink.
   * @return integer representing rink height.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Gets the radius of the corner of the ice rink.
   * @return integer representing rounded corner radii.
   */
  public int getCornerRadius() {
    return cornerRadius;
  }
}