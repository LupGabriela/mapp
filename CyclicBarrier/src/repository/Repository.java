package repository;

import exceptions.WritingExeption;
import model.ProgramState;
import model.statements.IStatement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Repository implements IRepository{

    private List<ProgramState> programs;
    private final String filename;
    private IStatement originalProgram;

    public Repository(ProgramState program, String filename) {
        this.originalProgram = program.getOriginalProgram();
        this.programs = new ArrayList<>();
        this.programs.add(program);
        this.filename = filename;
    }

    @Override
    public List<ProgramState> getProgramList() {
        return programs;
    }

    @Override
    public void setProgramList(List<ProgramState> programs) {
        this.programs = programs;
    }

    @Override
    public void logProgramStateExecution(ProgramState program) {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
            logFile.write(program.toString() + "\n");
            logFile.close();
        }
        catch(IOException e){
            throw new WritingExeption("Couldn't write to file");
        }

    }

    @Override
    public IStatement getOriginalProgram() {
        return originalProgram;
    }
}
