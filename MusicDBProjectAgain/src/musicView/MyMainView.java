package musicView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



public class MyMainView extends JPanel {

	
	private static final long serialVersionUID = -6586691518396309146L;

	private JPanel cPanel,nPanel, sPanel, ePanel, wPanel;
	
	private JPanel ePanelAlb, ePanelArt, ePanelTrk, ePanelGen;
	private MyCustomTxtField[] txtFieldsAlb, txtFieldsArt, txtFieldsTrk, txtFieldsGen;
	private JLabel[] labelsAlb, labelsArt, labelsTrk, labelsGen;
//	private JButton updateB, addB, deleteB, regenerateB;
	private MyCustomButton[] crudBtns;
	private JRadioButton[] rbs;
	private JTable mainTable;
	private JScrollPane mainScrollPane;
	
	private MyCustomButton searchB;
	private MyCustomTxtField searchField;
	
	private JFrame parentFrame;
	//private DefaultTableModel[] dm;
	private JMenuBar menubar;
	private JMenu navigate;
	private JMenuItem joinTables, editTables;
	private JLabel rbLabel;
	
	private JComboBox cb;
	
	private MyCustomButton qryB;
	private JTextArea qryTxtArea;
	
	
	
	public MyMainView(JFrame parentFrame) {
		this.parentFrame = parentFrame;
		initGUI();
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		makeCenterPanel();
		makeNorthPanel();
		makeSouthPanel();
		makeEastPanel();
		makeWestPanel();
		makeMenuBar();

	}
	
	private void makeMenuBar() {
		menubar = new JMenuBar();
		parentFrame.setJMenuBar(menubar);
		
		navigate = new JMenu("Navigate");
		menubar.add(navigate);
		editTables = new JMenuItem("Edit tables");
		joinTables = new JMenuItem("Query table data");
		navigate.add(editTables);
		navigate.add(joinTables);
	}
	
	private void makeCenterPanel() {
		cPanel = new JPanel(new BorderLayout());	
		JScrollPane cPanelScrollPane = new JScrollPane(cPanel);
		String[] colonTitles = {"colon1", "colon2", "colon3", "colon4"};
		Object[][] rowData = {
			{"data1","data2","data3","data4"},
			{"data5","data6","data7","data8"},
			{"data9","data10","data11","data12"},
			{"data13","data14","data15","data16"},
			{"data17","data18","data19","data20"}
		};
		
		mainTable = new JTable(rowData, colonTitles);
		mainTable.setFillsViewportHeight(true);
		mainScrollPane = new JScrollPane(mainTable);
		cPanel.add(mainScrollPane, BorderLayout.CENTER);
	
//		add(cPanel, BorderLayout.CENTER);
		add(cPanelScrollPane, BorderLayout.CENTER);
	}
	
	private void makeNorthPanel() {
		nPanel = new JPanel();
		crudBtns = new MyCustomButton[4];
		crudBtns[0] = new MyCustomButton("ADD");
		crudBtns[1] = new MyCustomButton("REGENERATE");
		crudBtns[2] = new MyCustomButton("UPDATE");
		crudBtns[3] = new MyCustomButton("DELETE");	
		for(int i=0;i<crudBtns.length;i++) 
				nPanel.add(crudBtns[i]);
		add(nPanel, BorderLayout.NORTH);
	}
	
	private void makeSouthPanel() {
		sPanel = new JPanel(new GridLayout(1,2));

		//Edit table view.
		searchB = new MyCustomButton("Search by name");
		searchField = new MyCustomTxtField("");
		sPanel.add(searchB);
		sPanel.add(searchField);
		
		//Table query view.
		qryB = new MyCustomButton("Qu£ry");
		qryTxtArea = new JTextArea("Add your own query", 5, 20);
		
		add(sPanel, BorderLayout.SOUTH);
	}
	
