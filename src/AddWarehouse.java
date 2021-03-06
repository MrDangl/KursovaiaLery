import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddWarehouse extends JFrame {

    private JTextField nameWare = new JTextField(), number = new JTextField(), profile = new JTextField(), address = new JTextField();
    private JButton ok, cancel;
    private WarehouseWork owner;
    private Warehouse editedWare = null;

    public AddWarehouse(WarehouseWork owner, Warehouse s) {
        this.getContentPane().setLayout(new GridLayout(5, 2, 5, 5));

        JLabel nameLabel = new JLabel("Название склада");
        JLabel numberLabel = new JLabel("Номер склада");
        JLabel profileLabel = new JLabel("Профиль");
        JLabel addressLabel = new JLabel("Адрес");

        ok = new JButton("ОК");
        cancel = new JButton("Отмена");

        this.owner = owner;
        this.editedWare = s;

        this.getContentPane().add(nameLabel);
        this.getContentPane().add(nameWare);
        this.getContentPane().add(numberLabel);
        this.getContentPane().add(number);
        this.getContentPane().add(profileLabel);
        this.getContentPane().add(profile);

        this.getContentPane().add(addressLabel);
        this.getContentPane().add(address);
        this.getContentPane().add(ok);
        this.getContentPane().add(cancel);


        this.setResizable(false);
        owner.setEnabled(false);
        this.setLocationRelativeTo(owner);
        this.setTitle("Добавление");
        this.setVisible(true);
        this.setMinimumSize(new Dimension(350, 150));

        Action a = new Action(this);

        ok.addActionListener(a);
        cancel.addActionListener(a);

        if (s != null) {

        }

        //слушатель закрытия окна
        super.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                owner.setEnabled(true);
            }
        });

    }

    public class Action implements ActionListener {

        private AddWarehouse owner;

        public Action(AddWarehouse owner) {
            this.owner = owner;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ok) {
                if (nameWare.getText().trim().isEmpty() && profile.getText().trim().isEmpty())
                    JOptionPane.showMessageDialog(this.owner, "Есть незаполненные поля!");
                else {
                    if (editedWare != null) {
                        System.out.println("Check");

                    } else


                    this.owner.owner.updateTable(Storage.getStorWarehouse());
                    this.owner.owner.setEnabled(true);
                    this.owner.dispose();
                }
            } else if (e.getSource() == cancel) {
                this.owner.owner.setEnabled(true);
                this.owner.dispose();
            }
        }
    }
}
