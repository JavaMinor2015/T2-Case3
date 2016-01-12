package com.infosupport.t2c3.esb.tasks;

import com.infosupport.t2c3.esb.model.EsbTask;
import lombok.AllArgsConstructor;

/**
 * Created by Stoux on 12/01/2016.
 */
@AllArgsConstructor
public class SupplySyncTask implements Runnable {

    private final EsbTask esbTask;

    @Override
    public void run() {
        esbTask.setStatus(EsbTask.Status.IN_PROGRESS);

        //TODO: Execute Task
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            //Can be ignored for now
        }

        esbTask.setStatus(EsbTask.Status.FINISHED);
    }
}
