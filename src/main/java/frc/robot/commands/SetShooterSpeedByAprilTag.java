package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

public class SetShooterSpeedByAprilTag extends InstantCommand {
    private final Shooter shooter;

    public SetShooterSpeedByAprilTag(Shooter shooter) {
        this.addRequirements(shooter);
        this.shooter = shooter;
    }
    @Override
    public void execute() {

        int detectedTagID = shooter.getDetectedAprilTagID();
        double speed  = 0;
        //System.out.println("SHOOTER INITALIZED");
        if(RobotContainer.aim.getAsBoolean()){
            //System.out.println("SHOOTER SEES AIM");
            switch (detectedTagID){
                case 6:  // Adjust speed for BLUE AMP 
                    speed = 0.175;  // Example speed for Tag 1
                    //System.out.println("\n\n BLUE AMP DETECTED");
                    break;
                case 7:  // Adjust speed for BLUE SPEAKER
                    speed = 1.0;   // Example speed for Tag 2
                    //System.out.println("\n\n BLUE Speaker DETECTED");
                    break;
                case 5:  // Adjust speed for RED AMP
                    speed = 0.175;  // Example speed for Tag 1
                   //System.out.println("\n\n RED AMP DETECTED");
                    break;
                case 4:  // Adjust speed for RED SPEAKER
                    speed = 1.0;   // Example speed for Tag 2
                    //System.out.println("\n\n RED Speaker DETECTED");
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    speed = TeleopSwerve.trapShooterSpeed;
                    break;
                default:
                    speed = 0.15;   // defualt speed, even if no tag is detected it will spin up when the aim is pressed
            }
        }
        shooter.runShooterAtSpeed(speed);
        //shooter.runShooterAtSpeed(0);

    }
}

