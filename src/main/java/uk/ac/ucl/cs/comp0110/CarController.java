package uk.ac.ucl.cs.comp0110;
import java.awt.event.*;

public class CarController {
    public CarView view;
    private Car model;

    private double lengthOfTimeHeld=0;
    public CarController(CarView view, Car model) {
        this.view = view;
        this.model = model;

    }

    public void addButtonFunctions() {
        view.getHazardSwitch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hazardSwitchPressed();
            }
        });
        view.getLeftDirection().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftDirectionPressed();
            }
        });
        view.getRightDirection().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightDirectionPressed();
            }
        });
        view.getSoldInUKOrCanada().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soldInUKOrCanadaPressed();
            }
        });
        view.getNoKeyInserted().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noKeyInsertedPressed();
            }
        });
        view.getKeyInPosition().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyInPosition();
            }
        });
        view.getKeyInserted().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyInsertedPressed();
            }
        });
        view.getLightRotarySwitch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lightRotarySwitchPressed();
            }
        });
        view.getLeftTipBlinking().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                model.countTimeInPosition();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                leftTipBlinkingPressed();

            }
        });
        view.getRightTipBlinking().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                model.countTimeInPosition();

            }
            @Override
            public void mouseReleased(MouseEvent e) {
                rightTipBlinkingPressed();
            }
        });
        view.getDoorPosition().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doorPressed();
            }
        });
        view.getAmbientLight().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ambientLightPressed();
            }
        });
        view.getDayTimeRunningLight().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dayTimeRunningLightPressed();
            }
        });
        view.getExteriorBrightness().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exteriorBrightnessPressed();
            }
        });
        view.getDarknessSwitch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darknessSwitchPressed();
            }
        });
        view.getSpeedOfCar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carDuration();
            }
        });
        view.getPassCorner().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passedCorner();
            }
        });
    }


    public void leftDirectionPressed() {

        if (model.getBlinkingState("Left") == Blinking.FLASHING && model.getHazardSwitchState()==false) {
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
        } else {
            model.setPitmanArmPosition(PitmanArmPosition.DOWNWARD7);
        }
    }
    public void rightDirectionPressed() {

        if (model.getBlinkingState("Right") == Blinking.FLASHING && model.getHazardSwitchState()==false) {
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);

        }else{
            model.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
        }

    }
    public void leftTipBlinkingPressed(){

        if (model.getBlinkingState("Left")==Blinking.FLASHING && model.getFlashingCycles("Left")==true){
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
            model.stopTimer();
        }else{
            model.stopTimer();
            model.tipPitmanArm(PitmanArmPosition.DOWNWARD5,model.getLengthOfTimeHeld());
        }
    }
    public void rightTipBlinkingPressed(){

        if (model.getBlinkingState("Right")==Blinking.FLASHING && model.getFlashingCycles("Right")==true){
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
            model.stopTimer();
        }else{
            model.stopTimer();
            model.tipPitmanArm(PitmanArmPosition.UPWARD5,model.getLengthOfTimeHeld());
        }
    }
    public void hazardSwitchPressed(){
        if (model.getHazardSwitchState()==false){
            model.pressHazardSwitch(true);
        }else{
            model.pressHazardSwitch(false);

        }
    }

    public void soldInUKOrCanadaPressed(){
        if (model.getDimmedLightStatus("Left")==0 || model.getDimmedLightStatus("Right")==0){
            model.setInUSAOrCanada(true);
        }else{
            model.setInUSAOrCanada(false);
        }
    }
    public void noKeyInsertedPressed(){
        model.isIgnitionOn(IgnitionStatus.NOKEYINSERTED);
    }
    public void keyInsertedPressed(){
        model.isIgnitionOn(IgnitionStatus.KEYINSERTED);
    }
    public void keyInPosition(){
        model.isIgnitionOn(IgnitionStatus.KEYINIGNITIONONPOSITION);
        model.checkPitmanArmState();
    }
    public void lightRotarySwitchPressed(){
        if (view.getLightRotarySwitch().getSelectedItem().equals("On")){
            model.turnLightRotarySwitch(LightRotarySwitchState.ON);
        }
        if (view.getLightRotarySwitch().getSelectedItem().equals("Off")){
            model.turnLightRotarySwitch(LightRotarySwitchState.OFF);
        }
        if (view.getLightRotarySwitch().getSelectedItem().equals("Auto")){
            model.turnLightRotarySwitch(LightRotarySwitchState.AUTO);
        }
    }
    public void dayTimeRunningLightPressed(){
        if (model.getDayTimeRunningLight()==false){
            model.engageDayTimeRunningLight(true);
        }else{
            model.engageDayTimeRunningLight(false);
        }
    }
    public void doorPressed(){
        if (model.getAllDoorsClosed()==true){
            model.isAllDoorsClosed(false);
        }else{
            model.isAllDoorsClosed(true);
        }
    }
    public void ambientLightPressed(){
        if (model.getAmbientLight()==true){
            model.engageAmbientLight(false);
        }else{
            model.engageAmbientLight(true);
        }
    }
    public void exteriorBrightnessPressed(){
        model.setExteriorBrightness(Integer.parseInt(view.getExteriorBrightness().getText()));
    }
    public void darknessSwitchPressed(){
        if (model.getDarknessSwitch()==true){
            model.pressDarknessSwitch(false);
        }else{
            model.pressDarknessSwitch(true);
        }
    }
    public void carDuration(){
        model.setDrivingSpeed(Integer.parseInt(view.getSpeedOfCar().getText()));
    }
    public void passedCorner(){
        model.countDurationOfPassingCornerTime();
    }
}
