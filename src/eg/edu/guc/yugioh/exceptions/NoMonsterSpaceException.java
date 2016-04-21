package eg.edu.guc.yugioh.exceptions;

public class NoMonsterSpaceException extends NoSpaceException {

	public NoMonsterSpaceException() {
		super("You can't add any more monsters.");
	}

}
