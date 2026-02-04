package view.commands;

import controller.Controller;
import exceptions.*;

public class RunExample extends Command {

    private final Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try{
            controller.allSteps();
        } catch (AlreadyExistingVariable | DivisionByZero | InvalidArguments | StackIsEmptyException |
                ClosingFileException | NotFoundException | ReadingException | WritingExeption exception){
            System.out.println(exception.getMessage());
        }
    }
}
