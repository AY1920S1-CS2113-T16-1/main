package leduc.exception;

import leduc.Ui;
import leduc.exception.DukeException;

/**
 * Represent a exception when the period of the event task is not given by the user.
 */
public class EmptyEventDateException extends DukeException {
    /**
     * Constructor of leduc.exception.EmptyEventDateException.
     * @param ui leduc.Ui which deals with the interactions with the user.
     */
    public EmptyEventDateException(Ui ui){
        super();
    }

    /**
     * Ask for a period for the event task to the user.
     */
    public String print(){
        return "\t emptyEventDateException:\n\t\t ☹ OOPS!!! Please enter a period for the event task";
    }
}
