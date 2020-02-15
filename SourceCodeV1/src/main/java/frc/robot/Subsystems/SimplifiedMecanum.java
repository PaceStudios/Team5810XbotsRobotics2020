package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.AnalogGyro;
import frc.robot.Subsystems.Constants;
/**
 * @author John C. Pace
 * @since 02/06/2020
 * @version 02/15/2020
 * @apiNote Class dedicated to simpling getting the robot to strafe in all  4 directions. Currently does not contain any Encoder, nor autonomous mode.
 */
public class SimplifiedMecanum{
    VictorSP frontLeft;
    VictorSP frontRight;
    VictorSP backLeft;
    VictorSP backRight;
    double turn = 0;
    double horiztonalSpeed = 0;
    double verticalSpeed = 0;
    MecanumDrive drive;
    AnalogGyro gyro;
    AnalogAccelerometer accel;
    

    public SimplifiedMecanum(){
        frontLeft = new VictorSP(Constants.FRONTLEFTMOTOR_PWM);
        frontRight = new VictorSP(Constants.FRONTRIGHTMOTOR_PWM);
        backLeft = new VictorSP(Constants.REARLEFTMOTOR_PWM);
        backRight = new VictorSP(Constants.REARRIGHTMOTOR_PWM);
        drive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
        gyro = new AnalogGyro(Constants.GYRO_ANALOG_PORT);
        //accel = new AnalogAccelerometer(0);
    }
    public void simplifiedDrive(boolean fieldRelative, double xSpeed, double ySpeed, double rot){
        turn = rot;
        horiztonalSpeed = xSpeed;
        verticalSpeed = ySpeed;
        drive.driveCartesian(xSpeed, ySpeed, rot);
    }
    public void moveExactDistance(double distance, String direction){

    }
    public void getOdometry(){}
    public void turn(double a){}
    public void move(String direction, double powerInput){}
    public void killAllMotors(){
        frontLeft.disable();
        frontRight.disable();
        backLeft.disable();
        backRight.disable();
    }
    /**
     * Using an ultrasonic sensor, the robot will drive forward until it is within 5 feet of another object. 
     */
    public void driveUntilWall(){}
    /**
     * Using encoders on all 4 mecanum wheels, the robot will only keep track of (vertical y distance)
     * This method will only keep track of forward and backward distance
     * @param distance
     */
    public void drive(double distance){}
    public void turnLeft(){
        double startAngle = gyro.getAngle();
        while((gyro.getAngle() - startAngle) > -90){
            frontRight.set(Constants.HALF_SPEED);
            frontLeft.set(Constants.REVERSE_HALF_SPEED);
            backLeft.set(Constants.HALF_SPEED);
            backRight.set(Constants.REVERSE_HALF_SPEED);
        } 
    }
    public void turnRight(){
        double startAngle = gyro.getAngle();
        while((gyro.getAngle()-startAngle)< 90){
            frontRight.set(Constants.REVERSE_HALF_SPEED);
            frontLeft.set(Constants.HALF_SPEED);
            backLeft.set(Constants.REVERSE_HALF_SPEED);
            backRight.set(Constants.HALF_SPEED);
        }
    }
}