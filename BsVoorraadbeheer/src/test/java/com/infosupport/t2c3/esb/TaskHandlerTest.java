package com.infosupport.t2c3.esb;

import com.infosupport.t2c3.esb.model.EsbTask;
import com.infosupport.t2c3.esb.tasks.SupplySyncTask;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Stoux on 21/01/2016.
 */
public class TaskHandlerTest {

    private AsyncTaskExecutor mockTaskExecutor;
    private AutowireCapableBeanFactory mockFactory;

    private TaskHandler taskHandler;

    @Before
    public void setUp() {
        //Build classes
        mockFactory = mock(AutowireCapableBeanFactory.class);
        mockTaskExecutor = mock(AsyncTaskExecutor.class);
        taskHandler = new TaskHandler();

        //Test TaskHandler default setup
        Object actualTaskExecutor = ReflectionTestUtils.getField(taskHandler, "taskExecutor");
        assertNotNull(actualTaskExecutor);

        //Overide with mocks
        ReflectionTestUtils.setField(taskHandler, "beanFactory", mockFactory);
        ReflectionTestUtils.setField(taskHandler, "taskExecutor", mockTaskExecutor);
    }

    @Test
    public void testStartSyncTask() throws Exception {
        //Execute
        EsbTask task = taskHandler.startSyncTask();

        //Assert
        assertEquals(EsbTask.Type.SYNC, task.getType());
        assertEquals(EsbTask.Status.STARTING, task.getStatus());

        verify(mockFactory, times(1)).autowireBean(any(SupplySyncTask.class));
        verify(mockTaskExecutor, times(1)).submit(any(SupplySyncTask.class));
    }

}