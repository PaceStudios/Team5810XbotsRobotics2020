package frc.robot;

//import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
//import edu.wpi.first.wpilibj.Joystick;


//import Constants class
import frc.robot.Subsystems.Constants;

public class DumbBot extends TimedRobot
{
    private final String mode;
    private Victor l_motor = new Victor(Constants.dumbBotArcadeBaseMotorL_PWM);
    private Victor r_motor = new Victor(Constants.dumbBotArcadeBaseMotorR_PWM);
    private XboxController joy_1 = new XboxController(Constants.XBOXCONTROL);

    public DumbBot(){
        mode = "Dumb Bot LMAO";
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
        double speed = (-joy_1.getRawAxis(1) * 0.6);
        double turn = (joy_1.getRawAxis(4) * 0.3);
        double left = speed + turn;
        double right = speed - turn;
        l_motor.set(left);
        r_motor.set(right);

    }
    @Override
    public void testPeriodic(){
        super.testPeriodic();
    }




}