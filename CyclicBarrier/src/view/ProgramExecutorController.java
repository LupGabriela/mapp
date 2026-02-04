package view;

import controller.Controller;
import exceptions.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import model.ProgramState;
import model.statements.IStatement;
import model.utilities.ADTs.*;
import model.utilities.DTOs.BarrierTableEntry;
import model.utilities.DTOs.HeapEntry;
import model.utilities.DTOs.SymbolTableEntry;
import model.values.StringValue;
import model.values.Value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgramExecutorController {

    @FXML
    private Label programStatesCounterLabel;
    @FXML
    private ListView<Integer> programStatesListView;
    @FXML
    private TableView<HeapEntry> heapTableView;
    @FXML
    private ListView<Value> outListView;
    @FXML
    private ListView<IStatement> exeStackListView;
    @FXML
    private TableView<SymbolTableEntry> symTableTableView;
    @FXML
    private ListView<StringValue> fileTableListView;
    @FXML
    private Button oneStepButton;
    @FXML
    private TableColumn<HeapEntry, String> heapAddress;
    @FXML
    private TableColumn<HeapEntry, String> heapValue;
    @FXML
    private TableColumn<SymbolTableEntry, String> symTableName;
    @FXML
    private TableColumn<SymbolTableEntry, String> symTableValue;

    @FXML
    private TableView<BarrierTableEntry> barrierTableTableView;
    @FXML
    private TableColumn<BarrierTableEntry, String> barrierIndex;
    @FXML
    private TableColumn<BarrierTableEntry, String> barrierValue;
    @FXML
    private TableColumn<BarrierTableEntry, String> barrierList;


    private Controller controller;
    private ProgramState lastMainProgram;

    public ProgramExecutorController(Controller controller) {
        this.controller = controller;
        this.lastMainProgram = null;
    }

    @FXML
    public void initialize(){
        heapAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        heapValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        symTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        symTableValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        programStatesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        barrierIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
        barrierValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        barrierList.setCellValueFactory(new PropertyValueFactory<>("list"));

        try {
            populateWidgets();
        } catch (AlreadyExistingVariable | DivisionByZero | InvalidArguments | StackIsEmptyException |
                ClosingFileException | NotFoundException | ReadingException | WritingExeption exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing to execute");
            alert.setHeaderText("There is nothing left to execute!");

            alert.showAndWait();
        }
    }

    private void populateWidgets() {
        List<ProgramState> programStates = controller.getProgramList();
        ProgramState currentProgram;
        if(programStates.size() == 0){
            programStatesCounterLabel.setText("Program states: 0");
            if(lastMainProgram == null){
                throw new GUIException("no more valid program states!");
            }
            else {
                currentProgram = lastMainProgram;
                lastMainProgram = null;
            }
        }
        else{
            lastMainProgram = programStates.get(0);
            currentProgram = programStates.get(0);
            programStatesCounterLabel.setText("Program states: " + programStates.size());
        }
        try {
            populateProgramStateListView(programStates);
            programStatesListView.getSelectionModel().selectIndices(0);
            populateExeStackListView(currentProgram);
            populateHeapTableView(currentProgram);
            populateSymTableTableView(currentProgram);
            populateOutListView(currentProgram);
            populateFileTableListView(currentProgram);

            populateBarrierTableTableView(currentProgram);

        } catch (AlreadyExistingVariable | DivisionByZero | InvalidArguments | StackIsEmptyException |
                ClosingFileException | NotFoundException | ReadingException | WritingExeption exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing to execute");
            alert.setHeaderText("There is nothing left to execute!");

            alert.showAndWait();
        }
    }

    private void populateBarrierTableTableView(ProgramState currentProgram) {
        IBarrierTable<Integer, Pair<Integer, List<Integer>>> barrierTable = currentProgram.getBarrierTable();
        ArrayList<BarrierTableEntry> entries = new ArrayList<>();
        for(Map.Entry<Integer, Pair<Integer, List<Integer>>> entry : barrierTable.getContent().entrySet()){
            entries.add(new BarrierTableEntry(entry.getKey(), entry.getValue().getKey(),entry.getValue().getValue().toString()));
        }
        barrierTableTableView.setItems(FXCollections.observableArrayList(entries));
    }

    private void populateFileTableListView(ProgramState currentProgram) {
        IFileTable fileTable = currentProgram.getFileTable();
        ArrayList<StringValue> fileList = new ArrayList<>(fileTable.getContent().keySet());
        fileTableListView.setItems(FXCollections.observableArrayList(fileList));
    }

    private void populateOutListView(ProgramState currentProgram) {
        IList<Value> out = currentProgram.getOutput();
        ArrayList<Value> outList = new ArrayList<>();
        Iterator<Value> iterator = out.getAll();
        while(iterator.hasNext()){
            outList.add(iterator.next());
        }
        outListView.setItems(FXCollections.observableArrayList(outList));
    }

    private void populateSymTableTableView(ProgramState currentProgram) {
        IDictionary<String, Value> symbolTable = currentProgram.getSymbolTable();
        ArrayList<SymbolTableEntry> entries = new ArrayList<>();
        for(Map.Entry<String, Value> entry : symbolTable.getContent().entrySet()){
            entries.add(new SymbolTableEntry(entry.getKey(), entry.getValue()));
        }
        symTableTableView.setItems(FXCollections.observableArrayList(entries));
    }

    private void populateHeapTableView(ProgramState currentProgram) {
        IHeap<Value> heap = currentProgram.getHeap();
        ArrayList<HeapEntry> entries =new ArrayList<>();
        for(Map.Entry<Integer, Value> entry : heap.getContent().entrySet()){
            entries.add(new HeapEntry(entry.getKey(), entry.getValue()));
        }
        heapTableView.setItems(FXCollections.observableArrayList(entries));
    }

    private void populateExeStackListView(ProgramState currentProgram) {
        IStack<IStatement> executionStack = currentProgram.getExecutionStack();
        exeStackListView.setItems(FXCollections.observableArrayList(executionStack.toList()));
    }

    private void populateProgramStateListView(List<ProgramState> programStates) {
        List<Integer> stateIds = programStates.stream().map(ProgramState::getProgramId).collect(Collectors.toList());
        programStatesListView.setItems(FXCollections.observableArrayList(stateIds));
    }

    @FXML
    void executeOneStep(MouseEvent event) {
        try{
            List<ProgramState> states = controller.getProgramList();
            if(states.size() > 0 || lastMainProgram != null)
                controller.executeOnlyOneStep();
        }
        catch(AlreadyExistingVariable | DivisionByZero | InvalidArguments | StackIsEmptyException |
                ClosingFileException | NotFoundException | ReadingException | WritingExeption exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Execution Error");
            alert.setHeaderText("An execution error has occured!");
            alert.setContentText(exception.getMessage());

            alert.showAndWait();
        }

        try{
            populateWidgets();
        } catch (GUIException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Execution Error");
            alert.setHeaderText("An execution error has occured!");
            alert.setContentText(exception.getMessage());

            alert.showAndWait();
        }
    }

    @FXML
    public void switchProgramState(MouseEvent event){
        List<ProgramState> states = controller.getProgramList();
        int index = programStatesListView.getSelectionModel().getSelectedIndex();
        ProgramState programState = states.get(index);
        try {
            populateExeStackListView(programState);
            populateSymTableTableView(programState);
        } catch (AlreadyExistingVariable | DivisionByZero | InvalidArguments | StackIsEmptyException |
                ClosingFileException | NotFoundException | ReadingException | WritingExeption exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing to execute");
            alert.setHeaderText("There is nothing left to execute!");

            alert.showAndWait();
        }
    }

}
