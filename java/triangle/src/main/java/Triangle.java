public class Triangle {
    private double x;
    private double y;
    private double z;

    public Triangle(double x, double y, double z) throws TriangleException {
        if (!isValid(x, y, z)) throw new TriangleException();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public TriangleKind getKind() {
        if (x == y && x == z) {
            return TriangleKind.EQUILATERAL;
        } else if (x == y || x == z || y == z) {
            return TriangleKind.ISOSCELES;
        }
        return TriangleKind.SCALENE;
    }

    private boolean isValid(double x, double y, double z) {
        return (x > 0) && (y > 0) && (z > 0)
                && (x + y) > z
                && (x + z) > y
                && (y + z) > x;
    }

}
