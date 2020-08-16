import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class MainForm
{

    private JPanel mainPanel;
    private JButton collapseButton;
    private JButton expandButton;
    private JLabel surnameLabel;
    private JLabel nameLabel;
    private JLabel patronicLabel;
    private JLabel fullNameLabel;
    private JTextField fullNameField;
    private JTextField patronicField;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextArea textArea1;
    private JPanel fullNameJPanel;
    private JPanel patronicJPanel;
    private JPanel nameJPAnel;
    private JPanel surnameJPanel;
    private JPanel panel1;

    public MainForm()
    {
        collapseButton.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (surnameField.getText().length() > 0 && nameField.getText().length() > 0){
                    fullNameField.setText(surnameField.getText() + " " + nameField.getText() + " " + patronicField.getText());
                    surnameJPanel.setVisible(false);
                    nameJPAnel.setVisible(false);
                    patronicJPanel.setVisible(false);
                    fullNameJPanel.setVisible(true);
                    collapseButton.setVisible(false);
                    expandButton.setVisible(true);
                }
                else {
                    textArea1.setText("Фамилия либо Имя не введены. Ведите Фамилию и Имя");
                }

            }
        });

        expandButton.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {

                String[] fullName = fullNameField.getText().split("\\s");
                if (fullName.length >= 2){
                    surnameField.setText(fullName[0]);
                    nameField.setText(fullName[1]);
                    if (fullName.length == 3) {
                        patronicField.setText(fullName[2]);
                    }

                    surnameJPanel.setVisible(true);
                    nameJPAnel.setVisible(true);
                    patronicJPanel.setVisible(true);
                    fullNameJPanel.setVisible(false);
                    collapseButton.setVisible(true);
                    expandButton.setVisible(false);
                }
                else {
                    textArea1.setText("Фамилия либо Имя не введены. Ведите Фамилию и Имя");
                }

            }
        });
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
