public class PointTest {
    public static void main(String[] args) {
        // testPointCompare();
        testSlope();
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
}
