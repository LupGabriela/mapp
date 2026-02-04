package controller;

import exceptions.ConcurrentException;
import exceptions.UnableToGetFutureExeption;
import model.ProgramState;
import model.utilities.ADTs.IHeap;
import model.values.RefValue;
import model.values.Value;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    private final IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
        executor = Executors.newFixedThreadPool(3);
    }

    public List<ProgramState> getProgramList(){
        return repository.getProgramList();
    }

    public void executeOnlyOneStep(){
        List<ProgramState> programList = removeCompletedPrograms(repository.getProgramList());
        collectTheGarbage(programList);
        oneStepForAllPrograms(programList);
        programList = removeCompletedPrograms(repository.getProgramList());
        repository.setProgramList(programList);
        if (programList.isEmpty()) {
            executor.shutdownNow();
        }
    }



    public List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList){
        return inProgramList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<ProgramState> programList){
//        print the initial program list
        programList.forEach(repository::logProgramStateExecution);

//        make the list of callables
        List<Callable<ProgramState>> callList = programList.stream()
                .map((ProgramState program) -> (Callable<ProgramState>)(program::oneStep))
                .collect(Collectors.toList());

        List<ProgramState> newProgramList;
        try {
            newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new UnableToGetFutureExeption(e.getMessage());
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (InterruptedException | UnableToGetFutureExeption e) {
            throw new ConcurrentException(e.getMessage());
        }

        programList.addAll(newProgramList);

        programList.forEach(repository::logProgramStateExecution);

        repository.setProgramList(programList);
    }

    public void allSteps(){
        List<ProgramState> programList = removeCompletedPrograms(repository.getProgramList());
        while(programList.size() > 0){
            collectTheGarbage(programList);
            oneStepForAllPrograms(programList);
            programList = removeCompletedPrograms(repository.getProgramList());
        }
        executor.shutdownNow();
        repository.setProgramList(programList);
    }

    private void collectTheGarbage(List<ProgramState> programList) {
        IHeap<Value> heap = programList.get(0).getHeap();
        heap.setContent(garbageCollector(
                getAddressesFromSymbolTablesAndHeap(heap.getContent()),
                heap.getContent()));
    }

    private Map<Integer, Value> garbageCollector(
            List<Integer> addresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(element -> addresses.contains(element.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValues) {
        return symbolTableValues.stream()
                .filter(value -> value instanceof RefValue)
                .map(value -> ((RefValue)value).getAddress())
                .collect(Collectors.toList());
    }

    private List<Integer> getAllAddressesFromSymbolTables() {
        return repository.getProgramList().stream()
                .map(program -> getAddressesFromSymbolTable(program.getSymbolTable().getContent().values()))
                .reduce(Stream.of(0).collect(Collectors.toList()),
                        (acc, item) -> Stream.concat(acc.stream(), item.stream())
                                .collect(Collectors.toList()));
    }

    private List<Integer> getAddressesFromSymbolTablesAndHeap(Map<Integer, Value> heap) {

        List<Integer> addressesList = getAllAddressesFromSymbolTables();

        Set<Map.Entry<Integer, Value>> heapEntrySet = heap.entrySet();
        boolean doneFinding = false;
        while (!doneFinding) {
            doneFinding = true;
            List<Integer> addressesFromHeap = heapEntrySet.stream()
                    .filter(element -> addressesList.contains(element.getKey()))
                    .filter(element -> element.getValue() instanceof RefValue)
                    .map(element -> ((RefValue) element.getValue()).getAddress())
                    .filter(address -> !addressesList.contains(address))
                    .collect(Collectors.toList());
            if(!addressesFromHeap.isEmpty()) {
                addressesList.addAll(addressesFromHeap);
                doneFinding = false;
            }
        }
        return addressesList;
    }

    @Override
    public String toString() {
        return repository.getOriginalProgram().toString();
    }
}
