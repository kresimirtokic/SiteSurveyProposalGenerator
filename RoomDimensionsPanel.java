import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;


public class RoomDimensionsPanel extends JPanel {

	//declare variables
	private JPanel nextPane = new RoomRequirements();
	
	//declare variables
	JCheckBox checkboxUnknownDistance;
	JTextPane furthestViewerFeet;
	JSpinner furthestViewerInches;
	JSpinner roomHeightInches;
	JSpinner displayWallWidthInches;
	JSpinner displayWallHeightInches;
	JSpinner roomWidthInches;
	JTextPane roomWidthFeet;
	JTextPane displayWallWidthFeet;
	JTextPane displayWallHeightFeet;
	JTextPane roomLengthFeet;
	JTextPane roomHeightFeet;
	JSpinner roomLengthInches;
	double displayHeight;
	static final double FIFTYFIVEDIMS[] = {48.81, 28.55};//width x height
	static final double SIXTYFIVEDIMS[] = {57.43, 33.42};
	static final double SEVENTYFIVEDIMS[] = {66.2, 37.8};
	static final double EIGHTYSIXDIMS[] = {75.83, 43.3};
	static final double NINETYEIGHTDIMS[] = {86.29, 49.09};
	static final double ALLDISPLAYHEIGHTS[] = {28.55, 33.42, 37.8, 43.3, 49.09};
	static final double ALLDISPLAYWIDTHS[] = {48.81, 57.43, 66.2, 75.83, 86.29};
	static double selectedDisplay = 0;
	static double totalRoomLength = 0;
	static double totalRoomWidth = 0;
	static double totalRoomHeight = 0;
	static double displayWallHeight = 0;
	static double displayWallWidth = 0;
	
	
	
