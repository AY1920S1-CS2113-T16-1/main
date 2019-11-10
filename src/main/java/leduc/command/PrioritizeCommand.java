package leduc.command;


import leduc.ui.Ui;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.Task;
import leduc.task.TaskList;

/**
 * Represents a prioritize command
 */
public class PrioritizeCommand extends Command {

    /**
     * static variable used for shortcut
     */
    private static String prioritizeShortcut = "prioritize";

    /**
     * Constructor of leduc.command.PrioritizeCommand
     * @param userInput String which represent the input string of the user.
     */
    public PrioritizeCommand(String userInput){
        super(userInput);
    }

    /**
     * Execution of leduc.command.PrioritizeCommand: allows to set the priority of a task.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.ui.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws FileException Exception caught when the file can't be open or read or modify.
     * @throws NonExistentTaskException Exception caught when the task to delete does not exist.
     * @throws PrioritizeFormatException Exception caught when the format of a prioritize command is not respected.
     * @throws PrioritizeLimitException Exception caught when the new priority is not an int or is greater than 9 or less than 0.
     * @throws EmptyArgumentException Exception caught when there is no argument
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FileException,
            NonExistentTaskException, PrioritizeFormatException, PrioritizeLimitException, EmptyArgumentException {
        String[] commandString = null;
        String subString = null;
        if(isCalledByShortcut){
            subString = userInput.substring(PrioritizeCommand.prioritizeShortcut.length()).trim();
        }
        else {
            subString = userInput.substring(10).trim();
        }
        if (subString.isBlank()){
            throw new EmptyArgumentException();
        }
        commandString = subString.split("prio",2);
        if (commandString.length==1){ // "prio" is not in the user input
            throw new PrioritizeFormatException();
        }
        int taskIndex = Integer.parseInt(commandString[0].trim()) - 1;
        if (taskIndex > tasks.size() - 1 || taskIndex < 0) {
            throw new NonExistentTaskException();
        }
        int priority = -1;
        try{
            priority = Integer.parseInt(commandString[1].trim());
        }
        catch (Exception e ){
            throw new PrioritizeFormatException();
        }
        if (priority < 1 || priority > 9) {
            throw new PrioritizeLimitException();
        }
        Task t = tasks.get(taskIndex);
        t.setPriority(priority);
        storage.save(tasks.getList());
        ui.showPrioritize(t);
    }

    /**
     * Returns is false for a leduc.command.PrioritizeCommand.
     * @return false
     */
    public boolean isExit(){
        return false;
    }

    /**
     * used when the user want to change the shortcut
     * @param prioritizeShortcut the new shortcut
     */
    public static void setPrioritizeShortcut(String prioritizeShortcut){
        PrioritizeCommand.prioritizeShortcut = prioritizeShortcut;
    }

    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getPrioritizeShortcut(){
        return PrioritizeCommand.prioritizeShortcut;
    }
}
