package leduc.exception;

import leduc.Ui;
import leduc.exception.DukeException;

/**
 * Represent a exception when the task to mark done or to delete does not exist.
 */
public class NonExistentTaskException extends DukeException {
    /**
     * Constructor of leduc.exception.NonExistentTaskException.
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public NonExistentTaskException(Ui ui){
        super();
    }

    /**
     * Tell the user that the tasks given does not exist.
     */
    public String print(){
        return "\t NonExistentTaskException:\n\t\t ☹ OOPS!!! The task doesn't exist";
    }
}
