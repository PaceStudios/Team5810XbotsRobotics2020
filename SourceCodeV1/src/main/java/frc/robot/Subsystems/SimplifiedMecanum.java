package frc.robot.Subsystems;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Constants;
/**
 * @author John C. Pace
 * @since 02/06/2020
 * @version 02/16/2020
 * @apiNote Class dedicated to simpling getting the robot to strafe in all  4 directions. Currently does not contain any Encoder, nor autonomous mode.
 */
public class SimplifiedMecanum{
    VictorSP frontLeft;
    VictorSP frontRight;
    VictorSP backLeft;
    VictorSP backRight;
    Encoder frontLeftEncoder;
    Encoder frontRightEncoder;
    Encoder backLeftEncoder;
    Encoder backRightEncoder;
    double turn = 0;
    double horiztonalSpeed = 0;
    double verticalSpeed = 0;
    MecanumDrive drive;
    AnalogGyro gyro;
    AnalogAccelerometer accel;
    private double frontLeftEncoderRate;
    private double backLeftEncoderRate;
    private double frontRightEncoderRate;
    private double backRightEncoderRate;

    public SimplifiedMecanum(){
        frontLeft = new VictorSP(Constants.FRONTLEFTMOTOR_PWM);
        frontRight = new VictorSP(Constants.FRONTRIGHTMOTOR_PWM);
        backLeft = new VictorSP(Constants.REARLEFTMOTOR_PWM);
        backRight = new VictorSP(Constants.REARRIGHTMOTOR_PWM);
        drive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
        gyro = new AnalogGyro(Constants.GYRO_ANALOG_PORT);
        gyro.reset();
        frontLeftEncoder = new Encoder(Constants.ENCODER_CHANNEL_CHANNEL1, Constants.ENCODER_CHANNEL_CHANNEL2, false, EncodingType.k4X);
        frontRightEncoder = new Encoder(Constants.ENCODER_CHANNEL_CHANNEL3, Constants.ENCODER_CHANNEL_CHANNEL4, true, EncodingType.k4X);
        backLeftEncoder = new Encoder(Constants.ENCODER_CHANNEL_CHANNEL5, Constants.ENCODER_CHANNEL_CHANNEL6, false, EncodingType.k4X);
        backRightEncoder = new Encoder(Constants.ENCODER_CHANNEL_CHANNEL7, Constants.ENCODER_CHANNEL_CHANNEL8, true, EncodingType.k4X);
        frontLeftEncoderRate =0;
        backLeftEncoderRate = 0;
        frontRightEncoderRate = 0;
        backRightEncoderRate = 0;
        //accel = new AnalogAccelerometer(0);
    }
    public void setUpEncoders(){
        frontLeftEncoder.setDistancePerPulse(Constants.kDistancePerPulse);
        backLeftEncoder.setDistancePerPulse(Constants.kDistancePerPulse);
        frontRightEncoder.setDistancePerPulse(Constants.kDistancePerPulse);
        backLeftEncoder.setDistancePerPulse(Constants.kDistancePerPulse);
        resetEncoders();
    }
    public void simplifiedDrive(boolean fieldRelative, double xSpeed, double ySpeed, double rot){
        turn = rot;
        horiztonalSpeed = xSpeed;
        verticalSpeed = ySpeed;
        setUpEncoders();
        drive.driveCartesian(xSpeed, ySpeed, rot);
    }
    public void getOdometry(){
        gyro.getAngle();
    }
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
    public void drive(double distance){
        resetEncoders();
        if (distance > 0){
        while(getAverageEncoderPosition() < distance){
            simplifiedDrive(true, Constants.SEMI_SPEED, 0, 0);
        }
        }
        else if(distance < 0){
            while(getAverageEncoderPosition() > distance){
                simplifiedDrive(true, -Constants.SEMI_SPEED, 0, 0);
            }
        }
        simplifiedDrive(true, 0, 0, 0);
    }
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
    public void resetEncoders(){
        frontLeftEncoder.reset();
        backLeftEncoder.reset();
        frontRightEncoder.reset();
        backRightEncoder.reset();
    }
    public double getAverageEncoderPosition(){
        return ((frontLeftEncoder.getDistance()+frontRightEncoder.getDistance()+backLeftEncoder.getDistance()+backRightEncoder.getDistance())/4);
    }
    public double getEncoderPosition(Encoder a, Encoder b){
        return ((a.getDistance() + b.getDistance())/2);
    }
    public void strafeLeft(double distance){
        resetEncoders();
        while(getEncoderPosition(backLeftEncoder, frontRightEncoder)< distance){
            simplifiedDrive(true, -Constants.SEMI_SPEED, 0, 0);
        }
        simplifiedDrive(true, 0, 0, 0);
    }
    public void strafeRight(double distance){
        resetEncoders();
        while(getEncoderPosition(frontLeftEncoder, backRightEncoder)<distance){
            simplifiedDrive(true, Constants.SEMI_SPEED, 0, 0);
        }
        simplifiedDrive(true, 0, 0, 0);
    }
    public void updateEncoderValues(){
        SmartDashboard.putNumber("Front Left Rate: ", frontLeftEncoder.getRate());
        SmartDashboard.putNumber("Back Left Rate: ", backLeftEncoder.getRate());
        SmartDashboard.putNumber("Front Right Rate", frontRightEncoder.getRate());
        SmartDashboard.putNumber("Back Right Rate", backRightEncoder.getRate());
    }
    /**
     * makes the robot drive in a 10x10 sqaure
     */
    public void auto1(){
        drive(10);
        turnLeft();
        drive(10);
        turnLeft();
        drive(10);
        turnLeft();
        drive(10);
        turnLeft();
    }
    public void auto2(){}
    public void auto3(){}
    public void auto4(){}
}