	/**
	 * Create the panel.
	 */
	public RoomDimensionsPanel() {
		setLayout(null);
		
		JLabel labelDisplayWallWidth = new JLabel("Display Wall Width");
		labelDisplayWallWidth.setBounds(10, 42, 111, 14);
		add(labelDisplayWallWidth);
		
		//initial value, min, max, step
		SpinnerModel model = new SpinnerNumberModel(0, 0, 11.75, 0.25);
		displayWallWidthInches = new JSpinner(model);
		displayWallWidthInches.setToolTipText("Select Inches");
		displayWallWidthInches.setBounds(290, 36, 50, 20);
		add(displayWallWidthInches);
		
		displayWallWidthFeet = new JTextPane();
		displayWallWidthFeet.setToolTipText("Enter Whole Numbers");
		displayWallWidthFeet.setText("8");
		displayWallWidthFeet.setBounds(131, 36, 80, 20);
		add(displayWallWidthFeet);
		
		JLabel labelFeet = new JLabel("Feet");
		labelFeet.setBounds(221, 42, 44, 14);
		add(labelFeet);
		
		JLabel labelInches = new JLabel("Inches");
		labelInches.setBounds(350, 42, 49, 14);
		add(labelInches);
		
		JLabel labelDisplayWallHeight = new JLabel("Display Wall Height");
		labelDisplayWallHeight.setBounds(10, 73, 111, 14);
		add(labelDisplayWallHeight);
		
		displayWallHeightFeet = new JTextPane();
		displayWallHeightFeet.setToolTipText("Enter Whole Numbers");
		displayWallHeightFeet.setText("8");
		displayWallHeightFeet.setBounds(131, 67, 80, 20);
		add(displayWallHeightFeet);
		
		JLabel label_1 = new JLabel("Feet");
		label_1.setBounds(221, 73, 44, 14);
		add(label_1);
		
		SpinnerModel model2 = new SpinnerNumberModel(0, 0, 11.75, 0.25);
		displayWallHeightInches = new JSpinner((SpinnerModel) model2);
		displayWallHeightInches.setToolTipText("Select Inches");
		displayWallHeightInches.setBounds(290, 67, 50, 20);
		add(displayWallHeightInches);
		
		JLabel label_2 = new JLabel("Inches");
		label_2.setBounds(350, 73, 49, 14);
		add(label_2);
		
		JLabel labelRoomWidth = new JLabel("Room Width");
		labelRoomWidth.setBounds(10, 149, 111, 14);
		add(labelRoomWidth);
		
		roomWidthFeet = new JTextPane();
		roomWidthFeet.setToolTipText("Enter Whole Numbers");
		roomWidthFeet.setText("8");
		roomWidthFeet.setBounds(131, 143, 80, 20);
		add(roomWidthFeet);
		
		JLabel label_3 = new JLabel("Feet");
		label_3.setBounds(221, 149, 44, 14);
		add(label_3);
		
		SpinnerModel model3 = new SpinnerNumberModel(0, 0, 11.75, 0.25);
		roomWidthInches = new JSpinner((SpinnerModel) model3);
		roomWidthInches.setToolTipText("Select Inches");
		roomWidthInches.setBounds(290, 143, 50, 20);
		add(roomWidthInches);
		
		JLabel label_4 = new JLabel("Inches");
		label_4.setBounds(350, 149, 49, 14);
		add(label_4);
		
		JLabel labelRoomLength = new JLabel("Room Length");
		labelRoomLength.setBounds(10, 180, 111, 14);
		add(labelRoomLength);
		
		roomLengthFeet = new JTextPane();
		roomLengthFeet.setToolTipText("Enter Whole Numbers");
		roomLengthFeet.setText("8");
		roomLengthFeet.setBounds(131, 174, 80, 20);
		add(roomLengthFeet);
		
		JLabel label_6 = new JLabel("Feet");
		label_6.setBounds(221, 180, 44, 14);
		add(label_6);
		
		SpinnerModel model4 = new SpinnerNumberModel(0, 0, 11.75, 0.25);
		roomLengthInches = new JSpinner((SpinnerModel) model4);
		roomLengthInches.setToolTipText("Select Inches");
		roomLengthInches.setBounds(290, 174, 50, 20);
		add(roomLengthInches);
		
		JLabel label_7 = new JLabel("Inches");
		label_7.setBounds(350, 180, 49, 14);
		add(label_7);
		
		JLabel labelRoomHeight = new JLabel("Room Height");
		labelRoomHeight.setBounds(10, 211, 111, 14);
		add(labelRoomHeight);
		
		roomHeightFeet = new JTextPane();
		roomHeightFeet.setToolTipText("Enter Whole Numbers");
		roomHeightFeet.setText("8");
		roomHeightFeet.setBounds(131, 205, 80, 20);
		add(roomHeightFeet);
		
		JLabel label_5 = new JLabel("Feet");
		label_5.setBounds(221, 211, 44, 14);
		add(label_5);
		
		SpinnerModel model5 = new SpinnerNumberModel(0, 0, 11.75, 0.25);
		roomHeightInches = new JSpinner((SpinnerModel) model5);
		roomHeightInches.setToolTipText("Select Inches");
		roomHeightInches.setBounds(290, 205, 50, 20);
		add(roomHeightInches);
		
		JLabel label_8 = new JLabel("Inches");
		label_8.setBounds(350, 211, 49, 14);
		add(label_8);
		
		JLabel labelRoomDims = new JLabel("Overall Room Dimensions");
		labelRoomDims.setBounds(154, 118, 175, 14);
		add(labelRoomDims);
		
		JLabel labelDisplayWallDims = new JLabel("Display Wall Dimensions");
		labelDisplayWallDims.setBounds(154, 11, 173, 14);
		add(labelDisplayWallDims);
		
		JLabel label = new JLabel("Inches");
		label.setBounds(350, 283, 49, 14);
		add(label);
		
		SpinnerModel model6 = new SpinnerNumberModel(0, 0, 11.75, 0.25);
		furthestViewerInches = new JSpinner((SpinnerModel) model6);
		furthestViewerInches.setToolTipText("Select Inches");
		furthestViewerInches.setBounds(290, 277, 50, 20);
		add(furthestViewerInches);
		
		JLabel label_9 = new JLabel("Feet");
		label_9.setBounds(221, 283, 44, 14);
		add(label_9);
		
		furthestViewerFeet = new JTextPane();
		furthestViewerFeet.setToolTipText("Enter Whole Numbers");
		furthestViewerFeet.setText("8");
		furthestViewerFeet.setBounds(131, 277, 80, 20);
		add(furthestViewerFeet);
		
		JLabel labelFurthestViewer = new JLabel("Furthest Viewer");
		labelFurthestViewer.setBounds(10, 283, 111, 14);
		add(labelFurthestViewer);
		
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				displaySize();
				setAllRoomDimensions();
				SiteSurveyProposalGenerator.mainFrame.setContentPane(nextPane);
				SiteSurveyProposalGenerator.mainFrame.invalidate();
				SiteSurveyProposalGenerator.mainFrame.validate();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(SiteSurveyProposalGenerator.mainFrame, "You entered bad data.");
				}
			}
		});
		nextButton.setToolTipText("Click To Advance");
		nextButton.setBounds(162, 367, 89, 23);;
		add(nextButton);
		
		JLabel lblViewingDistance = new JLabel("Display Viewing Distance");
		lblViewingDistance.setBounds(154, 252, 175, 14);
		add(lblViewingDistance);
		
		checkboxUnknownDistance = new JCheckBox("Unknown Distance");
		checkboxUnknownDistance.setToolTipText("Select if furthest viewing distance is unknown");
		checkboxUnknownDistance.setBounds(131, 310, 136, 23);
		add(checkboxUnknownDistance);
	}

	//determines display size by parsing user input then comparing display heights
	public void displaySize() throws NumberFormatException {
		double furthestViewer = 0;
		if (checkboxUnknownDistance.isSelected() == false) {
			try {
				furthestViewerInches.commitEdit();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String spinnerValue = furthestViewerInches.getValue().toString();
			furthestViewer = (Double.parseDouble(furthestViewerFeet.getText()) * 12) + (Double.parseDouble(spinnerValue));
		} else { //executes only if distance is unknown
			try {
				roomLengthInches.commitEdit();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String spinnerValue = roomLengthInches.getValue().toString();
			furthestViewer = (Double.parseDouble(roomLengthFeet.getText()) * 12) + (Double.parseDouble(spinnerValue) - 36);
		}
		displayHeight = furthestViewer / 6;
		findClosestDisplaySize(displayHeight);
	}

	//assigns display width to selected display variable to later calculate fit
	private void findClosestDisplaySize(double displayHeight) {
		if (displayHeight <= ALLDISPLAYHEIGHTS[0]) {
			selectedDisplay = ALLDISPLAYWIDTHS[0];
		} else if (displayHeight > ALLDISPLAYHEIGHTS[0] && displayHeight <= ALLDISPLAYHEIGHTS[1]) {
			selectedDisplay = ALLDISPLAYWIDTHS[1];
		} else if (displayHeight > ALLDISPLAYHEIGHTS[1] && displayHeight <= ALLDISPLAYHEIGHTS[2]) {
			selectedDisplay = ALLDISPLAYWIDTHS[2];
		} else if (displayHeight > ALLDISPLAYHEIGHTS[2] && displayHeight <= ALLDISPLAYHEIGHTS[3]) {
			selectedDisplay = ALLDISPLAYWIDTHS[3];
		} else if (displayHeight >= ALLDISPLAYHEIGHTS[4]) {
			selectedDisplay = ALLDISPLAYWIDTHS[4];
		}				
	}
	
	//assigns values to all room dimension variables
		private void setAllRoomDimensions() throws NumberFormatException {
			//sets display wall width variable in inches
			try {
				displayWallWidthInches.commitEdit();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String spinnerValue = displayWallWidthInches.getValue().toString();
			displayWallWidth = (Double.parseDouble(displayWallWidthFeet.getText()) * 12) + (Double.parseDouble(spinnerValue));
			
			//sets display wall height variable in inches
			try {
				displayWallHeightInches.commitEdit();
			}catch (ParseException e) {
				e.printStackTrace();
			}
			String spinnerValue2 = displayWallHeightInches.getValue().toString();
			displayWallHeight = (Double.parseDouble(displayWallHeightFeet.getText()) * 12) + (Double.parseDouble(spinnerValue2));
			//sets room width variable in inches
			try {
				roomWidthInches.commitEdit();
			}catch (ParseException e) {
				e.printStackTrace();
			}
			String spinnerValue3 = roomWidthInches.getValue().toString();
			totalRoomWidth = (Double.parseDouble(roomWidthFeet.getText()) * 12) + (Double.parseDouble(spinnerValue3));
			
			//sets room height variable in inches
			try {
				roomHeightInches.commitEdit();
			}catch (ParseException e) {
				e.printStackTrace();
			}
			String spinnerValue4 = roomHeightInches.getValue().toString();
			totalRoomHeight = (Double.parseDouble(roomHeightFeet.getText()) * 12) + (Double.parseDouble(spinnerValue4));
			
			//sets room length variable in inches
			try {
				roomLengthInches.commitEdit();
			}catch (ParseException e) {
				e.printStackTrace();
			}
			String spinnerValue5 = roomLengthInches.getValue().toString();
			totalRoomLength = (Double.parseDouble(roomLengthFeet.getText()) * 12) + (Double.parseDouble(spinnerValue5));
		}	
}
