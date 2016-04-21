package eg.edu.guc.yugioh.exceptions;

public class MissingFieldException extends UnexpectedFormatException {

	public MissingFieldException(String sourceFile, int sourceLine) {
		// super(sourceFile + " Line: " + sourceLine + " Missing field",
		// sourceLine);
		super(sourceFile, sourceLine);
	}

}
