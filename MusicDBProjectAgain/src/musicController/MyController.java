package musicController;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import musicModel.TopModel.MyTopModel;
import musicModel.TopModel.SqlQry;
import musicModel.TopModel.Table;
import musicView.MyMainView;



public class MyController implements ActionListener {

	MyMainView view;
	MyTopModel topModel;
	
	public MyController(MyMainView view, MyTopModel topModel) {
		this.view = view;
		view.addRadioButtonListener(new RadioButtonListener());
		view.addTableMouseListener(new TableMouseListener());
		view.addCRUDButtonsListener(new CRUDButtonsListener());
		view.addSearchButtonListener(new SearchButtonListener());
		view.addMenjuBarListener(new MenjuBarListener());
		this.topModel = topModel;
	}
	

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//TODO
	}
	//TODO switch betwen panels!
	private class MenjuBarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent aevt) {

			JMenuItem mObj = (JMenuItem) aevt.getSource();
			if(mObj.getText().equals("Edit Tables")) {
				view.getMainTable().setModel(new DefaultTableModel()); // clear table
				view.displayEditTablePanels();
				
			}else {
				//TODO make GUI for these new joins. Example: let every join-option that
				//you give the user be a radioButton on the new panel
				//JOIN TABLES
				//view.getMainTable().setModel(topModel.fetchJoinData(SqlQry.JOIN1, "searchStr"));
				view.displayJoinTablePanels();
				view.getMainTable().setModel(new DefaultTableModel()); // clear table

			}
			view.revalidate();
			view.repaint();
		}
		
	}
	
	private class RadioButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent aevt) {
			//jag vill ha ngt i stil med:
			
			JRadioButton rObj = (JRadioButton) aevt.getSource();
			if(rObj.getText().equals("Albums")) {
				view.getMainTable().setModel(topModel.fetchTableData(Table.ALBUM, ""));
			}else if(rObj.getText().equals("Artists")) {
				view.getMainTable().setModel(topModel.fetchTableData(Table.ARTIST, ""));
			}else if(rObj.getText().equals("Tracks")) {
				view.getMainTable().setModel(topModel.fetchTableData(Table.TRACK, ""));
			}else {
				view.getMainTable().setModel(topModel.fetchTableData(Table.GENRE, ""));
			}
			//gotta add listener again, after switched model.
			view.getMainTable().addMouseListener(new TableMouseListener());
			//disables the user to move the colons.
			view.getMainTable().getTableHeader().setReorderingAllowed(false);
			
			view.switchEastPanel(rObj);
		}
		
	}
	//TODO 
	private class TableMouseListener extends MouseAdapter {
		
		private JTable table;
		private JTextField[] txtFields;
		JRadioButton rObj;
		public void mouseClicked(MouseEvent mevt) {
			System.out.println("HEllo you have clicked on the table.");
			rObj = view.getSelectedRb();
			table = view.getMainTable();

			//changfe data in JTextFields to clicked row.
			if(table.getSelectedRow()!=-1) {
				if(rObj.getText().equals("Albums")) {
					txtFields = view.getTxtFieldsAlb();
					updateTxtFields();
				}else if(rObj.getText().equals("Artists")) {
					txtFields = view.getTxtFieldsArt();
					updateTxtFields();
				}else if(rObj.getText().equals("Tracks")) {
					txtFields = view.getTxtFieldsTrk();
					updateTxtFields();	
				}else {
					txtFields = view.getTxtFieldsGen();
					updateTxtFields();
				}
			}	
		}
		
		private void updateTxtFields() {
			for(int i=0; i<txtFields.length; i++) 
				txtFields[i].setText(table.getValueAt(table.getSelectedRow(),i).toString());
		}	
	}
	
	//TODO atm the only way to get auto update after clicking on delte,update and add button is to call
	//view.getMainTable().setModel(topModel.fetchTableData(ENUM, "")); , but I will wait a bit
	//and see if I get something else in my mind, a more general refresher of the table!
	private class CRUDButtonsListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent aevt) {
			// TODO Auto-generated method stub
			System.out.println("CRUD TO BE COMING MATE");
			JButton bObj = (JButton) aevt.getSource();
			JRadioButton rObj = view.getSelectedRb();
				
			if(bObj.getText().equals("ADD")) {
				if(rObj.getText().equals("Albums")) {
					addDialog(topModel.addData(Table.ALBUM,
						getGatheredStuff(view.getTxtFieldsAlb(), view.getTxtFieldsAlb().length)));
				}else if(rObj.getText().equals("Artists")) {
					addDialog(topModel.addData(Table.ARTIST,
						getGatheredStuff(view.getTxtFieldsArt(), view.getTxtFieldsArt().length)));		
				}else if(rObj.getText().equals("Tracks")) {
					addDialog(topModel.addData(Table.TRACK,
						getGatheredStuff(view.getTxtFieldsTrk(), view.getTxtFieldsTrk().length)));	
				}else {
					addDialog(topModel.addData(Table.GENRE,
						getGatheredStuff(view.getTxtFieldsGen(), view.getTxtFieldsGen().length)));	
				}
			}else if(bObj.getText().equals("REGENERATE")) {
				//TODO, no functionality yet, add thisd l8r
				
				
				
			}else if(bObj.getText().equals("UPDATE")) {
				if(rObj.getText().equals("Albums")) {
					updateDialog(topModel.updateData(Table.ALBUM,
						getGatheredStuff(view.getTxtFieldsAlb(), view.getTxtFieldsAlb().length)));
				}else if(rObj.getText().equals("Artists")) {
					updateDialog(topModel.updateData(Table.ARTIST,
						getGatheredStuff(view.getTxtFieldsArt(), view.getTxtFieldsArt().length)));		
				}else if(rObj.getText().equals("Tracks")) {
					updateDialog(topModel.updateData(Table.TRACK,
						getGatheredStuff(view.getTxtFieldsTrk(), view.getTxtFieldsTrk().length)));	
				}else {
					updateDialog(topModel.updateData(Table.GENRE,
						getGatheredStuff(view.getTxtFieldsGen(), view.getTxtFieldsGen().length)));	
				}
			}else {//Deletation.
				if(deleteConfirm()) {
					if(rObj.getText().equals("Albums")) {
						deleteDialog(topModel.deleteData(Table.ALBUM, 
							getGatheredStuff(view.getTxtFieldsAlb(), view.getTxtFieldsAlb().length)));
					}else if(rObj.getText().equals("Artists")) {
						deleteDialog(topModel.deleteData(Table.ARTIST,
							getGatheredStuff(view.getTxtFieldsArt(), view.getTxtFieldsArt().length)));
					}else if(rObj.getText().equals("Tracks")) {
						deleteDialog(topModel.deleteData(Table.TRACK, 
							getGatheredStuff(view.getTxtFieldsTrk(), view.getTxtFieldsTrk().length)));
					}else {
						deleteDialog(topModel.deleteData(Table.GENRE, 
							getGatheredStuff(view.getTxtFieldsGen(), view.getTxtFieldsGen().length)));
					}
				}else {
					JOptionPane.showMessageDialog(null, "Data was not deleted");
				}
			}

//			view.getMainTable().repaint();
//			view.getMainTable().revalidate();
			view.refreshView();
		}
		
		private String[] getGatheredStuff(JTextField[] txtFields, int numOfStuff) {
			String[] stuff = new String[numOfStuff];
			for(int i=0;i<stuff.length;i++)
				stuff[i] = txtFields[i].getText();
			return stuff;
		}
		
		private void addDialog(boolean bool) {
			if(bool)
				JOptionPane.showMessageDialog(null, "Data was sucessfully added");
			else
				JOptionPane.showMessageDialog(null, "Data was not sucessfully added");
		}
		private void updateDialog(boolean bool) {
			if(bool)
				JOptionPane.showMessageDialog(null, "Data was sucessfully updated");
			else
				JOptionPane.showMessageDialog(null, "Data was not sucessfully updated");
		}
		private void deleteDialog(boolean bool) {
			if(bool)
				JOptionPane.showMessageDialog(null, "Data was sucessfully deleted");
			else
				JOptionPane.showMessageDialog(null, "Data was not sucessfully deleted");
		}
		private boolean deleteConfirm() {
			Object[] options = {"HELL YEAH", "HELL NO"};
			int answer = JOptionPane.showOptionDialog(null,
				"Do you really want to delete this data?",
				"CONFIRM DELETE",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				options,
				options[1]); //1 of safety reason, lal ;) (if user missclicks data will not be deleted).
			if(answer==0) return true;
			else return false;
		}
		
	}
	
	//TODO ***fix: its very similar to RadioButtonListener class, repeated code can be done better! :)
	private class SearchButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent aevt) {
			JRadioButton rObj = view.getSelectedRb();
			String searchStr = view.getSearchField().getText();
			if(rObj.getText().equals("Albums")) {
				view.getMainTable().setModel(topModel.fetchTableData(Table.ALBUM, searchStr));
			}else if(rObj.getText().equals("Artists")) {
				view.getMainTable().setModel(topModel.fetchTableData(Table.ARTIST, searchStr));
			}else if(rObj.getText().equals("Tracks")) {
				view.getMainTable().setModel(topModel.fetchTableData(Table.TRACK, searchStr));
			}else {
				view.getMainTable().setModel(topModel.fetchTableData(Table.GENRE, searchStr));
			}
			view.getMainTable().addMouseListener(new TableMouseListener());
		}
		
	}
}
