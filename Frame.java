import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;

public class Frame extends javax.swing.JFrame implements ActionListener, MouseListener {

    static int atm = 0;
    static int c = 0;
    static JLabel lbl1;
    Chan chan = new Chan();
    ArrayList<Point> points = new ArrayList<>();
    int x, y;

    public Frame() {
        setResizable(true);
        setTitle("Chan's Convex Hull Algorithm -> O(nlogn)");
        initComponents();
        jPanel1.addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
        x = e.getX();
        y = e.getY();
        Graphics g = jPanel1.getGraphics();
        g.setColor(Color.ORANGE);
        drawCenteredCircle(g, x, y, 10);
        points.add(new Point(e.getX(), e.getY()));
        lbl1 = new JLabel("." + atm);
        lbl1.setForeground(new Color(201, 29, 35));
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

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new JPanel(null);
        jPanel1.setPreferredSize(new Dimension(500, 100));
        jPanel1.setBackground(Color.BLACK);
        jPanel1.setForeground(new Color(201, 29, 35));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JButton btnx = new JButton("Generate");
        btnx.setForeground(new Color(0, 0, 0));

        btnx.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                long startTime = System.currentTimeMillis();

                ArrayList<Point> hull = (ArrayList<Point>) chan.convexHull(points);

                Graphics g = jPanel1.getGraphics();
                if (c == 0)
                    g.setColor(Color.ORANGE);
                else
                    g.setColor(Color.BLUE);

                for (int i = 0; i < hull.size() - 1; i++) {
                    g.drawLine(hull.get(i).x, hull.get(i).y, hull.get(i + 1).x, hull.get(i + 1).y);
                }

                c++;

                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;

                System.out.println("Convex Hull Generation Time: " + elapsedTime + " milliseconds");
            }
        });

        JButton btnreset = new JButton("Reset");
        btnreset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                points.clear();
                atm = 0;
                jPanel1.removeAll();
                jPanel1.revalidate();
                repaint();
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
                                .createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnreset).addComponent(btnx))
                        .addContainerGap()));
        jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        getContentPane().setLayout(layout);

        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }

    private JPanel jPanel1;
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
    }
}
