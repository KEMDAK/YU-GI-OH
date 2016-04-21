package eg.edu.guc.yugioh.exceptions;

public class UnknownSpellCardException extends UnexpectedFormatException {
	private String unknownSpell;

	public String getUnknownSpell() {
		return unknownSpell;
	}

	public void setUnknownSpell(String unknownSpell) {
		this.unknownSpell = unknownSpell;
	}

	public UnknownSpellCardException(String sourceFile, int sourceLine,
			String unknownSpell) {
		// super(sourceFile + " Line: " + sourceLine + " Unknown spell: " +
		// unknownSpell, sourceLine);
		super(sourceFile, sourceLine);
		this.unknownSpell = unknownSpell;
	}

}
