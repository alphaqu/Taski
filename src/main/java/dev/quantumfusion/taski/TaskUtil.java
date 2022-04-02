package dev.quantumfusion.taski;


import dev.quantumfusion.taski.builtin.StepTask;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Contains util methods for common boilerplate.
 */
public final class TaskUtil {

	/**
	 * Iterates through a {@link List} with the consumer. <br>
	 * A {@link StepTask} is reset with the list size and then iterates through
	 * the entire list until its done while increasing the step.
	 * @param task Task with a set name.
	 * @param list List to iterate through.
	 * @param consumer Value consumer.
	 * @param <E> Element Type
	 */
	public static <E> void forEach(StepTask task, List<E> list, Consumer<E> consumer) {
		task.reset(list.size());
		list.forEach(e -> {
			consumer.accept(e);
			task.next();
		});
	}

	/**
	 * Iterates through a {@link Map} with the BiConsumer. <br>
	 * A {@link StepTask} is reset with the list size and then iterates through
	 * the entire map until its done while increasing the step.
	 * @param task Task with a set name.
	 * @param map List to iterate through.
	 * @param consumer Value consumer.
	 * @param <K> Key Type
	 * @param <V> Value Type
	 */
	public static <K, V> void forEach(StepTask task, Map<K, V> map, BiConsumer<K, V> consumer) {
		task.reset(map.size());
		map.forEach((k, v) -> {
			consumer.accept(k, v);
			task.next();
		});
	}

}
