package eg.edu.guc.yugioh.exceptions;

public class DefenseMonsterAttackException extends RuntimeException {

	public DefenseMonsterAttackException() {
		super("You can't attack in defense mode.");
	}

}
