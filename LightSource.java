import javafx.geometry.Point3D;

/**
 * Class containing all information related with the light source.
 * RGB values are included here, although they could be seen as part
 * of the spheres as well.
 */
public class LightSource {

    final int r;
    final int g;
    final int b;
    Point3D location;
    public double ambient_coeff;
    public double intensity;

    private final double[][] locations = {
            {700, -100, 857},        // 1
            {700, -2000, -857},      // 2
            {540, 331, -100},        // 3
            {510, -331, 200},        // 4
            {-500, 331, -100},       // 5
            {-500, 331, 1000},       // 6
            {500, -310, 600},        // 7
            {510, -801, 3010},       // 8
            {5100, -3010, -1000},    // 9
            {151, 1301, 901},      // 10
            {500, -301, -1010},      // 11
            {250, 301, -100},        // 12
            {550, 301, -1300},       // 13
            {1500, -301, 9010},      // 14
            {-500, 301, -1501},      // 15
            {-1500, -3001, 5000},    // 16
            {100, -120, 401},        // 17
            {-950, 301, -1040},      // 18
            {350, 301, -4130},       // 19
            {500, -301, 1000},       // 20
    };

    private final int[][] rgbValues = {
            {0, 200, 40},
            {40, 100, 255},
            {194, 165, 11},
            {90, 70, 1},
            {205, 9, 120},
            {50, 99, 0},
            {255, 0, 10},
            {0, 255, 0},
            {30, 30, 29},
            {77, 100, 133},
            {200, 200, 100},
            {250, 0, 10},
            {166, 120, 0},
            {10, 50, 10},
            {255, 255, 255},
            {0, 201, 245},
            {111, 180, 20},
            {43, 255, 0},
            {250, 250, 8},
            {134, 101, 199}
    };

    /**
     * Constructor of light source
     * Takes the number of the light source from the grid pane
     * and retrieves corresponding parameters so every sphere sub-pane
     * owns its own light source. Light sources are sequentially counted
     *
     * @param numberLightSource, number of light source
     */
    LightSource(int numberLightSource) {
        this.location = new Point3D(
                locations[numberLightSource][0],
                locations[numberLightSource][1],
                locations[numberLightSource][2]
        );
        this.location = addOffset(this.location, -100, -100, 0);
        this.r = rgbValues[numberLightSource][0];
        this.g = rgbValues[numberLightSource][1];
        this.b = rgbValues[numberLightSource][2];
    }

    /**
     * shifts the ligth source to the upper left corner for calculations
     * since this is where the sphere is calculated as well
     *
     * @param location, vector of the light source
     * @param xOffset,  x offset
     * @param yOffset,  y offset
     * @param zOffset,  x offset
     * @return vector of light source, shifted
     */
    private Point3D addOffset(Point3D location, int xOffset, int yOffset, int zOffset) {
        double x = location.getX() + xOffset;
        double y = location.getY() + yOffset;
        double z = location.getZ() + zOffset;
        return new Point3D(x, y, z);
    }
}
