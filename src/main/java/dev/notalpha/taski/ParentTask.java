package dev.notalpha.taski;

import org.jetbrains.annotations.Nullable;

public interface ParentTask {
	 @Nullable Task getChild();
}
