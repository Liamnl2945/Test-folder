package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants;

public class indexer extends SubsystemBase {

    private final WPI_TalonSRX indexerRight = new WPI_TalonSRX(constants.indexer.indexerRight);
    private final WPI_TalonSRX indexerLeft = new WPI_TalonSRX(constants.indexer.indexerLeft);
    public final DigitalInput limitSwitchRight = new DigitalInput(constants.indexer.limitSwitchRightPort);
    public final DigitalInput limitSwitchLeft = new DigitalInput(constants.indexer.limitSwitchLeftPort);
    private final double speed = 0.6;

public indexer(){
    indexerRight.configFactoryDefault();
    indexerLeft.configFactoryDefault();

    indexerRight.setInverted(true);
    indexerLeft.setInverted(true);
    
    indexerRight.setNeutralMode(NeutralMode.Brake);
    indexerLeft.setNeutralMode(NeutralMode.Brake);
}

public void runIndexer() {
    if (limitSwitchLeft.get() || limitSwitchRight.get()) {  // Check if top limit switch is not pressed
        indexerRight.set(-speed);
        indexerLeft.set(speed);
        System.out.println("RUN INDEXER \n\n\n");
    } else {
        stopIndexer();  // Stop if limit switch is activated
    }
}

public void reverseIndexer() {
    if (limitSwitchLeft.get() || limitSwitchRight.get()) {  // Check if bottom limit switch is not pressed
        indexerRight.set(-speed);
        indexerLeft.set(-speed);
        System.out.println("REVERSE INDEXER \n\n\n");
    } else {
        stopIndexer();  // Stop if limit switch is activated
    }
}

public void slowReverseIndexer(){
    indexerRight.set(speed);
    indexerLeft.set(speed);
}

public void stopIndexer(){
    indexerRight.set(0);
    indexerLeft.set(0);
}



    
}
