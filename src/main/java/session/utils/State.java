package session.utils;

import lombok.Getter;
import lombok.Setter;
import session.utils.Enum.Status;

@Getter
@Setter
public class State<T> {
    private Status status;
    private T data;

    // Constructor for SUCCESS or FAIL states without data
    public State(Status status) {
        this.status = status;
        this.data = null; // No data in this case
    }


    // Constructor for DATA state with data
    public State(Status status, T data) {
        this.status = status;
        this.data = data;
    }

    public State(T data) {

        this.data = data;
    }

    public State() {

    }

    // Factory methods for creating states
    public static <T> State<T> success() {
        return new State<>(Status.SUCCESS);
    }

    public static <T> State<T> error() {
        return new State<>(Status.ERROR);
    }

    // Getters
    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

}