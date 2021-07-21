package uk.ac.ucl.cs.comp0110;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CarView extends JFrame{
    public Car model;
    public Graphics2D g2d;
    public ScheduledExecutorService service;
    Polygon leftFrontIndicator;
    Polygon rightFrontIndicator;
    Polygon leftSideIndicator;
    Polygon rightSideIndicator;
    Polygon leftCornerLight;
    Polygon rightCornerLight;
    Polygon leftBackIndicator;
    Polygon rightBackIndicator;
    public boolean leftIndicatorFlashed;
    public boolean rightIndicatorFlashed;
    public JPanel bottomPanel = new JPanel();
    public JRadioButton leftDirection;
    public JRadioButton rightDirection;
    public JRadioButton leftTipBlinking;
    public JRadioButton rightTipBlinking;
    public JRadioButton hazardSwitch;
    public JRadioButton soldInUKOrCanada;
    public JRadioButton keyInPosition;
    public JRadioButton keyInserted;
    public JRadioButton noKeyInserted;
    public JRadioButton ambientLight;
    public JRadioButton dayTimeRunningLight;
    public JTextField exteriorBrightness;
    public JComboBox lightRotarySwitch;
    public JRadioButton darknessSwitch;
    public JRadioButton doorPosition;

    private int numberOfFlashCycles;
    public long numseconds=0;
    public CarView(Car model) {
        this.model=model;

        leftIndicatorFlashed=false;
        rightIndicatorFlashed=false;
        leftDirection = new JRadioButton("Downward Direction Blinking");
        rightDirection = new JRadioButton("Upward Direction Blinking");
        leftTipBlinking=new JRadioButton("Downward Tip-Blinking");
        rightTipBlinking=new JRadioButton("Upward Tip-Blinking");
        hazardSwitch=new JRadioButton("Hazard Warning Button");
        soldInUKOrCanada=new JRadioButton("Country sold in UK or Canada");
        keyInserted=new JRadioButton("Key Inserted");
        noKeyInserted=new JRadioButton("No Key Inserted");
        keyInPosition=new JRadioButton("Key In Position");
        ambientLight=new JRadioButton("Ambient Light Status");
        dayTimeRunningLight=new JRadioButton("Daytime Running Light Status");
        lightRotarySwitch= new JComboBox(new String[]{"Off", "On","Auto"});
        darknessSwitch=new JRadioButton("Darkness Switch");
        doorPosition=new JRadioButton("Door Open");
        exteriorBrightness=new JTextField();
        numberOfFlashCycles=0;
        service= Executors.newSingleThreadScheduledExecutor();
        makeFrame();


    }

    public void drawHazard(Graphics g){
        if (model.getHazardSwitchState()==true){
            if (leftIndicatorFlashed == false && rightIndicatorFlashed==false) {
                g2d.setPaint(new Color(255, 255, 0));
                g.fillPolygon(leftFrontIndicator);
                g.fillPolygon(leftSideIndicator);
                g.fillPolygon(leftBackIndicator);
                g.fillPolygon(rightFrontIndicator);
                g.fillPolygon(rightSideIndicator);
                g.fillPolygon(rightBackIndicator);
                leftIndicatorFlashed = true;
                rightIndicatorFlashed=true;


            } else {
                g2d.setPaint(new Color(211, 211, 211));
                g.fillPolygon(leftFrontIndicator);
                g.fillPolygon(leftSideIndicator);
                g.fillPolygon(leftBackIndicator);
                g.fillPolygon(rightFrontIndicator);
                g.fillPolygon(rightSideIndicator);
                g.fillPolygon(rightBackIndicator);
                leftIndicatorFlashed = false;
                rightIndicatorFlashed=false;


            }
        }
    }


    public void drawLeftBlinking(Graphics g){
        changeSelectedButton();

        if (model.getBlinkingState("Left")==Blinking.FLASHING && model.getFlashingCycles("Left") == false && model.getHazardSwitchState()==false) {
            
            if (model.getFlashState()==Flashing.DARK) {
                g2d.setPaint(new Color(255, 255, 0));
                if (model.getDimmedLightStatus("Left")==50){
                    g2d.setPaint(new Color(127,127,0));
                }
                g.fillPolygon(leftFrontIndicator);
                g.fillPolygon(leftSideIndicator);
               g.fillPolygon(leftBackIndicator);
                model.changeFlashState();



            } else {
                g2d.setPaint(new Color(211, 211, 211));
                g.fillPolygon(leftSideIndicator);

                if (model.getLowBeamState(model.getHeadLight())==LowBeamState.INACTIVE){
                    g.fillPolygon(leftFrontIndicator);
                    g.fillPolygon(rightBackIndicator);
                }
                model.changeFlashState();


            }
        } else if (model.getBlinkingState("Left") == Blinking.NONFLASHING && model.getFlashingCycles("Left")==false)  {

            g2d.setPaint(new Color(211, 211, 211));
            g.fillPolygon(leftSideIndicator);

            if (model.getLowBeamState(model.getHeadLight())==LowBeamState.INACTIVE){
                g.fillPolygon(leftFrontIndicator);
                g.fillPolygon(leftBackIndicator);

            }

        }
    }
    public void drawLowBeamHeadLight(Graphics g) {
        if (model.getLightRotarySwitchState() == LightRotarySwitchState.OFF) {
            g2d.setPaint(new Color(211, 211, 211));
            g.fillPolygon(leftFrontIndicator);
            g.fillPolygon(rightFrontIndicator);
        }
        if ((model.getLowBeamState(model.getHeadLight())==LowBeamState.ACTIVE)) {
            g2d.setPaint(new Color(100, 150, 0));
            g.fillPolygon(leftFrontIndicator);
            g.fillPolygon(rightFrontIndicator);
            g.fillPolygon(leftBackIndicator);
            g.fillPolygon(rightBackIndicator);
        }
        if ((model.getHeadLight().getLowBeamState()==LowBeamState.ACTIVE && model.getLeftIndicator().getDimmedLight()==50)) {
            g2d.setPaint(new Color(100, 100, 0));
            g.fillPolygon(leftFrontIndicator);
            g.fillPolygon(rightFrontIndicator);
            g.fillPolygon(leftBackIndicator);
            g.fillPolygon(rightBackIndicator);
        }
        if (darknessSwitch.isSelected()){
            ambientLight.setSelected(false);
        }

    }

    public void drawLeftTipBlinking(Graphics g){
        changeSelectedButton();
        if (model.getBlinkingState("Left")==Blinking.FLASHING && model.getFlashingCycles("Left") == true  && model.getHazardSwitchState()==false) {

            leftTipBlinking.setSelected(false);
            if (model.getFlashState()==Flashing.DARK) {
                g2d.setPaint(new Color(255, 255, 0));
                g.fillPolygon(leftBackIndicator);
                g.fillPolygon(leftSideIndicator);
                g.fillPolygon(leftFrontIndicator);
                model.changeFlashState();
                int flashCycle=model.getNumberofFlashCycles()+1;
                model.setNumberofFlashCycles(flashCycle);
            } else {
                g2d.setPaint(new Color(211, 211, 211));
                g.fillPolygon(leftSideIndicator);

                if (model.getLowBeamState(model.getHeadLight())==LowBeamState.INACTIVE){
                    g.fillPolygon(leftFrontIndicator);
                    g.fillPolygon(leftBackIndicator);
                }
                model.changeFlashState();

            }
            if (model.getNumberofFlashCycles()==3) {
                model.setNumberofFlashCycles(0);
                model.setLengthOfTimeHeld(0);
                model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
                leftTipBlinking.setSelected(false);

            }
        }
    }
    public void changeSelectedButton(){
        if (model.getBlinkingState("Left")==Blinking.FLASHING && model.getHazardSwitchState()==false){
            rightTipBlinking.setSelected(false);
            rightDirection.setSelected(false);
            if (model.getFlashingCycles("Left")==false){
                leftDirection.setSelected(true);
                leftTipBlinking.setSelected(false);
            }else{
                leftTipBlinking.setSelected(true);
                leftDirection.setSelected(false);
            }
        }
        if (model.getBlinkingState("Right")==Blinking.FLASHING && model.getHazardSwitchState()==false){
            leftTipBlinking.setSelected(false);
            leftDirection.setSelected(false);
            if (model.getFlashingCycles("Right")==false){
                rightDirection.setSelected(true);
                rightTipBlinking.setSelected(false);
            }else{
                rightDirection.setSelected(false);
                rightTipBlinking.setSelected(true);
            }
        }
    }
    public void drawRightBlinking(Graphics g){
        changeSelectedButton();
        if (model.getBlinkingState("Right") == Blinking.FLASHING && model.getFlashingCycles("Right") == false  && model.getHazardSwitchState()==false) {
            if (model.getFlashState()==Flashing.DARK) {

                g2d.setPaint(new Color(255, 255, 0));
                if (model.getDimmedLightStatus("Right")==50){
                    g2d.setPaint(new Color(127,127,0));
                }
                g.fillPolygon(rightFrontIndicator);
                g.fillPolygon(rightSideIndicator);
                g.fillPolygon(rightBackIndicator);
                model.changeFlashState();
            } else {

                g2d.setPaint(new Color(211, 211, 211));
                g.fillPolygon(rightSideIndicator);
                g.fillPolygon(rightBackIndicator);
                if (model.getLowBeamState(model.getHeadLight())==LowBeamState.INACTIVE){
                    g.fillPolygon(rightFrontIndicator);
                    g.fillPolygon(rightBackIndicator);
                }
                model.changeFlashState();

            }

        } else if (model.getBlinkingState("Right") == Blinking.NONFLASHING) {

            g2d.setPaint(new Color(211, 211, 211));
            g.fillPolygon(rightSideIndicator);

            if (model.getLowBeamState(model.getHeadLight())==LowBeamState.INACTIVE){
                g.fillPolygon(rightFrontIndicator);
                g.fillPolygon(rightBackIndicator);
            }

        }
    }
    public void drawRightTipBlinking(Graphics g){
        changeSelectedButton();
        if (model.getBlinkingState("Right") == Blinking.FLASHING && model.getFlashingCycles("Right") == true  && model.getHazardSwitchState()==false) {
            rightTipBlinking.setSelected(false);

            if (model.getFlashState()==Flashing.DARK) {

                g2d.setPaint(new Color(255, 255, 0));
                g.fillPolygon(rightFrontIndicator);
                g.fillPolygon(rightSideIndicator);
                g.fillPolygon(rightBackIndicator);
                model.changeFlashState();
                int flashCycle=model.getNumberofFlashCycles()+1;
                model.setNumberofFlashCycles(flashCycle);
            } else {

                g2d.setPaint(new Color(211, 211, 211));

                g.fillPolygon(rightSideIndicator);

                model.changeFlashState();
                if (model.getLowBeamState(model.getHeadLight())==LowBeamState.INACTIVE){
                    g.fillPolygon(rightFrontIndicator);
                    g.fillPolygon(rightBackIndicator);
                }

            }
            if (model.getNumberofFlashCycles()==3) {
                model.setNumberofFlashCycles(0);
                model.setLengthOfTimeHeld(0);
                model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
                rightTipBlinking.setSelected(false);
            }
        }
    }

    public void paint(final Graphics g) {
        super.paint(g);
        g2d = (Graphics2D) g;
        leftFrontIndicator = new Polygon(new int[]{getWidth() / 3 + 70, getWidth() / 3 + 25, getWidth() / 3 + 20}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        rightFrontIndicator = new Polygon(new int[]{getWidth() / 3 - 30, getWidth() / 3, getWidth() / 3}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        leftSideIndicator = new Polygon(new int[]{getWidth() / 3 + 70, getWidth() / 3 + 25, getWidth() / 3 + 20}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        rightSideIndicator = new Polygon(new int[]{getWidth() / 3 - 30, getWidth() / 3, getWidth() / 3}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        leftBackIndicator = new Polygon(new int[]{getWidth() / 3 + 70, getWidth() / 3 + 25, getWidth() / 3 + 20}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        rightBackIndicator = new Polygon(new int[]{getWidth() / 3 - 30, getWidth() / 3, getWidth() / 3}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        leftCornerLight= new Polygon(new int[]{getWidth() / 3 + 70, getWidth() / 3 + 25, getWidth() / 3 + 20}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        rightCornerLight=new Polygon(new int[]{getWidth() / 3 - 30, getWidth() / 3, getWidth() / 3}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        leftFrontIndicator.translate(1, -180);
        rightFrontIndicator.translate(260, -180);
        leftSideIndicator.translate(-70, -60);
        rightSideIndicator.translate(330, -60);
        leftBackIndicator.translate(1, 300);
        rightBackIndicator.translate(260, 300);
        leftCornerLight.translate(-140,-200);
        rightCornerLight.translate(360,-190);
        g.drawRect(getWidth() / 3, getHeight() / 8, 300, 600);
        g.drawPolygon(leftFrontIndicator);
        g.drawPolygon(rightFrontIndicator);
        g.drawPolygon(leftSideIndicator);
        g.drawPolygon(rightSideIndicator);
        g.drawPolygon(leftBackIndicator);
        g.drawPolygon(rightBackIndicator);
        g.drawPolygon(leftCornerLight);
        g.drawPolygon(rightCornerLight);
        checkIgnition();
        drawLowBeamHeadLight(g);
        drawLeftBlinking(g);
        drawRightBlinking(g);
        drawLeftTipBlinking(g);
        drawRightTipBlinking(g);
        drawHazard(g);
    }
    public void makeFrame() {
        Container contentPane = getContentPane();
        JPanel centralGrid = new JPanel();
        JPanel southPanel = new JPanel();
        JPanel westPanel=new JPanel();
        westPanel.setBorder(new EtchedBorder());
        southPanel.setBorder(new EtchedBorder());
        southPanel.add(makeBlinkingInputs());
        westPanel.add(makeOptionalInputs());
        centralGrid.setLayout(new BorderLayout(6, 6));
        centralGrid.add(southPanel, BorderLayout.SOUTH);
        centralGrid.add(westPanel,BorderLayout.WEST);
        contentPane.add(centralGrid);
        setTitle("Exterior Light System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        setVisible(true);
        service.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {

                    repaint();


            }
        },0,500,TimeUnit.MILLISECONDS);

    }
    public JPanel makeOptionalInputs(){
        final JPanel optionalInputs=new JPanel();
        optionalInputs.setLayout(new BoxLayout(optionalInputs, BoxLayout.Y_AXIS));
        optionalInputs.add(soldInUKOrCanada);
        optionalInputs.add(noKeyInserted);
        optionalInputs.add(keyInPosition);
        optionalInputs.add(keyInserted);
        optionalInputs.add(lightRotarySwitch);
        optionalInputs.add(doorPosition);
        optionalInputs.add(ambientLight);
        optionalInputs.add(dayTimeRunningLight);
        optionalInputs.add(exteriorBrightness);
        optionalInputs.add(darknessSwitch);
        return optionalInputs;
    }
    public JPanel makeBlinkingInputs() {
        final JPanel typeOfBlinking = new JPanel();
        final JPanel blinkingDirection = new JPanel();

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        blinkingDirection.setLayout(new FlowLayout());
        typeOfBlinking.setLayout(new FlowLayout());
        JLabel blinkingType = new JLabel("Type of Blinking:");
        JLabel direction = new JLabel("Direction of Pitarm");
        blinkingDirection.add(direction);
        blinkingDirection.add(leftDirection);
        blinkingDirection.add(leftTipBlinking);
        blinkingDirection.add(rightTipBlinking);
        blinkingDirection.add(rightDirection);
        blinkingDirection.add(hazardSwitch);
        typeOfBlinking.add(blinkingType);
        bottomPanel.add(typeOfBlinking);
        bottomPanel.add(blinkingDirection);
        return bottomPanel;
    }
    public void checkIgnition(){
        if (model.getIgnitionState()==IgnitionStatus.KEYINIGNITIONONPOSITION){
            noKeyInserted.setSelected(false);
            keyInserted.setSelected(false);
        }else if (model.getIgnitionState()==IgnitionStatus.KEYINSERTED){
            noKeyInserted.setSelected(false);
            keyInPosition.setSelected(false);
        }else{
            keyInPosition.setSelected(false);
            keyInserted.setSelected(false);
        }
        if (model.getIgnitionState()!=IgnitionStatus.KEYINIGNITIONONPOSITION){
            leftDirection.setEnabled(false);
            rightDirection.setEnabled(false);
            leftTipBlinking.setEnabled(false);
            rightTipBlinking.setEnabled(false);

            soldInUKOrCanada.setEnabled(false);

        }else{
            leftDirection.setEnabled(true);
            rightDirection.setEnabled(true);
            leftTipBlinking.setEnabled(true);
            rightTipBlinking.setEnabled(true);

            soldInUKOrCanada.setEnabled(true);

        }
    }
    public JRadioButton getLeftDirection(){
        return leftDirection;
    }
    public JRadioButton getRightDirection(){
        return rightDirection;
    }
    public JRadioButton getLeftTipBlinking(){
        return leftTipBlinking;
    }
    public JRadioButton getRightTipBlinking(){
        return rightTipBlinking;
    }
    public JRadioButton getHazardSwitch(){
        return hazardSwitch;
    }
    public JRadioButton getSoldInUKOrCanada(){
        return soldInUKOrCanada;
    }
    public JRadioButton getKeyInPosition(){
        return keyInPosition;
    }
    public JRadioButton getKeyInserted(){
        return keyInserted;
    }
    public JRadioButton getNoKeyInserted(){
        return noKeyInserted;
    }
    public JComboBox getLightRotarySwitch(){
        return lightRotarySwitch;
    }
    public JRadioButton getDoorPosition(){
        return doorPosition;
    }
    public JRadioButton getAmbientLight(){
        return ambientLight;
    }
    public JRadioButton getDayTimeRunningLight(){
        return dayTimeRunningLight;
    }
    public JTextField getExteriorBrightness(){
        return exteriorBrightness;
    }
    public JRadioButton getDarknessSwitch(){
        return darknessSwitch;
    }
}