package dev.quantumfusion.taski.builtin;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;


/**
 * <h2>Poll Task %</h2>
 * The PercentagePollTask polls the {@code pollFunc} to get the progress. That is later converted to display as a percentage. <br><br>
 * If you are iterating and know the size and position of the iteration. {@link CountPollTask} might be better suited for this job.
 */
public class PercentagePollTask extends AbstractTask {
	private Supplier<Float> pollFunc;

	public PercentagePollTask(String name) {
		super(name);
		this.pollFunc = null;
	}

	public PercentagePollTask(String name, Supplier<Float> pollFunc) {
		super(name);
		this.pollFunc = pollFunc;
	}

	@Override
	public void reset() {
		this.pollFunc = null;
	}

	public void reset(Supplier<Float> pollFunc) {
		this.pollFunc = pollFunc;
	}

	@Override
	public void finish() {
		this.pollFunc = () -> 1f;
	}

	@Override
	public boolean done() {
		if (pollFunc == null) {
			return false;
		}


		return pollFunc.get() >= 1;
	}

	@Override
	public float getProgress() {
		if (pollFunc == null) {
			return 0;
		}

		return pollFunc.get();
	}

	@Override
	public @Nullable String getProgressText() {
		if (pollFunc == null) {
			return null;
		}
		return (int) (pollFunc.get() * 100) + "%";
	}
}
