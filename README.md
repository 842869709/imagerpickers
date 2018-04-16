# imagerpickers
自己个人用的仿微信图片选择控件

该项目参考了：

https://github.com/jeasonlzy/ImagePicker#%E6%BC%94%E7%A4%BA

https://github.com/pengjianbo/GalleryFinal

https://github.com/easonline/AndroidImagePicker

喜欢原作的可以去使用。同时欢迎大家下载体验本项目，如果使用过程中遇到什么问题，欢迎反馈，我将持续更新此项目。

示例图片

![](https://github.com/842869709/imagerpickers/blob/master/S80412-161446.jpg)
![](https://github.com/842869709/imagerpickers/blob/master/S80412-161452.jpg)
![](https://github.com/842869709/imagerpickers/blob/master/S80412-161508.jpg)
![](https://github.com/842869709/imagerpickers/blob/master/S80412-161543.jpg)
![](https://github.com/842869709/imagerpickers/blob/master/S80412-161728.jpg)

## 1.用法
使用前，对于Android Studio的用户，可以选择添加:

```gradle
	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
	dependencies {
	        compile 'com.github.842869709:imagerpickers:v1.1.0'
	}
```
## 2.功能参数与含义
配置参数|参数含义
-|-
multiMode|	图片选着模式，单选/多选
selectLimit|	多选限制数量，默认为9
showCamera|	选择照片时是否显示拍照按钮
crop|	是否允许裁剪（单选有效）
style|	有裁剪时，裁剪框是矩形还是圆形
focusWidth|	矩形裁剪框宽度（圆形自动取宽高最小值）
focusHeight|	矩形裁剪框高度（圆形自动取宽高最小值）
outPutX|	裁剪后需要保存的图片宽度
outPutY|	裁剪后需要保存的图片高度
isSaveRectangle|	裁剪后的图片是按矩形区域保存还是裁剪框的形状，例如圆形裁剪的时候，该参数给true，那么保存的图片是矩形区域，如果该参数给fale，保存的图片是圆形区域
imageLoader|	需要使用的图片加载器，自需要实现ImageLoader接口即可

## 3.代码参考
1，图片展示
```
public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {

        Glide.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(com.yxd.imagepickers.R.drawable.ic_default_image)           //设置错误图片
                .placeholder(com.yxd.imagepickers.R.drawable.ic_default_image)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                .into(imageView);
    }
    
     //设置网络图片
    @Override
    public void displayInternetImage(Activity activity, String path, final ImageView imageView, int width, int height) {
        Glide.with(activity)
                .load(path)
                .asBitmap()
                .centerCrop()
                .thumbnail(0.05f)
                .error(R.drawable.ic_default_image)  //设置错误图片
                .placeholder(R.drawable.ic_default_image)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
                        circularBitmapDrawable.setCircular(false);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    @Override
    public void clearMemoryCache() {
    }
}
```
2，然后配置图片选择器，一般在Application初始化配置一次就可以,这里就需要将上面的图片加载器设置进来,其余的配置根据需要设置
```
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_picker);
    
    ImagePicker imagePicker = ImagePicker.getInstance();
    imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
    imagePicker.setShowCamera(true);  //显示拍照按钮
    imagePicker.setCrop(true);        //允许裁剪（单选才有效）
    imagePicker.setSaveRectangle(true); //是否按矩形区域保存
    imagePicker.setSelectLimit(9);    //选中数量限制
    imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
    imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
    imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
    imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
    imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
}
```
3，以上配置完成后，如果 开启相册，例如点击按钮时
}
```
 //打开选择,本次允许选择的数量
  ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
  Intent intent1 = new Intent(WxDemoActivity.this, ImageGridActivity.class);
  /* 如果需要进入选择的时候显示已经选中的图片，
  * 详情请查看ImagePickerActivity
  * */
  // intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
   startActivityForResult(intent1, REQUEST_CODE_SELECT);
}
```
如果想打开摄像头
```
 //打开选择,本次允许选择的数量
ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
Intent intent = new Intent(WxDemoActivity.this, ImageGridActivity.class);
intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
startActivityForResult(intent, REQUEST_CODE_SELECT);
```
				
重写onActivityResult方法,回调结果
```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
        if (data != null && requestCode == IMAGE_PICKER) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            MyAdapter adapter = new MyAdapter(images);
            gridView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
        }
    }
}
```
## v1.1.1图片预览支持网络图片修改
## v1.1.0图片预览支持网络图片
## v1.0.0第一次提交
