package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;

public class climberCom extends Command{

    private final Climber climber;
    public final Joystick manipulator;
    public climberCom(Climber climber, Joystick manipulator) {
        
        this.climber = climber;
        this.manipulator = manipulator;

        addRequirements(climber);
    }

    @Override 
    public void execute() {
        double climbLeft = manipulator.getRawAxis(1); // Get the left stick's Y-axis value
         // Get right stick's Y-axis value
        double climbRight = manipulator.getRawAxis(5); // Use axis 5 for right stick Y

        // Use rightStickY for your climber logic (consider deadband if needed)
        if (Math.abs(climbRight) < 0.15) {
            climbRight = 0;
        }

        // Use leftStickY for your climber logic
        if (Math.abs(climbLeft) < 0.15) {
            climbLeft = 0;
        }
    
        Climber.climbUpLeft(climbLeft);
        Climber.climbUpRight(climbRight);

    }
    
}
