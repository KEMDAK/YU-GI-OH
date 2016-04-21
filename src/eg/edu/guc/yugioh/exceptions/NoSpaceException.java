package eg.edu.guc.yugioh.exceptions;

public class NoSpaceException extends RuntimeException {

	public NoSpaceException(String message) {
		super(message);
	}

	public NoSpaceException() {
		super();
	}

}
