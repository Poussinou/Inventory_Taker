package inventoryGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;

public class Main_Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private FileFilter filter = new FileNameExtensionFilter("IDB File","idb");
	private Base64.Encoder b64enc = Base64.getMimeEncoder();
	private Base64.Decoder b64dec = Base64.getMimeDecoder();
	private static JMenuBar menuBar;
	private JMenu menu;
	private JLabel lab = new JLabel("<html><h1><center>Copyright © Salvador Pardiñas, 2017</center></h1><p>InventoryGUI is free software protected under the <b><a href=`https://www.gnu.org/licenses/gpl-3.0-standalone.html`>GPLv3 license</a></b>.</p></html>".replaceAll("`", ""+'"'));
	private JMenuItem mItem;
	private boolean splitorlabel;
	private JSplitPane splitPane_3;
	private JButton btnExpandTable;
	private JSplitPane splitPane_1;
	private JSplitPane supersplit;
	private JButton btnSave;
	private JButton btnLoad;
	private JScrollPane scroll;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Window frame = new Main_Window();
					frame.setJMenuBar(menuBar);
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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			return;
		}
		setTitle("Inventory GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 526, 300);
		menuBar = new JMenuBar();
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(menu);
		mItem = new JMenuItem("About");
		mItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
		mItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(splitorlabel)
				{
					supersplit.setRightComponent(lab);
					splitorlabel = false;
				}
				else
				{
					supersplit.setRightComponent(splitPane_3);
					splitorlabel = true;
				}
			}
			
		});
		menu.add(mItem);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		splitPane_1 = new JSplitPane();
		supersplit = new JSplitPane();
		supersplit.setLeftComponent(splitPane_1);
		contentPane.add(supersplit, BorderLayout.CENTER);
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(filter);
				int seleccion = fc.showSaveDialog(null);
				if(seleccion==JFileChooser.APPROVE_OPTION){
					String a = fc.getSelectedFile().getAbsolutePath();
					if(!a.toLowerCase().endsWith(".idb")){
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
					String out_str = "";
					for(String xbx : to_save)
					{
						out_str += xbx + "\n";
					}
					try {
						byte[] encoded = b64enc.encode(out_str.getBytes("UTF8"));
						byte[] result = new byte[encoded.length + 4];
						result[0] = 8;
						result[1] = 7;
						result[2] = 5;
						result[3] = 10;
						System.arraycopy(encoded, 0, result, 4, encoded.length);
						Files.write(dest, result);
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
					if(!(a.toLowerCase().endsWith(".idb")))
					{
						a = a + ".idb";
					}
				try {
					byte[] readin = Files.readAllBytes(Paths.get(a));
					byte[] input1 = b64dec.decode(Arrays.copyOfRange(readin, 4, readin.length));
					String input2 = new String(input1);
					List<String> input = Arrays.asList(input2.split("\n"));
					Object[][] out_datus = new Object[input.size()][2];
					String[] in_datus;
					for(int i = 0; i < input.size(); i++)
					{
						in_datus = input.get(i).split("\\|");
						out_datus[i] = new Object[]{in_datus[0].replaceAll("`", "|"),Integer.parseInt(in_datus[1])};
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
			@SuppressWarnings({ "rawtypes" })
			Class[] columnTypes = new Class[] {
				String.class, Long.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			
			
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(256);
		splitPane_3 = new JSplitPane();
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(150,200));;
		splitPane_3.setLeftComponent(scroll);
		supersplit.setRightComponent(splitPane_3);
		splitorlabel = true;
		supersplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_3.setResizeWeight(1);
		
		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setSize(new Dimension(200, 200));
		splitPane_3.setRightComponent(panel);
		supersplit.setResizeWeight(0);
		panel.setLayout(new BorderLayout(0, 0));
		
		btnExpandTable = new JButton("Expand Table");
		btnExpandTable.setBounds(0, 0, 0, 0);
		btnExpandTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				expand_table(table);
			}
		});
		panel.add(btnExpandTable);
	}

}
