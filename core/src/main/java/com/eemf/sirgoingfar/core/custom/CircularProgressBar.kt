package com.eemf.sirgoingfar.core.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator

import com.eemf.sirgoingfar.core.utils.DisplayUtil

/*
 *@author: www.reach.africa
 */

class CircularProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mViewWidth: Int = 0
    private var mViewHeight: Int = 0

    private val mStartAngle = -90f
    private var mSweepAngle = 0f
    private val mMaxSweepAngle = 360f
    private var mStrokeWidth = 20
    private val mAnimationDuration = 1500
    private val mMaxProgress = 100
    private var mDrawText = true
    private var mRoundedCorners = true
    private var mProgressColor = Color.BLACK
    private var mTextColor = Color.BLACK

    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mSubtext: String? = null
    private var mSubtextSize = 12.0f
    private val displayUtil: DisplayUtil = DisplayUtil(context)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initMeasurments()
        drawBackgroundCircle(canvas)
        drawOutlineArc(canvas)

        if (mDrawText) {
            drawText(canvas)
        }
    }

    private fun initMeasurments() {
        mViewWidth = width
        mViewHeight = height
    }

    private fun drawBackgroundCircle(canvas: Canvas) {

        val diameter = Math.min(mViewWidth, mViewHeight)
        val pad = mStrokeWidth / 2.0f
        val outerOval = RectF(pad, pad, diameter - pad, diameter - pad)

        mPaint.color = Color.parseColor("#dadce0")
        mPaint.strokeWidth = mStrokeWidth.toFloat()
        mPaint.isAntiAlias = true
        mPaint.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        mPaint.style = Paint.Style.STROKE
        canvas.drawArc(outerOval, 0f, 360f, false, mPaint)
    }

    private fun drawOutlineArc(canvas: Canvas) {

        val diameter = Math.min(mViewWidth, mViewHeight)
        val pad = mStrokeWidth / 2.0f
        val outerOval = RectF(pad, pad, diameter - pad, diameter - pad)

        mPaint.color = mProgressColor
        mPaint.strokeWidth = mStrokeWidth.toFloat()
        mPaint.isAntiAlias = true
        mPaint.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        mPaint.style = Paint.Style.STROKE
        canvas.drawArc(outerOval, mStartAngle, mSweepAngle, false, mPaint)
    }

    private fun drawText(canvas: Canvas) {
        mPaint.textSize = Math.min(mViewWidth, mViewHeight) / 6f
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.strokeWidth = 0f
        mPaint.color = mTextColor
        //        mPaint.setTypeface(FontUtils.selectTypeface(getContext(), FontUtils.STYLE_BOLD));

        // Center text
        val xPos = canvas.width / 2
        var yPos = (canvas.height / 2 - (mPaint.descent() + mPaint.ascent()) / 2).toInt()

        val balance = 20
        if (!TextUtils.isEmpty(mSubtext)) {
            yPos -= balance
        }

        canvas.drawText(calcProgressFromSweepAngle(mSweepAngle).toString() + "%", xPos.toFloat(), yPos.toFloat(), mPaint)

        if (!TextUtils.isEmpty(mSubtext)) {
            val y2 = (yPos.toFloat() + mPaint.descent() + balance.toFloat()).toInt()
            mPaint.textSize = displayUtil.sp(mSubtextSize)
            //            mPaint.setTypeface(FontUtils.selectTypeface(getContext(), FontUtils.STYLE_MEDIUM));
            canvas.drawText(mSubtext!!, xPos.toFloat(), y2.toFloat(), mPaint)
        }
    }

    private fun calcSweepAngleFromProgress(progress: Int): Float {
        return mMaxSweepAngle / mMaxProgress * progress
    }

    private fun calcProgressFromSweepAngle(sweepAngle: Float): Int {
        return (sweepAngle * mMaxProgress / mMaxSweepAngle).toInt()
    }

    /**
     * Set progress of the circular progress bar.
     *
     * @param progress progress between 0 and 100.
     */
    fun setProgress(progress: Int) {
        val animator = ValueAnimator.ofFloat(mSweepAngle, calcSweepAngleFromProgress(progress))
        animator.interpolator = DecelerateInterpolator()
        animator.duration = mAnimationDuration.toLong()
        animator.addUpdateListener { valueAnimator ->
            mSweepAngle = valueAnimator.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    fun setSubText(subText: String) {
        mSubtext = subText
        invalidate()
    }

    fun setTextSize(f: Float) {
        mSubtextSize = f
        invalidate()
    }

    fun resetProgress() {
        mSweepAngle = 0f
        mTextColor = Color.BLACK
        invalidate()
    }

    fun setProgressColor(color: Int) {
        mProgressColor = color
        invalidate()
    }

    fun setProgressWidth(width: Int) {
        mStrokeWidth = width
        invalidate()
    }

    fun setTextColor(color: Int) {
        mTextColor = color
        invalidate()
    }

    fun showProgressText(show: Boolean) {
        mDrawText = show
        invalidate()
    }

    /**
     * Toggle this if you don't want rounded corners on progress bar.
     * Default is true.
     *
     * @param roundedCorners true if you want rounded corners of false otherwise.
     */
    fun useRoundedCorners(roundedCorners: Boolean) {
        mRoundedCorners = roundedCorners
        invalidate()
    }
}
