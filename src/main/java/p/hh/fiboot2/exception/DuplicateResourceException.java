package p.hh.fiboot2.exception;


public class DuplicateResourceException extends Exception {
    private String resource;
    private String duplicateValue;

    public DuplicateResourceException(String resource, String duplicateValue) {
        super(resource + " [" + duplicateValue + "] already exsit.");
    }
}
