package eg.edu.guc.yugioh.exceptions;

public class MonsterMultipleAttackException extends RuntimeException {

	public MonsterMultipleAttackException() {
		super("You can't attack more than once per turn with the same monster.");
	}

}
