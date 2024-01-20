package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants;

public class indexer extends SubsystemBase {

    private final WPI_TalonSRX indexerbot = new WPI_TalonSRX(constants.indexer.indexerbot);
    private final WPI_TalonSRX indexertop = new WPI_TalonSRX(constants.indexer.indexertop);
    private final double speed = 0.4;

public indexer(){
    indexerbot.configFactoryDefault();
    indexertop.configFactoryDefault();

    indexerbot.setInverted(true);
    indexertop.setInverted(false);
    
    indexerbot.setNeutralMode(NeutralMode.Brake);
    indexertop.setNeutralMode(NeutralMode.Brake);
}

public void runIndexer() {
    indexerbot.set(speed);
    indexertop.set(speed);
    System.out.println("RUN INDEXER \n\n\n");
}

public void reverseIndexer() {
    indexerbot.set(-speed);
    indexertop.set(-speed);
    System.out.println("REVERSE INDEXER \n\n\n");
}
public void slowReverseIndexer(){
    indexerbot.set(speed);
    indexertop.set(speed);
}

public void stopIndexer(){
    indexerbot.set(0);
    indexertop.set(0);
}



    
}
