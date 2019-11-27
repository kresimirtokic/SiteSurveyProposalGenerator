import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.awt.event.ActionEvent;

public class RoomRequirements extends JPanel {

	//declare variables
	JSpinner tableInputCount;
	JSpinner tvCount;
	JSpinner spinnerSeats;
	JCheckBox ceilingSpeakersRequired;
	JCheckBox roomPCRequired;
	JCheckBox podiumRequired;
	JCheckBox environmentalControls;
	JCheckBox ceilingMics;
	JCheckBox wiremoldCheckBox;
	
	
	//creates panel
	public RoomRequirements() {
		setLayout(null);
		
		JLabel labelDisplayCount = new JLabel("How many displays?");
		labelDisplayCount.setBounds(10, 22, 243, 14);
		add(labelDisplayCount);
		
		tvCount = new JSpinner();
		tvCount.setModel(new SpinnerNumberModel(1, 1, 2, 1));
		tvCount.setBounds(296, 19, 46, 20);
		add(tvCount);
		
		JLabel labelTableInputs = new JLabel("How many table inputs?");
		labelTableInputs.setBounds(10, 53, 243, 14);
		add(labelTableInputs);
		
		tableInputCount = new JSpinner();
		tableInputCount.setModel(new SpinnerNumberModel(0, 0, 4, 1));
		tableInputCount.setBounds(296, 50, 46, 20);
		add(tableInputCount);
		
		JLabel labelSeats = new JLabel("How many seats at the conference room table?");
		labelSeats.setBounds(10, 84, 276, 14);
		add(labelSeats);
		
		spinnerSeats = new JSpinner();
		spinnerSeats.setModel(new SpinnerNumberModel(0, 0, 24, 1));
		spinnerSeats.setBounds(296, 81, 46, 20);
		add(spinnerSeats);
		
		ceilingSpeakersRequired = new JCheckBox("Check if ceiling speakers are required");
		ceilingSpeakersRequired.setBounds(10, 108, 283, 23);
		add(ceilingSpeakersRequired);
		
		roomPCRequired = new JCheckBox("Check if a dedicated room PC is required");
		roomPCRequired.setBounds(10, 133, 283, 23);
		add(roomPCRequired);
		
		podiumRequired = new JCheckBox("Check if a podium is required");
		podiumRequired.setBounds(10, 159, 243, 23);
		add(podiumRequired);
		
		environmentalControls = new JCheckBox("Check if evironmental controls are required (eg lights,shades)");
		environmentalControls.setBounds(10, 185, 420, 23);
		add(environmentalControls);
		
		/* implement later
		ceilingMics = new JCheckBox("Check if ceiling mics are required");
		ceilingMics.setBounds(10, 211, 420, 23);
		add(ceilingMics);
		*/
		
		JButton finishButton = new JButton("Finish");
		finishButton.addActionListener(new ActionListener() {
			//finishes the BOM and exits app
			public void actionPerformed(ActionEvent e) {
				readCurrentBOMFile();
				System.exit(0);
			}
		});
		finishButton.setToolTipText("Click to complete");
		finishButton.setBounds(162, 367, 89, 23);
		add(finishButton);
		
		wiremoldCheckBox = new JCheckBox("Check if wiremold/safcord required");
		wiremoldCheckBox.setBounds(10, 237, 420, 23);
		add(wiremoldCheckBox);
	}

	//reads and writes current BOM
	public void readCurrentBOMFile() {
		try {
			FileInputStream BOMFile = new FileInputStream(new File(SiteSurveyProposalGenerator.projectName + "-BOM.xlsx"));		
			XSSFWorkbook workbook = new XSSFWorkbook (BOMFile);
			XSSFSheet sheet = workbook.getSheetAt(0);
			BOMFile.close();
			Cell extronRackShelf = sheet.getRow(108).getCell(0);
			Cell ciscoRackEars = sheet.getRow(97).getCell(0);
			if (roomPCRequired.isSelected() == true) {
				Cell mediaPortCell = sheet.getRow(109).getCell(0);
				mediaPortCell.setCellValue(1);
				extronRackShelf.setCellValue(1);
				//add rack kit
			}			
			//adds P60 camera if podium required
			if (podiumRequired.isSelected() == true) {
				Cell secondCameraCell = sheet.getRow(98).getCell(0);
			  	secondCameraCell.setCellValue(1);
			  	//add room kit pro & quad cam & rack kit or wall mount kit?
			}
			
			//adds IPCP controller if light/shade control required
			if (environmentalControls.isSelected() == true) {
				Cell extronControllerCell = sheet.getRow(163).getCell(0);
				extronControllerCell.setCellValue(1);
				extronRackShelf.setCellValue(1);
				ciscoRackEars.setCellValue(1);
				//add equipment rack 
			}
			
			//calls method to determine speaker quantity
			if (ceilingSpeakersRequired.isSelected() == true) {
				calculateCeilingSpeakers(sheet);
			}
			
			//adds wiremold/safcord if required
			if (wiremoldCheckBox.isSelected() == true) {
				Cell wiremoldCell = sheet.getRow(191).getCell(0);
				Cell safcordCell = sheet.getRow(190).getCell(0);
				wiremoldCell.setCellValue(1);
				safcordCell.setCellValue(1);
			}

			determineIfTVsFit(sheet);
			determineMicQuantity(sheet);
			determineTableInputs(sheet);

			FileOutputStream outFile = new FileOutputStream(new File(SiteSurveyProposalGenerator.projectName + "-BOM.xlsx"));
			workbook.write(outFile);
			outFile.close();
			workbook.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(SiteSurveyProposalGenerator.mainFrame,"File Not Found! \nCheck File Location.");
		} catch (IOException e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(SiteSurveyProposalGenerator.mainFrame, "Input Output Exception.");
		} 
	}


