package com.example.administrator.zipfacedetection

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mLayoutInflater: LayoutInflater? = null
    val mImageSource = intArrayOf(R.mipmap.pic1, R.mipmap.pic2,R.mipmap.pic3,R.mipmap.pic4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mLayoutInflater = LayoutInflater.from(this)
        viewPager.adapter = object : PagerAdapter(){
            override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
               return view == `object`
            }

            override fun getCount(): Int {
                return 4
            }

            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                container?.removeView(`object` as View?)
            }

            override fun instantiateItem(container: ViewGroup?, position: Int): View? {
                var itemView: View? = mLayoutInflater?.inflate(R.layout.layout_viewpager_item, container, false)
                var originalPic: ImageView? = itemView?.findViewById(R.id.original)
                var resultPic1: ImageView? = itemView?.findViewById(R.id.result1)
                var resultPic2: ImageView? = itemView?.findViewById(R.id.result2)

                val id = mImageSource[position]
                originalPic?.setBackgroundResource(id)

                val faceDetectionHelper = FaceDetectionHelper()
                val bitmap = BitmapFactory.decodeResource(resources, id)
                val cropResult = faceDetectionHelper.cropBitmapByFace(bitmap, 400F, 400F)
                resultPic1?.setImageBitmap(cropResult.getBitmap())

                val bitmap2 = BitmapFactory.decodeResource(resources, id)
                val cropResult2 = faceDetectionHelper.cropBitmapByFace(bitmap2, 400F, 400F, 5, 4)
                resultPic2?.setImageBitmap(cropResult2.getBitmap())

                // 主线程耗时， 可能卡顿

                container?.addView(itemView)
                return itemView
            }
        }
    }



}
