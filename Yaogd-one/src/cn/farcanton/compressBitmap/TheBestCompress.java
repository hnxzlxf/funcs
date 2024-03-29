package cn.farcanton.compressBitmap;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * 取自“KJFrameForAndroid框架学习----高效加载Bitmap ”
 * http://blog.csdn.net/kymjs/article/details/38070369
 * @author yaoguangdong
 * 2014-9-3
 */
public class TheBestCompress {
	/**
     * 图片压缩处理（使用Options的方法）
     * 
     * @使用方法 首先你要将Options的inJustDecodeBounds属性设置为true，BitmapFactory.decode一次图片。
     *       然后将Options连同期望的宽度和高度一起传递到到本方法中。
     *       之后再使用本方法的返回值做参数调用BitmapFactory.decode创建图片。
     * 
     * @explain BitmapFactory创建bitmap会尝试为已经构建的bitmap分配内存
     *          ，这时就会很容易导致OOM出现。为此每一种创建方法都提供了一个可选的Options参数
     *          ，将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存
     *          ，返回值也不再是一个Bitmap对象， 而是null。虽然Bitmap是null了，但是Options的outWidth、
     *          outHeight和outMimeType属性都会被赋值。
     * @param reqWidth
     *            目标宽度
     * @param reqHeight
     *            目标高度
     */
    public static BitmapFactory.Options calculateInSampleSize(
            final BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        // 设置压缩比例
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return options;
    }
    
    /**
     * 图片压缩方法：（使用compress的方法）
     * 
     * @explain 如果bitmap本身的大小小于maxSize，则不作处理
     * @param bitmap  要压缩的图片
     * @param maxSize 压缩后的大小，单位kb
     */
    public static Bitmap imageZoom(Bitmap bitmap, double maxSize) {
        // 将bitmap放至数组中，意在获得bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 格式、质量、输出流
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        // 将字节换成KB
        double mid = b.length / 1024;
        // 获取bitmap大小 是允许最大大小的多少倍
        double i = mid / maxSize;
        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
        if (i > 1) {
            // 缩放图片 此处用到平方根 将宽度和高度压缩掉对应的平方根倍
            // （保持宽高不变，缩放后也达到了最大占用空间的大小）
            bitmap = scale(bitmap, bitmap.getWidth() / Math.sqrt(i),
                    bitmap.getHeight() / Math.sqrt(i));
        }
        return bitmap;
    }
    /***
     * 图片的缩放方法
     * 
     * @param src ：源图片资源
     * @param newWidth ：缩放后宽度
     * @param newHeight ：缩放后高度
     */
    public static Bitmap scale(Bitmap src, double newWidth, double newHeight) {
        // 记录src的宽高
        float width = src.getWidth();
        float height = src.getHeight();
        // 创建一个matrix容器
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 开始缩放
        matrix.postScale(scaleWidth, scaleHeight);
        // 创建缩放后的图片
        return Bitmap.createBitmap(src, 0, 0, (int) width, (int) height,
                matrix, true);
    }
    
}
