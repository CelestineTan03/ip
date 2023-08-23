import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A chatbot that provides a to-do list function
 *
 * @author Celestine
 */
public class Duke {
    protected List<Task> tasks;
    protected int numberOfTasks;

    /**
     * A constructor for the chatbot
     */
    public Duke() {
        tasks = new ArrayList<>();
        numberOfTasks = 0;
    }

    /**
     * Returns a success message when a task has been successfully added.
     *
     * @return the success message
     */
    public String printAddTaskSuccessMessage() {
        StringBuilder message = new StringBuilder();
        message.append("Got it. I've added this task:\n");
        message.append(" ");
        message.append(this.tasks.get(this.numberOfTasks - 1).toString());
        message.append("\n");
        message.append("Now you have ");
        message.append(this.numberOfTasks);
        message.append(" task(s) in the list.");
        return message.toString();
    }

    /**
     * Returns a success message when a task has been successfully deleted.
     *
     * @return the success message
     */
    public String printRemoveTaskSuccessMessage(int index) {
        StringBuilder message = new StringBuilder();
        message.append("Noted. I've removed this task:\n");
        message.append(" ");
        message.append(this.tasks.get(index).toString());
        message.append("\n");
        message.append("Now you have ");
        message.append(this.numberOfTasks);
        message.append(" task(s) in the list.");
        return message.toString();
    }

    /**
     * Takes in the task details and add the task to the list.
     *
     * @param task the task description
     * @param typeOfTask the type of the task to be added
     * @throws DukeException if invalid or incorrect command
     */
    public void addTask(String[] task, String typeOfTask) throws DukeException {
        switch (typeOfTask) {
            case "event":
                if (task.length == 1 || task[1].isBlank()) {
                    throw new DukeException(ExceptionTypes.INCOMPLETECOMMANDEVENT);
                } else if (task[1].contains("/from") && task[1].contains("/to")) {
                    String[] description = task[1].split(" /from ");
                    if (description.length <= 1) {
                        throw new DukeException(ExceptionTypes.INCOMPLETECOMMANDEVENT);
                    }
                    String[] interval = description[1].split(" /to ");
                    if (interval.length <= 1) {
                        throw new DukeException(ExceptionTypes.INVALIDCOMMANDDEADLINE);
                    }
                    this.tasks.add(new Event(description[0], interval[0], interval[1]));
                    this.numberOfTasks++;
                    System.out.println(printAddTaskSuccessMessage());
                    break;
                } else {
                    throw new DukeException(ExceptionTypes.INVALIDCOMMANDEVENT);
                }
            case "todo":
                if (task.length == 1 || task[1].isBlank()) {
                    throw new DukeException(ExceptionTypes.INCOMPLETECOMMANDTODO);
                }
                this.tasks.add(new Todo(task[1]));
                this.numberOfTasks++;
                System.out.println(printAddTaskSuccessMessage());
                break;
            case "deadline":
                if (task.length == 1 || task[1].isBlank()) {
                    throw new DukeException(ExceptionTypes.INCOMPLETECOMMANDDEADLINE);
                } else if (task[1].contains("/by")) {
                    String[] description = task[1].split(" /by ");
                    if (description.length <= 1) {
                        throw new DukeException(ExceptionTypes.INVALIDCOMMANDDEADLINE);
                    }
                    this.tasks.add(new Deadline(description[0], description[1]));
                    this.numberOfTasks++;
                    System.out.println(printAddTaskSuccessMessage());
                    break;
                } else {
                    throw new DukeException(ExceptionTypes.INVALIDCOMMANDDEADLINE);
                }
            default:
                throw new DukeException(ExceptionTypes.INVALIDCOMMAND);
        }
    }

