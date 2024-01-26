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


      double climbLeft = manipulator.getRawAxis(1);
      double climbRight = manipulator.getRawAxis(5);

      
     
        if(Math.abs(climbLeft) < .15){
            climbLeft = 0;
        } 
        if(Math.abs(climbRight)<.15){
            climbRight = 0;
        }
        
        Climber.climbUpLeft(climbLeft);
        Climber.climbUpRight(climbRight);

    }
    
}
