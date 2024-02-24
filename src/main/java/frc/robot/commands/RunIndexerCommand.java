package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.indexer;

public class RunIndexerCommand extends Command {

    private final indexer indexer;

    public RunIndexerCommand(indexer indexer) {
        this.indexer = indexer;
        addRequirements(indexer);
    }

    @Override
    public void execute() {
        indexer.runIndexer();
    }
}
