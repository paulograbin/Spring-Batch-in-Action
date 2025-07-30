package com.manning.sbia.ch08.skip;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class ExceptionSkipPolicy implements SkipPolicy {

    private Class<? extends Exception> exceptionClassToSkip;

    public ExceptionSkipPolicy(Class<? extends Exception> exceptionClassToSkip) {
        super();
        this.exceptionClassToSkip = exceptionClassToSkip;
    }

    @Override
    public boolean shouldSkip(Throwable t, int skipCount) throws SkipLimitExceededException {
        return exceptionClassToSkip.isAssignableFrom(t.getClass());
    }

}
