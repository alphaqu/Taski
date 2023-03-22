package dev.notalpha.taski;

import org.jetbrains.annotations.Range;

/**
 * <h2>Taski Java Library</h2>
 * This is the interface for your different tasks.
 * You can use one of the builtin task types standalone, extend one of them for integrated behavior or implement your own task for fully custom use-cases.
 */
public interface Task {
	/**
	 * @return The current task progress.
	 */
	@Range(from = 0, to = 1)
	float getProgress();

	/**
	 * Gets the fancy name for the current task's status.
	 * @return Text
	 */
	String getName();

	/**
	 * Resets the task to default values.
	 */
	void reset();

	/**
	 * Force finishes the task to its 100% values.
	 */
	void finish();

	/**
	 * Checks if the task is done with its progress and expects no further change in progress value.
	 * @return status.
	 */
	boolean done();
}