	private void makeEastPanel() {
		ePanel = new JPanel();
		
		//album
		ePanelAlb = new JPanel(new GridLayout(6,1));
		txtFieldsAlb = new MyCustomTxtField[3];
		txtFieldsAlb[0] = new MyCustomTxtField("pk_album_id");
		txtFieldsAlb[0].setEditable(false);
		txtFieldsAlb[1] = new MyCustomTxtField("album_name");
		txtFieldsAlb[2] = new MyCustomTxtField("fk_artist_id");
		
		labelsAlb = new JLabel[3];
		labelsAlb[0] = new JLabel("pk_album_id (A_I)");
		labelsAlb[1] = new JLabel("album_name");
		labelsAlb[2] = new JLabel("fk_artist_id");
		
		for(int i=0; i<txtFieldsAlb.length;i++) { // eller labelsAlb.length
			ePanelAlb.add(labelsAlb[i]);
			ePanelAlb.add(txtFieldsAlb[i]);
		}
		

		//artist
		ePanelArt = new JPanel(new GridLayout(6,1));
		txtFieldsArt = new MyCustomTxtField[3];
		txtFieldsArt[0] = new MyCustomTxtField("pk_artist_id");
		txtFieldsArt[0].setEditable(false);
		txtFieldsArt[1] = new MyCustomTxtField("artist_name");
		txtFieldsArt[2] = new MyCustomTxtField("country");
		
		labelsArt = new JLabel[3];
		labelsArt[0] = new JLabel("pk_artist_id (A_I)");
		labelsArt[1] = new JLabel("artist_name");
		labelsArt[2] = new JLabel("country");
		
		for(int i=0; i<txtFieldsArt.length;i++) { // eller labelsArt.length
			ePanelArt.add(labelsArt[i]);
			ePanelArt.add(txtFieldsArt[i]);
		}
		
		
		//track
		ePanelTrk = new JPanel(new GridLayout(14,1));
		txtFieldsTrk = new MyCustomTxtField[7];
		txtFieldsTrk[0] = new MyCustomTxtField("pk_track_id");
		txtFieldsTrk[0].setEditable(false);
		txtFieldsTrk[1] = new MyCustomTxtField("track_name");
		txtFieldsTrk[2] = new MyCustomTxtField("track_time");
		txtFieldsTrk[3] = new MyCustomTxtField("fk_artist_id");
		txtFieldsTrk[4] = new MyCustomTxtField("fk_album_id");
		txtFieldsTrk[5] = new MyCustomTxtField("release_date");
		txtFieldsTrk[6] = new MyCustomTxtField("fk_genre_id");
		
		labelsTrk = new JLabel[7];
		labelsTrk[0] = new JLabel("pk_track_id (A_I)");
		labelsTrk[1] = new JLabel("track_name");
		labelsTrk[2] = new JLabel("track_time");
		labelsTrk[3] = new JLabel("fk_artist_id");
		labelsTrk[4] = new JLabel("fk_album_id");
		labelsTrk[5] = new JLabel("release_date");
		labelsTrk[6] = new JLabel("fk_genre_id");
		
		for(int i=0; i<txtFieldsTrk.length;i++) { // eller labelsTrk.length
			ePanelTrk.add(labelsTrk[i]);
			ePanelTrk.add(txtFieldsTrk[i]);
		}
		
		
		//genre
		ePanelGen = new JPanel(new GridLayout(4,1));
		txtFieldsGen = new MyCustomTxtField[2];
		txtFieldsGen[0] = new MyCustomTxtField("pk_genre_id");
		txtFieldsGen[0].setEditable(false);
		txtFieldsGen[1] = new MyCustomTxtField("genre_name");
		
		labelsGen = new JLabel[2];
		labelsGen[0] = new JLabel("pk_genre_id (A_I)");
		labelsGen[1] = new JLabel("genre_name");
		
		for(int i=0; i<txtFieldsGen.length;i++) { // eller labelsTrk.length
			ePanelGen.add(labelsGen[i]);
			ePanelGen.add(txtFieldsGen[i]);
		}
		
		//ePanelAlb, ePanelArt, ePanelTrk, ePanelGen OK
		add(ePanel, BorderLayout.EAST);
	}
	
	public void switchEastPanel(JRadioButton rb) {
		ePanel.removeAll();
		switch(rb.getText()) {
			case "Albums":
				ePanel.add(ePanelAlb, BorderLayout.EAST);
				break;
			case "Artists":
				ePanel.add(ePanelArt, BorderLayout.EAST);
				break;
			case "Tracks":
				ePanel.add(ePanelTrk, BorderLayout.EAST);
				break;
			case "Genres":
				ePanel.add(ePanelGen, BorderLayout.EAST);
				break;
		}
		refreshView();
	}

	private void makeWestPanel() {
		wPanel = new JPanel();
		wPanel.setLayout(new BoxLayout(wPanel,BoxLayout.Y_AXIS));

		//Edit Table view.
		rbLabel = new JLabel("Tables:");
		wPanel.add(rbLabel);
		
		rbs = new JRadioButton[4];
		rbs[0] = new JRadioButton("Albums");
		rbs[1] = new JRadioButton("Artists");
		rbs[2] = new JRadioButton("Tracks");
		rbs[3] = new JRadioButton("Genres");
		
		ButtonGroup btnGrp = new ButtonGroup();
		for(int i=0; i<rbs.length; i++) {
			btnGrp.add(rbs[i]);
			wPanel.add(rbs[i]);
		}	
		
		//Query table view.
		cb = new JComboBox(new String[]{"JOIN1", "JOIN2", "ALBUM", "ARTIST", "TRACK", "GENRE"});
		
		add(wPanel, BorderLayout.WEST);
	}
	
