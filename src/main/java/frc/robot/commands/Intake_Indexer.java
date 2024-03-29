package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.indexer;
import frc.robot.subsystems.Intake;



public class Intake_Indexer extends Command {
    private final Intake intake;
    private final indexer indexer;
    public final Joystick intakeStick;

    public Intake_Indexer(Intake intake, indexer indexer, Joystick intakeStick){

        this.intake = intake;
        this.indexer = indexer;
        this.intakeStick = intakeStick;

        addRequirements(intake);
        addRequirements(indexer);
    }

    @Override
    public void execute(){
        //System.out.println("\n LEFT LIMIT SWITCH "+frc.robot.subsystems.indexer.limitSwitchLeft.get());
        //System.out.println("\nRIGHT LIMIT SWITCH "+frc.robot.subsystems.indexer.limitSwitchRight.get());

        if (!indexer.limitSwitchLeft.get() && !indexer.limitSwitchRight.get()) {
        // Safe to run the indexer
        //System.out.println("NEITHER LIMIT SWITCHES ACTIVE READY to run");
    } 
    else {
        // Stop the indexer if any limit switch is activated
        //System.out.println("LIMIT SWITCH ACTIVE");
        //indexer.stopIndexer();
    }
        if(intakeStick.getPOV() == 0 || intakeStick.getPOV() == 45 || intakeStick.getPOV() == 315){
            if(frc.robot.subsystems.indexer.limitSwitchLeft.get() && frc.robot.subsystems.indexer.limitSwitchRight.get()){
                //System.out.println("RUNNING INTAKE & INDEXER");
            intake.runIntake();
            indexer.runIndexer();
            }
            else{
                //System.out.println("NOT RUNNING INTAKE & INDEXER");
                intake.stopIntake();
                indexer.stopIndexer();
            }
            //System.out.println("\n INTAKE & INDEXER IN!!!");
        }
        else if(intakeStick.getPOV() == 135 || intakeStick.getPOV() == 180 || intakeStick.getPOV() == 225){
            intake.reverseIntake();
            indexer.reverseIndexer();
           // System.out.println("\n INTAKE & INDEXER Out!!!");
        }
        else{
            intake.stopIntake();
            indexer.stopIndexer();
            //System.out.println("INTAKE & INDEXER ARE OFF");
        }
         // Check limit switches before running the indexer
    }

}
