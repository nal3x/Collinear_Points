import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/*************************************************************************
 *  Examines 4 points at a time and checks whether they all lie on the same line segment, returning
 *  all such line segments. To do so it checks whether the three slopes between the points are
 *  equal.
 *
 *************************************************************************/

public class BruteCollinearPoints {
    private Stack<LineSegment> lineSegments;

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException();
        lineSegments = new Stack<>();
        // iterate through all combinations of 4 points (N choose 4) instead of all 4 tuples (N^4)
        // saving a factor of 4! = 24
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {

                        Point p = points[i]; Point q = points[j];
                        Point r = points[k]; Point s = points[l];

                        if (p == null || q == null || r == null || s == null) {
                            throw new IllegalArgumentException();
                        }

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
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
