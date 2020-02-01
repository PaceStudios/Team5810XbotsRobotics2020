package frc.robot;

//import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.command.button.JoystickButton;
// import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.*;
// import frc.robot.Subsystems.*;
// import java.util.*;


//import Constants class
import frc.robot.Subsystems.Constants;

public class DumbBot extends TimedRobot
{
    private final String mode;
    private Victor l_motor = new Victor(Constants.dumbBotArcadeBaseMotorL_PWM);
    private Victor r_motor = new Victor(Constants.dumbBotArcadeBaseMotorR_PWM);
    private XboxController x_1 = new XboxController(Constants.XBOXCONTROL_PORT);
    private Joystick joy1 = new Joystick(Constants.JOYSTICK_PORT);
    // private Intake intake = new Intake(Constants.DUMBBOT_ARCADEBOT_INTAKEMOTOR_PWM);
    // private Arm arm = new Arm();
    public double speed = 0;
    public double intake_power = 0;
    public double lift_power = 0;
    public double wrist_power = 0;
    public double turn = 0;
    public double smartdashboredtestvalue = 0;
    public boolean intakeActivated = false;
    public boolean outtakeActivated = false;
    public Double[] power_values = new Double[3];
    public double getAxis5_value = 0;
    public double getAxis2_value = 0;
    public double turnValue = 0;
    public double speedValue = 0;
    public DumbBot(){
        mode = "Dumb Bot LMAO";
    }

    @Override
    public void robotInit(){
        super.robotInit();
        System.out.println("Welcome to the program: " + "\n\t" + "Current Mode: " + "\t" + mode);
        power_values[0] = intake_power;
        power_values[1] = lift_power;
        power_values[2] = wrist_power;
    }
    
    @Override
    public void disabledInit(){
        super.disabledInit();
    }
    @Override
    public void autonomousInit(){
        super.autonomousInit();
    }
    @Override
    public void teleopInit(){
        super.teleopInit();
        
    }
    @Override
    public void testInit(){
        super.testInit();
    }
    @Override
    public void robotPeriodic(){
        SmartDashboard.putNumber("Drive Speed", speed);
        SmartDashboard.putNumber("button pressed", smartdashboredtestvalue);
        SmartDashboard.putBoolean("Intake Activated", intakeActivated);
        SmartDashboard.putNumber("Axis 5 Value", getAxis2_value);
        SmartDashboard.putNumber("Axis 2 Value", getAxis2_value);
        SmartDashboard.putNumberArray("Power Values", power_values);
        SmartDashboard.putNumber("Turn Value:", turnValue);
        SmartDashboard.putNumber("Speed Value:", speedValue);
    }
    @Override 
    public void disabledPeriodic(){
        super.disabledPeriodic();
    }
    @Override
    public void autonomousPeriodic(){
        super.autonomousPeriodic();
    }
    @Override
    public void teleopPeriodic(){
        getAxis2_value = x_1.getRawAxis(2);
        getAxis5_value = x_1.getRawAxis(5);
        
        speed = ((x_1.getTriggerAxis(Hand.kRight)+(-x_1.getTriggerAxis(Hand.kLeft))) * 0.6);
        speedValue = speed;
        turn = (x_1.getRawAxis(4) * 0.8);
        turnValue = turn;
        double left = speed + turn;
        double right = speed - turn;
        if((x_1.getTriggerAxis(Hand.kRight)+(-x_1.getTriggerAxis(Hand.kLeft))==0)){
            l_motor.set(turn);
            r_motor.set(turn);
        }
        else{
            l_motor.set(left);
            r_motor.set(-right);  
        }
        if(joy1.getRawButton(1) == true){
            intake_power = 1;
            intakeActivated = true;
            outtakeActivated = false;
        }
        else if(joy1.getRawButton(2) == true){
            intake_power = -1;
            intakeActivated = false;
            outtakeActivated = true;
        }
        // intake.intakeFunct(joy1, intake_power);;
        if(joy1.getRawButton(3) == true){
            smartdashboredtestvalue = 1;
        }
        else{smartdashboredtestvalue = -1;}
        if(joy1.getRawButton(12)==true){
            lift_power =1;
        }
        else if(joy1.getRawButton(11) == true){
            lift_power = -1;
        }
        else{
            lift_power = 0;
        }
        // arm.moveArm(joy1, lift_power);
        if(joy1.getRawButton(5)==true){
            wrist_power =1;
        }
        else if(joy1.getRawButton(3) == true){
            wrist_power = -1;
        }
        else{
            wrist_power = 0;
        }
        // arm.moveWrist(joy1, wrist_power);

    }
    @Override
    public void testPeriodic(){
        super.testPeriodic();
    }




}
