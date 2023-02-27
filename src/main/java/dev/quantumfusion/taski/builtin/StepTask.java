package dev.quantumfusion.taski.builtin;

import dev.quantumfusion.taski.ParentTask;
import dev.quantumfusion.taski.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * <h2>Counter</h2>
 * The StepTask is a counter. Which has a total count to hit where it will be 100%. <br>
 * The total amount cannot change in the middle of a counting session without also resetting the startValue.
 * However, you can reset it with a startValue.
 */
public class StepTask extends AbstractTask implements ParentTask {

	// Counter
	private int total;
	private int current;

	@Nullable
	private Task subTask;

	// Constructors
	public StepTask(String name) {
		super(name);
		this.total = 0;
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
		reset(0, 0);
	}

	public void finish() {
		this.current = this.total;
	}

	// Run
	public <T extends Task> void run(int amount, T subTask, Consumer<T> function) {
		this.subTask = subTask;
		function.accept(subTask);
		this.current += amount;
	}

	public <T extends Task> void run(T subTask, Consumer<T> function) {
		run(1, subTask, function);
	}

	public void run(int amount, Task subTask, Runnable function) {
		this.subTask = subTask;
		function.run();
		this.current += amount;
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

	// Do
	/**
	 * Iterates through a {@link List} with the consumer. <br>
	 * By resetting the current task
	 * @param list List to iterate through.
	 * @param consumer Value consumer.
	 * @param <E> Element Type
	 */
	public <E> void doForEach(List<E> list, Consumer<E> consumer) {
		this.reset(list.size());
		list.forEach(e -> {
			consumer.accept(e);
			this.next();
		});
	}

	/**
	 * Iterates through a {@link Map} with the BiConsumer. <br>
	 * By resetting the current task
	 * @param map List to iterate through.
	 * @param consumer Value consumer.
	 * @param <K> Key Type
	 * @param <V> Value Type
	 */
	public <K, V> void doForEach(Map<K, V> map, BiConsumer<K, V> consumer) {
		this.reset(map.size());
		map.forEach((k, v) -> {
			consumer.accept(k, v);
			this.next();
		});
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
		if (total == 0) {
			return false;
		}

		return current >= total;
	}

	@Override
	public float getProgress() {
		if (total == 0) {
			return 0;
		}

		float value = current / (float) total;

		if (subTask != null) {
			value += subTask.getProgress() / (float) total;
		}

		return value;
	}


	@Override
	public @Nullable String getProgressText() {
		if (total == 0) {
			return null;
		}

		return Math.min((current + 1), total) + " / " + total;
	}

	@Override
	public @Nullable Task getChild() {
		return this.subTask;
	}
}
