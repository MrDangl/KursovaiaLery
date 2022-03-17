import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class AddCom extends JFrame {

    private DefaultTableModel tm;
    private JTable wareTable;

    private JButton add;
    private JButton cancel;
    private JFrame kycm;
    private JLabel VvodNumberCom = new JLabel("Введите номер");
    private JLabel VvodEmail = new JLabel("Введите адрес эл.почты");
    private JLabel VvodComProfile = new JLabel("Введите профиль оказываемых услуг");
    private JLabel VvodFIO = new JLabel("Введите ФИО директора");
    private JLabel VvodAddress = new JLabel("Введите адрес");
    private MaskFormatter mf1 = null;
    private JFormattedTextField tfDate = new JFormattedTextField(mf1);

    private JTextField numberCom = new JTextField();
    private JTextField email = new JTextField();
    private JTextField comProfile = new JTextField();
    private JTextField FIO = new JTextField();
    private JTextField address = new JTextField();
    private MainFrame owner;
    private Com editedCom = null;
    private ArrayList<Warehouse> selectedList = new ArrayList<>();
    private ArrayList<Warehouse> comList = new ArrayList<>();
    public AddCom(MainFrame frame, Com c) {
        this.owner = frame;
        frame.setEnabled(false);
        super.setSize(800, 550);
        super.setResizable(true);
        super.setLocationRelativeTo(frame);
        super.getContentPane().setLayout(new BoxLayout(super.getContentPane(), BoxLayout.Y_AXIS));
        super.setTitle("Добавление");
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add = new JButton("Добавить");
        cancel = new JButton("Отмена");

        try {
            mf1 = new MaskFormatter("##.##.####");
            tfDate = new JFormattedTextField(mf1);
        } catch (ParseException pe) {
        }

        JPanel fieldPane = new JPanel(new GridLayout(5, 2, 5, 5));
        fieldPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        fieldPane.add(VvodNumberCom);
        fieldPane.add(numberCom);
        fieldPane.add(VvodEmail);
        fieldPane.add(email);
        fieldPane.add(VvodComProfile);
        fieldPane.add(comProfile);
        fieldPane.add(VvodFIO);
        fieldPane.add(FIO);
        fieldPane.add(VvodAddress);
        fieldPane.add(address);

        JPanel tableName = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel name = new JLabel("Таблица склады");
        tableName.add(name);

        String[] columnNames = {"", "id", "Номер складла", "Название склада", "Профиль", "Адрес"};
        tm = new DefaultTableModel(columnNames, 100);
        wareTable = new JTable(tm) {

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return true;
                }
                return false;
            }

            //первый столбец таблицы - флаги
            @Override
            public Class getColumnClass(int col) {
                if (getValueAt(0, col) != null) {
                    return getValueAt(0, col).getClass();
                } else {
                    return boolean.class;
                }
            }
        };

        wareTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel mainButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainButtons.add(add);
        mainButtons.add(cancel);

        JScrollPane scrollTable = new JScrollPane(wareTable);
        scrollTable.setPreferredSize(new Dimension(100, 370));
        super.getContentPane().add(fieldPane);
        super.getContentPane().add(tableName);
        super.getContentPane().add(new JScrollPane(scrollTable));
        super.getContentPane().add(mainButtons);

        super.setVisible(true);
        actionAdd action = new actionAdd(this);
        add.addActionListener(action);
        cancel.addActionListener(action);
        super.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                owner.setEnabled(true);
            }
        });

        if (c != null) {
            editedCom = c;

            add.setText("Изменить");
            super.setTitle("Изменение");
        }
        updateTable(Storage.getStorWarehouse());
    }

    public class actionAdd implements ActionListener {

        private AddCom owner;

        public actionAdd(AddCom add) {
            this.owner = add;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == add) {

                if ((numberCom.getText().trim().isEmpty()) || (email.getText().trim().isEmpty()) || (comProfile.getText().trim().isEmpty())
                        || (FIO.getText().trim().isEmpty()) || (address.getText().trim().isEmpty())) {
                    JOptionPane.showMessageDialog(this.owner, "Есть незаполненные поля!");
                } else {
                    String number = numberCom.getText();
                    // LocalDate date = LocalDate.parse(tfDate.getText(), MainFrame.formatter);
                    String postEmail = email.getText();
                    String oblastUslug = comProfile.getText();
                    String name = FIO.getText();
                    String adres = address.getText();
                    ArrayList<String> temp = new ArrayList<>();
                    for (int i = 0; i < tm.getRowCount(); i++) {
                        if ((boolean) wareTable.getValueAt(i, 0)) {
                            temp.add((String) wareTable.getValueAt(i, 1));
                        }
                    }
                    Integer[] tempArr = new Integer[temp.size()];
                    for (int i = 0; i < temp.size(); i++) {
                        tempArr[i] = Integer.parseInt(temp.get(i));
                    }

                    if (editedCom == null) {
                        JOptionPane.showMessageDialog(this.owner, "Добавлено");

                    } else {
                        JOptionPane.showMessageDialog(this.owner, "Изменено");

                    }

                    this.owner.owner.setEnabled(true);
                    this.owner.owner.updateTable(Storage.getStorCom()); // обновление таблицы главного окна
                    this.owner.dispose();
                }
            } else if (e.getSource() == cancel) {
                this.owner.owner.setEnabled(true);
                this.owner.dispose();
            }
        }

        //проверка валидности даты
        private boolean checkingDate(JTextField field) {
            try {
                //попытка парсинга строки в дату
                LocalDate.parse(field.getText(), MainFrame.formatter);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this.owner, "Неверная дата!");
                field.setText("");
                return true;
            }
            return false;
        }
    }

    protected void updateTable(ArrayList<Warehouse> selectedList) {
        this.tm.setRowCount(0);
        this.selectedList = selectedList;
        for (Warehouse s : selectedList) {
            Object[] data = {comList.contains(s) ? true : false, Integer.toString(s.getId()), s.getNameWarehouse(), s.getNumber(),
                    s.getProfile(), s.getAddress()};
            tm.addRow(data);
            wareTable.setRowSelectionInterval(0, 0);
        }
        if (tm.getRowCount() == 0) {
            //tm.addRow((Vector<String>) null);
        }
    }

}
