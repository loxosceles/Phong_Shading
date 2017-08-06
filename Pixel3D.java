import javafx.geometry.Point3D;
import java.awt.*;
import static java.lang.Math.min;

/**
 * Class containing and generating all information related to a pixel or screen coordinate
 */

class Pixel3D extends Point3D {
    private Color color;
    private double x, y, z;
    private Point3D normalVector;
    private Point3D viewVector;
    private double diffuseComponent;
    private double specularcomponent;
    private double ambientComponent;

    Pixel3D(double x, double y, double z) {
        super(x, y, z);
        this.x = super.getX();
        this.y = super.getY();
        this.z = super.getZ();
        this.color = new Color(0, 0, 0);
        calculateNormal(SpherePane.center);
        this.viewVector = new Point3D(100, 100, 5000).normalize();
    }

    /**
     * Add newly generated components to the rgb information already stored
     * in the Pixel3D
     *
     * @param c, color, calls the color setter
     */
    private void addShadingComponent(Color c) {
        // since the different intensities for ambient, diffuse and
        // reflection can exceed the legal value for color representation
        // we need to limit it to the maximum
        int r = min(255, this.getColor().getRed() + c.getRed());
        int g = min(255, this.getColor().getGreen() + c.getGreen());
        int b = min(255, this.getColor().getBlue() + c.getBlue());

        setColor(new Color(r, g, b));
    }

    /**
     * calculates the normalized normal vector from the center of the
     * sphere to the surface point
     *
     * @param center, coordinates of the center of the sphere
     */
    private void calculateNormal(Point3D center) {
        this.normalVector = (this.subtract(center)).normalize();
    }

    /**
     * getter for pixel color
     *
     * @return color of pixel
     */
    Color getColor() {
        return this.color;
    }

    /**
     * color setter for the pixel
     *
     * @param c, color rgb
     */
    private void setColor(Color c) {
        this.color = c;
    }

    /**
     * getter for the normalized normal vector
     *
     * @return normal vector
     */
    Point3D getNormalVector() {
        return this.normalVector;
    }

    /**
     * getter for x coordinate
     *
     * @return x
     */
    int getx() {
        return (int) this.x;
    }

    /**
     * getter for y coordinate
     *
     * @return y
     */
    int gety() {
        return (int) this.y;
    }

    /**
     * adds offset to the pixel in order to move the sphere to
     * the center of the sub pane
     *
     * @param xOffset
     * @param yOffset
     */
    void addOffset(int xOffset, int yOffset) {
        this.x = super.getX() + xOffset;
        this.y = super.getY() + yOffset;
        this.z = super.getZ();
    }

    /**
     * calculates the diffuse component
     *
     * @param diffuseCoefficient, depends on the material
     * @param diffReflComp,       calculated from angle of incidence
     * @param ls,                 light source
     */
    void calculateDiffuseComp(double diffuseCoefficient, double diffReflComp, LightSource ls) {

        double factor = diffuseCoefficient * diffReflComp;
        this.addShadingComponent(new Color((int) (factor * ls.r), (int) (factor * ls.g), (int) (factor * ls.b)));
    }

    /**
     * calculates the specular component
     *
     * @param specularCoefficient, depends on the material
     * @param reflectionVector,    reflected light vector
     * @param viewerVector,        the observer, in the middle of the sub pane, frontal
     * @param shininess,           factor which determines how big the reflection area is
     * @param ls,                  light source
     */
    void calculateSpecularComp(double specularCoefficient, Point3D reflectionVector, Point3D viewerVector, int shininess, LightSource ls) {
        viewerVector = this.viewVector;
        double angle = reflectionVector.dotProduct(viewerVector);
        if (angle > 0) {
            angle = Math.pow(angle, shininess);
            double factor = specularCoefficient * angle;
            this.addShadingComponent(new Color((int) (factor * ls.r), (int) (factor * ls.g), (int) (factor * ls.b)));
        }
    }

    /**
     * calculates the ambient component
     *
     * @param ambientCoefficient, depends on the material
     * @param ls,                 light source
     */
    void calculateAmbientComp(double ambientCoefficient, LightSource ls) {

        this.addShadingComponent(new Color(
                (int)(ambientCoefficient * ls.r),
                (int)(ambientCoefficient * ls.g),
                (int)(ambientCoefficient* ls.b)));
    }
}

