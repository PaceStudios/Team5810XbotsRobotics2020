package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.Subsystems.Constants;
import edu.wpi.first.wpilibj2.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
/**
 * @author John C. Pace
 * @since 01/12/20
 * @version 01/19/20
 * @apiNote Class meant to dealing with the potential motor systems. As of right now, the official intake remains unknown. 
 */
public class Intake extends SubsystemBase 

{
    // Motors can be changed according to what names they are.
    private boolean intakeEngaged = false;
    private Spark intake01;
    public Intake(){
    }
    public Intake(int pwm1){
        intake01 = new Spark(pwm1);
    }
    public void intakeBalls(double speed){
        intake01.set(-speed);
        }
    public void outtakeBalls(double speed){
        intake01.set(speed);
    }
    public boolean getIntakeStatus(){
        return intakeEngaged;
    }
    public void setEngaged(boolean flag){
        intakeEngaged = flag;
    }
    public void killAllMotors(){
        intake01.set(Constants.DEAD_SPEED);
    }
    
}