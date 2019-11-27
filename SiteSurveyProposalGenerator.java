import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SiteSurveyProposalGenerator {

	//declare variables
	static JFrame mainFrame;
	private JLabel labelClient;
	private JTextField textClient;
	private JTextField textName;
	private JTextField textAddress1;
	private JTextField textAddress2;
	private JTextField textPhoneOrEmail;
	private JTextField textBuilding;
	private JTextField textRoom;
	private JTextField textBAF;
	private JTextField textCIT;
	private JPanel nextPane = new RoomDimensionsPanel();
	private String filename = "NIH Master BOM Template 11-21-19.xlsx";
	static String projectName = null;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SiteSurveyProposalGenerator window = new SiteSurveyProposalGenerator();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SiteSurveyProposalGenerator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("NIH Guided Site Survey Proposal Generator");
		mainFrame.setBounds(100, 100, 450, 438);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		labelClient = new JLabel("Client (Institute)");
		labelClient.setBounds(20, 43, 113, 14);
		mainFrame.getContentPane().add(labelClient);
		
		textClient = new JTextField();
		textClient.setToolTipText("Enter Client/Institue Name");
		textClient.setBounds(143, 40, 261, 20);
		textClient.setText("NIH");
		mainFrame.getContentPane().add(textClient);
		textClient.setColumns(10);
		
		JLabel labeltName = new JLabel("Name");
		labeltName.setBounds(20, 71, 83, 14);
		mainFrame.getContentPane().add(labeltName);
		
		textName = new JTextField();
		textName.setToolTipText("Enter Contact Person Name");
		textName.setText("Josephine Chen");
		textName.setColumns(10);
		textName.setBounds(143, 68, 261, 20);
		mainFrame.getContentPane().add(textName);
		
		JLabel labelAddress1 = new JLabel("Address 1");
		labelAddress1.setBounds(20, 98, 83, 14);
		mainFrame.getContentPane().add(labelAddress1);
		
		textAddress1 = new JTextField();
		textAddress1.setToolTipText("Enter Street Addresss");
		textAddress1.setText("123 Any Street");
		textAddress1.setColumns(10);
		textAddress1.setBounds(143, 95, 261, 20);
		mainFrame.getContentPane().add(textAddress1);
		
		textAddress2 = new JTextField();
		textAddress2.setToolTipText("Enter City & State");
		textAddress2.setText("Bethesda, MD");
		textAddress2.setColumns(10);
		textAddress2.setBounds(143, 123, 261, 20);
		mainFrame.getContentPane().add(textAddress2);
		
		JLabel labelAddress2 = new JLabel("Address 2");
		labelAddress2.setBounds(20, 126, 83, 14);
		mainFrame.getContentPane().add(labelAddress2);
		
		JLabel lblPhoneemail = new JLabel("Phone/Email");
		lblPhoneemail.setBounds(20, 154, 83, 14);
		mainFrame.getContentPane().add(lblPhoneemail);
		
		textPhoneOrEmail = new JTextField();
		textPhoneOrEmail.setToolTipText("Enter Contact Person Phone or Email");
		textPhoneOrEmail.setText("jchen@nih.gov");
		textPhoneOrEmail.setColumns(10);
		textPhoneOrEmail.setBounds(143, 151, 261, 20);
		mainFrame.getContentPane().add(textPhoneOrEmail);
		
		JLabel labelBuilding = new JLabel("Building Number");
		labelBuilding.setBounds(20, 181, 113, 14);
		mainFrame.getContentPane().add(labelBuilding);
		
		textBuilding = new JTextField();
		textBuilding.setToolTipText("Enter Building Number");
		textBuilding.setText("31A");
		textBuilding.setColumns(10);
		textBuilding.setBounds(143, 178, 261, 20);
		mainFrame.getContentPane().add(textBuilding);
		
		JLabel labelRoom = new JLabel("Room Number");
		labelRoom.setBounds(20, 209, 83, 14);
		mainFrame.getContentPane().add(labelRoom);
		
		textRoom = new JTextField();
		textRoom.setToolTipText("Enter Room Number");
		textRoom.setText("B1B2");
		textRoom.setColumns(10);
		textRoom.setBounds(143, 206, 261, 20);
		mainFrame.getContentPane().add(textRoom);
		
		JLabel labelBAF = new JLabel("BAF Number");
		labelBAF.setBounds(20, 237, 83, 14);
		mainFrame.getContentPane().add(labelBAF);
		
		textBAF = new JTextField();
		textBAF.setToolTipText("Enter BAF Number");
		textBAF.setText("9876C");
		textBAF.setColumns(10);
		textBAF.setBounds(143, 234, 261, 20);
		mainFrame.getContentPane().add(textBAF);
		
		JLabel labelCIT = new JLabel("CIT Account");
		labelCIT.setBounds(20, 265, 83, 14);
		mainFrame.getContentPane().add(labelCIT);
		
		textCIT = new JTextField();
		textCIT.setToolTipText("Enter CIT Account Number");
		textCIT.setText("24680A");
		textCIT.setColumns(10);
		textCIT.setBounds(143, 262, 261, 20);
		mainFrame.getContentPane().add(textCIT);
		
		JButton buttonNext = new JButton("Next");
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readMasterBOM();
				
				mainFrame.setContentPane(nextPane);
				mainFrame.invalidate();
				mainFrame.validate();
			}
		});
		buttonNext.setToolTipText("Click To Begin");
		buttonNext.setBounds(162, 367, 89, 23);
		mainFrame.getContentPane().add(buttonNext);
	}

	//reads & writes spreadsheet
	public void readMasterBOM() {
		try {
			FileInputStream BOMFile = new FileInputStream(new File(filename));
			
			XSSFWorkbook workbook = new XSSFWorkbook (BOMFile);
			XSSFSheet sheet = workbook.getSheetAt(0);
			getFormDatas(sheet);
			BOMFile.close();
			
			projectName = textClient.getText().trim() + "_" + textBuilding.getText().trim() + "_" + textRoom.getText().trim();
			FileOutputStream outFile = new FileOutputStream(new File(projectName + "-BOM.xlsx"));
			workbook.write(outFile);
			outFile.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainFrame,"File Not Found! \nCheck File Location.");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainFrame, "Input Output Exception.");
		} finally {
		
		}	
	}

	//gets user input and inserts into spreadsheet
	private void getFormDatas(XSSFSheet sheet) {
		//create cell objects referencing 
		Cell clientCell = sheet.getRow(1).getCell(1);			//cell B2
		Cell nameCell = sheet.getRow(2).getCell(1);  			//cell B3
		Cell projectNameCell = sheet.getRow(3).getCell(1);		//cell B4
		Cell address1Cell = sheet.getRow(4).getCell(1);			//cell B5
		Cell address2Cell = sheet.getRow(5).getCell(1);			//cell B6
		Cell BAFCITCell = sheet.getRow(6).getCell(1);			//cell B7
		Cell phoneEmailCell = sheet.getRow(7).getCell(1);		//cell B8
		Cell buildingRoomCell = sheet.getRow(37).getCell(0);	//cell A38

		//fill cells with leading and trailing white space removed
		clientCell.setCellValue(textClient.getText().trim());
		nameCell.setCellValue(textName.getText().trim());
		String projectName = textClient.getText().trim() + "_" + textBuilding.getText().trim() + "_" + textRoom.getText().trim();
		projectNameCell.setCellValue(projectName);
		address1Cell.setCellValue(textAddress1.getText().trim());
		address2Cell.setCellValue(textAddress2.getText().trim());
		String BAFCITAccount = textBAF.getText().trim() + "/" + textCIT.getText().trim();
		BAFCITCell.setCellValue(BAFCITAccount);
		phoneEmailCell.setCellValue(textPhoneOrEmail.getText().trim());
		buildingRoomCell.setCellValue(textBuilding.getText().trim() + " " + textRoom.getText().trim());		
	}
}
