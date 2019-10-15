package leduc.command;

import leduc.Ui;
import leduc.exception.DukeException;
import leduc.exception.DuplicationShortcutException;
import leduc.exception.MeaninglessException;
import leduc.storage.Storage;
import leduc.task.TaskList;

import java.util.HashSet;

public class ShortcutCommand extends Command {

    public ShortcutCommand(String user){
        super(user);
    }
    private static HashSet<String> setShortcut = new HashSet<>();
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        String[] userCommand = user.split(" ");
        String newShortcut="";
        if (userCommand.length >= 1){
            ui.display("Please enter a shortcut for " + userCommand[1]);
            newShortcut = ui.readCommand();
            if(setShortcut.contains(newShortcut)){
                throw new DuplicationShortcutException();
            }
            else {
                setOneShortcut(userCommand[1], newShortcut);
                ui.display("The shortcut for " + userCommand[1] +" has been set");
            }
        }
        else {
            do{
                ui.display("The precedent shortcut for bye is " + ByeCommand.getByeShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("bye", newShortcut);

            do{
                ui.display("The precedent shortcut for list is " + ListCommand.getListShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("list", newShortcut);

            do{
                ui.display("The precedent shortcut for help is " + HelpCommand.getHelpShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("help", newShortcut);

            do{
                ui.display("The precedent shortcut for done is " + DoneCommand.getDoneShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("done", newShortcut);

            do{
                ui.display("The precedent shortcut for find is " + FindCommand.getFindShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("find", newShortcut);

            do{
                ui.display("The precedent shortcut for delete is " + DeleteCommand.getDeleteShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("delete", newShortcut);


            do{
                ui.display("The precedent shortcut for deadline is " + DeadlineCommand.getDeadlineShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("deadline", newShortcut);

            do{
                ui.display("The precedent shortcut for event is " + EventCommand.getEventShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("event", newShortcut);

            do{
                ui.display("The precedent shortcut for todo is " + TodoCommand.getTodoShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("todo", newShortcut);

            do{
                ui.display("The precedent shortcut for edit is " + EditCommand.getEditShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("edit", newShortcut);

            do{
                ui.display("The precedent shortcut for postpone is " + PostponeCommand.getPostponeShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("postpone", newShortcut);

            do{
                ui.display("The precedent shortcut for snooze is " + SnoozeCommand.getSnoozeShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("snooze", newShortcut);

            do{
                ui.display("The precedent shortcut for reschedule is " + RescheduleCommand.getRescheduleShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("reschedule", newShortcut);

            do{
                ui.display("The precedent shortcut for remind is " + RemindCommand.getRemindShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("remind", newShortcut);

            do{
                ui.display("The precedent shortcut for sort is " + SortCommand.getSortShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("sort", newShortcut);

            do{
                ui.display("The precedent shortcut for setWelcome is " + SetWelcomeCommand.getSetWelcomeShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("setwelcome", newShortcut);
            do{
                ui.display("The precedent shortcut for setWelcome is " + ShowCommand.getShowShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("show", newShortcut);

            do{
                ui.display("The precedent shortcut for prioritize is " + PrioritizeCommand.getPrioritizeShortcut() +" please enter new shortcut");
                newShortcut = ui.readCommand();
            } while(setShortcut.contains(newShortcut));
            setOneShortcut("prioritize", newShortcut);

            ui.display("All shortcut has been set");
        }
        storage.saveConfig();
    }

    public static void setOneShortcut(String commandName, String shortcutName) throws MeaninglessException {
        switch (commandName){
            case "bye" :
                setShortcut.remove(ByeCommand.getByeShortcut());
                ByeCommand.setByeShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "list" :
                setShortcut.remove(ListCommand.getListShortcut());
                ListCommand.setListShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "help" :
                setShortcut.remove(HelpCommand.getHelpShortcut());
                HelpCommand.setHelpShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "done" :
                setShortcut.remove(DoneCommand.getDoneShortcut());
                DoneCommand.setDoneShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "find" :
                setShortcut.remove(FindCommand.getFindShortcut());
                FindCommand.setFindShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "delete" :
                setShortcut.remove(DeleteCommand.getDeleteShortcut());
                DeleteCommand.setDeleteShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "deadline" :
                setShortcut.remove(DeadlineCommand.getDeadlineShortcut());
                DeadlineCommand.setDeadlineShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "event" :
                setShortcut.remove(EventCommand.getEventShortcut());
                EventCommand.setEventShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "todo" :
                setShortcut.remove(TodoCommand.getTodoShortcut());
                TodoCommand.setTodoShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "edit" :
                setShortcut.remove(EditCommand.getEditShortcut());
                EditCommand.setEditShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "postpone" :
                setShortcut.remove(PostponeCommand.getPostponeShortcut());
                PostponeCommand.setPostponeShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "snooze" :
                setShortcut.remove(SnoozeCommand.getSnoozeShortcut());
                SnoozeCommand.setSnoozeShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "reschedule" :
                setShortcut.remove(RescheduleCommand.getRescheduleShortcut());
                RescheduleCommand.setRescheduleShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "remind" :
                setShortcut.remove(RemindCommand.getRemindShortcut());
                RemindCommand.setRemindShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "sort" :
                setShortcut.remove(SortCommand.getSortShortcut());
                SortCommand.setSortShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "setwelcome" :
                setShortcut.remove(SetWelcomeCommand.getSetWelcomeShortcut());
                SetWelcomeCommand.setSetWelcomeShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            case "show":
                setShortcut.remove(ShowCommand.getShowShortcut());
                ShowCommand.setShowShortcut(shortcutName);
                setShortcut.add(shortcutName);
            case "prioritize" :
                setShortcut.remove(PrioritizeCommand.getPrioritizeShortcut());
                PrioritizeCommand.setPrioritizeShortcut(shortcutName);
                setShortcut.add(shortcutName);
                break;
            default:
                throw new MeaninglessException();
        }
    }

    public static HashSet<String> getSetShortcut(){
        return setShortcut;
    }
    public static void initializedSetShortcut(){
        setShortcut.add("bye");
        setShortcut.add("list");
        setShortcut.add("done");
        setShortcut.add("edit");
        setShortcut.add("deadline");
        setShortcut.add("event");
        setShortcut.add("find");
        setShortcut.add("help");
        setShortcut.add("postpone");
        setShortcut.add("remind");
        setShortcut.add("reschedule");
        setShortcut.add("setwelcome");
        setShortcut.add("snooze");
        setShortcut.add("sort");
        setShortcut.add("todo");
        setShortcut.add("delete");
        setShortcut.add("show");
    }
}
