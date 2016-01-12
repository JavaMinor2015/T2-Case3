package com.infosupport.t2c3.esb;

import com.infosupport.t2c3.esb.model.EsbTask;
import com.infosupport.t2c3.esb.tasks.SupplySyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Created by Stoux on 12/01/2016.
 */
@Component
public class TaskHandler {

    @Autowired
    private AsyncTaskExecutor taskExecutor;

    /**
     * Creates and starts a Supply Sync Task.
     *
     * @return the task
     */
    public EsbTask startSyncTask() {
        EsbTask esbTask = new EsbTask(EsbTask.Type.SYNC, EsbTask.Status.STARTING);
        SupplySyncTask syncTask = new SupplySyncTask(esbTask);
        taskExecutor.submit(syncTask);
        return esbTask;
    }

}
