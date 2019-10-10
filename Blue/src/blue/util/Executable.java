package blue.util;

public interface Executable<RET, ARGS> {
	public RET[] execute(ARGS[] args);
}
