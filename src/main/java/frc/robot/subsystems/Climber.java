package frc.robot.subsystems;

import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Climber extends SubsystemBase{   

    
        static TalonFX climberLeft = new TalonFX(14);
        static TalonFX climberRight = new TalonFX(15);


        public Climber(){
        climberLeft.setInverted(true);
        climberRight.setInverted(true);
        climberLeft.setNeutralMode(NeutralModeValue.Brake);
        climberRight.setNeutralMode(NeutralModeValue.Brake);
     }

     public static void climbUpLeft(double speed){
        climberLeft.set(speed);
        
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



