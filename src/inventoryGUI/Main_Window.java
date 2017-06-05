package inventoryGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;

public class Main_Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private FileFilter filter = new FileNameExtensionFilter("IDB File","idb");
	private JSplitPane splitPane_3;
	private JButton btnExpandTable;
	private JSplitPane splitPane_1;
	private JButton btnSave;
	private JButton btnLoad;
	private JScrollPane scroll;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Window frame = new Main_Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void expand_table(JTable arg)
	{
		DefaultTableModel a = (DefaultTableModel)(arg.getModel());
		a.addRow(new Object[]{"",0});
		System.out.println(a.getRowCount());
	}
	
	@SuppressWarnings("serial")
	public Main_Window() {
		setTitle("Inventory GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		splitPane_1 = new JSplitPane();
		contentPane.add(splitPane_1, BorderLayout.NORTH);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(filter);
				int seleccion = fc.showSaveDialog(null);
				if(seleccion==JFileChooser.APPROVE_OPTION){
					String a = fc.getSelectedFile().getAbsolutePath();
					if(!a.endsWith(".idb")){
						a+=".idb";
					}
					List<String> to_save = new LinkedList<String>();
					for(int i = 0; i < table.getRowCount(); i++)
					{
						//System.out.println((table.getModel().getValueAt(i, 0).toString().isEmpty()));
						if(!(table.getModel().getValueAt(i,0).toString().isEmpty())){
						to_save.add(table.getModel().getValueAt(i, 0).toString().replaceAll("\\|", "`")+"|"+table.getModel().getValueAt(i, 1).toString());
						}
					}
					Path dest = Paths.get(a);
					try {
						Files.write(dest, to_save,Charset.forName("UTF-8"));
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, e.toString(),"Error Writing",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		splitPane_1.setLeftComponent(btnSave);
		
		btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(filter);
				int seleccion = fc.showOpenDialog(null);
				if(seleccion==JFileChooser.APPROVE_OPTION){
					String a = fc.getSelectedFile().getAbsolutePath();
				try {
					List<String> input = Files.readAllLines(Paths.get(a), Charset.forName("UTF-8"));
					Object[][] out_datus = new Object[input.size()][2];
					String[] in_datus;
					for(int i = 0; i < input.size(); i++)
					{
						in_datus = input.get(i).split("\\|");
						out_datus[i] = new Object[]{in_datus[0],Integer.parseInt(in_datus[1])};
					}
					DefaultTableModel a1 = (DefaultTableModel) table.getModel();
					a1.setDataVector(out_datus, new Object[]{String.class,Long.class});
					a1.setColumnIdentifiers(new Object[]{"Item Name","Quantity"});
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.toString(),"Error Reading",JOptionPane.ERROR_MESSAGE);
				}
			}}
		});
		splitPane_1.setRightComponent(btnLoad);
		splitPane_1.setResizeWeight(0.5);
		
		
		table = new JTable();
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"", 0},
				{"", 0},
				{"", 0},
				{"", 0},
				{"", 0},
				{"", 0},
				{"", 0},
			},
			new String[] {
				"Item Name", "Quantity"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Long.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			
			public int change_data(Object[][] in)
			{
				this.setDataVector(in, columnTypes);
				return 0;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(256);
		splitPane_3 = new JSplitPane();
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(150,200));;
		splitPane_3.setLeftComponent(scroll);
		contentPane.add(splitPane_3, BorderLayout.SOUTH);
		splitPane_3.setResizeWeight(1);
		
		btnExpandTable = new JButton("Expand Table");
		btnExpandTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				expand_table(table);
			}
		});
		splitPane_3.setRightComponent(btnExpandTable);
	}

}
