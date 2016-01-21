package com.infosupport.t2c3.esb;

import com.infosupport.t2c3.esb.model.EsbTask;
import com.infosupport.t2c3.esb.model.EsbToken;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Stoux on 21/01/2016.
 */
public class SupplyServiceTest {

    private TaskHandler mockedTaskHandler;
    private SupplyService supplyService;

    @Before
    public void setUp() {
        mockedTaskHandler = mock(TaskHandler.class);
        supplyService = new SupplyService();
        ReflectionTestUtils.setField(supplyService, "taskHandler", mockedTaskHandler);
    }

    @Test
    public void testRequestSync() throws Exception {
        //Setup
        EsbTask task = new EsbTask(EsbTask.Type.SYNC, EsbTask.Status.IN_PROGRESS);
        when(mockedTaskHandler.startSyncTask()).thenReturn(task);

        //Execute
        EsbToken token = supplyService.requestSync();

        //Assert
        assertEquals(task, token.getTask());

        //After check
        checkForResponse(token);
    }

    @Test
    public void testExistingRequestSync() throws Exception {
        //Setup
        EsbTask task = new EsbTask(EsbTask.Type.SYNC, EsbTask.Status.IN_PROGRESS);
        when(mockedTaskHandler.startSyncTask()).thenReturn(task);

        //Execute
        EsbToken firstToken = supplyService.requestSync();
        EsbToken secondToken = supplyService.requestSync();

        //Assert
        assertEquals(task, firstToken.getTask());
        assertEquals(task, secondToken.getTask());
        assertNotEquals(firstToken.getToken(), secondToken.getToken());
    }

    @Test
    public void testOutdatedRequestSync() {
        //Setup
        EsbTask firstTask = new EsbTask(EsbTask.Type.SYNC, EsbTask.Status.IN_PROGRESS);
        EsbTask secondTask = new EsbTask(EsbTask.Type.SYNC, EsbTask.Status.IN_PROGRESS);
        when(mockedTaskHandler.startSyncTask()).thenReturn(firstTask, secondTask);

        //Execute first time
        supplyService.requestSync();

        //Modify first task
        firstTask.setStatus(EsbTask.Status.CANCELLED);

        //Execute second time
        EsbToken secondToken = supplyService.requestSync();

        //Assert
        assertEquals(EsbTask.Status.OUTDATED, firstTask.getStatus());
        assertNotEquals(firstTask, secondToken.getTask());
        assertEquals(secondTask, secondToken.getTask());
        verify(mockedTaskHandler, times(2)).startSyncTask();
    }


    @Test
    public void testNonExistingToken() throws Exception {
        //Execute
        ResponseEntity entity = supplyService.pollForSyncStatus("WELP");

        //Assert
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
        assertNull(entity.getBody());
    }

    private void checkForResponse(EsbToken token) {
        //Execute
        ResponseEntity entity = supplyService.pollForSyncStatus(token.getToken());

        //Assert
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(token.getTask(), entity.getBody());
    }

}
