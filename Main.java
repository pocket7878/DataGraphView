import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.table.TableModel; 
import java.awt.print.PrinterException;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.lang.*;

public class Main extends JFrame implements ActionListener {
	private String[] elements;
	private String line;
	private Vector<String> tmp;
	private String[] columnName = {"Date", "Time", "Accel", "InsideTemperature", "OutsideTemperature","LM60Temp","Brightness", "Pressure","wet","Disconfort"};
	private DefaultTableModel dtm = null;
	private BufferedReader br = null;
	private JTable dataTbl;
	private	JScrollPane pane = null;;
	private DataGraphView dgv;

	private void init() {//{{{
		tmp = new Vector<String>();
		dtm = new DefaultTableModel(columnName, 0);
		dataTbl = new JTable(dtm);
		dataTbl.setDefaultEditor(Object.class, null);
		pane = new JScrollPane(dataTbl);
	}//}}}

	private void update() {//{{{
		dataTbl.setModel(dtm);
	}//}}}

	private void loadFile(String filename) {//{{{
		try {
			br = new BufferedReader(new FileReader(new File(filename)));
			int lineCounter = 0;
			while((line = br.readLine()) != null) {
				lineCounter++;
			}
			br.close();

			br = new BufferedReader(new FileReader(new File(filename)));
			dtm = new DefaultTableModel(columnName,lineCounter);
			int counter = 0;
			while((line = br.readLine()) != null) {
				elements = line.split(",");
				for(int i = 0; i < elements.length; i++) {
					tmp.add(elements[i]);	
				}
				dtm.insertRow(counter,tmp.toArray());
				tmp.clear();
				counter++;
			}
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}//}}}

	private void initMenu() {//{{{
		MenuBar menuBar = new MenuBar();
		this.setMenuBar(menuBar);
		//add [File]
		Menu menuFile = new Menu("File");
		menuFile.addActionListener(this);
		menuBar.add(menuFile);
		//[File] -> [Open]
		MenuItem menuOpen = new MenuItem("Open", new MenuShortcut('O'));
		menuFile.add(menuOpen);
		//[File] -> [----] 
		menuFile.addSeparator();
		//[File] -> [Exit]
		MenuItem menuExit = new MenuItem("Exit");
		menuFile.add(menuExit);

		//add [View]
		Menu menuView = new Menu("View");
		menuView.addActionListener(this);
		menuBar.add(menuView);
		//[View]->[Graph]
		MenuItem menuGraph = new MenuItem("Graph", new MenuShortcut('G'));
		menuView.add(menuGraph);
	}//}}}

	public void actionPerformed(ActionEvent e) {//{{{
		JFileChooser filechooser = new JFileChooser();
		filechooser.setFileFilter(new FileFilterForDataFile());
		if(e.getActionCommand() == "Open") {
			int selected = filechooser.showOpenDialog(this);
			if(selected == JFileChooser.APPROVE_OPTION) {
				this.loadFile(filechooser.getSelectedFile().getPath());
				this.update();
			} else if(selected == JFileChooser.CANCEL_OPTION) {
				System.out.println("Canceled");
			} else if(selected == JFileChooser.ERROR_OPTION) {
				System.out.println("Error");
			}
		}

		if(e.getActionCommand() == "Exit") {
			System.exit(0);
		}

		if(e.getActionCommand() == "Graph") {
			//TODO Add some functions to show Graphe
			dgv = new DataGraphView();
			if(dataTbl.getSelectedRow() != -1) {
				dgv.loadData(getDataAt(dataTbl.getSelectedRow(), 
							(dataTbl.getSelectedRow() + dataTbl.getSelectedRowCount() - 1)));
				System.out.println("Start:" + dataTbl.getSelectedRow() + 
						" End:" + (dataTbl.getSelectedRow() + dataTbl.getSelectedRowCount()));
			}
		}
	}//}}}

	public Main() {//{{{
		this.init();
		this.initMenu();
//		this.loadFile("/home/masato/Desktop/MyProjects/SunSpotTools/ReceveData/Data201086153.data");
		this.update();
		getContentPane().add(pane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		this.setVisible(true);
	}//}}}

	public static void main(String[] args) {//{{{
		Main main = new Main();
	}//}}}

	private Object[][] getDataAt(int start, int end) {//{{{
		Object data[][] = new Object[end - start + 1][10];
		for(int i = 0; i < (end - start + 1); i++) {
			for(int j = 0; j < 10; j++) {
				data[i][j] = dataTbl.getValueAt(i,j);
			}
		}
		return data;
	}//}}}
}

