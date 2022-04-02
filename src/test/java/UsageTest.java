import dev.quantumfusion.taski.TaskUtil;
import dev.quantumfusion.taski.builtin.StageTask;
import dev.quantumfusion.taski.builtin.StepTask;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UsageTest {


	@Test
	void test() {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);


		StepTask main = new StepTask("Loading", 5);
		main.run(new StepTask("Bootstrap", 3), stepTask -> {
			stepTask.run(() -> {/* computePiForNoReason(); */});
			stepTask.run(() -> {/* solveImpossibleMathProblem(); */});
			stepTask.run(() -> {/* froge(); */});
		});

		main.run(new StageTask("Models"), stageTask -> {
			StepTask sprites = new StepTask("Sprites", 3);
			StepTask models = new StepTask("Models");
			stageTask.reset(sprites, models);
			sprites.finish();

			TaskUtil.forEach(models, list, model -> {
			});
		});


		main.run(new StepTask("Add Numbers"), stepTask -> {
			TaskUtil.forEach(stepTask, list, integer -> {
			});
		});
	}
}
