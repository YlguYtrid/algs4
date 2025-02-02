import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 or more points
        if (points == null)
            throw new IllegalArgumentException("points is null");
        for (Point p : points)
            if (p == null)
                throw new IllegalArgumentException("points contains null");
        Point[] pointsSorted = points.clone();
        Arrays.sort(pointsSorted);
        for (int i = 0; i < pointsSorted.length - 1; i++)
            if (pointsSorted[i].equals(pointsSorted[i + 1]))
                throw new IllegalArgumentException("repeated points");
        if (pointsSorted.length < 4) {
            segments = new LineSegment[0];
            return;
        }
        ArrayList<LineSegment> list = new ArrayList<>();
        for (Point p : pointsSorted) {
            Point[] pointsBySlope = pointsSorted.clone();
            Arrays.sort(pointsBySlope, p.slopeOrder());
            int i = 1;
            while (i < pointsBySlope.length - 2) {
                int j = i + 1;
                while (j < pointsBySlope.length && pointsBySlope[i].slopeTo(p) == pointsBySlope[j].slopeTo(p))
                    j++;
                if (j - i >= 3 && p.compareTo(pointsBySlope[i]) < 0)
                    list.add(new LineSegment(p, pointsBySlope[j - 1]));
                i = j;
            }
        }
        segments = new LineSegment[list.size()];
        list.toArray(segments);
    }

    public int numberOfSegments() { // the number of line segments
        return segments.length;
    }

    public LineSegment[] segments() { // the line segments
        return segments.clone();
    }
}
