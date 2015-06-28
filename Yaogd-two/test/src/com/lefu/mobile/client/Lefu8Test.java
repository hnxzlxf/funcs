package com.lefu.mobile.client;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.widget.Button;

public class Lefu8Test extends AndroidTestCase {

	private Button button ;
	
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        button = null;
        
        Log.i("yaogd", "--setUp--");
        
    }
    /**
     * @SmallTest：测试代码中不与任何的文件系统或网络交互。
     * @MediumTest：测试代码中访问测试用例运行时所在的设备的文件系统。
     * @LargeTest：测试代码中访问外部的文件系统或网络。
     */
    @SmallTest
    public void testPreconditions() {
        assertNotNull(button);
        assertTrue("yaogd true", 1 < 2);
    	assertEquals("yaogd true", "expected", "actual");
    	
    	Log.i("yaogd", "--@SmallTest--");
    	
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        
        Log.i("yaogd", "--tearDown()--");
    }
}
