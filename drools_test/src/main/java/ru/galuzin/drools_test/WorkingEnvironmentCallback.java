package ru.galuzin.drools_test;

import org.drools.FactException;
import org.drools.WorkingMemory;

public interface WorkingEnvironmentCallback {

	void initEnvironment(WorkingMemory workingMemory) throws FactException;
}
