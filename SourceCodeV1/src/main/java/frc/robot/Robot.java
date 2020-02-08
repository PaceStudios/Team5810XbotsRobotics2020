/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.*;
import frc.robot.Subsystems.*;


/**
 * @author John C. Pace
 * @since 01/06/2020
 * @version 02/08/2020
 * @apiNote This class is the central hub to the program, resposible 
 * for hosting all the different subsystems, I/O systems together in addition to
 * the different modes of the program 
 */

public class Robot extends TimedRobot {
  private XboxController m_controller;
  private Joystick m_joystick;
  private Timer timer01 = new Timer();
  private boolean isAimOn;
  /**
   * Creates and instantiates all the Subsystems
   */
  //private DriveTrain m_mecanum; /* As of Right Now, We are using Simplified Mecanum*/
  private Intake intake1;
  private Limelight limelight;
  private Shooter shoot;
  private Climber climb;
  private Alignment align;
  private SimplifiedMecanum simpDrive;
  /**
   * Double and Boolean Values
   */
  private double xSpeed = 0.0;
  private double ySpeed = 0.0;
  private double rot = 0.0;

  @Override
  public void robotInit(){
    super.robotInit();
    // Declares and Creates Controllers and Joystick
    m_controller = new XboxController(Constants.XBOXCONTROL_PORT);
    m_joystick = new Joystick(Constants.JOYSTICK_PORT);
    // Creates Subsystems and Activates Them in Teleop Periodic 
    isAimOn = false;
    intake1 = new Intake();
    limelight = new Limelight();
    shoot = new Shooter();
    climb = new Climber();
    align = new Alignment();
    simpDrive = new SimplifiedMecanum();
    }
  @Override
  public void robotPeriodic() {
    // TODO Auto-generated method stub
    super.robotPeriodic();
    SmartDashboard.setDefaultNumber("Horizontal Strafe Speed: ", xSpeed);
    SmartDashboard.setDefaultNumber("Vertical Strafe Speed: ", ySpeed);
    SmartDashboard.setDefaultNumber("Rotational Strafe Speed: ", rot);
    limelight.updateLimeLight();
  }
  @Override
  public void autonomousInit() {
    
    super.autonomousInit();
    driveWithXboxControl(false);
    timer01.reset();
  }
  

  @Override
  public void autonomousPeriodic() {
    timer01.reset();
    timer01.start();
    driveWithXboxControl(false);
    //m_mecanum.updateOdometry();
    
  }
  @Override
  public void teleopInit() {
    
    super.teleopInit();

  }
  @Override
  public void teleopPeriodic() {
    timer01.reset();              // Resets the timer for the round on the RoboRio
    timer01.start();              // Starts the timer on the RoboRio
    //driveWithXboxControl(true);      // Sets the method for driving in TeleOp, was modified to work with an Xbox Controller 
    driveSimplifiedXboxControl(true); // activates simplified mecanum 
  }
  @Override
  public void disabledInit(){
    super.disabledInit();
    intake1.killAllMotors();
    climb.killAllMotors();
    //m_mecanum.killAllMotors();
    shoot.killAllMotors();
  }
  @Override
  public void disabledPeriodic(){
    super.disabledPeriodic();
  }
  @Override
  public void testInit(){
    super.testInit();
  }
  @Override
  public void testPeriodic(){
    super.testPeriodic();
  }

  private void driveWithXboxControl(boolean fieldRelative) {
    // Get the x speed. We are inverting this because Xbox controllers return
    // negative values when we push forward.
    final var xSpeed = -m_controller.getY(GenericHID.Hand.kLeft) * DriveTrain.kMaxSpeed;

    // Get the y speed or sideways/strafe speed. We are inverting this because
    // we want a positive value when we pull to the left. Xbox controllers
    // return positive values when you pull to the right by default.
    final var ySpeed = -m_controller.getX(GenericHID.Hand.kLeft) * DriveTrain.kMaxSpeed;

    // Get the rate of angular rotation. We are inverting this because we want a
    // positive value when we pull to the left (remember, CCW is positive in
    // mathematics). Xbox controllers return positive values when you pull to
    // the right by default.
    final var rot = -m_controller.getX(GenericHID.Hand.kRight) * DriveTrain.kMaxAngularSpeed;

    //m_mecanum.drive(xSpeed, ySpeed, rot, fieldRelative);
  }
  public void driveSimplifiedXboxControl(boolean fieldRelative){
    // Get the x speed. We are inverting this because Xbox controllers return
    // negative values when we push forward.
    xSpeed = -m_controller.getRawAxis(4) * DriveTrain.kMaxSpeed;
     // Get the y speed or sideways/strafe speed. We are inverting this because
    // we want a positive value when we pull to the left. Xbox controllers
    // return positive values when you pull to the right by default.
    ySpeed  = -m_controller.getRawAxis(5) * DriveTrain.kMaxSpeed;
     // Get the rate of angular rotation. We are inverting this because we want a
    // positive value when we pull to the left (remember, CCW is positive in
    // mathematics). Xbox controllers return positive values when you pull to
    // the right by default. 
    rot = m_controller.getRawAxis(3);
    simpDrive.simplifiedDrive(fieldRelative, xSpeed, ySpeed, rot);

  }



  private void aimWithVision(NetworkTable a){
    /*
    Used to configure the Joystick to respond to moving towards the specified tx target
    */
    float Kp = -0.1f;
    float min_command = 0.05f;
    //NetworkTable table = a.getTable("limelight");
    float tx = (float)a.getEntry("tx").getDouble(0.0);
    float left_command = 0;
    float right_command = 0;
    float steering_command = 0;
    float heading_error = -tx; // Each time the robot should lurch less and less as the method calls it self over again. 
    if(tx > 1.0){
      steering_command = (Kp*heading_error) - min_command;
    }
    else if (tx < 1.0){
      steering_command = (Kp*heading_error) + min_command;
    }
    left_command+= steering_command;
    right_command-= steering_command;
    //m_mecanum.turn(left_command, right_command);
    if(tx == 1){
      align.setAlignment(true);
    }
    else
    {
      align.setAlignment(false);
    }
  }
}