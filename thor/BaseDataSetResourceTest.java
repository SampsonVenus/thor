package com.smc.csp.dataset;

import com.smc.csp.config.BaseTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { BaseTestConfig.class })
public class BaseDataSetResourceTest {

    @Autowired
    protected AuthorizeDataSetResource dataSetAuthorizeResource;

    @Autowired
    protected UpdateDataSetResource dataSetUpdateResource;
    
    @Mock
    protected ApproveRequest mockedApproveRequest;

    @Before
    public void setUp(){
        mockedApproveRequest = mock(ApproveRequest.class);
    }

    @Test
    public void testSetUp(){

    }
}
