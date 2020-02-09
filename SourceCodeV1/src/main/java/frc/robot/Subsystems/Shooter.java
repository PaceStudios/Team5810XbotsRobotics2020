package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.*;
/**
 * @author John C. Pace
 * @since 01/19/2020
 * @version 01/27/2020
 * @apiNote This class is dedicated to the beginning of what will be our shooting mechanism. It will work with the limelight class most likely. 
 * Going to use the 775 Pro Motors (Will use a maximum of 2 Motor Controllers)
 */

public class Shooter
{
    private boolean alignmentReady = false;
    private Spark shooterMotors; // In reality, this object is 2 motors connected to ONE PWM Port with inverted wiring. 
    private Victor beltDriveMotor; // Motor dedicated
    //private Servo serv1; // Dedicated to controlling the Pitch and CONNECTS to DIO PORT (Ground, Supply, and Signal);
    private boolean targetNotFound = true;
    /**
     * Constructor for the Shooter class
     */
    public Shooter(){
         shooterMotors = new Spark(Constants.SHOOTERMOTERS_PWM);
         beltDriveMotor =new Victor(Constants.BELTDRIVEMOTOR_PWM);
         
    }
    public void shootBalls(Joystick joy, double powerInput){
        if(joy.getRawButton(1)){
            beltDriveMotor.set(powerInput);
            shooterMotors.set(powerInput);
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
            
        }
        else{
            
        }
    }
    /**
     * @apiNote method dedicated to setting all motor values to 0
     * @return Sets all the motor values to 0. 
     */
    public void killAllMotors(){
        beltDriveMotor.disable();
        shooterMotors.disable();
    }
    /**
     * @apiNote Dedicated to checking Limelight values and returning correct values based off the limelight interpretation. 
     * @return isAligned variable function deid
     */
    public boolean isAligned(){
        return alignmentReady;
    }
}