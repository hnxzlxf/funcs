package com.yaogd.imgprcs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.os.Environment;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;

import com.yaogd.lib.A;

/**
 * 数据库测试
 * @author yaoguangdong
 * 2014-10-27
 */
public class Test extends TestFrame {

	private String WORKING_PATH = Environment.getExternalStorageDirectory().toString() + "/yaogdimg" ;
	private int REQ_WIDTH = 480 ;
	private int REQ_HEIGHT = 320 ;
	private int MIN_SIZE = 100 ;
	private int MAX_SIZE = 300 ;
	
	private String RAW_FILE_01 = WORKING_PATH + "/raw1024x768_2M.jpeg";
	private String RAW_FILE_02 = WORKING_PATH + "/raw1024x768_2M.jpeg";
	private String RAW_FILE_03 = WORKING_PATH + "/raw1024x768_2M.jpeg";
	private String RAW_FILE_04 = WORKING_PATH + "/raw1024x768_2M.jpeg";
	
	private String DES_FILE_01 = WORKING_PATH + "/des1024x768_2M.jpeg";
	private String DES_FILE_02 = WORKING_PATH + "/des1024x768_2M.jpeg";
	private String DES_FILE_03 = WORKING_PATH + "/des1024x768_2M.jpeg";
	private String DES_FILE_04 = WORKING_PATH + "/des1024x768_2M.jpeg";
	
	@Override
    public void setUp() throws Exception {
        super.setUp();
        File file = new File(WORKING_PATH); 
        if( ! file.exists()){
        	file.mkdirs() ;
        }
        
    }
	
	/**
	 * 先运行一次，在SDcard中准备好原始图片
	 * 从assets中读取出来，放到yaogdimg中
	 */
	@MediumTest
	public void testStep1PrepareImage(){
		copyAssetsToSdcard(RAW_FILE_01, DES_FILE_01) ;
		copyAssetsToSdcard(RAW_FILE_02, DES_FILE_02) ;
		copyAssetsToSdcard(RAW_FILE_03, DES_FILE_03) ;
		copyAssetsToSdcard(RAW_FILE_04, DES_FILE_04) ;
	}
	
	/**
	 * 将assets中的文件拷贝到sdcard中
	 * @param srcFilePath
	 * @param desFilePath
	 */
	private void copyAssetsToSdcard(String srcFilePath, String desFilePath){
		InputStream is = null;
		try {
			is = getContext().getAssets().open(srcFilePath);
			File file = new File(desFilePath); 
			if( ! file.exists()){
				file.createNewFile() ;
			}
			FileOutputStream fos = new FileOutputStream(file);
			
			byte[] b = new byte[1024];
			while((is.read(b)) != -1){
				fos.write(b);
			}
			
			fos.flush() ;
			fos.close() ;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    @LargeTest
    public void testStep2Compress() {
    	try {
    		
			//测试压缩图片尺寸
    		Bitmap bmp = PicTools.compressImgResize(RAW_FILE_01, REQ_WIDTH, REQ_HEIGHT) ;
    		
			//测试压缩图片质量
    		PicTools.compressBmpToFile(bmp, MIN_SIZE, MAX_SIZE, DES_FILE_01 ) ;
    		
    		//测试压缩图片质量的新方法
    		Bitmap bmp2 = PicTools.compressBmp(bmp, MAX_SIZE) ;
    		
    		PicTools.dumpBmpToFile(bmp2, WORKING_PATH + "/des1024x768_2M_2.jpeg") ;
    		
		} catch (Exception e) {
			A.e( "", e) ;
			assertNotNull(null);
		}
    }
    
}
