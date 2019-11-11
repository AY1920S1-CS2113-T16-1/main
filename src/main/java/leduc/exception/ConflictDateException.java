package leduc.exception;

import leduc.task.Task;

import java.util.ArrayList;

/**
 * throw an exception when there is a conflict between two date of event
 */
public class ConflictDateException extends DukeException {
    ArrayList<Task> tasks;

    /**
     * Constructor
     * @param tasks the list of event which are in conflict with the new event
     */
    public ConflictDateException(ArrayList<Task> tasks){
        super();
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
