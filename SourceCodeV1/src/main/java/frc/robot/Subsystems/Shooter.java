package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.*;
import frc.robot.Subsystems.Constants;
/**
 * @author John C. Pace
 * @since 01/19/2020
 * @version 01/19/2020
 * @apiNote This class is dedicated to the beginning of what will be our shooting mechanism. It will work with the limelight class most likely. 
 * Going to use the 775 Pro Motors (Will use a maximum of 2 Motor Controllers)
 */

public class Shooter {
    private boolean alignmentReady = false;
    private Spark shooterMotor1;
    private Spark shooterMotor2;
    // private Solenoid sol;
    // private Solenoid sol2;
    // private Servo serv1;
    public Shooter(){
        shooterMotor1 = new Spark(Constants.SHOOTERMOTOR1_PWM);
        shooterMotor2 = new Spark(Constants.SHOOTERMOTOR2_PWM);
        //serv1 = new Servo()
    }
    public void shootBalls(){
        shooterMotor1.set(Constants.FULL_SPEED);
        shooterMotor2.set(-Constants.FULL_SPEED);
    }
    public void swivel(){}
    public void findTarget(){
        
    }
    public void killAllMotors(){}
    public boolean isAligned(){
        return alignmentReady;
    }
}