	//gets room dimensions and calculates speaker requirements then adds to BOM
	private void calculateCeilingSpeakers(XSSFSheet sheet) {
		 double speakerDispersionRadians = Math.toRadians(170 / 2);
		 double dispersionCone = (2 * (RoomDimensionsPanel.totalRoomHeight - 48) * Math.tan(speakerDispersionRadians)) * 0.33;
		 double dispersionArea = (3.14 * (dispersionCone/12 * dispersionCone/12)) / 2;
		 double roomArea = RoomDimensionsPanel.totalRoomLength * RoomDimensionsPanel.totalRoomWidth;
		 //speaker count is rounded up
		 int speakerCount = (int) Math.ceil((roomArea / dispersionArea) / 10.0) + 1; 
		 Cell speakerCell = sheet.getRow(125).getCell(0);
		 Cell speakerCableCell = sheet.getRow(236).getCell(0);
		 if (speakerCount < 4) {
			 speakerCell.setCellValue(speakerCount);
			 Cell smallAmpCell = sheet.getRow(126).getCell(0);
			 smallAmpCell.setCellValue(1);
			 speakerCableCell.setCellValue(1);
		 } else {
			 speakerCell.setCellValue(speakerCount);
			 Cell largeAmpCell = sheet.getRow(127).getCell(0);
			 largeAmpCell.setCellValue(1);
			 speakerCableCell.setCellValue(2);
		 }
	}
	
