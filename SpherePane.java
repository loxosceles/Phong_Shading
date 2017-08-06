import javafx.geometry.Point3D;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import static java.lang.Math.max;

/**
 * Class related to the 20 sub-panes holding the spheres
 */

class SpherePane extends JPanel {

    static final Point3D center = new Point3D(0, 0, 0);
    private final double diffuseCoefficient;
    private final double ambientCoefficient;
    private final double specularCoefficient;
    private final int shininessExponent;
    private static int id = 0;
    private Color defaultBackground;
    private int radius;
    Color color;
    private LightSource lightSource;

    private final double[] ambientCoefficients = {
            0.098, 0.04, 0.08, 0.07, 0.04, 0.13, 0.32, 0.11, 0.5999, 0.274,
            0.078, 0.083, 0.039, 0.57, 0.0966, 0.512, 0.41, 0.0699, 0.12, 0.187
    };

    private final double[] diffuseCoefficients = {
            0.99, 0.4, 0.8, 0.3, 0.9, 0.53, 0.72, 0.19, 0.8999, 0.777,
            0.58, 0.93, 0.19, 0.87, 0.666, 0.812, 0.01, 0.399, 0.2, 0.97
    };

    private final double[] specularCoefficients = {
            0.59, 0.19, 0.78, 0.93, 0.69, 0.23, 0.92, 0.69, 0.2999, 0.5,
            0.48, 0.93, 0.19, 0.87, 0.666, 0.812, 0.01, 0.399, 0.2, 0.97
    };

    private final int[] shininessExponents = {
            2, 3, 1, 6, 4, 3, 0, 3, 0, 8,
            4, 3, 7, 2, 1, 6, 7, 8, 4, 30
    };

    SpherePane(LightSource lightSource) {
        id += 1;
        this.radius = 90;
        this.lightSource = lightSource;
        this.diffuseCoefficient = diffuseCoefficients[id - 1];
        this.ambientCoefficient = ambientCoefficients[id - 1];
        this.specularCoefficient = specularCoefficients[id - 1];
        this.shininessExponent = shininessExponents[id - 1];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background for circle
        //g.setColor(black);
        //g.setColor(Color.white);

        // ArrayList with all pixel3Ds to be painted after a slider move
        ArrayList<Pixel3D> pixel3Ds = evaluatePoint();

        // Plots all pixel3Ds currently held in the ArrayList
        for (Pixel3D pixel3D : pixel3Ds) {
            g.setColor(pixel3D.getColor());
            g.fillRect(pixel3D.getx(), pixel3D.gety(), 1, 1);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(200, 200);
    }

    /**
     * Evaluate every pixel on the canvass if it fulfills the equation.
     * The xOffset and yOffset must be set in order to center the circle
     * on the canvass.
     *
     * @return ArrayList pixel3Ds
     */
    private ArrayList<Pixel3D> evaluatePoint() {
        ArrayList<Pixel3D> pixel3Ds = new ArrayList<Pixel3D>();
        Point3D normal_v;
        Point3D ray_v;
        Point3D p;
        Pixel3D pixel3D;
        int xOffset = 100;
        int yOffset = 100;

        // check every pixel3D on the canvass
        for (int i = -xOffset; i < xOffset; i++) {
            for (int j = -yOffset; j < yOffset; j++) {
                // both i and j must comply
                if (i >= Math.pow(i, 2) + Math.pow(j, 2) - Math.pow(radius, 2) &&
                        j >= Math.pow(i, 2) + Math.pow(j, 2) - Math.pow(radius, 2)) {
                    // then the point is added to the ArrayList

                    // calculates the z coordinate for the 2D point
                    Pixel3D p3d = new Pixel3D((double) i, (double) j, calculateZCoordinate(i, j, radius));

                    // calling shading processing method
                    pixel3D = processPixel(p3d);

                    // adjust to canvass since the calculation is done with reference
                    // to the top left corner
                    pixel3D.addOffset(100, 100);

                    // adding the processed pixel to the plot arrayList
                    pixel3Ds.add(pixel3D);

                }
            }
        }
        return pixel3Ds;
    }

    /**
     * Method which processes the pixel, calling the methods for generating the
     * ray vector, the normal vector, the reflection vector and also the three
     * methods which do the calculations of the shading components
     *
     * @param point, unprocessed point
     * @return point, the point (pixel) with all shading components added
     */
    private Pixel3D processPixel(Pixel3D point) {

        Point3D rayVector = (lightSource.location.subtract(point)).normalize();
        Point3D normalVector = point.getNormalVector();

        double diffReflectionCoeff = max(0, normalVector.dotProduct(rayVector));

        point.calculateDiffuseComp(this.diffuseCoefficient, diffReflectionCoeff, lightSource);
        point.calculateAmbientComp(this.ambientCoefficient, lightSource);

        Point3D reflectionVector = calculateReflectionVector(rayVector, normalVector);

        point.calculateSpecularComp(this.specularCoefficient, reflectionVector,
                null, shininessExponent, lightSource);

        return point;
    }

    /**
     * Calculates the reflection vector at a given surface point on a sphere
     *
     * @param rayVector,    ray hitting the  surface
     * @param normalVector, normal vector at the point where the ray hits the sphere
     * @return reflection vector
     */
    private Point3D calculateReflectionVector(Point3D rayVector, Point3D normalVector) {
        // (2 * (v dot n) * n) - v
        double scalar = (2 * rayVector.dotProduct(normalVector));
        return (normalVector.multiply(scalar)).subtract(rayVector);
    }


    /**
     * Calculates the z coordinate for the given 2D Point coordinates
     * on the surface of a sphere
     *
     * @param x coordinate
     * @param y coordinate
     * @param r radius
     * @return z coordinate
     */
    private double calculateZCoordinate(double x, double y, double r) {

        return Math.sqrt((r * r) - (x * x) - (y * y));
    }

}
