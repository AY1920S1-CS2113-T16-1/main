package leduc.command;

import leduc.Date;
import leduc.Ui;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.EventsTask;
import leduc.task.HomeworkTask;
import leduc.task.Task;
import leduc.task.TaskList;


/**
 * Represents a EditCommand.
 * Allow to edit a task.
 */
public class EditCommand extends Command {

    /**
     * static variable used for shortcut
     */
    private static String editShortcut = "edit";
    /**
     * Constructor of EditCommand.
     * @param user String which represent the input string of the user.
     */
    public EditCommand(String user){
        super(user);
    }

    /**
     * Allow to edit a task.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws NonExistentDateException Exception caught when the date given does not exist.
     * @throws FileException Exception caught when the file can't be open or read or modify.
     * @throws NonExistentTaskException  Exception caught when the task to delete does not exist.
     * @throws UserAnswerException  Exception caught when the user did not answer correctly the question.
     * @throws EmptyEventDateException Exception caught when one of the two date given does not exist.
     * @throws ConflictDateException Exception thrown when the new event is in conflict with others event.
     * @throws DateComparisonEventException  Exception caught when the second date is before the first one.
     * @throws PrioritizeLimitException  Exception caught when the new priority is not an int or is greater than 9 or less than 0.
     * @throws EditFormatException Exception caught when the format of a one shot edit command is not respected.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws NonExistentDateException, FileException, NonExistentTaskException, EmptyEventDateException, ConflictDateException, DateComparisonEventException, PrioritizeLimitException, EditFormatException, UserAnswerException {
        String userSubstring;
        if(callByShortcut){
            userSubstring = user.trim().substring(EditCommand.editShortcut.length());
        }
        else {
            userSubstring = user.trim().substring(4);
        }
        Task t = null;
        if(userSubstring.isBlank()) { // Multi-steps command
            ui.display("\t Please choose the task to edit from the list by its index: ");
            ListCommand listCommand = new ListCommand(user);
            listCommand.execute(tasks, ui, storage);
            // The user choose the task
            String userEditTaskNumber = ui.readCommand();
            t = this.getEditTask(userEditTaskNumber,tasks,true);
            if (t.isTodo()) {
                ui.display("\t Please choose what you want to edit (1 or 2)\n\t 1. The description " +
                        "\n\t 2. The priority");
                String userEditTPart = ui.readCommand().trim();
                if (userEditTaskNumber.matches("\\d+")) {
                    int choice = Integer.parseInt(userEditTPart.trim());
                    if (choice == 1) {
                        ui.display("\t Please enter the new description of the task");
                        t.setTask(ui.readCommand());
                    } else if (choice == 2) {
                        ui.display("\t Please enter the new priority of the task");
                        String priorityString = ui.readCommand().trim();
                        this.editPriority(t,priorityString);
                    }
                }
                else {
                    throw new UserAnswerException();
                }
            }
            else {
                ui.display("\t Please choose what you want to edit (1 or 2 or 3)\n\t 1. The description " +
                        "\n\t 2. The deadline/period \n\t 3. The priority");
                String userEditTPart = ui.readCommand().trim();
                if (userEditTPart.matches("\\d+")) {
                    int choice = Integer.parseInt(userEditTPart);
                    if (choice == 1) {
                        ui.display("\t Please enter the new description of the task");
                        t.setTask(ui.readCommand().trim());
                    } else if (choice == 2) {
                        if (t.isHomework()) {
                            ui.display("\t Please enter the new deadline of the task");
                            String deadlineString = ui.readCommand().trim();
                            this.editHomeworkDate(t, deadlineString);
                        } else { //event task
                            ui.display("\t Please enter the new period of the task");
                            String periodString = ui.readCommand().trim();
                            this.editEventDate(t, tasks, periodString);
                        }
                    } else if (choice == 3) {
                        ui.display("\t Please enter the new priority of the task");
                        String priorityString = ui.readCommand().trim();
                        this.editPriority(t, priorityString);
                    } else {
                        throw new UserAnswerException();
                    }
                }
                else {
                    throw new UserAnswerException();
                }
            }
        }
        else { // one shot command
            String[] descriptionString = userSubstring.split("description");
            String[] homeworkDateString = userSubstring.split("/by");
            String[] eventPeriodString = userSubstring.split("/at");
            String[] priorityString = userSubstring.split("prio");
            if (descriptionString.length == 2 ){
                t = getEditTask(descriptionString[0].trim(),tasks,false);
                t.setTask(descriptionString[1].trim());
            }
            else if (priorityString.length ==2){
                t = getEditTask(priorityString[0].trim(),tasks,false);
                this.editPriority(t,priorityString[1].trim());
            }
            else if (homeworkDateString.length == 2){
                t = getEditTask(homeworkDateString[0].trim(),tasks,false);
                if (!t.isHomework()){
                    throw new EditFormatException();
                }
                this.editHomeworkDate(t,homeworkDateString[1].trim());
            }
            else if (eventPeriodString.length == 2){
                t = getEditTask(eventPeriodString[0].trim(),tasks,false);
                if (!t.isEvent()){
                    throw new EditFormatException();
                }
                this.editEventDate(t,tasks,eventPeriodString[1].trim());
            }
            else {
                throw new EditFormatException();
            }
        }
        ui.display("\t The task is edited: \n\t " + t.toString());
        storage.save(tasks.getList());
    }

    /**
     * Allows to edit the period of an event task.
     * @param t the event task to be edited.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param period the period part of the user input string.
     * @throws EmptyEventDateException Exception caught when one of the two date given does not exist.
     * @throws NonExistentDateException Exception caught when the date given does not exist.
     * @throws ConflictDateException Exception thrown when the new event is in conflict with others event.
     * @throws DateComparisonEventException Exception caught when the second date is before the first one.
     */
    private void editEventDate(Task t, TaskList tasks, String period) throws EmptyEventDateException,
            NonExistentDateException, ConflictDateException, DateComparisonEventException {
        EventsTask eventsTask = (EventsTask) t;
        String[] dateString = period.split(" - ");
        if (dateString.length == 1) {
            throw new EmptyEventDateException();
        } else if (dateString[0].isBlank() || dateString[1].isBlank()) {
            throw new EmptyEventDateException();
        }
        Date date1 = new Date(dateString[0]);
        Date date2 = new Date(dateString[1]);
        tasks.verifyConflictDate(date1, date2);
        eventsTask.reschedule(date1, date2);
    }

