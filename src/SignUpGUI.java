
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUpGUI {

    JPanel signUpPnl;
    JPanel header;
    JLabel title;
    JLabel welcomeLbl;
    JTextField userName;
    JTextField phone;
    JTextField cnic;
    JPasswordField pass;
    JLabel userNameLbl;
    JLabel passLbl;
    JLabel phoneLbl;
    JLabel cnicLbl;
    JButton signInBtn;
    JButton signUpBtn;

    SignUpGUI() {
        initSignUpGUI();
    }

    public void initSignUpGUI() {
        signUpPnl = new JPanel();
        header = new JPanel();
        title = new JLabel("Book It.");
        title.setFont(new Font("Sarina", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        userName = new JTextField();
        phone = new JTextField();
        cnic = new JTextField();
        pass = new JPasswordField();

        welcomeLbl = new JLabel("SIGN UP");
        welcomeLbl.setFont(new Font("Monospaced", Font.BOLD, 24));
        userNameLbl = new JLabel("Name: ");
        phoneLbl = new JLabel("Phone # ");
        cnicLbl = new JLabel("CNIC: ");
        passLbl = new JLabel("Password : ");

        signInBtn = new JButton("Sign In");
        signInBtn.setFocusPainted(false);
        signInBtn.setBackground(Color.BLACK);
        signInBtn.setForeground(Color.WHITE);

        signUpBtn = new JButton("Sign Up");
        signUpBtn.setFocusPainted(false);
        signUpBtn.setBackground(Color.BLACK);
        signUpBtn.setForeground(Color.WHITE);

        signUpPnl.setLayout(null);

        signUpPnl.setSize(1100, 600);
        header.setLayout(null);
        header.setBackground(Color.BLACK);
        header.setBounds(0, 0, 1100, 100);

        title.setBounds(100, 30, 150, 40);

        welcomeLbl.setBounds(500, 150, 700, 25);
        userNameLbl.setBounds(400, 190, 100, 40);
        userName.setBounds(400, 220, 300, 40);
        phoneLbl.setBounds(400, 270, 100, 40);
        phone.setBounds(400, 300, 300, 40);
        cnicLbl.setBounds(400, 350, 100, 40);
        cnic.setBounds(400, 380, 300, 40);
        passLbl.setBounds(400, 430, 100, 40);
        pass.setBounds(400, 460, 300, 40);
        signUpBtn.setBounds(400, 510, 300, 30);
        signInBtn.setBounds(950, 60, 120, 30);
        signInBtn.setFont(new Font("Monospaced", Font.PLAIN, 18));
        signInBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signInBtn.setBorderPainted(false);

        header.add(title);
        header.add(signInBtn);
        signUpPnl.add(header);
        signUpPnl.add(welcomeLbl);
        signUpPnl.add(userNameLbl);
        signUpPnl.add(userName);
        signUpPnl.add(phoneLbl);
        signUpPnl.add(phone);
        signUpPnl.add(cnicLbl);
        signUpPnl.add(cnic);
        signUpPnl.add(passLbl);
        signUpPnl.add(pass);
        signUpPnl.add(signUpBtn);

        signUpPnl.setVisible(false);

    }

}
