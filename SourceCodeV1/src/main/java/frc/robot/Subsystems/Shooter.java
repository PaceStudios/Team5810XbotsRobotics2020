package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.*;
import frc.robot.Subsystems.Constants;
/**
 * @author John C. Pace
 * @since 01/19/2020
 * @version 01/19/2020
 * @apiNote This class is dedicated to the beginning of what will be our shooting mechanism. It will work with the limelight class most likely. 
 */
public class Shooter {
    private boolean alignmentReady = false;
    private VictorSP shooterMotor;
    public Shooter(){
        shooterMotor = new VictorSP(Constants.SHOOTERMOTOR1_PWM);

    }
    public void shootBalls(){
        shooterMotor.set(Constants.FULL_SPEED);
    }
    public void swivel(){}
    public void findTarget(){
        
    }
    public void killAllMotors(){}
    public boolean isAligned(){
        return alignmentReady;
    }
}