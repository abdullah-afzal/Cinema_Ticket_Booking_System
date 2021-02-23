
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Scheduling {

    JFrame schedulingFr;
    String given_id;
    JLabel stime_Lbl, etime_Lbl, date_Lbl,price_Lbl;
    JTextField stime_txt, etime_txt, price_txt;
    JComboBox sHall;
    JDatePickerImpl datePicker;

    public Scheduling() {
        SchedulingInit();
    }

    public void SchedulingInit() {
        schedulingFr = new JFrame("Schedule It");

        price_Lbl=new JLabel("Ticket Price:");
        price_Lbl.setBounds(350, 70, 300, 20);
        price_txt=new JTextField();
        price_txt.setBounds(350, 90, 300, 30);
        stime_Lbl = new JLabel("Starting Time:");
        stime_Lbl.setBounds(350, 130, 300, 20);
        stime_txt = new JTextField();
        stime_txt.setBounds(350, 150, 300, 30);
        etime_Lbl = new JLabel("Ending Time:");
        etime_Lbl.setBounds(350, 190, 300, 20);
        etime_txt = new JTextField();
        etime_txt.setBounds(350, 210, 300, 30);
        date_Lbl = new JLabel("Date:");
        date_Lbl.setBounds(350, 250, 300, 20);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(350, 270, 300, 30);
        

        sHall = new JComboBox();
        sHall.setBounds(350, 310, 300, 30);

        schedulingFr.add(price_Lbl);
        schedulingFr.add(price_txt);
        schedulingFr.add(stime_Lbl);
        schedulingFr.add(stime_txt);
        schedulingFr.add(etime_Lbl);
        schedulingFr.add(etime_txt);
        schedulingFr.add(date_Lbl);
        schedulingFr.add(datePicker);
        schedulingFr.add(sHall); 
        schedulingFr.setLayout(null);
        schedulingFr.setBounds(100, 0, 700, 529);
        schedulingFr.setResizable(false);
        schedulingFr.setVisible(true);
    }

    public class DateLabelFormatter extends AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }

}
