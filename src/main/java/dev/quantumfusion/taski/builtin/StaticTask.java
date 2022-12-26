package dev.quantumfusion.taski.builtin;

import org.jetbrains.annotations.Nullable;

/**
 * <h2>Fixed Value</h2>
 * The StaticTask is immutable and has a static value on creation which cannot be changed.
 */
public class StaticTask extends AbstractTask {
	public static final StaticTask FULL = new StaticTask("", 1);
	public static final StaticTask EMPTY = new StaticTask("", 0);
	private final float value;

	public StaticTask(String name, float value) {
		super(name);
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	@Override
	public float getProgress() {
		return value;
	}

	@Override
	public void reset() {}

	@Override
	public void finish() {}

	@Override
	public boolean done() {
		return true;
	}

	@Override
	public @Nullable String getProgressText() {
		return null;
	}
}
