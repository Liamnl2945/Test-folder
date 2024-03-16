package frc.robot.subsystems;

import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants;

public class Shooter extends SubsystemBase {
    
    private final TalonFX ShooterLeft = new TalonFX(constants.Shooter.ShooterLeft);
    private final TalonFX ShooterRight = new TalonFX(constants.Shooter.ShooterRigth);
    NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    

    public Shooter(){
        ShooterLeft.setInverted(true);
        ShooterRight.setInverted(false);
        ShooterRight.setNeutralMode(NeutralModeValue.Coast);
        ShooterLeft.setNeutralMode(NeutralModeValue.Coast);
    }

   // public void runShooter() {
      //  ShooterLeft.set(speed);
        //ShooterRight.set(speed);
        //ShooterLeft.setVoltage(12.0);
        //ShooterRight.setVoltage(12.0);
       // System.out.println("\n\nShooter motor left speed " + ShooterLeft.get() + " Shooter motor Right speed  " + ShooterRight.get() );
   // }
    public void slowShoot(){
        //System.out.println("SHOOTER");
        ShooterLeft.set(0.175);
        ShooterRight.set(0.175);
    }

    public void reverseShooter() {
        ShooterLeft.set(-1);
        ShooterRight.set(-1);
        //System.out.println("REVERESE SHOOTER");
    }

    public void stopShooter(){
        ShooterLeft.set(0);
        ShooterRight.set(0);
    }
    
    public int getDetectedAprilTagID() {
        return limelightTable.getEntry("tid").getNumber(0).intValue();
    }

    public void runShooterAtSpeed(double speed) {
        ShooterLeft.set(speed);
        ShooterRight.set(speed);
    }
    public void AutorunShooter() {
        ShooterLeft.set(1.0);
        ShooterRight.set(1.0);
   }
    
    

    
}