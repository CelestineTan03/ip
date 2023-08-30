import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task of type Event
 */
public class Event extends Task {

    private final LocalDateTime from;
    private final LocalDateTime to;
    private final DateTimeFormatter formatter;

    /**
     * A constructor for task of type Event
     *
     * @param description the task details
     * @param from the start date/time for the task
     * @param to the end date/time for the task
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
        this.formatter = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(formatter) + " to: " +
                this.to.format(formatter) + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + this.isDone + " | " + this.description + " | " + this.from.format(formatter) +
                " | " + this.to.format(formatter);
    }
}
