package com.eemf.sirgoingfar.core.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.eemf.sirgoingfar.core.R
import com.eemf.sirgoingfar.core.utils.AppUtil
import com.eemf.sirgoingfar.core.utils.DisplayUtil

/*
 *@author: www.reach.africa
  */
class CircularProgressView @JvmOverloads constructor(private var mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(mContext, attrs, defStyleAttr) {
    private var mTitle: String? = null
    private var mSubtext: String? = null
    private var mProgressPercent: Int = 0

    private var mRingThickness: Float = 0.toFloat()
    private var mRingDiameter: Float = 0.toFloat()

    private var mSubtextSize: Float = 0.toFloat()
    private var mPercentTextSize: Float = 0.toFloat()
    private var mTitleTextSize: Float = 0.toFloat()
    private var mProgressColor: Int = 0
    private var mSubtextColor: Int = 0
    private var mTitleColor: Int = 0
    private var mSubtextMargin: Int = 0

    private val DEFAULT_SUBTEXT_MARGIN = 5
    private val DEFAULT_TITLE_TEXT_SIZE = 10.0f
    private val DEFAULT_RING_THICKNESS = 12.0f
    private val DEFAULT_PROGRESS_COLOR = Color.RED
    private val DEFAULT_SUBTEXT_COLOR = Color.BLACK
    private val DEFAULT_TITLE_COLOR = Color.BLACK
    private val DEFAULT_RING_DIAMETER = 80.0f
    private val DEFAULT_SUBTEXT_SIZE = 10.0f
    private val DEFAULT_PERCENT_TEXT_SIZE = 17.0f

    private lateinit var ringView: CircularProgressBar
    private lateinit var ringFrame: RelativeLayout
    private lateinit var textsContainer: LinearLayout
    private lateinit var tv_title: TextView
    private lateinit var tv_subtext: TextView
    private lateinit var tv_progressText: TextView

    private var displayUtil: DisplayUtil
    private var isAttached = false

    private val maxW: Int
        get() = (mRingDiameter - mRingThickness * 2 - displayUtil.dp(8)).toInt()

    private val maxWSubtext: Int
        get() = (mRingDiameter - mRingThickness * 2 - displayUtil.dp(18)).toInt()

    init {
        displayUtil = DisplayUtil(mContext)

        if (attrs != null) {
            val ta = mContext.obtainStyledAttributes(attrs, R.styleable.CircularProgressView)
            if (ta.hasValue(R.styleable.CircularProgressView_percent)) {
                mProgressPercent = ta.getInt(R.styleable.CircularProgressView_percent, 0)
            }

            if (ta.hasValue(R.styleable.CircularProgressView_title)) {
                mTitle = ta.getString(R.styleable.CircularProgressView_title)
            }

            if (ta.hasValue(R.styleable.CircularProgressView_subtext)) {
                mSubtext = ta.getString(R.styleable.CircularProgressView_subtext)
            }

            mRingThickness = ta.getDimension(R.styleable.CircularProgressView_thickness, DEFAULT_RING_THICKNESS)
            mProgressColor = ta.getColor(R.styleable.CircularProgressView_progressColor, DEFAULT_PROGRESS_COLOR)
            mSubtextColor = ta.getColor(R.styleable.CircularProgressView_subtextColor, DEFAULT_SUBTEXT_COLOR)
            mTitleColor = ta.getColor(R.styleable.CircularProgressView_titleColor, DEFAULT_TITLE_COLOR)
            mRingDiameter = ta.getDimension(R.styleable.CircularProgressView_ringDiameter, DEFAULT_RING_DIAMETER)
            mSubtextSize = ta.getDimension(R.styleable.CircularProgressView_subtextSize, DEFAULT_SUBTEXT_SIZE)
            mPercentTextSize = ta.getDimension(R.styleable.CircularProgressView_percentTextSize, DEFAULT_PERCENT_TEXT_SIZE)
            mTitleTextSize = ta.getDimension(R.styleable.CircularProgressView_titleTextSize, DEFAULT_TITLE_TEXT_SIZE)
            mSubtextMargin = ta.getInt(R.styleable.CircularProgressView_subtextMargin, DEFAULT_SUBTEXT_MARGIN)

            ta.recycle()
        } else {
            // Set defaults
            mRingThickness = displayUtil.dp(DEFAULT_RING_THICKNESS)
            mProgressColor = DEFAULT_PROGRESS_COLOR
            mSubtextColor = DEFAULT_SUBTEXT_COLOR
            mTitleColor = DEFAULT_TITLE_COLOR
            mRingDiameter = displayUtil.dp(DEFAULT_RING_DIAMETER)
            mSubtextSize = DEFAULT_SUBTEXT_SIZE
            mPercentTextSize = DEFAULT_PERCENT_TEXT_SIZE
            mTitleTextSize = DEFAULT_TITLE_TEXT_SIZE
            mSubtextMargin = DEFAULT_SUBTEXT_MARGIN
        }

        orientation = LinearLayout.VERTICAL
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isAttached = true
        attachViews()
    }

    private fun attachViews() {
        attachRingFrame()
        attachRing()
        attachTextsContainer()

        if (!TextUtils.isEmpty(mTitle)) {
            attachTitle()
        }
    }

    private fun attachRingFrame() {
        ringFrame = RelativeLayout(mContext)

        val lp_ringFrame = LinearLayout.LayoutParams(mRingDiameter.toInt(), mRingDiameter.toInt())
        lp_ringFrame.gravity = Gravity.CENTER_HORIZONTAL
        addView(ringFrame, lp_ringFrame)
    }

    private fun attachRing() {
        ringView = CircularProgressBar(mContext)
        ringView.id = R.id.ring_view
        ringView.resetProgress()
        ringView.setProgressWidth(mRingThickness.toInt())
        ringView.setProgress(mProgressPercent)
        ringView.setProgressColor(mProgressColor)
        ringView.setTextColor(Color.BLACK)
        ringView.showProgressText(false)

        val lp_ring = RelativeLayout.LayoutParams(mRingDiameter.toInt(), mRingDiameter.toInt())
        ringFrame.addView(ringView, lp_ring)
    }

    private fun attachTextsContainer() {
        textsContainer = LinearLayout(mContext)
        textsContainer.orientation = LinearLayout.VERTICAL

        val lp_textsContainer = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        lp_textsContainer.addRule(RelativeLayout.CENTER_IN_PARENT)

        ringFrame.addView(textsContainer, lp_textsContainer)

        attachPercentText()

        if (!TextUtils.isEmpty(mSubtext)) {
            attachSubtext()
        }
    }

    private fun attachPercentText() {
        tv_progressText = TextView(mContext)
        tv_progressText.id = AppUtil.generateViewId()
        //        tv_progressText.setTypeface(FontUtils.selectTypeface(mContext, FontUtils.STYLE_BOLD));
        tv_progressText.setTextColor(Color.BLACK)
        tv_progressText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mPercentTextSize)
        tv_progressText.setLineSpacing(0f, 0.9f)
        tv_progressText.maxLines = 1
        tv_progressText.ellipsize = TextUtils.TruncateAt.END
        tv_progressText.gravity = Gravity.CENTER

        val lp_progressText = LinearLayout.LayoutParams(maxW, RelativeLayout.LayoutParams.WRAP_CONTENT)
        textsContainer.addView(tv_progressText, lp_progressText)

        fn_setProgressPercent()
    }

    private fun attachSubtext() {
        tv_subtext = TextView(mContext)
        tv_subtext.id = AppUtil.generateViewId()
        tv_subtext.setTextColor(mSubtextColor)
        //        tv_subtext.setTypeface(FontUtils.selectTypeface(mContext, FontUtils.STYLE_MEDIUM));
        tv_subtext.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSubtextSize)
        tv_subtext.setLineSpacing(0f, 0.9f)
        tv_subtext.gravity = Gravity.CENTER

        fn_setSubtext()

        val lp_subtext = LinearLayout.LayoutParams(maxWSubtext, RelativeLayout.LayoutParams.WRAP_CONTENT)
        lp_subtext.width = maxW
        lp_subtext.topMargin = mSubtextMargin

        textsContainer.addView(tv_subtext, lp_subtext)
    }

    private fun attachTitle() {
        tv_title = TextView(mContext)
        tv_title.text = mTitle
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize)
        tv_title.setTextColor(mTitleColor)
        //        tv_title.setTypeface(FontUtils.selectTypeface(mContext, FontUtils.STYLE_MEDIUM));
        tv_title.gravity = Gravity.CENTER
        tv_title.maxLines = 2
        tv_title.setLineSpacing(0f, 0.9f)

        val lp_title = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp_title.gravity = Gravity.CENTER_HORIZONTAL
        lp_title.topMargin = displayUtil.dp(4).toInt()
        addView(tv_title, lp_title)
    }

    private fun fn_setProgressPercent() {
        tv_progressText.text = "0%"

        val animator = ValueAnimator.ofInt(0, mProgressPercent)
        animator.interpolator = DecelerateInterpolator()
        animator.duration = 1500
        animator.addUpdateListener { valueAnimator ->
            val i = valueAnimator.animatedValue as Int
            tv_progressText.text = "$i%"
        }
        animator.start()
    }

    private fun fn_setSubtext() {
        tv_subtext.text = mSubtext
        tv_subtext.setTextColor(mSubtextColor)
        tv_subtext.maxLines = 2
        tv_subtext.ellipsize = TextUtils.TruncateAt.END
        tv_subtext.maxWidth = maxWSubtext
        tv_subtext.setPadding(14, 0, 14, 0)
        tv_subtext.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                tv_subtext.text = mSubtext
                tv_subtext.maxWidth = maxWSubtext
                tv_subtext.setPadding(16, 0, 16, 0)
                tv_subtext.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    fun setTitleColor(mTitleColor: Int) {
        this.mTitleColor = mTitleColor
    }

    fun setTitle(title: String) {
        mTitle = title
        if (isAttached) {
            tv_title.text = mTitle
        }
    }

    fun setSubtext(subtext: String) {
        mSubtext = subtext
        if (isAttached) {
            fn_setSubtext()
        }
    }

    fun setProgressPercent(percent: Int) {
        mProgressPercent = percent
        if (isAttached) {
            fn_setProgressPercent()
            ringView.resetProgress()
            ringView.setProgress(mProgressPercent)
            ringView.showProgressText(false)
        }
    }

    fun setProgressColor(color: Int) {
        this.mProgressColor = color
        if (isAttached) {
            ringView.setProgressColor(mProgressColor)
        }
    }

    fun setRingDiameter(ringDiameter: Float) {
        this.mRingDiameter = ringDiameter
        if (isAttached) {
            val lp_ring = ringView.layoutParams as RelativeLayout.LayoutParams
            lp_ring.width = mRingDiameter.toInt()
            lp_ring.height = mRingDiameter.toInt()
            ringView.layoutParams = lp_ring
            ringView.invalidate()
        }
    }

    fun setRingThickness(ringThickness: Float) {
        this.mRingThickness = ringThickness
        if (isAttached) {
            this.ringView.setProgressWidth(displayUtil.dp(ringThickness).toInt())
        }
    }

    fun setPercentTextSize(textSize: Float) {
        mPercentTextSize = textSize
        if (isAttached) {
            tv_progressText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mPercentTextSize)
        }
    }

    fun setSubtextSize(textSize: Float) {
        mSubtextSize = textSize
        if (isAttached) {
            tv_subtext.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSubtextSize)
        }
    }

    fun setTitleTextSize(textSize: Float) {
        mTitleTextSize = textSize
        if (isAttached) {
            tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize)
        }
    }

    fun setSubtextMargin(margin: Int) {
        mSubtextMargin = margin
        if (isAttached) {
            val lp_subtext = tv_subtext.layoutParams as LinearLayout.LayoutParams
            lp_subtext.topMargin = mSubtextMargin
            tv_subtext.layoutParams = lp_subtext
        }
    }
}
