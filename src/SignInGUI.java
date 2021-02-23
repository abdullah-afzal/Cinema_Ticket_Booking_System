
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignInGUI {

    JPanel signInPnl;
    JPanel header;
    JLabel title;
    JLabel welcomeLbl;
    JTextField CNIC;
    JPasswordField pass;
    JButton signInBtn;
    JButton signUpBtn;
    JLabel CNICLbl;
    JLabel passLbl;

    SignInGUI() {
        initSignInGUI();
    }

    public void initSignInGUI() {
        signInPnl = new JPanel();
        header = new JPanel();
        title = new JLabel("Book It.");
        title.setFont(new Font("Sarina", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        CNIC = new JTextField();
        pass = new JPasswordField();

        welcomeLbl = new JLabel("LOG IN");
        welcomeLbl.setFont(new Font("Monospaced", Font.BOLD, 24));
        CNICLbl = new JLabel("CNIC : ");
        passLbl = new JLabel("Password : ");
        signInBtn = new JButton("Sign In");
        signInBtn.setFocusPainted(false);
        signInBtn.setBackground(Color.BLACK);
        signInBtn.setForeground(Color.WHITE);
        signUpBtn = new JButton("Sign Up");
        signUpBtn.setFocusPainted(false);
        signUpBtn.setBackground(Color.BLACK);
        signUpBtn.setForeground(Color.WHITE);

        signInPnl.setLayout(null);

        signInPnl.setSize(1100, 680);
        header.setLayout(null);
        header.setBackground(Color.BLACK);
        header.setBounds(0, 0, 1100, 100);

        title.setBounds(100, 30, 150, 40);
        welcomeLbl.setBounds(500, 190, 700, 25);
        CNICLbl.setBounds(400, 250, 100, 40);
        CNIC.setBounds(400, 280, 300, 40);
        passLbl.setBounds(400, 330, 100, 40);
        pass.setBounds(400, 360, 300, 40);
        signInBtn.setBounds(400, 410, 300, 30);
        signUpBtn.setBounds(950, 60, 120, 30);
        signUpBtn.setFont(new Font("Monospaced", Font.PLAIN, 18));
        signUpBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpBtn.setBorderPainted(false);

        header.add(title);
        header.add(signUpBtn);
        signInPnl.add(header);
        signInPnl.add(welcomeLbl);
        signInPnl.add(CNICLbl);
        signInPnl.add(CNIC);
        signInPnl.add(pass);
        signInPnl.add(passLbl);
        signInPnl.add(signInBtn);

        signInPnl.setVisible(false);

    }

}
