import java.util.ArrayList;
import java.util.List;

public class Jarvis {
    private boolean CCW(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        return val < 0;
    }

    public List<Integer> convexHull(List<Point> points) {
        int n = points.size();

        if (n < 3) {
            // Convex hull is not possible with less than 3 points
            return new ArrayList<>();
        }

        // Find the leftmost point
        int leftMost = 0;
        for (int i = 1; i < n; i++) {
            if (points.get(i).x < points.get(leftMost).x) {
                leftMost = i;
            }
        }

        int p = leftMost, q;
        List<Integer> hull = new ArrayList<>();

        do {
            hull.add(p);

            q = (p + 1) % n;

            for (int i = 0; i < n; i++) {
                if (CCW(points.get(p), points.get(i), points.get(q))) {
                    q = i;
                }
            }

            p = q;
        } while (p != leftMost);
        hull.add(p); // Add the leftmost point to the hull

        return hull;
    }
}

