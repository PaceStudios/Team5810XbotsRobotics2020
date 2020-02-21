package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Subsystems.Constants;
/**
 * @author John C. Pace
 * @since 01/12/20
 * @version 01/19/20
 * @apiNote Class meant to dealing with the potential motor systems. As of right now, the official intake remains unknown. 
 */
public class Intake
{
    // Motors can be changed according to what names they are.
    private boolean intakeEngaged = false;
    private VictorSP intake01;
    public Intake(){
        intake01 = new VictorSP(Constants.INTAKEMOTOR1_PWM);
    }
    public Intake(int pwm1){
        intake01 = new VictorSP(pwm1);
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
        intake01.disable();
    }
    
}