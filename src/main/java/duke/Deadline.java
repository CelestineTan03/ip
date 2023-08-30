package duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task of type Deadline
 */
public class Deadline extends Task {

    private final LocalDateTime by;
    private final DateTimeFormatter formatter;

    /**
     * A constructor for a task of type Deadline
     *
     * @param description the task details
     * @param by the deadline for the task
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
        this.formatter = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(formatter) + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + this.isDone + " | " + this.description + " | " + this.by.format(formatter);
    }

    /**
     * Return the type of the task.
     *
     * @return the type of the task
     */
    @Override
    public String getTaskType() {
        return "deadline";
    }
}
