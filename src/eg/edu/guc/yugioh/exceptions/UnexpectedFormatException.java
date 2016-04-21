package eg.edu.guc.yugioh.exceptions;

public class UnexpectedFormatException extends Exception {
	private String sourceFile;
	private int sourceLine;

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public int getSourceLine() {
		return sourceLine;
	}

	public void setSourceLine(int sourceLine) {
		this.sourceLine = sourceLine;
	}

	public UnexpectedFormatException(String m) {
		super(m);
	}

	public UnexpectedFormatException(String sourceFile, int sourceLine) {
		super("Unexpected error occured.");
		this.sourceFile = sourceFile;
		this.sourceLine = sourceLine;
	}

}
