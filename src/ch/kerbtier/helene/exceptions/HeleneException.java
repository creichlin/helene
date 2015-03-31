package ch.kerbtier.helene.exceptions;

public class HeleneException extends RuntimeException {
  public HeleneException(String desc) {
    super(desc);
  }

  public HeleneException(Exception e) {
    super(e);
  }
}
