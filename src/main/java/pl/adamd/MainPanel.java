package pl.adamd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import static pl.adamd.Matrix.getTransform;
import static pl.adamd.Triangle.getTriangles;
import static pl.adamd.Utils.calculateRangeToProcessed;
import static pl.adamd.Utils.getzBuffer;

public class MainPanel {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        double[] x = new double[1], y = new double[1];

        JPanel renderPanel = new JPanel() {
            public void paintComponent(Graphics graphics) {

                Graphics2D graphics2D = getGraphics2D((Graphics2D) graphics);

                List<Triangle> triangleList = getTriangles();

                Matrix matrixTransform = getTransform(x[0], y[0]);

                BufferedImage img = getBufferedImage();

                double[] zBuffer = getzBuffer(img);

                for (Triangle triangle : triangleList) {
                    Vertex v1 = getTransformedVertex(matrixTransform, triangle.v1);
                    Vertex v2 = getTransformedVertex(matrixTransform, triangle.v2);
                    Vertex v3 = getTransformedVertex(matrixTransform, triangle.v3);

                    calculateRangeToProcessed(img, zBuffer, triangle, v1, v2, v3);

                    graphics2D.drawImage(img, 0, 0, null);
                }
            }

            private Graphics2D getGraphics2D(Graphics2D graphics) {
                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, getWidth(), getHeight());
                return graphics;
            }

            /**
             * @param matrixTransform matrix to transform
             * @param vertex vertex to transform
             * @return transformed vertex after transforming matrix
             */
            private Vertex getTransformedVertex(Matrix matrixTransform, Vertex vertex) {
                Vertex v1 = matrixTransform.transform(vertex);
                v1.x += getWidth() / 2.0;
                v1.y += getHeight() / 2.0;
                return v1;
            }

            private BufferedImage getBufferedImage() {
                return new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            }
        };
        pane.add(renderPanel, BorderLayout.CENTER);
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        /*
         * Change the angles represented by x and y by listening to the motion of the mouse
         */
        renderPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                double yi = 180.0 / renderPanel.getHeight();
                double xi = 180.0 / renderPanel.getWidth();
                x[0] = (int) (e.getX() * xi);
                y[0] = -(int) (e.getY() * yi);
                renderPanel.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }
}
