package dev.quantumfusion.taski.builtin;

import dev.quantumfusion.taski.Task;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <h2>Task Group</h2>
 * The StageTask holds multiple tasks.
 * It yields the same as {@link StepTask} but implementations can use the available information for upcoming tasks.
 */
public class StageTask extends AbstractTask {
	private List<Task> stages = new ArrayList<>();

	public StageTask(String name) {
		super(name);
	}

	public StageTask(String name, List<Task> stages) {
		super(name);
		reset(stages);
	}

	public StageTask(String name, Task... stages) {
		super(name);
		reset(stages);
	}

	public List<Task> getStages() {
		return stages;
	}

	public void reset(List<Task> stages) {
		this.stages = stages;
	}

	public void reset(Task... stages) {
		List<Task> out = new ArrayList<>(stages.length);
		Collections.addAll(out, stages);
		this.stages = out;
	}

	@Override
	public void reset() {
		for (Task stage : stages) {
			stage.reset();
		}
	}

	@Override
	public void finish() {
		for (Task stage : stages) {
			stage.finish();
		}
	}

	@Override
	public boolean done() {
		if (stages.isEmpty()) {
			return false;
		}

		for (Task stage : stages) {
			if (!stage.done()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public float getProgress() {
		// Just in case another thread changes this.stages while this function executes.
		List<Task> stages = this.stages;

		if (stages.isEmpty()) {
			return 0;
		}

		float total = 0;
		for (Task stage : stages) {
			if (stage.done()) {
				total += 1;
			} else {
				total += stage.getProgress();
				break;
			}
		}

		return total / stages.size();
	}

	@Override
	public @Nullable String getNameSuffix() {
		// Just in case another thread changes this.stages while this function executes.
		List<Task> stages = this.stages;

		if (stages.isEmpty()) {
			return null;
		}

		int done = 0;
		for (Task stage : stages) {
			if (stage.done()) {
				done += 1;
			} else {
				break;
			}
		}


		return  Math.min((done + 1), stages.size()) + " / " + stages.size();
	}
}