	public void addRadioButtonListener(ActionListener actionListen) {
		for(int i=0; i<rbs.length; i++) 
			rbs[i].addActionListener(actionListen);
	}	
	public void addTableMouseListener(MouseListener moutseListen) {
		mainTable.addMouseListener(moutseListen);

	}	
	public void addCRUDButtonsListener(ActionListener actionListen) {
		for(int i=0; i<crudBtns.length;i++)
			crudBtns[i].addActionListener(actionListen);
	}
	public void addSearchButtonListener(ActionListener actionListen) {
		searchB.addActionListener(actionListen);
	}
	public void addMenjuBarListener(ActionListener actionListen) {
		editTables.addActionListener(actionListen);
		joinTables.addActionListener(actionListen);
	}	
	public void addComboBokListener(ActionListener actionListen) {
		cb.addActionListener(actionListen);
	}	
	public void addQryButtonListener(ActionListener actionListen) {
		qryB.addActionListener(actionListen);
	}	

	public JTable getMainTable() {
		return mainTable;
	}
	
	public JPanel getEastPanel() {
		return ePanel;
	}
	public JPanel getCenterPanel() {
		return cPanel;
	}
	public JPanel getNorthPanel() {
		return nPanel;
	}
	public JPanel getWestPanel() {
		return wPanel;
	}
	public JPanel getSouthPanel() {
		return sPanel;
	}
	
	public JRadioButton getSelectedRb() {
		if(rbs[0].isSelected())
			return rbs[0];
		else if(rbs[1].isSelected())
			return rbs[1];
		else if(rbs[2].isSelected())
			return rbs[2];
		else 
			return rbs[3];
	}
	
	public JRadioButton getAlbRb() {
		return rbs[0];
	}
	public JRadioButton getArtRb() {
		return rbs[1];
	}
	public JRadioButton getTrkRb() {
		return rbs[2];
	}
	public JRadioButton getGenRb() {
		return rbs[3];
	}
	
	
	public JTextField[] getTxtFieldsAlb() {
		return txtFieldsAlb;
	}
	public JTextField[] getTxtFieldsArt() {
		return txtFieldsArt;
	}
	public JTextField[] getTxtFieldsTrk() {
		return txtFieldsTrk;
	}
	public JTextField[] getTxtFieldsGen() {
		return txtFieldsGen;
	}
	
	public JTextField getSearchField() {
		return searchField;
	}
	public JButton getSearchButton() {
		return searchB;
	}
	
	public JComboBox getComboBox() {
		return cb;
	}
	public JTextArea getQryTxtArea() {
		return qryTxtArea;
	}
	
	public void refreshView() {
		this.revalidate();
		this.repaint();
	}
	
	public void displayEditTablePanels() {
		//cPanel.removeAll();
		ePanel.removeAll();
		wPanel.removeAll();
		nPanel.removeAll();
		sPanel.removeAll();

		
		//north 
		for(int i=0;i<crudBtns.length;i++) 
			nPanel.add(crudBtns[i]);

		//south
		sPanel.add(searchB);
		sPanel.add(searchField);
		
		
		//west
		wPanel.setLayout(new BoxLayout(wPanel, BoxLayout.Y_AXIS ));
		for(int i=0; i<rbs.length; i++) {
			wPanel.add(rbs[i]);
		}	
		refreshView();
	}
	
	//TODO , hmm it wont respond as I want it to.
	public void displayJoinTablePanels() {
//		cPanel.removeAll();
		ePanel.removeAll();
		wPanel.removeAll();
		nPanel.removeAll();
		sPanel.removeAll();
		
		//west
		wPanel.setLayout(new FlowLayout());
		wPanel.add(cb);
		
		//south
		sPanel.add(qryTxtArea);
		sPanel.add(qryB);
		
		refreshView();
	}
	
	private class MyCustomTxtField extends JTextField {
		private final int CSTM_WIDTH = 150;
		private final int CSTM_HEIGHT = 25;
		
		public MyCustomTxtField(String string) {
			super(string);
			setPreferredSize(new Dimension(CSTM_WIDTH,CSTM_HEIGHT));
		}
		
	}
	
	private class MyCustomButton extends JButton {
		private final int CSTM_WIDTH = 150;
		private final int CSTM_HEIGHT = 50;
		
		public MyCustomButton(String string) {
			super(string);
			setPreferredSize(new Dimension(CSTM_WIDTH,CSTM_HEIGHT));
		}

	}
	

}
