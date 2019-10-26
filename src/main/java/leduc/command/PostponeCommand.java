package leduc.command;

import leduc.Date;
import leduc.Ui;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.HomeworkTask;
import leduc.task.Task;
import leduc.task.TaskList;


/**
 * Represents Postpone command which postpone the deadline of a homework task.
 */
public class PostponeCommand extends Command {

    /**
     * static variable used for shortcut
     */
    private static String postponeShortcut = "postpone";
    /**
     * Constructor of PostponeCommand.
     * @param user String which represent the input string of the user.
     *
     */
    public PostponeCommand(String user){
        super(user);
    }

    /**
     * Allows to postpone the deadline of a deadline task.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws NonExistentTaskException Exception caught when the task does not exist.
     * @throws DeadlineTypeException Exception caught when the task is not a deadline task.
     * @throws FileException Exception caught when the file doesn't exist or cannot be created or cannot be opened.
     * @throws EmptyHomeworkDateException Exception caught when the date of the homework task is not given.
     * @throws NonExistentDateException Exception caught when the date given does not exist.
     * @throws PostponeHomeworkException Exception caught when the new homework is before the old deadline.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws NonExistentTaskException,
            DeadlineTypeException, FileException, EmptyHomeworkDateException, NonExistentDateException,
            PostponeHomeworkException {
        String userSubstring;
        if(callByShortcut){
            userSubstring = user.substring(PostponeCommand.postponeShortcut.length() + 1);
        }
        else {
            userSubstring = user.substring(9);
        }
        String[] postponeString = userSubstring.split("/by");
        if (postponeString.length == 1) { // no /by in input
            throw new EmptyHomeworkDateException();
        }
        int index = -1;
        try {
            index = Integer.parseInt(postponeString[0].trim()) - 1;
        }
        catch(Exception e){
            throw new NonExistentTaskException();
        }
        if (index > tasks.size() - 1 || index < 0) {
            throw new NonExistentTaskException();
        }
        else { // the tasks exist
            Task postponeTask = tasks.get(index);
            if (!postponeTask.isHomework()){
                throw new DeadlineTypeException();
            }
            HomeworkTask postponeHomeworkTask = (HomeworkTask) postponeTask;
            Date d = new Date(postponeString[1]);
            postponeHomeworkTask.postponeHomework(d);
            storage.save(tasks.getList());
            ui.display("\t Noted. I've postponed this task: \n" +
                    "\t\t "+postponeHomeworkTask.getTag() + postponeHomeworkTask.getMark() + " " + postponeHomeworkTask.getTask()+
                    " by:" + postponeHomeworkTask.getDeadlines() + "\n");
        }
    }

    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getPostponeShortcut() {
        return postponeShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param postponeShortcut the new shortcut
     */
    public static void setPostponeShortcut(String postponeShortcut) {
        PostponeCommand.postponeShortcut = postponeShortcut;
    }
}
