package dev.quantumfusion.taski.builtin;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * <h2>Poll Task i/size</h2>
 * The CountPollTask polls the {@code countFunc} to get the current iteration number and stores the expected total iteration count.
 * You cannot change the total amount without restarting the task. However you can reset and then resupply with the same supplier. <br><br>
 * If you are iterating but don't know the position or size of the iteration. {@link PercentagePollTask} might be better suited for your job.
 */
public class CountPollTask extends AbstractTask {
	private Supplier<Integer> countFunc;
	private int total;

	public CountPollTask(String name) {
		super(name);
		this.total = 0;
		this.countFunc = null;
	}

	public CountPollTask(String name, int total, Supplier<Integer> countFunc) {
		super(name);
		this.total = total;
		this.countFunc = countFunc;
	}

	public int getTotal() {
		return total;
	}

	public Supplier<Integer> getCountFunc() {
		return countFunc;
	}

	public void reset(int total, Supplier<Integer> countFunc) {
		this.total = total;
		this.countFunc = countFunc;
	}

	@Override
	public void reset() {
		this.total = 0;
		this.countFunc = null;
	}

	@Override
	public void finish() {
		this.countFunc = () -> this.total;
	}

	@Override
	public boolean done() {
		if (countFunc == null) {
			return false;
		}

		return countFunc.get() >= total;
	}

	@Override
	public float getProgress() {
		if (countFunc == null) {
			return 0;
		}

		return countFunc.get();
	}

	@Override
	public @Nullable String getProgressText() {
		if (countFunc == null) {
			return null;
		}

		return Math.min((countFunc.get() + 1), total) + " / " + total;
	}
}
