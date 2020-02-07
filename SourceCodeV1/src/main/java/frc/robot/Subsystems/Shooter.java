package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.*;
import frc.robot.Subsystems.Constants;
import frc.robot.Subsystems.*;
import edu.wpi.first.wpilibj2.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
/**
 * @author John C. Pace
 * @since 01/19/2020
 * @version 01/27/2020
 * @apiNote This class is dedicated to the beginning of what will be our shooting mechanism. It will work with the limelight class most likely. 
 * Going to use the 775 Pro Motors (Will use a maximum of 2 Motor Controllers)
 */

public class Shooter extends SubsystemBase
{
    private boolean alignmentReady = false;
    private Spark shooterMotors; // In reality, this object is 2 motors connected to ONE PWM Port with inverted wiring. 
    private Victor beltDriveMotor; // Motor dedicated
    private Servo serv1; // Dedicated to controlling the Pitch and CONNECTS to DIO PORT (Ground, Supply, and Signal);
    private boolean targetNotFound = true;
    private Limelight lemonlight = new Limelight();
    /**
     * Constructor for the Shooter class
     */
    public Shooter(){
         shooterMotors = new Spark(Constants.SHOOTERMOTERS_PWM);
         beltDriveMotor =new Victor(Constants.BELTDRIVEMOTOR_PWM);
         serv1 = new Servo(Constants.SERVO_MOTOR1_DIO);
    }
    public void shootBalls(Joystick joy, int power_level){
        if(joy.getRawButton(1)){
            beltDriveMotor.set(power_level);
            shooterMotors.set(power_level);
        }
        else
        {
            beltDriveMotor.set(0);
            shooterMotors.set(0);
        }
    }
    /**
     * @apiNote Method responsible for moving the motors, sirvo, and actuators for the swivel shooter. 
     */
    public void swivel(){}
    public void findTarget(Limelight a, int power_level){
        if(targetNotFound){
            serv1.set(power_level);
        }
        else{
            serv1.set(0);
        }
    }
    /**
     * @apiNote method dedicated to setting all motor values to 0
     * @return Sets all the motor values to 0. 
     */
    public void killAllMotors(){
        beltDriveMotor.set(0);
        shooterMotors.set(0);
        serv1.set(0);
    }
    /**
     * @apiNote Dedicated to checking Limelight values and returning correct values based off the limelight interpretation. 
     * @return isAligned variable function deid
     */
    public boolean isAligned(){
        return alignmentReady;
    }
}