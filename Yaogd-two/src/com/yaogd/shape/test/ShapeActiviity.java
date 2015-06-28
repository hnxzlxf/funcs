package com.yaogd.shape.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.yaogd.R;
/**
 * 
 * @author yaoguangdong
 * 2014-4-29
 */
public class ShapeActiviity extends Activity {

    private Button btn1;  
    
    @Override  
    public void onCreate(Bundle savedInstanceState){  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.shape_test);  
        
        btn1 = (Button)findViewById(R.id.shape_test_btn);
        
        
    }

}
