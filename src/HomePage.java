
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
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class HomePage {

    JPanel mainPnl;
    ScrollPane scrollPane;
    JPanel innerPnl;
    JLabel userNameLbl;
    JTextField search;
    JButton dashBtn;
    JButton lOutBtn;
    JButton searchBtn;
    JButton homeReload;
    DataConnector dCon;
    ImageIcon icon;
    ResultSet rs;
    ImageIcon ic;
    Blob bl;
    String searchText;
    String uID;
    MainGUI.ButtonHandler hnd;

    HomePage(MainGUI.ButtonHandler h) throws SQLException {
        hnd = h;
        initGUI();
    }

    private void initGUI() throws SQLException {

        mainPnl = new JPanel();
        innerPnl = new JPanel();
        innerPnl.setLayout(null);
        dCon = new DataConnector();

        lOutBtn = new JButton("LOG OUT");
        lOutBtn.setFocusPainted(false);
        lOutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dashBtn = new JButton("Dashboard");
        dashBtn.setFocusPainted(false);
        dashBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        search = new JTextField();
        searchBtn = new JButton("Go");
        searchBtn.setFocusPainted(false);
        searchBtn.setBorder(new LineBorder(Color.WHITE));
        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        homeReload = new JButton("\u2770 Home");
        homeReload.setBackground(Color.BLACK);
        homeReload.setForeground(Color.WHITE);
        homeReload.setFocusPainted(false);

        searchBtn.addActionListener(hnd);
        lOutBtn.addActionListener(hnd);
        dashBtn.addActionListener(hnd);
        homeReload.addActionListener(hnd);

        scrollPane = new ScrollPane();
        scrollPane.setBounds(0, 100, 1095, 500);

        userNameLbl = new JLabel("");
        userNameLbl.setFont(new Font("Times new roman", Font.BOLD, 18));
        userNameLbl.setOpaque(true);
        userNameLbl.setBackground(Color.black);
        userNameLbl.setForeground(Color.WHITE);

        mainPnl.setLayout(null);
        mainPnl.setBackground(Color.BLACK);
        mainPnl.setSize(1100, 900);
        innerPnl.setBounds(100, 100, 1000, 500);

        userNameLbl.setBounds(0, 30, 500, 30);
        search.setBounds(503, 30, 304, 30);
        searchBtn.setBounds(807, 30, 50, 30);
        searchBtn.setBackground(Color.BLACK);
        searchBtn.setForeground(Color.WHITE);

        dashBtn.setBounds(870, 30, 115, 30);
        dashBtn.setBorderPainted(false);
        dashBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dashBtn.setBackground(Color.BLACK);
        dashBtn.setForeground(Color.WHITE);

        lOutBtn.setBounds(985, 30, 115, 30);
        lOutBtn.setBorderPainted(false);
        lOutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lOutBtn.setBackground(Color.BLACK);
        lOutBtn.setForeground(Color.WHITE);

        mainPnl.add(userNameLbl);
        mainPnl.add(dashBtn);
        mainPnl.add(search);
        mainPnl.add(searchBtn);

        mainPnl.add(lOutBtn);
        mainPnl.setVisible(false);

    }

    public void setName(String uName) {
        userNameLbl.setText("       You're signed in as " + uName);
    }

    public void updateHomePnl() throws SQLException, IOException {
        innerPnl.removeAll();
        int posX = 100, posY = 10;
        int n = dCon.getNumberOfMoviesTobePlayed();
        if (n <= 0) {
            JLabel empty = new JLabel("<html>:) Greetings <br/> We are glad to see you here,<br/> but currently there is no schedualed movie :(</html>");
            empty.setFont(new Font("Times new roman", Font.BOLD, 25));
            empty.setBounds(posX, posY, 900, 100);
            innerPnl.add(empty);
        } else {
            rs = dCon.getScheduledMoviesRecords();
            rs.next();

            JLabel[] Schedual_id = new JLabel[n];
            JLabel[] movie_picture = new JLabel[n];
            JLabel[] movie_name = new JLabel[n];
            JPanel[] movie_pnl = new JPanel[n];
            JLabel[] flag=new JLabel[n];
            
            for (int i = 0; i < n; i++) {
                flag[i]=new JLabel("h");
                Blob b = rs.getBlob(3);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);

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

                innerPnl.add(movie_pnl[i]);
                rs.next();
            }
        }
        innerPnl.setPreferredSize(new Dimension(950, posY + 250));
        scrollPane.add(innerPnl);
        mainPnl.add(scrollPane);

    }

    public void updateOnSearch() throws SQLException {

        String mID;
        searchText = search.getText().trim();
        innerPnl.removeAll();
        int posX = 100, posY = 10;
        int n = dCon.getNumberOfSearchResults(searchText);
      //  mID = String.valueOf(dCon.getMovieID(searchText));
       
        if (n <= 0) {

            JLabel empty = new JLabel("<html>:(<br/>  No results founds, try with different spellings</html>");
            empty.setFont(new Font("Times new roman", Font.BOLD, 25));
            empty.setBounds(posX, posY, 500, 100);
            homeReload.setBounds(posX, 150, 100, 40);
            innerPnl.add(homeReload);
            innerPnl.add(empty);
        } else {
            rs = dCon.getSearchedMovie(searchText);
            rs.next();

            JLabel[] Schedual_id = new JLabel[n];
            JLabel[] movie_picture = new JLabel[n];
            JLabel[] movie_name = new JLabel[n];
            JPanel[] movie_pnl = new JPanel[n];
            JLabel[] flag=new JLabel[n];
            homeReload.setBounds(posX, posY, 100, 40);
            posY = posY + 110;
            innerPnl.add(homeReload);

            for (int i = 0; i < n; i++) {
                flag[i]=new JLabel("h");
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

                innerPnl.add(movie_pnl[i]);
                rs.next();
            }

        }
        innerPnl.setPreferredSize(new Dimension(950, posY + 250));
        scrollPane.add(innerPnl);
        mainPnl.add(scrollPane);
    }

}
