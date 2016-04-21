package eg.edu.guc.yugioh.exceptions;

public class WrongPhaseException extends RuntimeException {

	public WrongPhaseException() {
		super("You can't do this action in this phase.");
	}

}
