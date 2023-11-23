import java.util.ArrayList;

public class BruteForceConvexHull {

    private boolean isCounterClockwise(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        return val > 0;
    }

    private boolean isInsideHull(ArrayList<Point> hull, Point p) {
        for (int i = 0; i < hull.size() - 1; i++) {
            if (isCounterClockwise(hull.get(i), hull.get(i + 1), p)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Integer> convexHull(ArrayList<Point> points) {
        int n = points.size();

        if (n < 3) {
            // Convex hull is not possible with less than 3 points
            return null;
        }

        ArrayList<Point> hull = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                ArrayList<Point> potentialHull = new ArrayList<>();
                potentialHull.add(points.get(i));
                potentialHull.add(points.get(j));

                for (int k = 0; k < n; k++) {
                    if (k != i && k != j && isInsideHull(potentialHull, points.get(k))) {
                        potentialHull.add(points.get(k));
                    }
                }

                if (potentialHull.size() > hull.size()) {
                    hull.clear();
                    hull.addAll(potentialHull);
                }
            }
        }

        // Convert hull points to indices
        ArrayList<Integer> result = new ArrayList<>();
        for (Point point : hull) {
            result.add(points.indexOf(point));
        }

        return result;
    }
}
