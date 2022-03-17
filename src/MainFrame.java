import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Vector;

public class MainFrame extends JFrame {

    protected JTable table1;
    protected DefaultTableModel tableCom;
    private JMenuItem itemAddCom, itemDelCom, itemEditCom, itemSearhCom, itemShowAll, itemShowWarehouse;
    private ArrayList<Com> selectedList;

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public MainFrame() {

        this.setSize(new Dimension(900, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridBagLayout());
        this.setTitle("Курсовая");

        JMenuBar menubar = new JMenuBar();

        JMenu menuStudent = new JMenu("БД cкладов");
        itemShowWarehouse = new JMenuItem("Показать базу складов");

        JMenu menuCom = new JMenu("Строительные компании");
        itemAddCom = new JMenuItem("Добавить компанию");
        itemDelCom = new JMenuItem("Удалить компанию");
        itemEditCom = new JMenuItem("Редактировать данные компании");
        itemSearhCom = new JMenuItem("Поиск");

        itemShowAll = new JMenuItem("Показать всех");

        menuStudent.add(itemShowWarehouse);

        menuCom.add(itemAddCom);
        menuCom.add(itemDelCom);
        menuCom.add(itemEditCom);
        menuCom.add(itemSearhCom);
        menuCom.add(itemShowAll);
        menubar.add(menuStudent);
        menubar.add(menuCom);

        this.setJMenuBar(menubar);

        JLabel nameTable = new JLabel("Список компаний в системе (для просмотра привязанных поставщиков необходимо дважды нажать на компанию");

        tableCom = new DefaultTableModel(new String[]{"ID", "Название компании", "Эл. почта  ", "Профиль услуг", "ФИО директора", "Адрес компании"}, 10);
        table1 = new JTable(tableCom) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        }; // sozdanie tablici po modeli tablici

        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // moji vibrat' nol'ko 1 elem table

        table1.addMouseListener((MyMouseAdapter) (l) -> { // слушатель двойного клика по таблице
            if (l.getClickCount() == 2) {
               WarehouseWork s = new WarehouseWork(this, selectedList.get(table1.getSelectedRow()));
            }
        });

        Action a = new Action(this);

        itemShowWarehouse.addActionListener(a);
        itemAddCom.addActionListener(a);
        itemDelCom.addActionListener(a);
        itemEditCom.addActionListener(a);
        itemSearhCom.addActionListener(a);
        itemShowAll.addActionListener(a);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.getContentPane().add(new JScrollPane(table1));
        Storage.loadData();
        this.updateTable(Storage.getStorCom());
        this.setVisible(true);
    }

    // функциональный интерфейс для обработки двойного клика через лямбду
    @FunctionalInterface
    interface MyMouseAdapter extends MouseListener {

        @Override
        default public void mouseEntered(final MouseEvent e) {
        }

        @Override
        default public void mouseExited(final MouseEvent e) {
        }

        @Override
        default public void mousePressed(final MouseEvent e) {
        }

        @Override
        default public void mouseReleased(final MouseEvent e) {
        }
    }

    protected void updateTable(ArrayList<Com> selectedList) {
        tableCom.setRowCount(0);
        this.selectedList = selectedList;

        for (Com h : selectedList) {

            String[] data = {Integer.toString(h.getId()), h.getNumberWarehouse(), h.getEmail(),h.getWareProfile(), h.getFioWare(), h.getWareAdress()
            };
            tableCom.addRow(data);
            table1.setRowSelectionInterval(0, 0);
        }
        if (tableCom.getRowCount() == 0) {
            tableCom.addRow((Vector<String>) null);
        }
    }

    private class Action implements ActionListener {

        MainFrame owner = null;

        public Action(MainFrame owner) {
            this.owner = owner;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == itemShowWarehouse) {
                WarehouseWork s = new WarehouseWork(this.owner, null);
            } else if (e.getSource() == itemAddCom) {
                AddCom a = new AddCom(owner, null);
            } else if (e.getSource() == itemDelCom) {
                Com c = selectedList.get(table1.getSelectedRow());
                //Storage.removeConcert(c);
                //updateTable(Storage.getStorHosp());
            } else if (e.getSource() == itemEditCom) {
                AddCom a = new AddCom(owner, selectedList.get(table1.getSelectedRow()));
            } else if (e.getSource() == itemSearhCom) {
                String request = JOptionPane.showInputDialog(this.owner, "Введите номер", "Поиск",
                        JOptionPane.QUESTION_MESSAGE);
                ArrayList<Com> tmpList = new ArrayList<>();
                if (request != null) {
                    if (tmpList.isEmpty()) {
                        JOptionPane.showMessageDialog(this.owner, "По вашему запросу ничего не найдено");
                    } else {
                       // updateTable(tmpList);
                    }
                }
            }
            //else if (e.getSource() == itemShowAll) {
//                //updateTable(Storage.getStorHosp());
//            }
        }
    }
}
