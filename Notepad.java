	//Design a text editor
// Part 1- Design the GUI
/*
	JMenuBar
		-- Horizontal Bar
			--Multiple JMenu
				--Each JMenu have JMenuItem
				
	Step 1:
		Create JMenuItem to JMenu
	Step 2:
		
	Step 3:
		
	Step 4:
		
*/
// Part 2- Handle Events- (A) Open File- CLick on Open- Action Event- Action Listener- Show Open dialog- Selects file- Display the Contents 
// Part 3- FIle Operations- BufferedReader- FileReader- readline

import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Notepad extends JFrame implements ActionListener
{
	// 1. Design the menubar
	JMenuBar mainBar;
	JMenu mnuFile, mnuEdit, mnuFormat, mnuHelp;
	JMenuItem itmNew, itmOpen, itmSave, itmSaveAs, itmExit, itmCut, itmCopy, itmPaste, itmColor;
	JCheckBoxMenuItem itmWordWrap;
	
	// Create JTextArea and JScrollPane
	JScrollPane scrEdit;
	JTextArea txtEdit;
	
	// To create open and save dialog
	JFileChooser jfc;
	
	// Create file operations objects 
	FileReader fr;
	BufferedReader br;
	FileWriter fw;
	BufferedWriter bw;
	
	//File Object
	File selectedFile;
	
	//File name
	String fileN;
	
	void createMenu()
	{
		// create an object of MenuBar
		mainBar = new JMenuBar();
		
		// --------- start of File menu ---------
		// create and design file Menu
		mnuFile = new JMenu("File");
		
		// create Meun Items 
		itmNew = new JMenuItem("New");
		itmNew.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					txtEdit.setText("");
					fileN="Untitled";
					setTitle(fileN+" - MyNotepad");
				}
			}
		);
		
		itmOpen = new JMenuItem("Open");
		// Add Action Listener to open button
		itmOpen.addActionListener(this);
		
		itmSave = new JMenuItem("Save");
		// Add Action Listener to Save button
		itmSave.addActionListener(this);
		
		itmSaveAs = new JMenuItem("Save As");
		//Annonymous inner class - class written inside another class - which has no name
		//handle events
		//compact way of handling events
		//class abc implements ActionListener
		//abc a1=new abc();
		//in case of annoynmous inner classes we create an object and define class at same time 

		itmSaveAs.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					if(jfc.showSaveDialog(itmSaveAs)==JFileChooser.APPROVE_OPTION)
					{
						selectedFile=jfc.getSelectedFile();
						fileN=selectedFile.getName();
						setTitle(fileN+" - MyNotepad");
						saveFile(selectedFile);
			
					}
				}
			}
		);

		itmExit = new JMenuItem("Exit");
		
		// Add menu items to menu
		mnuFile.add(itmNew);
		mnuFile.add(itmOpen);
		mnuFile.addSeparator();	// Add Separator
		mnuFile.add(itmSave);
		mnuFile.add(itmSaveAs);
		mnuFile.add(itmExit);
		
		mnuFile.setMnemonic('F');	//Set Mnemonic- Shortcuts alt + F
		
		// Add Menu to MenuBar
		mainBar.add(mnuFile);
		// --------- end of file menu ---------
		
		// --------- start of Edit menu ---------
		// create and design Edit Menu
		mnuEdit = new JMenu("Edit");
		
		// create Meun Items 
		itmCut = new JMenuItem("Cut");
		itmCopy = new JMenuItem("Copy");
		itmPaste = new JMenuItem("Paste");
		
		// Add menu items to menu
		mnuEdit.add(itmCut);
		mnuEdit.add(itmCopy);
		mnuEdit.add(itmPaste);
		
		mnuEdit.setMnemonic('E');	//Set Mnemonic
		
		// Add Menu to MenuBar
		mainBar.add(mnuEdit);
		// --------- end of Edit menu ---------
		
		// --------- start of Format menu ---------
		// create and design Format Menu
		mnuFormat = new JMenu("Format");
		
		// create CheckBox Menu Item and Meun Items 
		itmWordWrap = new JCheckBoxMenuItem("Word Wrap");
		itmColor = new JMenuItem("Color");
		
		// Add menu items to menu
		mnuFormat.add(itmWordWrap);
		mnuFormat.add(itmColor);
		
		mnuFormat.setMnemonic('o');	//Set Mnemonic
		
		// Add Menu to MenuBar
		mainBar.add(mnuFormat);
		// --------- end of Format menu ---------
		
		// --------- start of Help menu ---------
		// create and design Help Menu
		mnuHelp = new JMenu("Help");
		
		mnuHelp.setMnemonic('H');	//Set Mnemonic
		
		// Add Menu to MenuBar
		mainBar.add(mnuHelp);
		//--------- end of Help menu ---------
		
		//Add MenuBar to JFrame
		setJMenuBar(mainBar);
	}
	
	//to handle events and perform action override actionPerformed
	public void actionPerformed(ActionEvent ae)
	{
		String actionCmd = ae.getActionCommand();
		
		if(actionCmd.equals("Open"))
		{
			// Open file operation
			// Show Open Dialog
			if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				// Open File
				// Display Data
				txtEdit.setText("");
				try
				{
					selectedFile = jfc.getSelectedFile();
					fr = new FileReader(selectedFile);
					fileN = selectedFile.getName();
					setTitle(fileN + " - MyNotepad");
					br = new BufferedReader(fr);
					//read a line
					String line = br.readLine();
					
					while(line != null)
					{
						txtEdit.append(line + "\n");
						line = br.readLine();
					}
					fr.close();
					br.close();
				}
				catch(Exception ex)
				{		
					JOptionPane.showMessageDialog(this, "Exception: " + ex.getMessage());
			
					//System.out.println("Exception: " + ex.getMessage());
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, "User has cancelled the action.");
			}
		}
		
		if(actionCmd.equals("Save"))
		{
			if(fileN.equals("Untitled"))
			{
				//show save dialog-box
				if(jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
				{
					//Write Function to save and call it
					
					//Set the selected files name
					selectedFile = jfc.getSelectedFile();
					
					fileN = selectedFile.getName();
					setTitle(fileN + " - MyNotepad");
					
					saveFile(selectedFile);
				}
			}
			else
			{
				//Write Function to save and call it
				saveFile(selectedFile);
			}
		}
	}
	
	// Function to save the contents to file
	void saveFile(File temp)
	{
		try
		{
			fw = new FileWriter(temp);
			bw = new BufferedWriter(fw);
			bw.write(txtEdit.getText());
			
			bw.close(); //First close connection of BufferedWriter and then FileWriter
			fw.close();
		}
		catch(Exception ex)
		{		
			JOptionPane.showMessageDialog(this, "Exception: " + ex.getMessage());
		}
	}
	
	public Notepad()
	{
		fileN = "Untitled";
		setTitle(fileN + " - MyNotepad");
		
		createMenu();
		
		jfc = new JFileChooser();
		
		// Add Text Area
		txtEdit = new JTextArea(20, 20);
		scrEdit = new JScrollPane(txtEdit);
		add(scrEdit, BorderLayout.CENTER);
		
		setSize(700, 450);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String args[])
	{
		new Notepad();
	}
}