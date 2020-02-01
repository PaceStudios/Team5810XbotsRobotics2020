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
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Subsystems.*;
import frc.robot.Subsystems.Constants;
/**
 * @author John C. Pace
 * @since 01/06/2020
 * @version 01/19/2020
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
  private DriveTrain m_mecanum = new DriveTrain();
  private Intake intake1 = new Intake();
  private Limelight limelight = new Limelight();
  private Shooter shoot = new Shooter();
  private Climber climb = new Climber();
  private Alignment align = new Alignment();

  @Override
  public void robotInit(){
    super.robotInit();
    // Declares and Creates Controllers and Joystick
    m_controller = new XboxController(Constants.XBOXCONTROL_PORT);
    m_joystick = new Joystick(Constants.JOYSTICK_PORT);
    // Creates Subsystems and Activates Them in Teleop Periodic 
    m_mecanum = new DriveTrain();
    timer01 = new Timer();
    intake1 = new Intake();
    limelight = new Limelight();
    isAimOn = false;
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
    m_mecanum.updateOdometry();
    
  }
  @Override
  public void teleopInit() {
    
    super.teleopInit();

  }
  
  @Override
  public void teleopPeriodic() {
    timer01.reset();              // Resets the timer for the round on the RoboRio
    timer01.start();              // Starts the timer on the RoboRio
    driveWithXboxControl(true);      // Sets the method for driving in TeleOp, was modified to work with an Xbox Controller 
    if (m_joystick.getRawButton(1)){  // Method working with the Intake and Outtake for the Robot (Will be Eventually modified)
      intake1.setEngaged(true);
      intake1.intakeBalls(0.8);
    }
    else
    {
      intake1.setEngaged(false);
    }
    if (m_joystick.getRawButton(2)){
      intake1.outtakeBalls(0.8);
    }
    if(m_controller.getRawButton(1)){ // Red Button 'A'. Toggles itself on or off based on button press. 
      isAimOn = !isAimOn;
      while(isAimOn){
        aimWithVision(limelight.getTable());
      }
    }
    /*
    Responsible for dealing with the TeleOp alignment
    */
    if(m_joystick.getRawButton(7)){
      align.moveUp(2);
    }
    if(m_joystick.getRawButton(10)){
      align.moveDown(2);
    }
    if(m_joystick.getRawButton(8)){
      align.moveLeft(2);
    }
    if(m_joystick.getRawButton(11)){
      align.moveRight(2);
    }
    if(m_joystick.getRawButton(9)){
      align.moveUp(0.5);
    }
    if(m_joystick.getRawButton(12)){
      align.moveDown(0.5);
    }
    if(m_joystick.getRawButton(5)){
      align.moveLeft(0.5);
    }
    if(m_joystick.getRawButton(6)){
      align.moveRight(0.5);
    }
  }
  @Override
  public void disabledInit(){
    super.disabledInit();
    intake1.killAllMotors();
    climb.killAllMotors();
    m_mecanum.killAllMotors();
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

    m_mecanum.drive(xSpeed, ySpeed, rot, fieldRelative);
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
    m_mecanum.turn(left_command, right_command);
    if(tx == 1){
      align.setAlignment(true);
    }
    else
    {
      align.setAlignment(false);
    }
  }
}