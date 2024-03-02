package frc.robot.subsystems;

import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants;


public class Climber extends SubsystemBase{   
   private static Pigeon2 gyro;
   private static double skew;
    
        static TalonFX climberLeft = new TalonFX(constants.Climber.climberLeft);
        static TalonFX climberRight = new TalonFX(constants.Climber.climberRight);


        public Climber(){
        climberLeft.setInverted(true);
        climberRight.setInverted(true);
        climberLeft.setNeutralMode(NeutralModeValue.Brake);
        climberRight.setNeutralMode(NeutralModeValue.Brake);
                
        gyro = new Pigeon2(constants.Swerve.pigeonID);
        gyro.getConfigurator().apply(new Pigeon2Configuration());
         
     }
     public static double calculateSkew(){
      System.out.println(gyro.getRoll().getValue()/35);
      return gyro.getRoll().getValue()/35;
     }
     public static void climbUpRight(double speed){
        skew = calculateSkew();
        climberRight.set(speed + skew);
        climberLeft.set(speed - skew);
     }

     public void climbDownRight(double speed){
        skew = calculateSkew();
        climberRight.set(-speed - skew);
        climberLeft.set(-speed + skew);
     }
}



