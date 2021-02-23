
import java.awt.Color;
import java.io.FileInputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddMovie {

    JFrame miniFrame;
    JLabel titleLbl;
    JLabel genereLbl;
    JLabel durationLbl;
    JLabel cover_photo;
    JTextField preview;
    JTextField titleTextField;
    JTextField genereTextField;
    JTextField durationTextField;
    JButton choosePhoto;
    JButton addRecordBtn;
    JButton cancel;
    FileInputStream imageStream;

    public AddMovie() {
        initGUI();
    }

    public void initGUI() {
        
        miniFrame = new JFrame("Add Movie");
        titleLbl = new JLabel("Movie Title:");
        genereLbl = new JLabel("Movie Genere:");
        durationLbl = new JLabel("Movie Duration:");
        titleTextField = new JTextField();
        genereTextField = new JTextField();
        durationTextField = new JTextField();
        preview = new JTextField();
        choosePhoto = new JButton("Upload");
        addRecordBtn = new JButton("Add to Record");
        cancel = new JButton("Cancel");

        titleLbl.setBounds(30, 40, 150, 30);
        titleTextField.setBounds(30, 70, 350, 30);
        genereLbl.setBounds(30, 120, 150, 30);
        genereTextField.setBounds(30, 150, 350, 30);
        durationLbl.setBounds(30, 190, 150, 30);
        durationTextField.setBounds(30, 220, 350, 30);
        preview.setBounds(500, 20, 200, 200);
        preview.setEditable(false);
        choosePhoto.setBounds(550, 220, 100, 30);
        choosePhoto.setBackground(Color.black);
        choosePhoto.setForeground(Color.WHITE);
        cancel.setBounds(400, 290, 100, 30);
        cancel.setBackground(Color.black);
        cancel.setForeground(Color.WHITE);
        addRecordBtn.setBounds(550, 290, 150, 30);
        addRecordBtn.setBackground(Color.black);
        addRecordBtn.setForeground(Color.WHITE);

        miniFrame.add(titleLbl);
        miniFrame.add(titleTextField);
        miniFrame.add(genereLbl);
        miniFrame.add(genereTextField);
        miniFrame.add(durationLbl);
        miniFrame.add(durationTextField);
        miniFrame.add(preview);
        miniFrame.add(choosePhoto);
        miniFrame.add(cancel);
        miniFrame.add(addRecordBtn);

        miniFrame.setLayout(null);
        miniFrame.setResizable(false);
        miniFrame.setSize(800, 400);
        miniFrame.setVisible(true);
    }

}
