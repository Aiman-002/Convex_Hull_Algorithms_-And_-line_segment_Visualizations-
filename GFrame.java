import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class GFrame extends javax.swing.JFrame implements ActionListener, MouseListener {

    static int atm = 0;
    static int c = 0;
    static JLabel clear, lbl1;
    BruteForceConvexHull bruteForceConvexHull = new BruteForceConvexHull();
    ArrayList<Point> points = new ArrayList<>();
    int x, y;

    public GFrame() {
        setResizable(true);
        setTitle("Brute Force Convex Hull -> TimeComplexity O(n^3)");
        initComponents();
        jPanel1.addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
        x = e.getX();
        y = e.getY();
        Graphics g = jPanel1.getGraphics();
        g.setColor(Color.PINK);
        drawCenteredCircle(g, x, y, 10);
        points.add(new Point(e.getX(), e.getY()));
        lbl1 = new JLabel("." + atm);
        lbl1.setForeground(new Color(211, 125, 198));
        jPanel1.add(lbl1);
        lbl1.setLocation(e.getX() + 5, e.getY() + 5);
        lbl1.setSize(36, 14);
        atm = atm + 1;
    }

    public void drawCenteredCircle(Graphics g, int x, int y, int r) {
        x = x - (r / 2);
        y = y - (r / 2);
        g.fillOval(x, y, r, r);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    private void initComponents() {
        jPanel1 = new JPanel(null);
        jPanel1.setPreferredSize(new Dimension(500, 100));
        jPanel1.setBackground(Color.BLACK);
        jPanel1.setForeground(new Color(211, 125, 198));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JButton btnx = new JButton("Generate");
        btnx.setForeground(new Color(0, 0, 0));

        btnx.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                long startTime = System.currentTimeMillis();

                ArrayList<Integer> n = bruteForceConvexHull.convexHull(points);
                Graphics g = jPanel1.getGraphics();
                if (c == 0)
                    g.setColor(Color.PINK);
                else
                    g.setColor(Color.BLUE);
                for (int i = 0; i < n.size() - 1; i++) {
                    g.drawLine(points.get(n.get(i)).x, points.get(n.get(i)).y, points.get(n.get(i + 1)).x,
                            points.get(n.get(i + 1)).y);
                }

                // Connect the last point to the first one to complete the polygon
                g.drawLine(points.get(n.get(n.size() - 1)).x, points.get(n.get(n.size() - 1)).y,
                        points.get(n.get(0)).x, points.get(n.get(0)).y);

                c++;


                long endTime = System.currentTimeMillis();

                // Calculate the elapsed time in milliseconds
                long elapsedTime = endTime - startTime;

                // Print or use the elapsed time as needed
                System.out.println("Convex Hull Generation Time: " + elapsedTime + " milliseconds");
            }
        });


        JButton btnreset = new JButton("Reset");
        btnreset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                points.clear();
                atm = 0;
                jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
                jPanel1.removeAll();
                jPanel1.revalidate();
                repaint();
                jPanel1.repaint();
                c = 0;
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup().addGap(53)
                        .addComponent(btnx, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE).addGap(45)
                        .addComponent(btnreset, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE).addGap(52)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout
                                .createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnreset)
                                .addComponent(btnx))
                        .addContainerGap()));
        jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        getContentPane().setLayout(layout);

        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GFrame().setVisible(true);
            }
        });
    }

    private JPanel jPanel1;

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

