package frc.robot.Subsystems;
<<<<<<< Updated upstream
public class Intake{
    public Intake(){
        // Enter intialiization code here.
    }
    public void intializeIntakeMotors(int speed_1, int speed_2, int speed_3, int speed_4){}
    
    public void intakeBalls(){}

    public void outtakeBalls(){}
=======
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.*;
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
    private Spark intake01;
    public Intake(){
    }
    public Intake(int pwm1){
        intake01 = new Spark(pwm1);
    }
    public void intakeBalls(double speed, boolean a){
        while(a){
        intake01.set(-speed);
        }
        }
    public void outtakeBalls(double speed, boolean a){
        while(a){intake01.set(speed);
        }
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
    public void intakeFunct(Joystick joy, double power_input){
        if(joy.getRawButton(1) == true){
            intake01.set(power_input);
        }
        else if (joy.getRawButton(2) == true){
            intake01.set(power_input);
        }
        else{
            intake01.set(0);
        }
    }
>>>>>>> Stashed changes
    

}