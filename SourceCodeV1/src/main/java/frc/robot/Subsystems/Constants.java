package frc.robot.Subsystems;
/**
 * @author John C. Pace
 * @since 01/06/2020
 * @version 02/09/2020
 * @apiNote This class is dedicated to creating permanent values to any number value that may be used in the program, for a higher efficiency on the compiler. 
 */
public final class Constants{
    /*
    All Motor Values for Main Robot added here:
    */
    public static final int FRONTLEFTMOTOR_PWM = 0;  
    public static final int FRONTRIGHTMOTOR_PWM = 1;
    public static final int REARLEFTMOTOR_PWM = 2;
    public static final int REARRIGHTMOTOR_PWM = 3;
    public static final int SHOOTERMOTERS_PWM = 4;
    public static final int BELTDRIVEMOTOR_PWM = 5; // Works with the Shooter Motor 
    public static final int INTAKEMOTOR1_PWM = 6;
    public static final int INTAKEMOTOR2_PWM = 7;
    public static final int CLIMBMOTOR_PWM = 8;
    //public static final int EXTRAMOTOR2_PWM = 9;

    /**
     * Analog and DiO ports go here. For now, nothing will be here until future notice. 
     */
    public static final int GYRO_ANALOG_PORT = 2;

    /**
     * Encoder Calculation Values go here:
     */
    public static final double kPulsesPerRevolution = 20; // According to the CIM Motor Encoder Coder from AndyMark (Might be 2000 or 20)
    public static final double kDistancePerRevolution = 25.13; // Circumference calculated with 8" Diameter Mecanum Wheels.
    public static final double kDistancePerPulse = kDistancePerRevolution / kPulsesPerRevolution;
    /**
     * Encoder Channel Values go here:
     */
    public static final int ENCODER_CHANNEL_CHANNEL1 = 0;
    public static final int ENCODER_CHANNEL_CHANNEL2 = 1;
    public static final int ENCODER_CHANNEL_CHANNEL3 = 2;
    public static final int ENCODER_CHANNEL_CHANNEL4 = 3;
    public static final int ENCODER_CHANNEL_CHANNEL5 = 4;
    public static final int ENCODER_CHANNEL_CHANNEL6 = 5;
    public static final int ENCODER_CHANNEL_CHANNEL7 = 6;
    public static final int ENCODER_CHANNEL_CHANNEL8 = 7;
    public static final int ENCODER_CHANNEL_CHANNEL9 = 8;
    public static final int ENCODER_CHANNEL_CHANNEL10 = 9;

    public static final double EXTENDED_INTAKE = 2E-3; 
    public static final double RETRACTED_INTAKE = 1E-3;
    
    

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
