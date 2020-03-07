package frc.robot;
/**
 * @apiNote Class dedicated to testing specific components of the robot according to what is being tested at that time. 
 * @author John C. Pace
 * @version Color Sensor and Color Detection Testing 
 */
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
/**
 * Imports Rev Robotics Information
 */
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
public class ColorBot extends TimedRobot{
    public ColorBot(){}
    private String col;
    private String mode;
    private Timer time = new Timer();
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private ColorSensorV3 m_colorSensor;
    private ColorMatch m_colorMatcher;
    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    @Override
    public void robotInit(){
        super.robotInit();
        mode = new String("Intialize");
        col = ""; 
        m_colorSensor = new ColorSensorV3(i2cPort);
        m_colorMatcher = new ColorMatch();
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
    }
    @Override
    public void robotPeriodic(){
        super.robotPeriodic();
        SmartDashboard.putString("Current Color", "Non-Existant");
        SmartDashboard.putString("Mode of Operation:", mode);
        SmartDashboard.putString("Constant Time", time.toString());
        detectColor();
        SmartDashboard.putString("Raw Color", col);
    }
    @Override
    public void teleopInit(){
        time.reset();
        super.teleopInit();
        mode = "Teleop Init";
    }
    @Override
    public void teleopPeriodic(){
        time.start();
        super.teleopPeriodic();
        mode = "Teleop Periodic";
    }
    @Override
    public void autonomousInit(){
        super.autonomousInit();
        mode = "Autonomous Init";
        time.reset();
    }  
    @Override
    public void autonomousPeriodic(){
        super.autonomousPeriodic();
        time.start();
        mode = "Autonomous Periodic";
    }  
    @Override
    public void disabledInit(){super.disabledInit();
    time.stop();}
    public void disabledPeriodic(){super.disabledPeriodic();
    mode = "Disabled";}
    public void testInit(){super.testInit();}
    public void testPeriodic(){super.testPeriodic();
    mode = "Test";}

    public void detectColor(){
        Color detectedColor = m_colorSensor.getColor();

        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
          col = "Blue";
        } else if (match.color == kRedTarget) {
          col = "Red";
        } else if (match.color == kGreenTarget) {
          col = "Green";
        } else if (match.color == kYellowTarget) {
          col = "Yellow";
        } else {
          col = "Unknown";
        }
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", col);

    }
}