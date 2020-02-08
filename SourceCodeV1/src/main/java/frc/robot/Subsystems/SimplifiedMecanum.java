package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
//import edu.wpi.first.wpilibj.AnalogGyro;



/**
 * @author John C. Pace
 * @since 02/06/2020
 * @version 02/08/20
 * @apiNote Class dedicated to simpling getting the robot to strafe in all 4
 *          directions. Currently does not contain any Encoder, nor autonomous
 *          mode.
 */
public class SimplifiedMecanum {
    private double turn;
    private double horiztonalSpeed;
    private double verticalSpeed;
    private MecanumDrive drive;
    private VictorSP frontLeft;
    private VictorSP frontRight;
    private VictorSP backLeft;
    private VictorSP backRight;
    
    //private AnalogGyro gyro = new AnalogGyro(Constants.GYRO_ANALOG_PORT);
     

    public SimplifiedMecanum() {   
      frontLeft = new VictorSP(Constants.FRONTLEFTMOTOR_PWM);
      frontRight = new VictorSP(Constants.FRONTRIGHTMOTOR_PWM);
      backLeft = new VictorSP(Constants.REARLEFTMOTOR_PWM);
      backRight = new VictorSP(Constants.REARRIGHTMOTOR_PWM);
      drive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
      turn = 0;
      horiztonalSpeed = 0;
      verticalSpeed = 0;
      //gyro = new AnalogGyro(Constants.)
    }
    public void simplifiedDrive(boolean fieldRelative, double xSpeed, double ySpeed, double rot){
        turn = rot;
        horiztonalSpeed = xSpeed;
        verticalSpeed = ySpeed;
        drive.driveCartesian(horiztonalSpeed, verticalSpeed, turn);
    }
}