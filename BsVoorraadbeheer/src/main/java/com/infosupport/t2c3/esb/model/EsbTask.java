package com.infosupport.t2c3.esb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Stoux on 12/01/2016.
 */
@Getter
@AllArgsConstructor
public class EsbTask {

    private Type type;
    @Setter
    private Status status;

    /**
     * Type of task.
     */
    public enum Type {
        SYNC;
    }

    /**
     * Status of a task.
     */
    public enum Status {
        STARTING,
        IN_PROGRESS,
        FINISHED,
        CANCELLED,
        FAILED,
        OUTDATED;
    }

}
