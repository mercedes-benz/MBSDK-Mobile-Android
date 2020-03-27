package com.daimler.mbingresskit;

import com.daimler.mbnetworkkit.task.BaseTask;
import com.daimler.mbnetworkkit.task.TaskObject;
import com.daimler.mbnetworkkit.task.TaskState;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaskTest {

    TestResult result;

    @Before
    public void setUp() {
        result = new TestResult();
    }

    @Test
    public void taskCompleted() throws Exception {
        TaskObject<String, String> task = new TaskObject<>();
        task.futureTask()
                .onComplete(new Function1<String, Unit>() {
                    @Override
                    public Unit invoke(String s) {
                        result.onComplete(s);
                        return Unit.INSTANCE;
                    }
                });

        task.complete("C");

        assertTrue(task.isCompleted());
        assertEquals("C", result.completedResult);
    }

    @Test
    public void taskFailed() throws Exception {
        TaskObject<String, String> task = new TaskObject<>();
        task.futureTask()
                .onFailure(new Function1<String, Unit>() {
                    @Override
                    public Unit invoke(String s) {
                        result.onFail(s);
                        return Unit.INSTANCE;
                    }
                });

        task.fail("F");

        assertTrue(task.isFailed());
        assertEquals("F", result.failureResult);
    }

    @Test
    public void taskCompletedAndAlwaysCalled() {
        TaskObject<String, String> task = new TaskObject<>();
        task.futureTask()
                .onAlways(new Function3<TaskState, String, String, Unit>() {
                    @Override
                    public Unit invoke(TaskState taskState, String s, String s2) {
                        result.onAllways(taskState, s, s2);
                        return Unit.INSTANCE;
                    }
                });
        task.complete("C");

        assertTrue(task.isCompleted());
        assertEquals("C", result.completedResult);
        assertEquals(TaskState.COMPLETED, result.taskState);
        assertEquals("null", result.failureResult);
    }

    @Test
    public void taskFailedAndAlwaysCalled() {
        TaskObject<String, String> task = new TaskObject<>();
        task.futureTask()
                .onAlways(new Function3<TaskState, String, String, Unit>() {
                    @Override
                    public Unit invoke(TaskState taskState, String s, String s2) {
                        result.onAllways(taskState, s, s2);
                        return Unit.INSTANCE;
                    }
                });
        task.fail("F");

        assertTrue(task.isFailed());
        assertEquals("F", result.failureResult);
        assertEquals(TaskState.FAILED, result.taskState);
        assertEquals("null", result.completedResult);
    }

    @Test(expected = BaseTask.AlreadyFinishedException.class)
    public void taskCompletedTwice() {
        TaskObject<String, String> task = new TaskObject<>();
        task.complete("");
        task.complete("");
    }

    @Test(expected = BaseTask.AlreadyFinishedException.class)
    public void taskFailedTwice() {
        TaskObject<String, String> task = new TaskObject<>();
        task.fail("");
        task.fail("");
    }

    class TestResult {

        String completedResult = "";

        String failureResult = "";

        TaskState taskState = TaskState.PENDING;

        public void onComplete(String result) {
            completedResult = result;
        }

        public void onAllways(@NotNull TaskState state, @Nullable String completeResult, @Nullable String failureResult) {
            taskState = state;
            this.completedResult = this.completedResult + completeResult;
            this.failureResult = this.failureResult + failureResult;
        }

        public void onFail(String result) {
            failureResult = result;
        }
    }

}
