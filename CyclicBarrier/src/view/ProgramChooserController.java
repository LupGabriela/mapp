package view;

import controller.Controller;
import exceptions.TypeCheckException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProgramChooserController {

    @FXML
    private ListView<Controller> programsListView;

    @FXML
    private Button startProgramButton;

    @FXML
    public void startProgram(ActionEvent event){
        Controller controller = programsListView.getSelectionModel().getSelectedItem();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ProgramExecutor.fxml"));
            ProgramExecutorController executorController = new ProgramExecutorController(controller);
            loader.setController(executorController);
            StackPane executorRoot = loader.load();
            Scene scene = new Scene(executorRoot, 1000,511);
            Stage executorStage = new Stage();
            executorStage.setScene(scene);
            executorStage.setTitle("Program Execution");
            executorStage.show();
        } catch (Exception exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Loading Error");
            alert.setHeaderText("A loading error has occured!");
            alert.setContentText(exception.toString());

            alert.showAndWait();
        }
    }

    @FXML
    public void initialize(){
        programsListView.setItems(getControllerList());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private ObservableList<Controller> getControllerList() {
        IList<IStatement> statements = getStatementsList();
        List<Controller> controllers = new ArrayList<>();
        int i = 1;
        Iterator<IStatement> iterator = statements.getAll();
        while(iterator.hasNext()){
            try{
                IStatement statement = iterator.next();
                statement.typecheck(new MyDictionary<>());
                ProgramState state = new ProgramState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new FileTable(), statement, new MyHeap<>(), new BarrierTable<>());
                IRepository repository = new Repository(state, "log" + i + ".txt");
                Controller controller = new Controller(repository);
                controllers.add(controller);
                i++;
            } catch (TypeCheckException exception){
                System.out.println(exception.getMessage());
            }

        }
        return FXCollections.observableArrayList(controllers);
    }

    private IList<IStatement> getStatementsList() {
        IList<IStatement> statements = new MyList<>();
        IStatement statement1 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v",
                                new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VarExpression("v"))
                )
        );

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

        IStatement bad_statement = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new AssignmentStatement("v", new ValueExpression(new BooleanValue(true)))
        );

        IStatement statement12 = new CompoundStatement(
                new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(
                        new VariableDeclarationStatement("v2", new RefType(new IntType())),
                        new CompoundStatement(
                                new VariableDeclarationStatement("v3", new RefType(new IntType())),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("cnt", new IntType()),
                                        new CompoundStatement(
                                                new NewStatement("v1", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(
                                                        new NewStatement("v2", new ValueExpression(new IntValue(3))),
                                                        new CompoundStatement(
                                                                new NewStatement("v3", new ValueExpression(new IntValue(4))),
                                                                new CompoundStatement(
                                                                        new NewBarrierStatement("cnt", new ReadHeapExpression(new VarExpression("v2"))),
                                                                        new CompoundStatement(
                                                                                new ForkStatement(new CompoundStatement(
                                                                                        new AwaitStatement("cnt"),
                                                                                        new CompoundStatement(
                                                                                            new HeapWriteStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VarExpression("v1")), new ValueExpression(new IntValue(10)), 3)),
                                                                                            new PrintStatement(new ReadHeapExpression(new VarExpression("v1")))
                                                                                        )
                                                                                )),
                                                                                new CompoundStatement(
                                                                                        new ForkStatement(new CompoundStatement(
                                                                                                new AwaitStatement("cnt"),
                                                                                                new CompoundStatement(
                                                                                                        new HeapWriteStatement("v2", new ArithmeticExpression(new ReadHeapExpression(new VarExpression("v2")), new ValueExpression(new IntValue(10)), 3)),
                                                                                                        new CompoundStatement(
                                                                                                                new HeapWriteStatement("v2", new ArithmeticExpression(new ReadHeapExpression(new VarExpression("v2")), new ValueExpression(new IntValue(10)), 3)),
                                                                                                                new PrintStatement(new ReadHeapExpression(new VarExpression("v2")))
                                                                                                        )
                                                                                                )
                                                                                        )),
                                                                                        new CompoundStatement(
                                                                                                new AwaitStatement("cnt"),
                                                                                                new PrintStatement(new ReadHeapExpression(new VarExpression("v3")))
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );


        statements.add(statement1);
        statements.add(statement2);
        statements.add(statement3);
        statements.add(statement4);
        statements.add(statement5);
        statements.add(statement6);
        statements.add(statement7);
        statements.add(statement8);
        statements.add(statement9);
        statements.add(statement10);
        statements.add(statement11);
        statements.add(bad_statement);
        statements.add(statement12);
        return statements;
    }


}
