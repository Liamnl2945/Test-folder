package frc.robot.subsystems;

import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    
    private final TalonFX ShooterLeft = new TalonFX(4);
    private final TalonFX ShooterRight = new TalonFX(5);
    private final double speed = 0.4;

    public Shooter(){
        ShooterLeft.setInverted(true);
        ShooterRight.setNeutralMode(NeutralModeValue.Brake);
    }

    public void runShooter() {
        ShooterLeft.set(-0.4);
        ShooterRight.set(-0.4);
    }

    public void reverseShooter() {
        ShooterLeft.set(speed*2);
        ShooterRight.set(speed*2);
    }

    public void stopIntake(){
        ShooterLeft.set(0);
        ShooterRight.set(0);
    }

    
}
