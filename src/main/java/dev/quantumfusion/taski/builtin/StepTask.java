package dev.quantumfusion.taski.builtin;

import dev.quantumfusion.taski.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * <h2>Counter</h2>
 * The StepTask is a counter. Which has a total count to hit where it will be 100%. <br>
 * The total amount cannot change in the middle of a counting session without also resetting the startValue.
 * However, you can reset it with a startValue.
 */
public class StepTask extends AbstractTask {
	public static final int UNKNOWN_TOTAL = -1;

	// Counter
	private int total;
	private int current;

	@Nullable
	private Task subTask;

	// Constructors
	public StepTask(String name) {
		super(name);
		this.total = UNKNOWN_TOTAL;
	}

	public StepTask(String name, int total) {
		super(name);
		this.total = total;
	}

	public StepTask(String name, int total, int startValue) {
		super(name);
		this.total = total;
		this.current = startValue;
	}

	// Setters
	public void setSubTask(@NotNull Task subTask) {
		this.subTask = subTask;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	// Getters

	public @Nullable Task getSubTask() {
		return subTask;
	}

	public int getCurrent() {
		return current;
	}

	public int getTotal() {
		return total;
	}


	// Util
	public void reset(int total, int startValue) {
		this.total = total;
		this.current = startValue;
	}

	public void reset(int total) {
		reset(total, 0);
	}

	public void reset() {
		reset(UNKNOWN_TOTAL, 0);
	}

	public void finish() {
		this.current = this.total;
	}

	// Run
	public <T extends Task> void run(int amount, T subTask, Consumer<T> function) {
		function.accept(subTask);
		this.current += amount;
		this.subTask = subTask;
	}

	public <T extends Task> void run(T subTask, Consumer<T> function) {
		run(1, subTask, function);
	}

	public void run(int amount, Task subTask, Runnable function) {
		function.run();
		this.current += amount;
		this.subTask = subTask;
	}

	public void run(Task subTask, Runnable function) {
		run(1, subTask, function);
	}

	public void run(int amount, Runnable function) {
		run(amount, null, function);
	}

	public void run(Runnable function) {
		run(1, null, function);
	}

	// Next
	public void next(int amount, Task subTask) {
		this.current += amount;
		this.subTask = subTask;
	}

	public void next(Task subTask) {
		next(1, subTask);
	}

	public void next(int amount) {
		next(amount, null);
	}

	public void next() {
		next(1, null);
	}

	@Override
	public boolean done() {
		if (total == UNKNOWN_TOTAL) {
			return false;
		}

		return current >= total;
	}

	@Override
	public float getProgress() {
		if (total == UNKNOWN_TOTAL) {
			return 0;
		}

		float value = current / (float) total;

		if (subTask != null) {
			value += subTask.getProgress() / (float) total;
		}

		return value;
	}


	@Override
	public @Nullable String getNameSuffix() {
		if (total == UNKNOWN_TOTAL) {
			return null;
		}

		return Math.min((current + 1), total) + " / " + total;
	}
}