    /**
     * Allows to edit the priority of a task from a priority given in String.
     * @param t the task to be edited.
     * @param priorityString the priority part of the user input string.
     * @throws PrioritizeLimitException Exception caught when the new priority is not an int or is greater than 9 or less than 0.
     */
    private void editPriority(Task t, String priorityString) throws PrioritizeLimitException {
        if (priorityString.matches("\\d+")){
            int priority = Integer.parseInt(priorityString);
            if (priority < 0 || priority > 9) {
                throw new PrioritizeLimitException();
            }
            t.setPriority(priority);
        }
        else {
            throw new PrioritizeLimitException();
        }
    }

    /**
     * Allows to edit the date of an homework task from a date in String.
     * @param t the task to be edited.
     * @param dateString the date in String.
     * @throws NonExistentDateException  Exception caught when the date given does not exist.
     */
    private void editHomeworkDate(Task t, String dateString) throws NonExistentDateException {
        Date d = new Date(dateString);
        HomeworkTask homeworkTask = (HomeworkTask) t;
        homeworkTask.setDeadlines(d);
    }


    /**
     * Allows to get the task corresponding to the INDEX entered for the edit command.
     * @param indexString the index (of the task) part of the user input String
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param multiStepsEditCommand the thrown exception depends on weather it is an multi step or an one shot edit command.
     * @return the task corresponding to the index entered by the user
     * @throws NonExistentTaskException Exception caught when the task to delete does not exist.
     * @throws EditFormatException Exception caught when the format of a one shot edit command is not respected.
     * @throws UserAnswerException Exception caught when the user did not answer correctly the question.
     */
    private Task getEditTask(String indexString,TaskList tasks, boolean multiStepsEditCommand) throws NonExistentTaskException, EditFormatException, UserAnswerException {
        if (indexString.matches("\\d+")) {
            int index = Integer.parseInt(indexString.trim()) - 1;
            if (index > tasks.size() - 1 || index < 0) {
                throw new NonExistentTaskException();
            }
            return tasks.get(index);
        }
        else if (multiStepsEditCommand){
            throw new UserAnswerException();
        }
        else {
            throw new EditFormatException();
        }
    }
    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getEditShortcut() {
        return editShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param editShortcut the new shortcut
     */
    public static void setEditShortcut(String editShortcut) {
        EditCommand.editShortcut = editShortcut;
    }
}