    /**
     * Takes in the task command to mark or unmark
     * a task and mark the task as done or mark a task as
     * not done
     *
     * @param words the mark or unmark command
     * @param action to mark or unmark the task
     * @throws DukeException if invalid or incorrect command
     */
    public void markTask(String[] words, String action) throws DukeException {
        switch(action) {
            case "mark":
                if (this.numberOfTasks == 0) {
                    throw new DukeException(ExceptionTypes.MARKEMPTYLIST);
                } else if (words.length == 1 || words[1].isBlank()) {
                    throw new DukeException(ExceptionTypes.INCOMPLETETASKNUMBER);
                }
                int markIndex = Integer.parseInt(words[1]);
                if (markIndex > this.numberOfTasks || markIndex <= 0) {
                    throw new DukeException(ExceptionTypes.INVALIDTASKNUMBER);
                }
                System.out.println("Nice! I've marked this task as done:");
                Task markTask = this.tasks.get(markIndex - 1);
                markTask.markAsDone();
                System.out.println(markTask.toString());
                break;
            case "unmark":
                if (this.numberOfTasks == 0) {
                    throw new DukeException(ExceptionTypes.UNMARKEMPTYLIST);
                } else if (words.length == 1 || words[1].isBlank()) {
                    throw new DukeException(ExceptionTypes.INCOMPLETETASKNUMBER);
                }
                int unmarkIndex = Integer.parseInt(words[1]);
                if (unmarkIndex > this.numberOfTasks || unmarkIndex <= 0) {
                    throw new DukeException(ExceptionTypes.INVALIDTASKNUMBER);
                }
                System.out.println("OK, I've marked this task as not done yet:");
                Task unmarkTask = this.tasks.get(unmarkIndex - 1);
                unmarkTask.markAsNotDone();
                System.out.println(unmarkTask.toString());
                break;
            default:
                throw new DukeException(ExceptionTypes.INVALIDCOMMAND);
        }
    }

    /**
     * Takes in the command to delete a task
     * and remove the task from the list.
     *
     * @param words the delete command
     * @throws DukeException if invalid or incorrect command
     */
    public void removeTask(String[] words) throws DukeException {
        if (this.numberOfTasks == 0) {
            throw new DukeException(ExceptionTypes.DELETEEMPTYLIST);
        } else if (words.length == 1 || words[1].isBlank()) {
            throw new DukeException(ExceptionTypes.INCOMPLETETASKNUMBER);
        }
        int deleteIndex = Integer.parseInt(words[1]);
        if (deleteIndex > this.numberOfTasks || deleteIndex <= 0) {
            throw new DukeException(ExceptionTypes.INVALIDTASKNUMBER);
        }
        numberOfTasks--;
        System.out.println(printRemoveTaskSuccessMessage(deleteIndex - 1));
        this.tasks.remove(deleteIndex - 1);
    }

    public static void main(String[] args) {
        Duke chatbot = new Duke();

        String greetings = "Hello! I'm Botty!\nWhat can I do for you?";
        String farewell = "Bye. Hope to see you again soon!";
        System.out.println(greetings);

        Scanner sc = new Scanner(System.in);
        // Only exit when user types the command bye
        label:
        while (true) {
            if (sc.hasNextLine()) {
                String command = sc.nextLine();
                // Split string into first word and remaining words
                String[] words = command.split(" ", 2);
                String firstWord = words[0];

                switch (firstWord) {
                    case "bye":
                        System.out.println(farewell);
                        sc.close();
                        break label;
                    // Display the stored commands
                    case "list":
                        if (chatbot.numberOfTasks == 0) {
                            System.out.println("You do not have any tasks in the list.");
                            break;
                        }
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < chatbot.numberOfTasks; i++) {
                            System.out.println(i + 1 + "." + chatbot.tasks.get(i).toString());
                        }
                        break;
                    // Add task
                    case "todo":
                        try {
                            chatbot.addTask(words, "todo");
                        } catch (DukeException exception) {
                            System.out.println(exception.getMessage());
                        }
                        break;
                    case "deadline":
                        try {
                            chatbot.addTask(words, "deadline");
                        } catch (DukeException exception) {
                            System.out.println(exception.getMessage());
                        }
                        break;
                    case "event":
                        try {
                            chatbot.addTask(words, "event");
                        } catch (DukeException exception) {
                            System.out.println(exception.getMessage());
                        }
                        break;
                    // Mark task as done
                    case "mark":
                        try {
                            chatbot.markTask(words, "mark");
                        } catch (DukeException exception) {
                            System.out.println(exception.getMessage());
                        }
                        break;
                    // Mark task as not done
                    case "unmark":
                        try {
                            chatbot.markTask(words, "unmark");
                        } catch (DukeException exception) {
                            System.out.println(exception.getMessage());
                        }
                        break;
                    // remove task
                    case "delete":
                        try {
                            chatbot.removeTask(words);
                        } catch (DukeException exception) {
                            System.out.println(exception.getMessage());
                        }

                        break;
                    // Invalid command
                    default:
                        System.out.println("OOPS!!! Invalid command. Try the following commands instead:");
                        System.out.println("> todo <task>");
                        System.out.println("> deadline <task> /by <deadline>");
                        System.out.println("> event <task> /from <start date/time> /to <end date/time>");
                        System.out.println("> list");
                        System.out.println("> mark <task number>");
                        System.out.println("> unmark <task number>");
                        System.out.println("> delete <task number>");
                        System.out.println("> bye");
                        break;
                }
            }
        }
    }
}
