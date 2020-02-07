package frc.robot.Subsystems;
import edu.wpi.first.wpilibj2.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
/**
 * @author John C. Pace
 * @since 01/06/2020
 * @version 01/27/2020
 * @apiNote This class is dedicated to creating permanent values to any number value that may be used in the program, for a higher efficiency on the compiler. 
 */
public final class Constants extends SubsystemBase{
    /*
    All Motor Values for Main Robot added here:
    */
    public static final int FRONTLEFTMOTOR_PWM = 1;  
    public static final int FRONTRIGHTMOTOR_PWM = 3;
    public static final int REARLEFTMOTOR_PWM = 0;
    public static final int REARRIGHTMOTOR_PWM = 2;
    public static final int SHOOTERMOTERS_PWM = 4;
    public static final int BELTDRIVEMOTOR_PWM = 5;
    public static final int INTAKEMOTOR1_PWM = 6;
    public static final int INTAKEMOTOR2_PWM = 7;
    public static final int EXTRAMOTOR1_PWM = 8;
    public static final int EXTRAMOTOR2_PWM = 9;

    /* DiO and Analog Values Here:
    */
    public static final int SERVO_MOTOR1_DIO = 0;
    public static final int GYRO_ANALOG_PORT = 0;

    /*
    All Dumbot Values for Dumbot added here:
    */

    public static final int dumbBotArcadeBaseMotorL_PWM = 0;
    public static final int dumbBotArcadeBaseMotorR_PWM = 1;
    public static final int DUMBBOT_ARCADEBOT_LIFT = 3;
    public static final int DUMBBOT_ARCADEBOT_WRIST = 2;
    public static final int DUMBBOT_ARCADEBOT_INTAKEMOTOR_PWM = 4;
    public static final int XBOXCONTROL_PORT = 0;
    public static final int JOYSTICK_PORT = 1;
    /*
    Plan to set all motor speeds and different speeds here
    */
    public static final double HALF_SPEED = 0.5;
    public static final double FULL_SPEED = 1.0;
    public static final double SEMI_SPEED = 0.8;
    public static final double DEAD_SPEED = 0.0;
    public static final double REVERSE_FULL_SPEED = -1.0;
    public static final double REVERSE_SEMI_SPEED = -0.8;
    public static final double REVERSE_HALF_SPEED = -0.5;
    /**
     * String values
     */
    public static final String MODE_UP = "UP";
    public static final String MODE_DOWN = "DOWN";
    public static final String MODE_RIGHT = "RIGHT";
    public static final String MODE_LEFT = "LEFT";
}