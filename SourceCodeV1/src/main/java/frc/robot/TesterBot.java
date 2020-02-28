package frc.robot;
/**
 * @apiNote Class dedicated to testing specific components of the robot according to what is being tested at that time. 
 * @author John C. Pace
 * @version Encoding and Linear Actuator Test
 */

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TesterBot extends TimedRobot{
    public TesterBot(){}
    /**
     * Initializes the Robot Program
     */
    private String mode;
    private VictorSP motor1;
    private Encoder encoder1;
    private XboxController xcontrol;
    @Override
    public void robotInit(){
        super.robotInit();
        mode = new String("");
        motor1 = new VictorSP(0);
        encoder1 = new Encoder(0, 1, false, EncodingType.k4X);
        setUpEncoder();
        xcontrol = new XboxController(0);
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
        updateEncoder();
        if(xcontrol.getRawButton(0))
            runMotor();
        
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
         
        if (xcontrol.getRawButton(0)){
            motor1.set(1); 
        }
        else{
            motor1.set(0);
            encoder1.reset();
        }  
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
        int count = encoder1.get();
        int rawcount = encoder1.getRaw();
        double distance = encoder1.getDistance();
        double rate = encoder1.getRate();
        boolean direction = encoder1.getDirection();
        boolean stopped = encoder1.getStopped();

        SmartDashboard.setDefaultNumber("Count:", count);
        SmartDashboard.setDefaultNumber("Raw Count:", rawcount);
        SmartDashboard.setDefaultNumber("Distance", distance);
        SmartDashboard.setDefaultNumber("Rate", rate);
        SmartDashboard.setDefaultBoolean("Direction", direction);
        SmartDashboard.setDefaultBoolean("Stopped", stopped);
    

    }

}