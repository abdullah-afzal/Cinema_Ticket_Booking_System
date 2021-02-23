
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class ChangePass {

    JFrame miniFrame;
    JLabel oldPassLbl;
    JLabel newPassLbl;
    JPasswordField oldPass;
    JPasswordField newPass;
    JButton confirm;
    JButton cancel;

    public ChangePass() {
        initGUI();
    }

    public void initGUI() {
        miniFrame = new JFrame("Change Password");
        oldPassLbl = new JLabel("Your current password:");
        newPassLbl = new JLabel("Your new password:");
        oldPass = new JPasswordField();
        newPass = new JPasswordField();
        confirm = new JButton("Confirm");
        cancel = new JButton("Cancel");

        oldPassLbl.setBounds(10, 30, 150, 30);
        newPassLbl.setBounds(10, 80, 150, 30);
        oldPass.setBounds(160, 30, 300, 30);
        newPass.setBounds(160, 80, 300, 30);
        cancel.setBounds(240, 140, 100, 30);
        cancel.setBackground(Color.black);
        cancel.setForeground(Color.WHITE);
        confirm.setBounds(360, 140, 100, 30);
        confirm.setBackground(Color.black);
        confirm.setForeground(Color.WHITE);

        miniFrame.add(oldPassLbl);
        miniFrame.add(oldPass);
        miniFrame.add(newPassLbl);
        miniFrame.add(newPass);
        miniFrame.add(cancel);
        miniFrame.add(confirm);

        miniFrame.setLayout(null);
        miniFrame.setResizable(false);
        miniFrame.setSize(500, 250);
        miniFrame.setVisible(true);
    }
}
