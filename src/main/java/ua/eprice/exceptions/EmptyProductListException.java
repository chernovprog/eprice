package ua.eprice.exceptions;

public class EmptyProductListException extends RuntimeException {
    private String message;

    public EmptyProductListException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
