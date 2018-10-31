import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;

/*************************************************************************
 *  Examines 4 points at a time and checks whether they all lie on the same line segment, returning
 *  all such line segments. This is achieved by examining whether the slopes between the points are
 *  equal. The brute-force method has running time of n^4 in the worst case.
 *
 *************************************************************************/

public class BruteCollinearPoints {
    private final Stack<LineSegment> lineSegments;
    private final Point[] points;

    public BruteCollinearPoints(Point[] inputPoints) { // finds all line segments containing 4 points
        if (inputPoints == null) throw new IllegalArgumentException();
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
        // iterate through all combinations of 4 points (N choose 4) instead of all 4 tuples (N^4)
        // saving a factor of 4! = 24
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[m];
                        double slopePToQ = p.slopeTo(q);
                        double slopePToR = p.slopeTo(r);
                        if (slopePToQ == Double.NEGATIVE_INFINITY
                                || slopePToR == Double.NEGATIVE_INFINITY) {
                            throw new IllegalArgumentException();
                        }
                        if (slopePToQ == slopePToR) { // three collinear, check forth p -> s
                            double slopePToS = p.slopeTo(s);
                            if (slopePToS == Double.NEGATIVE_INFINITY) {
                                throw new IllegalArgumentException();
                            }
                            if (slopePToQ == slopePToS) { // four collinear points
                                Point[] collinearPoints = { p, q, r, s };
                                Arrays.sort(collinearPoints); // sort points to find endpoints
                                Point firstEnd = collinearPoints[0];
                                Point secondEnd = collinearPoints[collinearPoints.length - 1];
                                lineSegments.push(new LineSegment(firstEnd, secondEnd));

                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() { // the number of line segments
        return lineSegments.size();

    }
    public LineSegment[] segments() { // the line segments
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
        // BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        // for (LineSegment segment : collinear.segments()) {
        //     StdOut.println(segment);
        //     segment.draw();
        // }
        // StdDraw.show();

    }
}
