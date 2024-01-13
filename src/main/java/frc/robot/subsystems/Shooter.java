package frc.robot.subsystems;

import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants;

public class Shooter extends SubsystemBase {
    
    private final TalonFX ShooterLeft = new TalonFX(constants.Shooter.ShooterLeft);
    private final TalonFX ShooterRigth = new TalonFX(constants.Shooter.ShooterRigth);
    private final double speed = 0.4;

public Shooter(){
    ShooterLeft.TalonFXConfiguration();
    ShooterRigth.configFactoryDefault();

    ShooterLeft.setInverted(true);
    ShooterRigth.setInverted(false);
    
    ShooterLeft.setNeutralMode(NeutralMode.Brake);
    ShooterRigth.setNeutralMode(NeutralMode.Brake);
}

public void runIntake() {
    ShooterLeft.set(-0.4);
    ShooterRigth.set(-0.4);
}

public void reverseIntake() {
    ShooterLeft.set(speed*2);
    ShooterRigth.set(speed*2);
}
public void slowReverseIntake(){
    ShooterLeft.set(speed);
    ShooterRigth.set(speed);
}

public void stopIntake(){
    ShooterLeft.set(0);
    ShooterRigth.set(0);
}

    
}
