package frc.robot;

//import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Subsystems.*;


//import Constants class
import frc.robot.Subsystems.Constants;
/**
 * @author John C. Pace
 * @since 01/03/2020
 * @version 01/27/2020
 * @apiNote Class dedicated to manipulating and working with the old robot. 
 */
public class DumbBot extends TimedRobot
{
    private final String mode;
    private Victor l_motor = new Victor(Constants.dumbBotArcadeBaseMotorL_PWM);
    private Victor r_motor = new Victor(Constants.dumbBotArcadeBaseMotorR_PWM);
    private XboxController x_1 = new XboxController(Constants.XBOXCONTROL_PORT);
    private Joystick joy1 = new Joystick(Constants.JOYSTICK_PORT);
    private Intake intake = new Intake(Constants.DUMBBOT_ARCADEBOT_INTAKEMOTOR_PWM);
    private Arm arm = new Arm();
    private double armMovementSpeed = 0;
    private double botMovementSpeed = 0;
    private double wristMovementSpeed = 0;  
    private Limelight lemonlight;

    public DumbBot(){
        mode = "Dumb Bot LMAO";
        lemonlight = new Limelight();
    }

    @Override
    public void robotInit(){
        super.robotInit();
        System.out.println("Welcome to the program: " + "\n\t" + "Current Mode: " + "\t" + mode);
        
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
        super.robotPeriodic();
        SmartDashboard.setDefaultNumber("Arm Speed", armMovementSpeed);
        SmartDashboard.setDefaultNumber("Wrist Speed", wristMovementSpeed);
        SmartDashboard.setDefaultNumber("Drive Speed" , botMovementSpeed);
        lemonlight.updateLimeLight();

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
        double speed = (-x_1.getRawAxis(1) * 0.6);
        double turn = (x_1.getRawAxis(4) * 0.3);
        double left = speed + turn;
        double right = speed - turn;
        l_motor.set(left);
        r_motor.set(right);

        
        if(joy1.getRawButtonPressed(8)){
            armMovementSpeed = 1;
        }
        else if (joy1.getRawButton(7)){
            armMovementSpeed = 1;
        }
        arm.liftArm(joy1, armMovementSpeed);
        if(joy1.getRawButton(5)){
            wristMovementSpeed = 1;
        }
        else if (joy1.getRawButton(3)){
            wristMovementSpeed = 1;
        }

    }
    @Override
    public void testPeriodic(){
        super.testPeriodic();
    }




}