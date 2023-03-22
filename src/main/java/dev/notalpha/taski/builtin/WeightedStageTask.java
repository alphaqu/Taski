package dev.notalpha.taski.builtin;

import dev.notalpha.taski.Task;
import dev.notalpha.taski.ParentTask;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;

public class WeightedStageTask extends AbstractTask implements ParentTask {
	private List<WeightedStage> stages = new ArrayList<>();
	private float totalWeight;

	public WeightedStageTask(String name) {
		super(name);
		reset();
	}

	public WeightedStageTask(String name, List<WeightedStage> stages) {
		super(name);
		reset(stages);
	}

	public List<WeightedStage> getStages() {
		return stages;
	}

	public float getTotalWeight() {
		return totalWeight;
	}

	public void reset(List<WeightedStage> stages) {
		this.stages = stages;
		this.totalWeight = 0;
		for (WeightedStage stage : stages) {
			this.totalWeight += stage.weight;
		}
	}

	@Override
	public void reset() {
		for (WeightedStage stage : this.stages) {
			if (stage.task != null)  {
				stage.task.reset();
			}
		}
	}

	@Override
	public void finish() {
		for (WeightedStage stage : stages) {
			if (stage.task != null) {
				stage.task.finish();
			} else {
				stage.task = StaticTask.FULL;
			}
		}
	}


	@Override
	public @Range(from = 0, to = 1) float getProgress() {
		List<WeightedStage> stages = this.stages;

		if (stages.isEmpty()) {
			return 0;
		}

		float total = 0;
		for (WeightedStage stage : stages) {
			if (stage.task == null) {
				break;
			} else if (stage.task.done()) {
				total += 1 * stage.weight;
			} else {
				total += stage.task.getProgress() * stage.weight;
				break;
			}
		}

		return total / totalWeight;
	}

	@Override
	public boolean done() {
		if (stages.isEmpty()) {
			return false;
		}

		for (WeightedStage stage : stages) {
			if (stage.task == null || !stage.task.done()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public @Nullable String getProgressText() {
		// Just in case another thread changes this.stages while this function executes.
		List<WeightedStage> stages = this.stages;

		if (stages.isEmpty()) {
			return null;
		}

		int done = 0;
		for (WeightedStage stage : stages) {
			if (stage.task != null && stage.task.done()) {
				done += 1;
			} else {
				break;
			}
		}


		return Math.min((done + 1), stages.size()) + " / " + stages.size();
	}

	@Override
	public @Nullable Task getChild() {
		for (WeightedStage stage : stages) {
			if (stage.task == null) {
				break;
			} else if (!stage.task.done()) {
				return stage.task;
			}
		}

		return null;
	}

	public static class WeightedStage {
		public final float weight;
		@Nullable
		public Task task;

		public WeightedStage(float weight, @Nullable Task task) {
			this.weight = weight;
			this.task = task;
		}
	}
}
