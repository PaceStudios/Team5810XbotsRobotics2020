package frc.robot;
/**
 * @apiNote Class dedicated to testing specific components of the robot according to what is being tested at that time. 
 * @author John C. Pace
 * @version Encoding and Linear Actuator Test
 */

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Constants;
import edu.wpi.first.wpilibj.Servo;

public class TesterBot extends TimedRobot{
    public TesterBot(){}
    /**
     * Initializes the Robot Program
     */
    private String mode;
    private VictorSP motor1;
    private Encoder encoder1;
    private XboxController xcontrol;
    private Joystick stick;
    private boolean aPressed;
    private boolean bPressed;
    private boolean xPressed;
    private boolean lb;
    private boolean rb;
    private Servo serv;
    @Override
    public void robotInit(){
        super.robotInit();
        mode = new String("");
        motor1 = new VictorSP(0);
        encoder1 = new Encoder(0, 1, false, EncodingType.k4X);
        setUpEncoder();
        xcontrol = new XboxController(0);
        stick = new Joystick(1);
        aPressed = false;
        bPressed = false;
        xPressed = false;
        lb = false;
        rb = false;
        serv = new Servo(2);
    }
    /**
     * Initailizes the Autonomous Program
     */
    @Override
    public void autonomousInit(){
        super.autonomousInit();
        mode = "Autonomous";
    }
    /**
     * Intializes the teleOp Component of the Program
     */
    @Override
    public void teleopInit(){
        super.teleopInit();
        mode = "Tele Operation";
        ;

    }
    /**
     * Initializes the Disabled Portion of the Program
     */
    @Override
    public void disabledInit(){
        super.disabledInit();
        mode = "Disabled";
    }
    /**
     * Intializes the test portion of the program
     */
    public void testInit(){
        super.testInit();
        mode = "Testing";
    }
    /**
     * The constant periodic calling of the main Robot method
     */
    @Override
    public void robotPeriodic(){
        
        super.robotPeriodic();
        SmartDashboard.putString("Mode:", mode);
        SmartDashboard.putBoolean("A Button Pressed", aPressed);
        SmartDashboard.putBoolean("B Button Pressed", bPressed);
        SmartDashboard.putBoolean("X Button Pressed", xPressed);
        SmartDashboard.putBoolean("Right Bumper Pressed", rb);
        SmartDashboard.putBoolean("Left Bumper Pressed", lb);
       // SmartDashboard.putNumber("DB/String 1", encoder1.getDistance());
       // SmartDashboard.putNumber("DB/String 2", encoder1.getRate());
        SmartDashboard.putBoolean("Encoder is Moving", encoder1.getStopped());
        SmartDashboard.putNumber("Distance XA", encoder1.getDistance());
        SmartDashboard.putNumber("Rate XA", encoder1.getRate());
    }
        
    /**
     * The constant periodic autonomous caling of the main Robot method
     */
    @Override
    public void autonomousPeriodic(){
        super.autonomousPeriodic();
    }
    /**
     * Initalizes the Teleop Portion of the Program, best to call button commands and methods here 
     */
    @Override
    public void teleopPeriodic(){
        super.teleopPeriodic();
        updateEncoder();
        if(xcontrol.getRawButton(1)){
            aPressed = true;
            bPressed = false;
            xPressed = false;
            runMotor();
        }
        else if(xcontrol.getYButton()){
            aPressed = false;
            xPressed = false;
            bPressed = false;
            motor1.set(-.3);
        }
        else if(xcontrol.getBButton()){
            aPressed = false;
            bPressed = true;
            xPressed = false;
            encoder1.reset();
            setServoPositon(1);
        }
        else if(xcontrol.getXButton()){
            aPressed = false;
            bPressed = false;
            xPressed = true;
            setServoPositon(.25);
        }
        else{aPressed = false;
            bPressed = false;
            xPressed = false;
        motor1.set(0);
        }
    
    }
    /**
     * Intializes the disabled portion of the program (disable all motors here)
     */
    @Override
    public void disabledPeriodic(){super.disabledPeriodic();
    motor1.disable();}
    @Override
    public void testPeriodic(){super.testPeriodic();}
    /**
     * Runs a basic motor based 
     */
    public void runMotor(){
            motor1.set(.3); 
        
          
    }
    /**
     * Responsible for setting up the encoder 
     */
    public void setUpEncoder(){
        encoder1.setMaxPeriod(0.1);
        encoder1.setMinRate(10);
        encoder1.setDistancePerPulse(15);
        encoder1.setReverseDirection(false);
        encoder1.setSamplesToAverage(7);
    }
    /**
     * Responsible for updating the encoder values
     */
    public void updateEncoder(){
    }

    public void setServoPositon(double x){serv.setPosition(x);}

}