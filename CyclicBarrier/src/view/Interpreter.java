package view;

import controller.Controller;
import exceptions.TypeCheckException;
import model.ProgramState;
import model.expressions.*;
import model.statements.*;
import model.types.BooleanType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.utilities.ADTs.*;
import model.values.BooleanValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import view.commands.ExitCommand;
import view.commands.RunExample;

public class Interpreter {

    public static void main(String[] args) {

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        IStatement statement1 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v",
                                new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VarExpression("v"))
                )
        );

        try {
            statement1.typecheck(new MyDictionary<>());
            ProgramState state1 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), statement1, new MyHeap<>(), new BarrierTable<>());
            IRepository repository1 = new Repository(state1, "log1.txt");
            Controller controller1 = new Controller(repository1);
            menu.addCommand(new RunExample("1",statement1.toString(),controller1));
        } catch (TypeCheckException exception){
            System.out.println("There was a type error: " + exception.getMessage());
        }

        IStatement statement2 = new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ArithmeticExpression(
                                        new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5)),
                                                3),
                                        1)
                                ),
                                new CompoundStatement(
                                        new AssignmentStatement("b",new ArithmeticExpression(
                                                new VarExpression("a"),
                                                new ValueExpression(new IntValue(1)),
                                                1)),
                                        new PrintStatement(new VarExpression("b"))
                                )
                        )
                )
        );

        try {
            statement2.typecheck(new MyDictionary<>());
            ProgramState state2 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), statement2, new MyHeap<>(), new BarrierTable<>());
            IRepository repository2 = new Repository(state2, "log2.txt");
            Controller controller2 = new Controller(repository2);
            menu.addCommand(new RunExample("2",statement2.toString(),controller2));
        } catch (TypeCheckException exception){
            System.out.println("There was a type error: " + exception.getMessage());
        }


        IStatement statement3 = new CompoundStatement(
                new VariableDeclarationStatement("a", new BooleanType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VarExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))
                                        ),
                                        new PrintStatement(new VarExpression("v"))
                                )
                        )
                )
        );

        try {
            statement3.typecheck(new MyDictionary<>());
            ProgramState state3 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), statement3, new MyHeap<>(), new BarrierTable<>());
            IRepository repository3 = new Repository(state3, "log3.txt");
            Controller controller3 = new Controller(repository3);
            menu.addCommand(new RunExample("3",statement3.toString(),controller3));
        } catch (TypeCheckException exception){
            System.out.println("There was a type error: " + exception.getMessage());
        }


        IStatement statement4 = new CompoundStatement(
                new VariableDeclarationStatement("file", new StringType()),
                new CompoundStatement(
                        new AssignmentStatement("file", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenRFileStatement(new VarExpression("file")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("v", new IntType()),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VarExpression("file"), "v"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VarExpression("v")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VarExpression("file"), "v"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VarExpression("v")),
                                                                        new CloseRFileStatement(new VarExpression("file"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        try {
            statement4.typecheck(new MyDictionary<>());
            ProgramState state4 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), statement4, new MyHeap<>(), new BarrierTable<>());
            IRepository repository4 = new Repository(state4, "log4.txt");
            Controller controller4 = new Controller(repository4);
            menu.addCommand(new RunExample("4",statement4.toString(),controller4));
        } catch (TypeCheckException exception){
            System.out.println("There was a type error: " + exception.getMessage());
        }


        IStatement statement5 = new CompoundStatement(
                new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VarExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VarExpression("v")),
                                                new PrintStatement(new VarExpression("a"))
                                        )
                                )
                        )
                )
        );
        try {
            statement5.typecheck(new MyDictionary<>());
            ProgramState state5 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), statement5, new MyHeap<>(), new BarrierTable<>());
            IRepository repository5 = new Repository(state5, "log5.txt");
            Controller controller5 = new Controller(repository5);
            menu.addCommand(new RunExample("5",statement5.toString(),controller5));
        } catch (TypeCheckException exception){
            System.out.println("There was a type error: " + exception.getMessage());
        }


        IStatement statement6 = new CompoundStatement(
                new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VarExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new ReadHeapExpression(new VarExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression( new ReadHeapExpression(new ReadHeapExpression(new VarExpression("a"))), new ValueExpression(new IntValue(5)), 1 ))
                                        )
                                )
                        )
                )
        );

        try {
            statement6.typecheck(new MyDictionary<>());
            ProgramState state6 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), statement6, new MyHeap<>(), new BarrierTable<>());
            IRepository repository6 = new Repository(state6, "log6.txt");
            Controller controller6 = new Controller(repository6);
            menu.addCommand(new RunExample("6",statement6.toString(),controller6));
        } catch (TypeCheckException exception){
            System.out.println("There was a type error: " + exception.getMessage());
        }


        IStatement statement7 = new CompoundStatement(
                new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(new VarExpression("v"))),
                                new CompoundStatement(
                                        new HeapWriteStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(
                                                new ArithmeticExpression(
                                                        new ReadHeapExpression(new VarExpression("v")),
                                                        new ValueExpression(new IntValue(5)),
                                                        1
                                                )
                                        )
                                )
                        )
                )
        );

        try {
            statement7.typecheck(new MyDictionary<>());
            ProgramState state7 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), statement7, new MyHeap<>(), new BarrierTable<>());
            IRepository repository7 = new Repository(state7, "log7.txt");
            Controller controller7 = new Controller(repository7);
            menu.addCommand(new RunExample("7",statement7.toString(),controller7));
        } catch (TypeCheckException exception){
            System.out.println("There was a type error: " + exception.getMessage());
        }


        IStatement statement8 = new CompoundStatement(
                new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VarExpression("v")),
                                        new CompoundStatement(
                                                new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VarExpression("a"))))
                                        )
                                )
                        )
                )
        );

        try {
            statement8.typecheck(new MyDictionary<>());
            ProgramState state8 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), statement8, new MyHeap<>(), new BarrierTable<>());
            IRepository repository8 = new Repository(state8, "log8.txt");
            Controller controller8 = new Controller(repository8);
            menu.addCommand(new RunExample("8", statement8.toString(), controller8));
        } catch (TypeCheckException exception){
            System.out.println("There was a type error: " + exception.getMessage());
        }



        IStatement statement9 = new CompoundStatement(
                new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new NewStatement("v", new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ReadHeapExpression( new VarExpression("v")))
                        )
                )
        );

        try {
            statement9.typecheck(new MyDictionary<>());
            ProgramState state9 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), statement9, new MyHeap<>(), new BarrierTable<>());
            IRepository repository9 = new Repository(state9, "log9.txt");
            Controller controller9 = new Controller(repository9);
            menu.addCommand(new RunExample("9", statement9.toString(), controller9));
        } catch (TypeCheckException exception){
            System.out.println("There was a type error: " + exception.getMessage());
        }

        IStatement statement10 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(new VarExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                                        new CompoundStatement(
                                                new PrintStatement(new VarExpression("v")),
                                                new AssignmentStatement("v", new ArithmeticExpression(new VarExpression("v"), new ValueExpression(new IntValue(1)), 2))
                                        )
                                ),
                                new PrintStatement(new VarExpression("v"))
                        )
                )
        );

        try {
            statement10.typecheck(new MyDictionary<>());
            ProgramState state10 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), statement10, new MyHeap<>(), new BarrierTable<>());
            IRepository repository10 = new Repository(state10, "log10.txt");
            Controller controller10 = new Controller(repository10);
            menu.addCommand(new RunExample("10", statement10.toString(), controller10));
        } catch (TypeCheckException exception){
            System.out.println("There was a type error: " + exception.getMessage());
        }

        IStatement statement11 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VarExpression("v")),
                                                                                new PrintStatement(new ReadHeapExpression(new VarExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VarExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VarExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );


        try {
            statement11.typecheck(new MyDictionary<>());
            ProgramState state11 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), statement11, new MyHeap<>(), new BarrierTable<>());
            IRepository repository11 = new Repository(state11, "log11.txt");
            Controller controller11 = new Controller(repository11);
            menu.addCommand(new RunExample("11", statement11.toString(), controller11));
        } catch (TypeCheckException exception){
            System.out.println("There was a type error: " + exception.getMessage());
        }
        
        IStatement statement12 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new AssignmentStatement("v", new ValueExpression(new BooleanValue(true)))
        );


        try {
            statement12.typecheck(new MyDictionary<>());
            ProgramState state12 = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), statement12, new MyHeap<>(), new BarrierTable<>());
            IRepository repository12 = new Repository(state12, "log12.txt");
            Controller controller12 = new Controller(repository12);
            menu.addCommand(new RunExample("12", statement12.toString(), controller12));
        } catch (TypeCheckException exception){
            System.out.println("There was a type error: " + exception.getMessage());
        }


        menu.show();
    }
}
