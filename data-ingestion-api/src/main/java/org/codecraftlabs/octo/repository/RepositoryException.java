package org.codecraftlabs.octo.repository;

public class RepositoryException extends Exception {
    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable exception) {
        super(message, exception);
    }
}
