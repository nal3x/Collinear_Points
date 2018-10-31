import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;

/*************************************************************************
 *  Finds all lines containing 4 or more collinear points. Uses Point's Comparable interface and
 *  Point.slopeOrder's Comparator to sort points either by natural order or by the slope they make
 *  with an invoking point respectively. Sorting all points (n) against every other point (nlogn)
 *  according to slope, FastCollinearPoints achieves n^2logn worst-case running time. Points that
 *  have equal slopes with respect to an invoking point are collinear, and sorting brings these
 *  points together.
 *
 *************************************************************************/

public class FastCollinearPoints {
    private static final int MIN_SEGMENT_POINTS = 4;
    private final Point[] points;
    private final Stack<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] inputPoints) { // finds all line segments containing >=4 points
        if (inputPoints == null) throw new IllegalArgumentException();
        // creating a defensive copy of the input points array
        points = new Point[inputPoints.length]; // instance variable
        for (int i = 0; i < inputPoints.length; i++) {
            //  throw a java.lang.IllegalArgumentException if any point in the array is null
            if (inputPoints[i] == null) throw new IllegalArgumentException();
            // create defensive copy of mutable input array
            points[i] = inputPoints[i];
        }
        Arrays.sort(points); // sort according to natural order to find duplicates
        for (int i = points.length - 1; i > 0; i--) {
            if (points[i].compareTo(points[i - 1]) == 0) throw new IllegalArgumentException();
        }
        lineSegments = new Stack<>();
        // used to maintain points order since we 'll be sorting Point[] points. Can eliminate by
        // re-sorting again according to natural order.
        Point[] initialArray = makeArrayCopy(points);
        for (Point p : initialArray) {
            // points is sorted according to slope versus invoking point p.
            Arrays.sort(points, p.slopeOrder());
            findSegments();
        }
    }

    public int numberOfSegments() { // the number of line segments
        return lineSegments.size();
    }

    public LineSegment[] segments() { // uses stack of segments to return an array of them
        LineSegment[] segmentsArray = new LineSegment[numberOfSegments()];
        int i = 0;
        for (LineSegment segment : lineSegments) { // iterator from Stack
            segmentsArray[i++] = segment;
        }
        return segmentsArray;
    }

    public static void main(String[] args) {
        // // read the n points from a file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // Point[] points = new Point[n];
        // for (int i = 0; i < n; i++) {
        //     int x = in.readInt();
        //     int y = in.readInt();
        //     points[i] = new Point(x, y);
        // }
        //
        // // draw the points
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();
        //
        // // print and draw the line segments
        // FastCollinearPoints collinear = new FastCollinearPoints(points);
        // for (LineSegment segment : collinear.segments()) {
        //     StdOut.println(segment);
        //     segment.draw();
        // }
        // StdDraw.show();
    }

    private static Point[] makeArrayCopy(Point[] points) { // hard copy
        Point[] copy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copy[i] = points[i];
        }
        return copy;
    }

    private void showSlopes() { // helper method for debugging
        System.out.println();
        double[] slopesArray = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            slopesArray[i] = points[i].slopeTo(points[0]);
        }
        for (double d : slopesArray) {
            System.out.print(d + ",");
        }
        System.out.println();
    }

    private void printArrayOfPoint(Point[] pointsArray) { // helper method for debugging
        for (int i = 0; i < pointsArray.length; i++) {
            System.out.println(pointsArray[i]);
        }
    }

    private void findSegments() { // fills stack with segments using sorted by slope points array
        Point invokingPoint = points[0]; // point[0] has -Infinity slope to itself
        // j scans array elements after i that are equal, finding collinear points w/ equal slopes
        for (int i = 1, j = i; i < points.length;) {
            // printArrayOfPoint(points);
            // showSlopes();
            while (j < points.length // &&'s short-circuit avoids ArrayIndexOutOfBoundsException
                    && points[i].slopeTo(invokingPoint) == points[j].slopeTo(invokingPoint)) {
                j++;
            }
            // points with equal slopes to invoking point in [i,j-1]
            if (j - i >= MIN_SEGMENT_POINTS - 1) { // at least 3+1 (invoking) collinear points
                Arrays.sort(points, i, j); // order subarray by natural order
                if (invokingPoint.compareTo(points[i]) < 0) {
                    // only include segments with invoking point as the lesser avoiding subsegments
                    lineSegments.push(new LineSegment(points[0], points[j - 1]));
                }
            }
            i = j; // after a continuous segment is found, i jumps to j
        }
    }
}