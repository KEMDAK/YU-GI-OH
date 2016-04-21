package eg.edu.guc.yugioh.exceptions;

public class NoSpellSpaceException extends NoSpaceException {

	public NoSpellSpaceException() {
		super("You can't add any more spells.");
	}

}
