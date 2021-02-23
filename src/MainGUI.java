
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainGUI {

    JFrame fr;
    ImageIcon logo;
    SignInGUI sIn;
    SignUpGUI sUp;
    ButtonHandler hnd;
    DataConnector dCon;
    HomePage HP;
    Dashboard DB;
    Admin Ad;
    ChangePass changePassFrame;
    AddMovie addMovieFrame;
    MovieDetails MD;
    Scheduling SFr;
    String id;

    MainGUI() throws SQLException {
        initGUI();
    }

    public void initGUI() throws SQLException {
        fr = new JFrame("Book It");
        logo = new ImageIcon(getClass().getClassLoader().getResource(".\\res\\logo.jpg"));
        fr.setIconImage(logo.getImage());
        sUp = new SignUpGUI();
        sIn = new SignInGUI();
        hnd = new ButtonHandler();
        dCon = new DataConnector();
        HP = new HomePage(hnd);
        DB = new Dashboard(hnd);
        Ad = new Admin(hnd);

        sIn.signUpBtn.addActionListener(hnd);
        sIn.signInBtn.addActionListener(hnd);
        sUp.signInBtn.addActionListener(hnd);
        sUp.signUpBtn.addActionListener(hnd);

        sIn.signInPnl.setVisible(true);
        fr.add(sIn.signInPnl);
        fr.add(sUp.signUpPnl);
        fr.add(HP.mainPnl);
        fr.add(DB.dashboardPnl);
        fr.add(Ad.dashboardPnl);

        fr.setVisible(true);
        fr.setLayout(null);
        fr.setSize(1100, 680);
        fr.setLocationRelativeTo(null);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setResizable(false);

    }

    public class ButtonHandler implements ActionListener, MouseListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(sIn.signUpBtn)) {
                sIn.signInPnl.setVisible(false);
                sUp.signUpPnl.setVisible(true);
            } else if (e.getSource().equals(sUp.signInBtn)) {
                sUp.signUpPnl.setVisible(false);
                sIn.signInPnl.setVisible(true);
            } else if (e.getSource().equals(sUp.signUpBtn)) {

                if (sUp.userName.getText().trim().equals("") || sUp.cnic.getText().trim().equals("") || sUp.pass.getText().trim().equals("") || sUp.phone.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Fill Every Field", "SignUp", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    dCon.addUserRecord(sUp.userName.getText(), sUp.phone.getText(), sUp.cnic.getText(), sUp.pass.getText());
                    sUp.userName.setText("");
                    sUp.cnic.setText("");
                    sUp.pass.setText("");
                    sUp.phone.setText("");
                    sIn.signInPnl.setVisible(true);
                    sUp.signUpPnl.setVisible(false);
                }

            } else if (e.getSource().equals(sIn.signInBtn)) {

                if (sIn.CNIC.getText().trim().equals("") || sIn.pass.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Fill Every Field", "Sign In Failed", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    String uName = dCon.getUserName(sIn.CNIC.getText(), sIn.pass.getText());
                    String uID = dCon.getUserID(sIn.CNIC.getText());
                    id = uID;
                    sIn.pass.setText("");

                    if (uName != null) {
                        HP.setName(uName);
                        DB.setData(uName, uID);
                        Ad.setData(uName);
                        try {
                            HP.updateHomePnl();
                        } catch (SQLException ex) {
                            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if (dCon.isAdmin(id)) {
                            try {

                                Ad.updateDashboard();
                                sIn.signInPnl.setVisible(false);
                                Ad.dashboardPnl.setVisible(true);
                            } catch (SQLException ex) {
                                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            sIn.signInPnl.setVisible(false);

                            HP.mainPnl.setVisible(true);
                        }

                    }
                }
            } else if (e.getActionCommand().equals("LOG OUT")) {
                sIn.signInPnl.setVisible(true);
                HP.search.setText("");
                HP.mainPnl.setVisible(false);
                DB.dashboardPnl.setVisible(false);
                Ad.dashboardPnl.setVisible(false);
            } else if (e.getActionCommand().equals("Dashboard")) {
                try {
                    if (dCon.isAdmin(id)) {
                        Ad.updateDashboard();
                        Ad.dashboardPnl.setVisible(true);
                    } else {
                        DB.updateDashboard();
                        DB.dashboardPnl.setVisible(true);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                HP.mainPnl.setVisible(false);
            } else if (e.getActionCommand().equals("Go")) {
                try {
                    if (!HP.search.getText().trim().equals("")) {
                        HP.updateOnSearch();
                        HP.mainPnl.setVisible(false);
                        HP.mainPnl.setVisible(true);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else if (e.getActionCommand().equals("Home")) {
                HP.mainPnl.setVisible(true);
                Ad.dashboardPnl.setVisible(false);
                DB.dashboardPnl.setVisible(false);

            } else if (e.getActionCommand().equals("Change Password")) {
                changePassFrame = new ChangePass();
                changePassFrame.miniFrame.setLocationRelativeTo(fr);
                changePassFrame.miniFrame.setIconImage(logo.getImage());
                changePassFrame.cancel.addActionListener(hnd);
                changePassFrame.confirm.addActionListener(hnd);
            } else if (e.getActionCommand().equals("Add")) {
                addMovieFrame = new AddMovie();
                addMovieFrame.miniFrame.setLocationRelativeTo(fr);
                addMovieFrame.miniFrame.setIconImage(logo.getImage());
                addMovieFrame.cancel.addActionListener(hnd);
                addMovieFrame.choosePhoto.addActionListener(hnd);
                addMovieFrame.addRecordBtn.addActionListener(hnd);
            } else if (e.getActionCommand().equals("Cancel")) {
                if (addMovieFrame != null) {
                    addMovieFrame.miniFrame.dispose();
                } else if (changePassFrame != null) {
                    changePassFrame.miniFrame.dispose();
                }
            } else if (e.getActionCommand().equals("Confirm")) {
                int n = dCon.updatePassword(sIn.CNIC.getText(), changePassFrame.oldPass.getText(), changePassFrame.newPass.getText());
                if (n < 1) {
                    JOptionPane.showMessageDialog(null, "Invalid Password", "Password Update fail", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    changePassFrame.miniFrame.dispose();
                    JOptionPane.showMessageDialog(null, "Password Updated Successfully", "Password Update", JOptionPane.INFORMATION_MESSAGE);
                }

            } else if (e.getActionCommand().equals("\u2770 Home")) {
                try {
                    HP.updateHomePnl();
                    HP.mainPnl.setVisible(false);
                    HP.mainPnl.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (e.getActionCommand().equals("Upload")) {
                JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.home")));
                chooser.showOpenDialog(fr);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png");
                chooser.addChoosableFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                File pic = chooser.getSelectedFile();

                try {
                    if (pic != null) {
                        String path = pic.getAbsolutePath();
                        File image = new File(path);

                        addMovieFrame.preview.setText(pic.getName());
                        addMovieFrame.imageStream = new FileInputStream(image);
                    }

                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Select a .jpg file", "Selection failed", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (e.getActionCommand().equals("Add to Record")) {
                if (addMovieFrame.titleTextField.getText().trim().equals("") || addMovieFrame.genereTextField.getText().trim().equals("") || addMovieFrame.durationTextField.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Fill Every Field", "Insertion", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    dCon.addMovieRecord(addMovieFrame.titleTextField.getText(), addMovieFrame.genereTextField.getText(), addMovieFrame.durationTextField.getText(), addMovieFrame.imageStream);
                    addMovieFrame.titleTextField.setText("");
                    addMovieFrame.genereTextField.setText("");
                    addMovieFrame.durationTextField.setText("");
                }
            } else if (e.getActionCommand().equals("Confirm Booking")) {

                dCon.confirmBooking(id, MD.given_id);
                MD.MDetailsFr.dispose();
            } else if (e.getActionCommand().equals("Cancle Booking")) {

                dCon.cancelBooking(id, MD.given_id);
                try {
                    DB.updateDashboard();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                MD.MDetailsFr.dispose();
            } else if (e.getActionCommand().equals("Delete Movie")) {

                dCon.deleteMovie(MD.given_id);
                try {
                    Ad.updateDashboard();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                MD.MDetailsFr.dispose();
            } else if (e.getActionCommand().equals("Add to Schedule")) {
                schedule_a_movie(MD.given_id);
                MD.MDetailsFr.dispose();
            } else if (e.getActionCommand().equals("Confirm Schedule")) {
                dCon.addToSchedual(SFr.given_id,SFr.price_txt.getText(),SFr.stime_txt.getText(),SFr.etime_txt.getText(),SFr.datePicker.getJFormattedTextField().getText(),SFr.sHall.getSelectedItem().toString());
               JOptionPane.showMessageDialog(null, "Schedule added", "Completed",  JOptionPane.INFORMATION_MESSAGE);
                try {
                    Ad.updateDashboard();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                SFr.schedulingFr.dispose();
            }

        }

        public void dashboard_movie_Details_function(String sid) {
            MD = new MovieDetails();
            MD.MDetailsFr.setLocationRelativeTo(fr);
            MD.MDetailsFr.setIconImage(logo.getImage());
            MD.given_id = sid;
            JLabel movie_name, movie_picture, rate, date, time;
            JButton cancleBookBtn;

            try {

                ResultSet rs = dCon.getdMoviesDetailsBySchedual_ID(sid);
                rs.next();

                Blob b = rs.getBlob(2);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Times new roman", Font.BOLD, 28));
                rate = new JLabel("Price: " + rs.getString(3) + "/-");
                rate.setBounds(320, 65, 200, 20);
                date = new JLabel("Date: " + rs.getString(4));
                date.setBounds(320, 85, 200, 20);
                time = new JLabel("Time: " + rs.getString(5));
                time.setBounds(320, 105, 200, 20);

                cancleBookBtn = new JButton("Cancle Booking");
                cancleBookBtn.addActionListener(hnd);
                cancleBookBtn.setBounds(350, 400, 300, 50);
                cancleBookBtn.setBackground(Color.black);
                cancleBookBtn.setForeground(Color.WHITE);

                MD.MDetailsFr.add(movie_name);
                MD.MDetailsFr.add(movie_picture);
                MD.MDetailsFr.add(rate);
                MD.MDetailsFr.add(date);
                MD.MDetailsFr.add(time);
                MD.MDetailsFr.add(cancleBookBtn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void schedule_a_movie(String sid) {
            SFr = new Scheduling();
            SFr.schedulingFr.setLocationRelativeTo(fr);
            SFr.schedulingFr.setIconImage(logo.getImage());
            SFr.given_id = sid;
            JLabel movie_name, movie_picture;
            JButton scheduleBtn;

            try {

                ResultSet rs = dCon.getMoviesDetailsByMovie_ID(sid);
                rs.next();

                Blob b = rs.getBlob(2);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Times new roman", Font.BOLD, 28));

                rs = dCon.getHallsName();
                while (rs.next()) {
                    SFr.sHall.addItem(rs.getString(1));
                }

                scheduleBtn = new JButton("Confirm Schedule");
                scheduleBtn.addActionListener(hnd);
                scheduleBtn.setBounds(350, 400, 300, 50);
                scheduleBtn.setBackground(Color.black);
                scheduleBtn.setForeground(Color.WHITE);
                scheduleBtn.addActionListener(hnd);

                SFr.schedulingFr.add(movie_name);
                SFr.schedulingFr.add(movie_picture);
                SFr.schedulingFr.add(scheduleBtn);

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void movie_basic_Details_function(String sid) {
            MD = new MovieDetails();
            MD.MDetailsFr.setLocationRelativeTo(fr);
            MD.MDetailsFr.setIconImage(logo.getImage());
            MD.given_id = sid;
            JLabel movie_name, movie_picture;
            JButton scheduleBtn, deleteMovieBtn, editMovieBtn;

            try {

                ResultSet rs = dCon.getMoviesDetailsByMovie_ID(sid);
                rs.next();

                Blob b = rs.getBlob(2);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Times new roman", Font.BOLD, 28));

                scheduleBtn = new JButton("Add to Schedule");
                scheduleBtn.addActionListener(hnd);
                scheduleBtn.setBounds(350, 280, 300, 50);
                scheduleBtn.setBackground(Color.black);
                scheduleBtn.setForeground(Color.WHITE);

                editMovieBtn = new JButton("Edit Details");
                editMovieBtn.addActionListener(hnd);
                editMovieBtn.setBounds(350, 340, 300, 50);
                editMovieBtn.setBackground(Color.black);
                editMovieBtn.setForeground(Color.WHITE);

                deleteMovieBtn = new JButton("Delete Movie");
                deleteMovieBtn.addActionListener(hnd);
                deleteMovieBtn.setBounds(350, 400, 300, 50);
                deleteMovieBtn.setBackground(Color.black);
                deleteMovieBtn.setForeground(Color.WHITE);

                MD.MDetailsFr.add(movie_name);
                MD.MDetailsFr.add(movie_picture);
                MD.MDetailsFr.add(scheduleBtn);
                MD.MDetailsFr.add(editMovieBtn);
                MD.MDetailsFr.add(deleteMovieBtn);

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void homePage_movie_Details_function(String sid) {

            MD = new MovieDetails();
            MD.MDetailsFr.setLocationRelativeTo(fr);
            MD.MDetailsFr.setIconImage(logo.getImage());
            MD.given_id = sid;
            JLabel movie_name, movie_picture, rate, date, time;
            JButton bookBtn;

            try {

                ResultSet rs = dCon.getdMoviesDetailsBySchedual_ID(sid);
                rs.next();

                Blob b = rs.getBlob(2);
                Image img = ImageIO.read(b.getBinaryStream()).getScaledInstance(300, 500, Image.SCALE_SMOOTH);

                movie_picture = new JLabel(new ImageIcon(img));
                movie_picture.setBounds(0, 0, 300, 500);
                movie_name = new JLabel(rs.getString(1));
                movie_name.setBounds(320, 20, 250, 40);
                movie_name.setFont(new Font("Times new roman", Font.BOLD, 28));
                rate = new JLabel("Price: " + rs.getString(3) + "/-");
                rate.setBounds(320, 65, 200, 20);
                date = new JLabel("Date: " + rs.getString(4));
                date.setBounds(320, 85, 200, 20);
                time = new JLabel("Time: " + rs.getString(5));
                time.setBounds(320, 105, 200, 20);

                bookBtn = new JButton("Confirm Booking");
                bookBtn.addActionListener(hnd);
                bookBtn.setBounds(350, 400, 300, 50);
                bookBtn.setBackground(Color.black);
                bookBtn.setForeground(Color.WHITE);

                MD.MDetailsFr.add(movie_name);
                MD.MDetailsFr.add(movie_picture);
                MD.MDetailsFr.add(rate);
                MD.MDetailsFr.add(date);
                MD.MDetailsFr.add(time);
                MD.MDetailsFr.add(bookBtn);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            JPanel temp = (JPanel) e.getSource();
            JLabel flag = (JLabel) temp.getComponent(3);
            if (flag.getText().equals("h")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                homePage_movie_Details_function(selected_id.getText());
            } else if (flag.getText().equals("d")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                dashboard_movie_Details_function(selected_id.getText());
            } else if (flag.getText().equals("am")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                movie_basic_Details_function(selected_id.getText());
            } else if (flag.getText().equals("sm")) {
                JLabel selected_id = (JLabel) temp.getComponent(2);
                dashboard_movie_Details_function(selected_id.getText());
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
//            JPanel temp = (JPanel) e.getSource();
//            JLabel selected_id = (JLabel) temp.getComponent(2);
//            homePage_movie_Details_function(selected_id.getText());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // System.out.println(e.getSource());
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Border blackline = BorderFactory.createBevelBorder(0);
            JPanel temp = (JPanel) e.getSource();
            temp.setBorder(blackline);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JPanel temp = (JPanel) e.getSource();
            temp.setBorder(null);
        }

    }

}
