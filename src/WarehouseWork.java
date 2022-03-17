import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Vector;

public class WarehouseWork extends JFrame {

    private DefaultTableModel wareModel;
    private JTable wareTable;
    private JButton add, edit, search, request, showAll, close;
    private ArrayList<Warehouse> selectedList;
    private MainFrame owner;

    public WarehouseWork(MainFrame owner, Com c) {
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add = new JButton("Добавить");

        edit = new JButton("Изменить");

        search = new JButton("Поиск");

        request = new JButton("Запрос");

        showAll = new JButton("Показать всех");

        buttons.add(add);
        buttons.add(edit);
        buttons.add(search);
        buttons.add(request);
        buttons.add(showAll);

        String[] data = { "id", "Номер склада ", "Название склада", "Профиль склада", "Адрес склада"};

        wareModel = new DefaultTableModel(data, 10);
        wareTable = new JTable(wareModel) {

            //запрет редактировния таблицы
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        wareTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollTable = new JScrollPane(wareTable);
        scrollTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        close = new JButton("Закрыть");

        closePanel.add(close);
        this.getContentPane().add(buttons);
        this.getContentPane().add(scrollTable);
        this.getContentPane().add(closePanel);

        wareModel.addTableModelListener(a -> {
            if (selectedList == null || selectedList.isEmpty()) {
                edit.setEnabled(false);
                request.setEnabled(false);
            } else {
                edit.setEnabled(true);
                request.setEnabled(true);
            }
        });

        this.setTitle("Работа с аптеками");
        this.setMinimumSize(new Dimension(600, 500));
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Action a = new Action(this);
        add.addActionListener(a);
        edit.addActionListener(a);
        search.addActionListener(a);
        request.addActionListener(a);
        showAll.addActionListener(a);
        close.addActionListener(a);

        this.owner = owner;
        if (c != null) {
            ArrayList<Warehouse> comList = new ArrayList<>();
            close.setText("OK");
            buttons.setVisible(false);
//            if (c.getWarehouse() != null) {
//                for (Integer i : c.getWarehouse())
//                  ComList.add(Storage.getWarehouseById(i));
//            }
            this.updateTable(comList);
        } else {}
//            this.updateTable(Storage.getStorWarehouse());

        super.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                //owner.updateTable(Storage.getStorCom());
            }
        });
    }

    private class Action implements ActionListener {

        private WarehouseWork owner;

        public Action(WarehouseWork owner) {
            this.owner = owner;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == close) {
                this.owner.dispose();
                owner.owner.updateTable(Storage.getStorCom());
            } else if (e.getSource() == add) {
                AddWarehouse a = new AddWarehouse(this.owner, null);
            } else if (e.getSource() == edit) {
               // AddWarehouse a = new AddPharmacy(this.owner, Storage.getWareById(Integer.parseInt((String) WareModel.getValueAt(WareTable.getSelectedRow(), 0))));
            } else if (e.getSource() == search) {
                String request = JOptionPane.showInputDialog(this.owner, "Введите номер аптеки", "Поиск",
                        JOptionPane.QUESTION_MESSAGE);

                if (request != null && !request.isEmpty()) {
                    ArrayList<Warehouse> tmpList = new ArrayList<>();
                    for (Warehouse s : Storage.getStorWarehouse())
//                        if (s.getNamePharmacy().equals(request))
//                            tmpList.add(s);
                    if (tmpList.isEmpty())
                        JOptionPane.showMessageDialog(this.owner, "По вашему запросу ничего не найдено");
                    else
                        updateTable(tmpList);
                }
            } else if (e.getSource() == request) {
//                Integer requestId = selectedList.get(wareTable.getSelectedRow()).getId();
//                ArrayList<Warehouse> tmpList = new ArrayList<>();
//                for (Hospitals c : Storage.getStorHosp())
//                    if (c.getPharmacy() != null)
//                        for (Integer i : c.getPharmacy())
//                            if (i.equals(requestId)) {
//                                tmpList.add(c);
//                                break;
//                            }
//                owner.owner.updateTable(tmpList);
            } else if (e.getSource() == showAll) {
                updateTable(Storage.getStorWarehouse());
            }

        }
    }

    protected void updateTable(ArrayList<Warehouse> selectedList) {
        this.wareModel.setRowCount(0);
        this.selectedList = selectedList;
        for (Warehouse s : selectedList) {
            Object[] data = { Integer.toString(s.getId()), s.getNameWarehouse(),s.getNumber(), s.getProfile(),s.getAddress() };
            wareModel.addRow(data);
            wareTable.setRowSelectionInterval(0, 0);
        }
        if (wareModel.getRowCount() == 0)
            wareModel.addRow((Vector<String>) null);
    }
}
