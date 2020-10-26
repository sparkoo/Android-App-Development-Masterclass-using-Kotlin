package cz.sparko.course.android.flickrbrowser

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "RecyclerItemClickListen"

class RecyclerItemClickListener(
  context: Context,
  recyclerView: RecyclerView,
  private val listener: OnRecyclerClickListener
) : RecyclerView.SimpleOnItemTouchListener() {
  interface OnRecyclerClickListener {
    fun onItemClick(view: View, position: Int)
    fun onItemLongClick(view: View, position: Int)
  }

  private val gestureDetector = GestureDetectorCompat(
    context,
    object : GestureDetector.SimpleOnGestureListener() {
      override fun onSingleTapUp(e: MotionEvent): Boolean {
        Log.d(TAG, "onSingleTapUp: ")
        val childView = recyclerView.findChildViewUnder(e.x, e.y)
        childView?.let { listener.onItemClick(it, recyclerView.getChildAdapterPosition(childView)) }
        return true
      }

      override fun onLongPress(e: MotionEvent) {
        Log.d(TAG, "onLongPress: ")
        val childView = recyclerView.findChildViewUnder(e.x, e.y)
        childView?.let { listener.onItemLongClick(it, recyclerView.getChildAdapterPosition(childView)) }
      }
    })

  override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
    Log.d(TAG, "onInterceptTouchEvent: $e")
    return gestureDetector.onTouchEvent(e)
  }
}