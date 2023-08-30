package duke;

public class Ui {
    public void printGreeting() {
        System.out.println("Hello! I'm Botty!\nWhat can I do for you?");
    }

    public void printFarewell() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void printAllCommands() {
        System.out.println("OOPS!!! Invalid command. Try the following commands instead:");
        System.out.println("> todo <task>");
        System.out.println("> deadline <task> /by yyyy-mm-dd hh:mm");
        System.out.println("> event <task> /from yyyy-mm-dd hh:mm /to yyyy-mm-dd hh:mm");
        System.out.println("> list");
        System.out.println("> mark <task number>");
        System.out.println("> unmark <task number>");
        System.out.println("> delete <task number>");
        System.out.println("> find <keyword>");
        System.out.println("> bye");
    }
}
