
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.ScrollPane;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dashboard {

    JPanel dashboardPnl;
    JPanel innerPanel;
    JLabel userNameLbl;
    JButton lOutBtn;
    JButton homeBtn;
    JButton pswdBtn;
    DataConnector dCon;
    ImageIcon icon;
    ResultSet rs;
    ImageIcon ic;
    Blob bl;
    ScrollPane scrollPane;
    String uName;
    String uID;
    MainGUI.ButtonHandler hnd;

    Dashboard(MainGUI.ButtonHandler h) throws SQLException {
        hnd = h;
        initGUI();
    }

    private void initGUI() throws SQLException {

        dashboardPnl = new JPanel();
        dashboardPnl.setBackground(Color.BLACK);
        dCon = new DataConnector();

        innerPanel = new JPanel();
        innerPanel.setLayout(null);
        innerPanel.setBounds(100, 100, 1000, 500);
        innerPanel.setVisible(true);

        lOutBtn = new JButton("LOG OUT");
        lOutBtn.setFocusPainted(false);
        lOutBtn.setBorderPainted(false);
        lOutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        homeBtn = new JButton("Home");
        homeBtn.setFocusPainted(false);
        homeBtn.setBorderPainted(false);
        homeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pswdBtn = new JButton("Change Password");
        pswdBtn.setFocusPainted(false);
        pswdBtn.setBorderPainted(false);
        pswdBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        scrollPane = new ScrollPane();

        scrollPane.setBounds(0, 100, 1095, 500);
        userNameLbl = new JLabel("");
        userNameLbl.setFont(new Font("Times new roman", Font.BOLD, 18));
        userNameLbl.setOpaque(true);
        userNameLbl.setBackground(Color.black);
        userNameLbl.setForeground(Color.WHITE);

        userNameLbl.setText("       You're signed in as " + this.uName);

        dashboardPnl.setLayout(null);

        dashboardPnl.setSize(1100, 680);

        userNameLbl.setBounds(0, 30, 500, 30);

        pswdBtn.setBounds(710, 30, 150, 30);
        pswdBtn.setBackground(Color.BLACK);
        pswdBtn.setForeground(Color.WHITE);

        homeBtn.setBounds(870, 30, 100, 30);
        homeBtn.setBackground(Color.BLACK);
        homeBtn.setForeground(Color.WHITE);

        lOutBtn.setBounds(980, 30, 100, 30);
        lOutBtn.setBackground(Color.BLACK);
        lOutBtn.setForeground(Color.WHITE);

        pswdBtn.addActionListener(hnd);
        lOutBtn.addActionListener(hnd);
        homeBtn.addActionListener(hnd);

        dashboardPnl.add(userNameLbl);
        dashboardPnl.add(pswdBtn);
        dashboardPnl.add(homeBtn);
        dashboardPnl.add(lOutBtn);
        dashboardPnl.setVisible(false);

    }

    public void updateDashboard() throws SQLException {

        innerPanel.removeAll();
        int posX = 100, posY = 10;
        int n = dCon.getNumberOfTicketsForUser(uID);
        if (n <= 0) {
            JLabel empty = new JLabel("<html>:(<br/> You have no bookings<br/> Go to Home page to book a movie</html>");
            empty.setFont(new Font("Times new roman", Font.BOLD, 25));
            empty.setBounds(posX, posY, 500, 100);
            innerPanel.add(empty);
        } else {
            rs = dCon.getBookedMoviesRecords(uID);
            rs.next();

            JLabel[] Schedual_id = new JLabel[n];
            JLabel[] movie_picture = new JLabel[n];
            JLabel[] movie_name = new JLabel[n];
            JPanel[] movie_pnl = new JPanel[n];
            JLabel[] flag = new JLabel[n];

            for (int i = 0; i < n; i++) {
                flag[i] = new JLabel("d");
                Blob b = rs.getBlob(3);
                Image img;
                try {
                    img = ImageIO.read(b.getBinaryStream()).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(img);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                Schedual_id[i] = new JLabel(rs.getString(1));
                movie_picture[i] = new JLabel(icon);
                movie_picture[i].setBounds(0, 0, 200, 200);

                movie_name[i] = new JLabel("  " + rs.getString(2));
                movie_name[i].setBounds(0, 0 + 200, 200, 35);
                movie_name[i].setFont(new Font("Times new roman", Font.BOLD, 18));

                Schedual_id[i].setVisible(false);
                movie_pnl[i] = new JPanel();
                movie_pnl[i].setLayout(null);
                movie_pnl[i].add(movie_picture[i]);
                movie_pnl[i].add(movie_name[i]);
                movie_pnl[i].add(Schedual_id[i]);
                movie_pnl[i].add(flag[i]);
                movie_pnl[i].setBackground(Color.WHITE);
                movie_pnl[i].setBounds(posX, posY, 200, 235);
                movie_pnl[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                movie_pnl[i].addMouseListener(hnd);
                posX = (posX + 225) % 900;

                if (posX == 100 && i != n - 1) {
                    posY = posY + 250;
                }

                innerPanel.add(movie_pnl[i]);
                rs.next();
            }
        }
        innerPanel.setPreferredSize(new Dimension(950, posY + 250));
        scrollPane.add(innerPanel);
        dashboardPnl.add(scrollPane);
    }

    public void setData(String uName, String ID) {
        this.uName = uName;
        this.uID = ID;
        userNameLbl.setText("       You're signed in as " + this.uName);
    }

}
