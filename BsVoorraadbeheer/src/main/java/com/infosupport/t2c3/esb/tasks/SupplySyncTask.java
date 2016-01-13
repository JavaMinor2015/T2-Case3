package com.infosupport.t2c3.esb.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosupport.t2c3.domain.products.Supply;
import com.infosupport.t2c3.esb.model.EsbTask;
import com.infosupport.t2c3.repositories.SupplyRepository;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by Stoux on 12/01/2016.
 */
@Configurable
public class SupplySyncTask implements Runnable {

    public static final int MINIMUM_EXCUTION_TIME = 60000;

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private final EsbTask esbTask;

    @Autowired
    private SupplyRepository repository;

    /**
     * Create a new SupplySyncTask.
     *
     * @param esbTask The attached task
     */
    public SupplySyncTask(EsbTask esbTask) {
        this.esbTask = esbTask;
    }

    @Override
    public void run() {
        esbTask.setStatus(EsbTask.Status.IN_PROGRESS);
        long startTime = System.currentTimeMillis();

        try {
            List<Supply> inventory = repository.findAll();

            File outputFile = new File("esb/supply.json");
            File dirFile = outputFile.getParentFile();
            if (!dirFile.exists() && !dirFile.mkdirs()) {
                logger.fatal("Failed to create dirs for Sync task");
                esbTask.setStatus(EsbTask.Status.FAILED);
                return;
            }

            new ObjectMapper().writeValue(outputFile, inventory);
        } catch (IOException e) {
            logger.fatal("Failed to output Sync to file.", e);
            esbTask.setStatus(EsbTask.Status.FAILED);
            return;
        }

        //TODO: Remove this, as this is only here to lengthen te duration of this task.
        long tookTime = System.currentTimeMillis() - startTime;
        if (tookTime < MINIMUM_EXCUTION_TIME) {
            try {
                Thread.sleep(MINIMUM_EXCUTION_TIME - tookTime);
            } catch (InterruptedException e) {
                //Can be ignored for now
            }
        }

        esbTask.setStatus(EsbTask.Status.FINISHED);
    }
}
