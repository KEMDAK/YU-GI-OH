package eg.edu.guc.yugioh.exceptions;

public class MultipleMonsterAdditionException extends RuntimeException {

	public MultipleMonsterAdditionException() {
		super("You can't add more than one monster per turn");
	}

}