	// determines table input quantity and adds TX/RX kit to BOM
	private void determineTableInputs(XSSFSheet sheet) {
		Cell tableTX;
		Cell tableRX = sheet.getRow(107).getCell(0);
		Cell underTablePowerStrip = sheet.getRow(192).getCell(0);
		Cell extenderCable = sheet.getRow(235).getCell(0);
		Cell extronHDMICable = sheet.getRow(227).getCell(0);
		try {
			tableInputCount.commitEdit();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int spinnerValue = (Integer) tableInputCount.getValue();
		if (spinnerValue == 1) {
			tableTX = sheet.getRow(105).getCell(0);
			tableTX.setCellValue(1);
			tableRX.setCellValue(1);
			underTablePowerStrip.setCellValue(1);
			extenderCable.setCellValue(1);
			extronHDMICable.setCellValue(1);
		} else if (spinnerValue > 1 && spinnerValue <= 3) {
			tableTX = sheet.getRow(106).getCell(0);
			tableTX.setCellValue(1);
			tableRX.setCellValue(1);
			underTablePowerStrip.setCellValue(1);
			extenderCable.setCellValue(1);
			extronHDMICable.setCellValue(spinnerValue);
		}	else if (spinnerValue > 3) {
			tableTX = sheet.getRow(106).getCell(0);
			tableTX.setCellValue(2);
			tableRX.setCellValue(2);
			underTablePowerStrip.setCellValue(1);
			extenderCable.setCellValue(2);
			extronHDMICable.setCellValue(spinnerValue);
		} 
	}

	//determines if TVs fit on display wall and reduces quantity if necessary
	private void determineIfTVsFit(XSSFSheet sheet) {
		Cell mountCoupler = sheet.getRow(52).getCell(0);
		try {
			tvCount.commitEdit();
		} catch(ParseException e) {
			e.printStackTrace();
		}
		int spinnerValue = (Integer) tvCount.getValue();
		if ((RoomDimensionsPanel.selectedDisplay * spinnerValue) < RoomDimensionsPanel.displayWallWidth) {
			mountCoupler.setCellValue(spinnerValue);
			addDisplaysToBom(spinnerValue, sheet);
		} else {
			//reduces TV count 1 before adding to BOM
			spinnerValue--;
			addDisplaysToBom(spinnerValue, sheet);
		}		
	}
	
	//adds displays, mounts, and accessories to BOM
	private void addDisplaysToBom(int spinnerValue, XSSFSheet sheet) {
		//if spinnerValue is 2 then must use roomKitPlus or Pro
		Cell tvCell;
		Cell tvMount;
		Cell tvPAC115 = sheet.getRow(51).getCell(0);
		Cell tvPower = sheet.getRow(53).getCell(0);
		tvPAC115.setCellValue(spinnerValue);
		tvPower.setCellValue(spinnerValue);
		//add spinnerValue to appropriate cell
		if (RoomDimensionsPanel.selectedDisplay == RoomDimensionsPanel.ALLDISPLAYWIDTHS[0]) {
			tvCell = sheet.getRow(42).getCell(0); //55" TV
			tvCell.setCellValue(spinnerValue);
			tvMount = sheet.getRow(48).getCell(0); //MTM1U mount
			tvMount.setCellValue(spinnerValue);		
		} else if (RoomDimensionsPanel.selectedDisplay == RoomDimensionsPanel.ALLDISPLAYWIDTHS[1]) {
			tvCell = sheet.getRow(43).getCell(0); //65" TV
			tvCell.setCellValue(spinnerValue);
			tvMount = sheet.getRow(49).getCell(0); //LTM1U mount
			tvMount.setCellValue(spinnerValue);
		} else if (RoomDimensionsPanel.selectedDisplay == RoomDimensionsPanel.ALLDISPLAYWIDTHS[2]) {
			tvCell = sheet.getRow(44).getCell(0); //75" TV
			tvCell.setCellValue(spinnerValue);
			tvMount = sheet.getRow(50).getCell(0); //XTM1U mount
			tvMount.setCellValue(spinnerValue);
		} else if (RoomDimensionsPanel.selectedDisplay == RoomDimensionsPanel.ALLDISPLAYWIDTHS[3]) {
			tvCell = sheet.getRow(45).getCell(0); //86" TV
			tvCell.setCellValue(spinnerValue);
			tvMount = sheet.getRow(50).getCell(0); //XTM1U mount
			tvMount.setCellValue(spinnerValue);
		} else if (RoomDimensionsPanel.selectedDisplay == RoomDimensionsPanel.ALLDISPLAYWIDTHS[4]) {
			tvCell = sheet.getRow(46).getCell(0); //98" TV
			tvCell.setCellValue(spinnerValue);
			tvMount = sheet.getRow(50).getCell(0); //XTM1U mount
			tvMount.setCellValue(spinnerValue);
		}
	}

	//determines how many mics are required based on number seats at conf table
	protected void determineMicQuantity(XSSFSheet sheet) {
		Cell kitMicCell = sheet.getRow(118).getCell(0);
		Cell kitMicExtCell = sheet.getRow(119).getCell(0);
		Cell proMicCell = sheet.getRow(120).getCell(0);
		Cell proMicExtCell = sheet.getRow(121).getCell(0);	
		try {
			spinnerSeats.commitEdit();
		} catch(ParseException e) {
			e.printStackTrace();
		}	
		int spinnerValue = (Integer) spinnerSeats.getValue();
		if (spinnerValue <= 5) {
			Cell miniKitCell = sheet.getRow(82).getCell(0);
			miniKitCell.setCellValue(1);		
		} else if (spinnerValue > 5 && spinnerValue <= 8) {
			Cell roomKitCell = sheet.getRow(83).getCell(0);
			roomKitCell.setCellValue(1);
			kitMicCell.setCellValue(2);
			kitMicExtCell.setCellValue(2);	
		} else if (spinnerValue > 8 && spinnerValue <= 12) {
			Cell roomKitPlusCell = sheet.getRow(84).getCell(0);
			roomKitPlusCell.setCellValue(1);
			kitMicCell.setCellValue(spinnerValue / 4);
			kitMicExtCell.setCellValue(spinnerValue / 4);
		} else if (spinnerValue > 12) {
			Cell roomKitProCell = sheet.getRow(85).getCell(0);
			roomKitProCell.setCellValue(1);
			proMicCell.setCellValue(spinnerValue / 4);
			proMicExtCell.setCellValue(spinnerValue / 4);
		}
	}
}
