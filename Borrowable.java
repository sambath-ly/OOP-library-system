package model;

public interface Borrowable {
    boolean borrowBook();
    boolean returnBook();
    boolean isAvailable();
}