import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

  private Picture picture;
  private static final double BORDER_PIXEL_ENERGY = 1000d;

  // create a seam carver object based on the given picture
  public SeamCarver(Picture picture) {
    if (picture == null) {
      throw new IllegalArgumentException();
    }
    this.picture = new Picture(picture);
  }

  // current picture
  public Picture picture() {
    return new Picture(this.picture);
  }

  // width of current picture
  public int width() {
    return this.picture.width();
  }

  // height of current picture
  public int height() {
    return this.picture.height();
  }

  // energy of pixel at column x and row y
  public double energy(int x, int y) {
    if (x < 0 || x >= width() || y < 0 || y >= height()) {
      throw new IllegalArgumentException();
    }

    if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
      return BORDER_PIXEL_ENERGY;
    }

    double rx = picture.get(x + 1, y).getRed() - picture.get(x - 1, y).getRed(),
        gx = picture.get(x + 1, y).getGreen() - picture.get(x - 1, y).getGreen(),
        bx = picture.get(x + 1, y).getBlue() - picture.get(x - 1, y).getBlue(),
        ry = picture.get(x, y + 1).getRed() - picture.get(x, y - 1).getRed(),
        gy = picture.get(x, y + 1).getGreen() - picture.get(x, y - 1).getGreen(),
        by = picture.get(x, y + 1).getBlue() - picture.get(x, y - 1).getBlue();

    return Math.sqrt(rx * rx + gx * gx + bx * bx + ry * ry + gy * gy + by * by);
  }

  // sequence of indices for horizontal seam
  public int[] findHorizontalSeam() {
    Pair[][] energies = new Pair[height()][width()];
    for (int i = 0; i < height(); i++) {
      energies[i][0] = new Pair(BORDER_PIXEL_ENERGY, -1);
    }
    for (int col = 1; col < width(); col++) {
      energies[0][col] = new Pair(BORDER_PIXEL_ENERGY, -1);
      for (int row = 0; row < height(); row++) {
        relaxHorizontal(energies, row, col);
      }
    }
    return extractHorizontalSeam(energies);
  }

  // sequence of indices for vertical seam
  public int[] findVerticalSeam() {
    Pair[][] energies = new Pair[height()][width()];
    for (int i = 0; i < width(); i++) {
      energies[0][i] = new Pair(BORDER_PIXEL_ENERGY, -1);
    }

    for (int row = 1; row < height(); row++) {
      energies[row][0] = new Pair(BORDER_PIXEL_ENERGY, -1);
      for (int col = 0; col < width(); col++) {
        relaxVertical(energies, row, col);
      }
    }
    return extractVerticalSeam(energies);
  }

  // remove horizontal seam from current picture
  public void removeHorizontalSeam(int[] seam) {
    if (seam == null || seam.length != width() || height() <= 1) {
      throw new IllegalArgumentException("Invalid horizontal seam");
    }

    Picture newPicture = new Picture(width(), height() - 1);
    for (int x = 0; x < width(); x++) {
      int k = 0; // Index for the new picture
      for (int y = 0; y < height(); y++) {
        if (y != seam[x]) {
          newPicture.setRGB(x, k++, picture.getRGB(x, y));
        }
      }
    }

    picture = newPicture;
  }

  private boolean isValidPixel(int col, int row) {
    return col > -1 && col < width() && row > -1 && row < height();
  }

  private void relaxVertical(Pair[][] energies, int row, int col) {
    double myEnergy = energy(col, row);
    Pair[] paths = {
        new Pair(isValidPixel(col - 1, row - 1) ? myEnergy + energies[row - 1][col - 1].energy : Double.MAX_VALUE,
            col - 1),
        new Pair(isValidPixel(col, row - 1) ? myEnergy + energies[row - 1][col].energy : Double.MAX_VALUE, col),
        new Pair(isValidPixel(col + 1, row - 1) ? myEnergy + energies[row - 1][col + 1].energy : Double.MAX_VALUE,
            col + 1)
    };
    Arrays.sort(paths);
    energies[row][col] = paths[0];
  }

  private void relaxHorizontal(Pair[][] energies, int row, int col) {
    double myEnergy = energy(col, row);
    Pair[] paths = {
        new Pair(isValidPixel(col - 1, row - 1) ? myEnergy + energies[row - 1][col - 1].energy : Double.MAX_VALUE,
            row - 1),
        new Pair(isValidPixel(col - 1, row) ? myEnergy + energies[row][col - 1].energy : Double.MAX_VALUE, row),
        new Pair(isValidPixel(col - 1, row + 1) ? myEnergy + energies[row + 1][col - 1].energy : Double.MAX_VALUE,
            row + 1)
    };
    Arrays.sort(paths);
    energies[row][col] = paths[0];
  }

  private int[] extractVerticalSeam(Pair[][] energies) {
    int[] seam = new int[height()];
    double lowestEnergy = Double.MAX_VALUE;
    int index = -1;
    // find lowest energy
    for (int col = 0; col < width(); col++) {
      if (energies[height() - 1][col].energy < lowestEnergy) {
        lowestEnergy = energies[height() - 1][col].energy;
        index = col;
      }
    }

    int row = height() - 1;
    while (row > -1) {
      seam[row] = index;
      index = energies[row][index].prev;
      row--;
    }
    return seam;
  }

  private int[] extractHorizontalSeam(Pair[][] energies) {
    int[] seam = new int[width()];
    double lowestEnergy = Double.MAX_VALUE;
    int index = -1;
    // find lowest energy
    for (int row = 0; row < height(); row++) {
      if (energies[row][width() - 1].energy < lowestEnergy) {
        lowestEnergy = energies[row][width() - 1].energy;
        index = row;
      }
    }

    int col = width() - 1;
    while (col > -1) {
      seam[col] = index;
      index = energies[index][col].prev;
      col--;
    }
    return seam;
  }

  // remove vertical seam from current picture
  public void removeVerticalSeam(int[] seam) {
    if (seam == null || seam.length != height() || width() <= 1) {
      throw new IllegalArgumentException("Invalid vertical seam");
    }

    Picture newPicture = new Picture(width() - 1, height());
    for (int y = 0; y < height(); y++) {
      int k = 0; // Index for the new picture
      for (int x = 0; x < width(); x++) {
        if (x != seam[y]) {
          newPicture.setRGB(k++, y, picture.getRGB(x, y));
        }
      }
    }

    picture = newPicture;
  }

  private static class Pair implements Comparable<Pair> {
    public final double energy;
    public final int prev;

    public Pair(double energy, int prev) {
      this.energy = energy;
      this.prev = prev;
    }

    @Override
    public int compareTo(Pair o) {
      if (this.energy > o.energy) {
        return 1;
      } else if (this.energy < o.energy) {
        return -1;
      }
      return 0;
    }
  }

  // unit testing (optional)
  public static void main(String[] args) {

  }

}