/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;
//import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.*;
/**
 * @author John C. Pace
 * @since 01/06/2020
 * @version 02/22/2020
 * @apiNote This class is the central hub to the program, resposible 
 * for hosting all the different subsystems, I/O systems together in addition to
 * the different modes of the program 
 */
public class Robot extends TimedRobot {
  private XboxController m_controller;
  private Joystick m_joystick;
  private Timer timer01 = new Timer();
  //private boolean isAimOn;
  private boolean isShooterActivated;
  private boolean isClimberActivated;
  //private boolean isAlignActivated;
  private boolean intakeActivated;
  private Timer time = new Timer();  
  /**
   * Creates and instantiates all the Subsystems
   */
  //private DriveTrain m_mecanum; /* As of Right Now, We are using Simplified Mecanum*/
  private Intake intake1;
  private Limelight limelight;
  private Shooter shoot;
  private Climber climb;
  //private Alignment align;
  private SimplifiedMecanum simpDrive;
  /**
   * Double and Boolean Values
   */
  private double xSpeed = 0.0;
  private double encoderRate = 0.0;
  private double ySpeed = 0.0;
  private double rot = 0.0;
  private double intakeSpeed = 0;
  private double climbSpeed = 0;
  private double shootSpeed = 0;
  private String autoSelection;
  private boolean isXButtonPressed;
  private double extension;
  private boolean yButtonPressed;
  @Override
  public void robotInit(){
    super.robotInit();
    // Declares and Creates Controllers and Joystick
    m_controller = new XboxController(Constants.XBOXCONTROL_PORT);
    m_joystick = new Joystick(Constants.JOYSTICK_PORT);
    // Creates Subsystems and Activates Them in Teleop Periodic 
    //isAimOn = false;
    isShooterActivated = false;
    //isAlignActivated = false;
    isClimberActivated = false;
    intakeActivated = false;
    intake1 = new Intake();
    limelight = new Limelight();
    shoot = new Shooter();
    climb = new Climber();
    //align = new Alignment();
    simpDrive = new SimplifiedMecanum();
    autoSelection = new String("");
    isXButtonPressed = false;
    extension = 0; 
    yButtonPressed = false;
  }

  @Override
  public void robotPeriodic() {
    super.robotPeriodic();
    //limelight.updateLimeLight();
    //simpDrive.updateEncoderValues();
    SmartDashboard.setDefaultBoolean("Y Button", yButtonPressed);
  }
  @Override
  public void autonomousInit() {
    super.autonomousInit();
    driveWithXboxControl(false);
    timer01.reset();
    if(m_controller.getAButton()){autoSelection = "A";}
    else if(m_controller.getBButton()){autoSelection = "B";}
    else if(m_controller.getYButton()){autoSelection = "Y";}
    else {autoSelection = "X";}
  }
  @Override
  public void autonomousPeriodic() {
    timer01.reset();
    timer01.start();
    driveSimplifiedXboxControl(false);
    switch(autoSelection){
      case("A"):
        simpDrive.auto1();
      case("B"):
        simpDrive.auto2();
      case("Y"):
        simpDrive.auto3();
      case("X"):
        simpDrive.auto4();
    }
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
    if(m_joystick.getRawButton(1)){
      shootSpeed = 1;
      isShooterActivated = true;
    }
    else{
      shootSpeed = 0;
      isShooterActivated = false;
    }
    if(m_joystick.getRawButton(2)){
      intakeSpeed = 1;
      intakeActivated= true;
    }
    else{
      intakeSpeed = 0;
      intakeActivated = false;
    }
    if(m_joystick.getRawButton(3)){
      climbSpeed = 1;
      isClimberActivated = true;
    }
    else {
      climbSpeed = 0;
      isClimberActivated = false;
    }
    intake1.intakeBalls(intakeSpeed);
    climb.climb(climbSpeed);
    shoot.shootBalls(m_joystick,shootSpeed);
  }
  @Override
  public void disabledInit(){
    super.disabledInit();
    intake1.killAllMotors();
    climb.killAllMotors();
    //m_mecanum.killAllMotors();
    shoot.killAllMotors();
    simpDrive.killAllMotors();
    
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
    // SmartDashboard.setDefaultNumber("Test Motor Speed", testMotor.getSpeed());

  }
  /**
   * This method deals with driving for the advance mecanum drive train. As of this point, we are not using it. 
   * @param fieldRelative
   */
  private void driveWithXboxControl(boolean fieldRelative) {
    // Get the x speed. We are inverting this because Xbox controllers return
    // negative values when we push forward.
    //final var xSpeed = -m_controller.getY(GenericHID.Hand.kLeft) * DriveTrain.kMaxSpeed;

    // Get the y speed or sideways/strafe speed. We are inverting this because
    // we want a positive value when we pull to the left. Xbox controllers
    // return positive values when you pull to the right by default.
    //final var ySpeed = -m_controller.getX(GenericHID.Hand.kLeft) * DriveTrain.kMaxSpeed;

    // Get the rate of angular rotation. We are inverting this because we want a
    // positive value when we pull to the left (remember, CCW is positive in
    // mathematics). Xbox controllers return positive values when you pull to
    // the right by default.
   // final var rot = -m_controller.getX(GenericHID.Hand.kRight) * DriveTrain.kMaxAngularSpeed;

    //m_mecanum.drive(xSpeed, ySpeed, rot, fieldRelative);
  }
  /**
   * This method deals with driving in (GTA style of driving for the simplified mecanum drive base)
   * @param fieldRelative
   */
  private void driveSimplifiedXboxControl(boolean fieldRelative){
    // Get the x speed. We are inverting this because Xbox controllers return
    // negative values when we push forward. 
    xSpeed = (m_controller.getRawAxis(3) + (-m_controller.getRawAxis(2))) * Constants.HALF_SPEED;
     // Get the y speed or sideways/strafe speed. We are inverting this because
    // we want a positive value when we pull to the left. Xbox controllers
    // return positive values when you pull to the right by default.
    ySpeed  = m_controller.getRawAxis(1) * Constants.HALF_SPEED;
     // Get the rate of angular rotation. We are inverting this because we want a
    // positive value when we pull to the left (remember, CCW is positive in
    // mathematics). Xbox controllers return positive values when you pull to
    // the right by default. 
    rot = m_controller.getRawAxis(4) * Constants.HALF_SPEED;
    simpDrive.simplifiedDrive(fieldRelative, xSpeed, ySpeed, rot);

  }
  /**
   * This version of autonomous is dedicated to essentially making the robot into a Roomba. It will move forward until it cannot move forward anymore, check its surroundings and turn as needed.
   */
/*
  private void aimWithVision(NetworkTable a){
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
  */
}