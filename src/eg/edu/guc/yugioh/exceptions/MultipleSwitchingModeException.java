package eg.edu.guc.yugioh.exceptions;

public class MultipleSwitchingModeException extends RuntimeException {

	public MultipleSwitchingModeException() {
		super("You can only switch the monster's mode once per turn.");
	}

}
