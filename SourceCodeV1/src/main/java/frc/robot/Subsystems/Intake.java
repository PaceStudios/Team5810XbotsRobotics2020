package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.*;
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
    private Servo servo;

    public Intake(){
        intake01 = new VictorSP(Constants.INTAKEMOTOR1_PWM);
        servo = new Servo(9);
    }
    public Intake(int pwm1){
        intake01 = new VictorSP(pwm1);
    }
    public void moveIntake(XboxController x){
        if(x.getAButton()==true)
        {
            servo.set(1);
        }
        else if (x.getBButton()){
            servo.set(0);
        }
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
    public void bringIntakeDown(){
        servo.set(Constants.EXTENDED_INTAKE);
    }

    public void bringintakeUp(){
        servo.set(Constants.RETRACTED_INTAKE);
    }
}