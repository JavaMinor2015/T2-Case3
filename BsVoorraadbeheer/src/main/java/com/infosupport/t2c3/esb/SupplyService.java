package com.infosupport.t2c3.esb;

import com.infosupport.t2c3.esb.model.EsbTask;
import com.infosupport.t2c3.esb.model.EsbToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Stoux on 12/01/2016.
 */
@RestController
@RequestMapping(path = "/supply", produces = "application/json")
public class SupplyService {

    @Autowired
    private TaskHandler taskHandler;

    private final ConcurrentMap<String, EsbToken> uuidToToken;
    private final List<EsbTask> currentTasks;

    /**
     * Construct a new SupplyService.
     */
    public SupplyService() {
        uuidToToken = new ConcurrentHashMap<>();
        currentTasks = new ArrayList<>();
    }

    /**
     * Outside party can request a Supply Sync via an ESB.
     *
     * @return Their token
     */
    @RequestMapping(path = "/sync")
    public EsbToken requestSync() {
        //Generate and store token
        EsbToken token = EsbToken.generateToken();
        uuidToToken.put(token.getToken(),token);

        //See if the task is already running
        Optional<EsbTask> task = findTask(EsbTask.Type.SYNC);
        if (task.isPresent()) {
            boolean isRemoved = removeIfOutdated(task.get());
            if (!isRemoved) {
                token.setTask(task);
                return token;
            }
        }

        //Create a new task
        EsbTask esbTask = taskHandler.startSyncTask();
        token.setTask(Optional.of(esbTask));
        return token;
    }

    /**
     * Get the SyncStatus on a certain Token.
     *
     * @param tokenKey The token
     * @return Either a TokenNotFound or the task's status
     */
    @RequestMapping(path = "/sync/status/{token}")
    public ResponseEntity pollForSyncStatus(@PathVariable("token") String tokenKey) {
        if (!uuidToToken.containsKey(tokenKey)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        EsbToken foundToken = uuidToToken.get(tokenKey);
        return new ResponseEntity<>(foundToken.getTask().get(), HttpStatus.OK);
    }

    private Optional<EsbTask> findTask(EsbTask.Type type) {
        synchronized (currentTasks) {
            for (EsbTask currentTask : currentTasks) {
                if (currentTask.getType() == type) {
                    return Optional.of(currentTask);
                }
            }
        }
        return Optional.empty();
    }

    private boolean removeIfOutdated(EsbTask task) {
        switch (task.getStatus()) {
            case FAILED:
            case FINISHED:
            case OUTDATED:
            case CANCELLED:
                task.setStatus(EsbTask.Status.OUTDATED);
                removeFromList(task);
                return true;

            default:
                return false;
        }
    }

    private void removeFromList(EsbTask task) {
        synchronized (currentTasks) {
            currentTasks.remove(task);
        }
    }


}
