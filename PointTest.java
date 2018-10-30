import edu.princeton.cs.algs4.Knuth;

import java.util.Arrays;

public class PointTest {
    public static void main(String[] args) {
        // testPointCompare();
        // testSlope();
        testComparator();
    }
    private static void testPointCompare() {
        Point axisOrigin = new Point(0, 0);
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                Point a = new Point(x, y);
                System.out.println(a + " to " + axisOrigin + " -> "+ a.compareTo(axisOrigin));
            }
        }
    }

    private static void testSlope() {
        Point axisOrigin = new Point(0, 0);
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                Point a = new Point(x, y);
                System.out.println(a + " to " + axisOrigin + " -> "+ a.slopeTo(axisOrigin));
            }
        }
        Point twoThirds = new Point(3, 2); // slope 2/3 to origin
        System.out.println(twoThirds + " to " + axisOrigin + " -> "+ twoThirds.slopeTo(axisOrigin));

    }

    private static void testComparator() {
        Point originPoint = new Point(0, 0);
        Point lowSlopePoint = new Point(1, 1);
        Point highSlopePoint = new Point(1, 2);
        Point samePoint = new Point(0, 0);
        Point verticalPoint = new Point(0, 1);
        Point oneEightyPoint = new Point (-1, 0);

        Point[] pointsArray = {originPoint, highSlopePoint, lowSlopePoint, samePoint, verticalPoint
                ,oneEightyPoint};
        Knuth.shuffle(pointsArray);
        System.out.println("*********Shuffled points:*********");
        for (Point p : pointsArray) {
            System.out.println(p + " " + String.valueOf(p.slopeTo(originPoint)));
        }
        System.out.println("*********Sorted points:*********");
        Arrays.sort(pointsArray, originPoint.slopeOrder());
        for (Point p : pointsArray) {
            System.out.println(p + " " + String.valueOf(p.slopeTo(originPoint)));
        }


    }
}
