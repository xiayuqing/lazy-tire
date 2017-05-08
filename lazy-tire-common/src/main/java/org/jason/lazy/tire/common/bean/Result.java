package org.jason.lazy.tire.common.bean;

import java.io.Serializable;

/**
 * Created by Jason.Xia on 17/5/8.
 */
public class Result implements Serializable {
    private Action action;
    private String msg;
    private Exception e;
    private TaskStatistics taskStatistics;

    private Result() {
    }

    private Result(Action action, String msg) {
        this.action = action;
        this.msg = msg;
    }

    private Result(Action action, Exception e) {
        this.action = action;
        this.msg = e.getMessage();
        this.e = e;
    }

    public static Result failure(String msg) {
        return new Result(Action.FAILED, msg);
    }

    public static Result success() {
        return new Result(Action.SUCCESS, "success");
    }

    public static Result exception(Exception e) {
        return new Result(Action.EXCEPTION, e);
    }


    public Result setTaskStatistics(TaskStatistics taskStatistics) {
        this.taskStatistics = taskStatistics;
        return this;
    }

    public TaskStatistics getTaskStatistics() {
        return taskStatistics;
    }

    public Action getAction() {
        return action;
    }

    public String getMsg() {
        return msg;
    }

    public Exception getE() {
        return e;
    }

    public enum Action {
        SUCCESS,
        FAILED,
        EXCEPTION
    }
}
