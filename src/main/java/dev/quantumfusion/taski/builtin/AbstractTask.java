package dev.quantumfusion.taski.builtin;

import dev.quantumfusion.taski.Task;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTask implements Task {
	private final String name;

	protected AbstractTask(String name) {
		this.name = name;
	}

	@Nullable
	public abstract String getNameSuffix();

	@Override
	public String getName() {
		String suffix = getNameSuffix();
		if (suffix == null) {
			return name;
		} else {
			return name + " " + suffix;
		}
	}

}
