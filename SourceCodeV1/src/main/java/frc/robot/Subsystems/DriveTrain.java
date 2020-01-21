/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.AnalogGyro; // Requires connection to an Analog input 
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.MecanumDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.MecanumDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.MecanumDriveWheelSpeeds;

/**
 * Represents a mecanum drive style drivetrain.
 */
/**
 * @author John C. Pace, Marco Colón, Wpilib Library
 * @since 01/13/2020
 * @version 01/20/2020
 * @apiNote This class represent a mecanum style drive train through the use of analog gyroscope, RoboRio, 
 * Several Notes to Consider if Errors persists:
 * We are assuming that setPulseDistance is already calculated, for the getRate and get Distance (01/20/20)
 * 
 */
@SuppressWarnings("PMD.TooManyFields")
public class DriveTrain {
  public static final double kMaxSpeed = 3.0; // 3 meters per second
  public static final double kHalfSpeed = 1.5; // 1.5 meters per second
  public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second

  private final SpeedController m_frontRightMotor = new PWMVictorSPX(Constants.DRIVEBASEMOTOR1_PWM); // Motor 1
  private final SpeedController m_backLeftMotor = new PWMVictorSPX(Constants.DRIVEBASEMOTOR2_PWM); // Motor 2
  private final SpeedController m_backRightMotor = new PWMVictorSPX(Constants.DRIVEBASEMOTOR3_PWM); // Motor 3
  private final SpeedController m_frontLeftMotor = new PWMVictorSPX(Constants.DRIVEBASEMOTOR4_PWM); // Motor 4
  
  
  /// DO NOT TOUCH THIS YET, IN PROCESS OF LEARNING
  private final Encoder m_frontLeftEncoder = new Encoder(0, 1);
  private final Encoder m_frontRightEncoder = new Encoder(2, 3);
  private final Encoder m_backLeftEncoder = new Encoder(4, 5);
  private final Encoder m_backRightEncoder = new Encoder(6, 7);

