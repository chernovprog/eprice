package ua.eprice.exceptions;

public class JsoupDocumentException extends RuntimeException {

    private String message;

    public JsoupDocumentException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
