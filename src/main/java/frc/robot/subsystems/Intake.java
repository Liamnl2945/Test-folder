package frc.robot.subsystems;



import frc.robot.constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase{
    private final WPI_TalonSRX intakeLeft = new WPI_TalonSRX(constants.Intake.intakeLeft);
    private final WPI_TalonSRX intakeRight = new WPI_TalonSRX(constants.Intake.intakeRight);
    private final double speed = 1.0;

public Intake(){
    intakeLeft.configFactoryDefault();
    intakeRight.configFactoryDefault();

    intakeLeft.setInverted(true);
    intakeRight.setInverted(false);
    
    intakeLeft.setNeutralMode(NeutralMode.Brake);
    intakeRight.setNeutralMode(NeutralMode.Brake);
}

public void runIntake() {
    intakeLeft.set(speed);
    intakeRight.set(speed);
    System.out.println("RUN INTAKE \n\n\n");
}

public void reverseIntake() {
    intakeLeft.set(-speed);
    intakeRight.set(-speed);
    System.out.println("REVERSE INTAKE \n\n\n");
}
public void slowReverseIntake(){
    intakeLeft.set(speed);
    intakeRight.set(speed);
    System.out.println("REVERSE INTAKE SLOW \n\n\n");
}

public void stopIntake(){
    intakeLeft.set(0);
    intakeRight.set(0);
}




}