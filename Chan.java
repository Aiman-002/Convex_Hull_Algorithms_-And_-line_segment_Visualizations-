import java.util.ArrayList;
import java.util.List;

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Chan {
    private boolean CCW(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        return val < 0;
    }

    private List<Point> merge(List<Point> hull1, List<Point> hull2, int idx1, int idx2) {
        int sz1 = hull1.size();
        int sz2 = hull2.size();

        List<Point> mergedHull = new ArrayList<>();

        int i = idx1;
        int j = idx2;

        while (i < sz1 && j < sz2) {
            mergedHull.add(hull1.get(i));
            i++;

            if (i == sz1) {
                i = 0; // Wrap around
            }

            mergedHull.add(hull2.get(j));
            j++;

            if (j == sz2) {
                j = 0; // Wrap around
            }
        }

        return mergedHull;
    }


    private int tangentPoint(List<Point> hull, Point point, int currentIdx, boolean isLowerTangent) {
        int sz = hull.size();
        int left = 0;
        int right = sz;
        int prevTurn1 = 0;
        int nextTurn1 = 0;

        if (sz - 1 >= 0) {
            prevTurn1 = CCW(point, hull.get(sz - 1), hull.get(0)) ? 1 : -1;
        }
        nextTurn1 = CCW(point, hull.get(0), hull.get((left + 1) % right)) ? 1 : -1;

        while (left < right) {
            int mid = (left + right) / 2;

            int midPrevTurn = CCW(point, hull.get((mid - 1 + sz) % sz), hull.get(mid)) ? 1 : -1;
            int midNextTurn = CCW(point, hull.get(mid), hull.get((mid + 1) % sz)) ? 1 : -1;
            int midSideTurn = CCW(point, hull.get(left), hull.get(mid)) ? 1 : -1;

            if (midPrevTurn != -1 && midNextTurn != -1) {
                return mid;
            } else if ((midSideTurn == 1 && (nextTurn1 == -1 || prevTurn1 == nextTurn1)) ||
                    (midSideTurn == -1 && midPrevTurn == -1)) {
                right = mid;
            } else {
                left = mid + 1;
                prevTurn1 = -midNextTurn;

                if (left < sz) {
                    nextTurn1 = CCW(point, hull.get((left + 1) % sz), hull.get(left)) ? 1 : -1;
                } else {
                    return -1;
                }
            }
        }
        return left;
    }

    private int leftMostIndex(List<Point> hull) {
        int leftMost = 0;
        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(i).x < hull.get(leftMost).x) {
                leftMost = i;
            }
        }
        return leftMost;
    }

    private int rightMostIndex(List<Point> hull) {
        int rightMost = 0;
        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(i).x > hull.get(rightMost).x) {
                rightMost = i;
            }
        }
        return rightMost;
    }

    public List<Point> convexHull(List<Point> points) {
        int n = points.size();

        if (n <= 5) {
            // Use another convex hull algorithm for small inputs
            // For example, you can use the Jarvis March algorithm here
            Jarvis jarvis = new Jarvis();
            List<Integer> indices = jarvis.convexHull(new ArrayList<>(points));

            // Convert indices to points
            List<Point> result = new ArrayList<>();
            for (int index : indices) {
                result.add(points.get(index));
            }
            return result;
        }

        int mid = n / 2;

        List<Point> leftHalf = points.subList(0, mid);
        List<Point> rightHalf = points.subList(mid, n);

        // Recursively compute the convex hulls of the left and right halves
        List<Point> leftHull = convexHull(leftHalf);
        List<Point> rightHull = convexHull(rightHalf);

        // Combine the convex hulls of the left and right halves
        return merge(leftHull, rightHull, mid - 1, 0);
    }
}