  private final Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381);
  private final Translation2d m_frontRightLocation = new Translation2d(0.381, -0.381);
  private final Translation2d m_backLeftLocation = new Translation2d(-0.381, 0.381);
  private final Translation2d m_backRightLocation = new Translation2d(-0.381, -0.381);

  private final PIDController m_frontLeftPIDController = new PIDController(1, 0, 0);
  private final PIDController m_frontRightPIDController = new PIDController(1, 0, 0);
  private final PIDController m_backLeftPIDController = new PIDController(1, 0, 0);
  private final PIDController m_backRightPIDController = new PIDController(1, 0, 0);

  private final AnalogGyro m_gyro = new AnalogGyro(0);

  private final MecanumDriveKinematics m_kinematics = new MecanumDriveKinematics(
      m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation
  );

  private final MecanumDriveOdometry m_odometry = new MecanumDriveOdometry(m_kinematics,
      getAngle());

  /**
   * Constructs a MecanumDrive and resets the gyro.
   */
  public DriveTrain() {
    m_gyro.reset();
  }

  /**
   * Returns the angle of the robot as a Rotation2d.
   *
   * @return The angle of the robot.
   */
  public Rotation2d getAngle() {
    // Negating the angle because WPILib gyros are CW positive.
    return Rotation2d.fromDegrees(-m_gyro.getAngle());
  }

  /**
   * Returns the current state of the drivetrain.
   *
   * @return The current state of the drivetrain.
   */
  public MecanumDriveWheelSpeeds getCurrentState() {
    return new MecanumDriveWheelSpeeds(
        m_frontLeftEncoder.getRate(),
        m_frontRightEncoder.getRate(),
        m_backLeftEncoder.getRate(),
        m_backRightEncoder.getRate()
    );
  }

  /**
   * Set the desired speeds for each wheel.
   *
   * @param speeds The desired wheel speeds.
   */
  public void setSpeeds(MecanumDriveWheelSpeeds speeds) {
    final var frontLeftOutput = m_frontLeftPIDController.calculate(
        m_frontLeftEncoder.getRate(), speeds.frontLeftMetersPerSecond
    );
    final var frontRightOutput = m_frontRightPIDController.calculate(
        m_frontRightEncoder.getRate(), speeds.frontRightMetersPerSecond
    );
    final var backLeftOutput = m_backLeftPIDController.calculate(
        m_backLeftEncoder.getRate(), speeds.rearLeftMetersPerSecond
    );
    final var backRightOutput = m_backRightPIDController.calculate(
        m_backRightEncoder.getRate(), speeds.rearRightMetersPerSecond
    );

    m_frontLeftMotor.set(frontLeftOutput);
    m_frontRightMotor.set(frontRightOutput);
    m_backLeftMotor.set(backLeftOutput);
    m_backRightMotor.set(backRightOutput);
  }

  /**
   * Method to drive the robot using joystick info.
   *
   * @param xSpeed        Speed of the robot in the x direction (forward).
   * @param ySpeed        Speed of the robot in the y direction (sideways).
   * @param rot           Angular rate of the robot.
   * @param fieldRelative Whether the provided x and y speeds are relative to the field.
   */
  @SuppressWarnings("ParameterName")
  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
    var mecanumDriveWheelSpeeds = m_kinematics.toWheelSpeeds(
        fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
            xSpeed, ySpeed, rot, getAngle()
        ) : new ChassisSpeeds(xSpeed, ySpeed, rot)
    );
    mecanumDriveWheelSpeeds.normalize(kMaxSpeed);
    setSpeeds(mecanumDriveWheelSpeeds);
  }

  /**
   * Updates the field relative position of the robot.
   */
  public void updateOdometry() {
    m_odometry.update(getAngle(), getCurrentState());
  }

  // Turning Left is -90 (Oppostive of the Unit Cirle Representation)
  // Turning Right is +90
  public void turnLeft90Degrees(){
    double initialAngle = m_gyro.getAngle(); 
    double sampleAngle = m_gyro.getAngle();
    while(initialAngle-90 >= sampleAngle){
      m_frontLeftMotor.set(-Constants.HALF_SPEED);
      m_frontRightMotor.set(Constants.HALF_SPEED);
      m_backLeftMotor.set(-Constants.HALF_SPEED);
      m_backRightMotor.set(Constants.HALF_SPEED);
      sampleAngle = m_gyro.getAngle();
    }

    }
  public void turnRight90Degrees(){
    double initialAngle = m_gyro.getAngle(); 
    double sampleAngle = m_gyro.getAngle();
    while(initialAngle-90 >= sampleAngle){
      m_frontLeftMotor.set(Constants.HALF_SPEED);
      m_frontRightMotor.set(-Constants.HALF_SPEED);
      m_backLeftMotor.set(Constants.HALF_SPEED);
      m_backRightMotor.set(-Constants.HALF_SPEED);
      sampleAngle = m_gyro.getAngle();
    }
    
  }
  public void turn(double motorvalue1, double motorvalue2){
    m_frontLeftMotor.set(motorvalue1);
    m_backLeftMotor.set(motorvalue1);
    m_frontRightMotor.set(motorvalue2);
    m_backRightMotor.set(motorvalue2);
  }

  
  /*
  Through the use of Encoders to find and move a specific distance
  */

  /**
   * Mentor Tom suggests completely flipping the forward and front and back of the robot, Potentially move it either or (ASK for More
   * Information))
   * @param distance
   * @param mode
   */

  public void moveExactDistance(double distance, String mode){
    m_backLeftEncoder.reset();
    m_backRightEncoder.reset();
    m_frontLeftEncoder.reset();
    m_frontRightEncoder.reset();
    double currentDistance = m_backLeftEncoder.getDistance() + m_backRightEncoder.getDistance() + m_frontLeftEncoder.getDistance() + m_frontRightEncoder.getDistance();
    var mecanumDriveWheelSpeeds2 = new MecanumDriveWheelSpeeds(m_frontLeftEncoder.getRate(), m_frontRightEncoder.getRate(), m_backLeftEncoder.getRate(), m_backRightEncoder.getRate()); ; 
    if(mode == Constants.MODE_UP){
      mecanumDriveWheelSpeeds2 = new MecanumDriveWheelSpeeds(m_frontLeftEncoder.getRate(), m_frontRightEncoder.getRate(), m_backLeftEncoder.getRate(), m_backRightEncoder.getRate());
    }
    if(mode == Constants.MODE_DOWN){
      mecanumDriveWheelSpeeds2 = new MecanumDriveWheelSpeeds(-m_frontLeftEncoder.getRate(), -m_frontRightEncoder.getRate(), -m_backLeftEncoder.getRate(), -m_backRightEncoder.getRate());
    }
    if(mode == Constants.MODE_LEFT){
      mecanumDriveWheelSpeeds2 = new MecanumDriveWheelSpeeds(-m_frontLeftEncoder.getRate(), m_frontRightEncoder.getRate(), m_backLeftEncoder.getRate(), -m_backRightEncoder.getRate());
    }
    if(mode == Constants.MODE_RIGHT){
      mecanumDriveWheelSpeeds2 = new MecanumDriveWheelSpeeds(m_frontLeftEncoder.getRate(), -m_frontRightEncoder.getRate(), -m_backLeftEncoder.getRate(), m_backRightEncoder.getRate());
    }
    while(currentDistance != distance*4){     // Talk to Marco about this logic and specific distances 
      mecanumDriveWheelSpeeds2.normalize(kHalfSpeed);
      setSpeeds(mecanumDriveWheelSpeeds2);
      currentDistance =  m_backLeftEncoder.getDistance() + m_backRightEncoder.getDistance() + m_frontLeftEncoder.getDistance() + m_frontRightEncoder.getDistance();
    } // Talk to Marco about this 
  }
  /**
   * Method only called during disAbled Init , while the motors will already be killed, it is safer to also 
   * reset the their values to a neuatral 0 
   */
  public void killAllMotors(){
    m_frontLeftMotor.set(Constants.DEAD_SPEED);
    m_backLeftMotor.set(Constants.DEAD_SPEED);
    m_frontRightMotor.set(Constants.DEAD_SPEED);
    m_backLeftMotor.set(Constants.DEAD_SPEED);
  }

}