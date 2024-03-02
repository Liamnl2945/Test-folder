package frc.robot.subsystems;

import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants;


public class Climber extends SubsystemBase{   

    
        static TalonFX climberLeft = new TalonFX(constants.Climber.climberLeft);
        static TalonFX climberRight = new TalonFX(constants.Climber.climberRight);
        public Pigeon2 gyro;


      public Climber(){
      climberLeft.setInverted(true);
      climberRight.setInverted(true);
      climberLeft.setNeutralMode(NeutralModeValue.Brake);
      climberRight.setNeutralMode(NeutralModeValue.Brake);
      gyro = new Pigeon2(constants.Swerve.pigeonID);
      gyro.getConfigurator().apply(new Pigeon2Configuration());
      
      
      
      }

      public static void ClimberBalanced(double speed){
         
      }

     public static void climbUpLeft(double speed){
        climberLeft.set(speed);  
     }
     public static void getRoll(){
      
     }

     public static void climbUpRight(double speed){
        climberRight.set(speed);
     }

     public void climbDownLeft(double speed){
        climberLeft.set(-speed);
     }
     public void climbDownRight(double speed){
        climberRight.set(-speed);
     }



}



