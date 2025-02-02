import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
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
        for (int i = 0; i < pointsSorted.length - 3; i++) {
            for (int j = i + 1; j < pointsSorted.length - 2; j++) {
                for (int k = j + 1; k < pointsSorted.length - 1; k++) {
                    for (int m = k + 1; m < pointsSorted.length; m++) {
                        Point p = pointsSorted[i];
                        Point q = pointsSorted[j];
                        Point r = pointsSorted[k];
                        Point s = pointsSorted[m];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s))
                            list.add(new LineSegment(p, s));
                    }
                }
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
