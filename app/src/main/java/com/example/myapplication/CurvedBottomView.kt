package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class CurvedBottomView :
    BottomNavigationView, BottomNavigationView.OnNavigationItemSelectedListener {

    private var mPath: Path? = null
    private var mPaint: Paint? = null
    private var mPath1: Path? = null
    private var mPaint1: Paint? = null

    /** the CURVE_CIRCLE_RADIUS represent the radius of the fab button  */
    private val CURVE_CIRCLE_RADIUS = 56

    // the coordinates of the first curve
    private val mFirstCurveStartPoint = Point()
    private val mFirstCurveEndPoint = Point()
    private val mFirstCurveControlPoint1 = Point()
    private val mFirstCurveControlPoint2 = Point()

    //the coordinates of the second curve
    private var mSecondCurveStartPoint = Point()
    private val mSecondCurveEndPoint = Point()
    private val mSecondCurveControlPoint1 = Point()
    private val mSecondCurveControlPoint2 = Point()
    private var mNavigationBarWidth = 0
    private var mNavigationBarHeight = 0


    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        mPath = Path()
        mPaint = Paint()
        mPaint!!.style = Paint.Style.FILL_AND_STROKE
        mPaint!!.color = Color.WHITE
        setBackgroundColor(Color.TRANSPARENT)

        mPath1 = Path()
        mPaint1 = Paint()
        mPaint1!!.style = Paint.Style.STROKE
        mPaint1!!.color = ContextCompat.getColor(this.context, R.color.primaryInputBorder)
        mPaint1!!.strokeWidth = 2f
        setOnNavigationItemSelectedListener(this)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // get width and height of navigation bar
        // Navigation bar bounds (width & height)
        mNavigationBarWidth = width
        mNavigationBarHeight = height


        // the coordinates (x,y) of the start point before curve
        mFirstCurveStartPoint.set(
            mNavigationBarWidth / 8 - CURVE_CIRCLE_RADIUS - CURVE_CIRCLE_RADIUS / 1,
            36
        )
        // the coordinates (x,y) of the end point after curve
        mFirstCurveEndPoint.set(mNavigationBarWidth / 8, 0)
        // same thing for the second curve
        mSecondCurveStartPoint = mFirstCurveEndPoint
        mSecondCurveEndPoint.set(
            mNavigationBarWidth / 8 + CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 1,
            36
        )

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mFirstCurveControlPoint1.set(
            mFirstCurveStartPoint.x + CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4,
            mFirstCurveStartPoint.y
        )
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mFirstCurveControlPoint2.set(
            mFirstCurveEndPoint.x - CURVE_CIRCLE_RADIUS * 2 + CURVE_CIRCLE_RADIUS,
            mFirstCurveEndPoint.y
        )
        mSecondCurveControlPoint1.set(
            mSecondCurveStartPoint.x + CURVE_CIRCLE_RADIUS * 2 - CURVE_CIRCLE_RADIUS,
            mSecondCurveStartPoint.y
        )
        mSecondCurveControlPoint2.set(
            mSecondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4),
            mSecondCurveEndPoint.y
        )

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)





        mPath!!.reset()

        mPath!!.moveTo(0f, 37f)
        mPath!!.lineTo(mFirstCurveStartPoint.x.toFloat(), 37f)
        mPath!!.cubicTo(
            mFirstCurveControlPoint1.x.toFloat(), mFirstCurveControlPoint1.y.toFloat() + 1f,
            mFirstCurveControlPoint2.x.toFloat(), mFirstCurveControlPoint2.y.toFloat() + 1f,
            mFirstCurveEndPoint.x.toFloat(), mFirstCurveEndPoint.y.toFloat() + 1f
        )
        mPath!!.cubicTo(
            mSecondCurveControlPoint1.x.toFloat(), mSecondCurveControlPoint1.y.toFloat() + 1f,
            mSecondCurveControlPoint2.x.toFloat(), mSecondCurveControlPoint2.y.toFloat() + 1f,
            mSecondCurveEndPoint.x.toFloat(), mSecondCurveEndPoint.y.toFloat() + 1f
        )
        mPath!!.lineTo(mNavigationBarWidth.toFloat(), 37f)
        mPath!!.lineTo(mNavigationBarWidth.toFloat(), mNavigationBarHeight.toFloat())
        mPath!!.lineTo(0f, mNavigationBarHeight.toFloat())
        mPath!!.close()
        canvas.drawPath(mPath!!, mPaint!!)

        mPath1!!.reset()

        mPath1!!.moveTo(0f, 30f)
        mPath1!!.lineTo(mFirstCurveStartPoint.x.toFloat(), 30f)
        mPath1!!.cubicTo(
            mFirstCurveControlPoint1.x.toFloat(), mFirstCurveControlPoint1.y.toFloat() - 1f,
            mFirstCurveControlPoint2.x.toFloat(), mFirstCurveControlPoint2.y.toFloat() - 1f,
            mFirstCurveEndPoint.x.toFloat(), mFirstCurveEndPoint.y.toFloat()
        )
        mPath1!!.cubicTo(
            mSecondCurveControlPoint1.x.toFloat(), mSecondCurveControlPoint1.y.toFloat() - 1f,
            mSecondCurveControlPoint2.x.toFloat(), mSecondCurveControlPoint2.y.toFloat() - 1f,
            mSecondCurveEndPoint.x.toFloat(), 30f
        )
        mPath1!!.lineTo(mNavigationBarWidth.toFloat(), 30f)
        mPath1!!.lineTo(mNavigationBarWidth.toFloat(), mNavigationBarHeight.toFloat() )
        mPath1!!.lineTo(0f, mNavigationBarHeight.toFloat())
        mPath1!!.close()
        canvas.drawPath(mPath1!!, mPaint1!!)
    }


    fun draw(i: Int) {
        mFirstCurveStartPoint[mNavigationBarWidth / 8 - CURVE_CIRCLE_RADIUS - CURVE_CIRCLE_RADIUS / 1 + mNavigationBarWidth * i / 4] =
            37
        // the coordinates (x,y) of the end point after curve
        mFirstCurveEndPoint[mNavigationBarWidth / 8 + +mNavigationBarWidth * i / 4] =
            0
        // same thing for the second curve
        mSecondCurveStartPoint = mFirstCurveEndPoint
        mSecondCurveEndPoint[mNavigationBarWidth / 8 + CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 1 + mNavigationBarWidth * i / 4] =
            37

        mFirstCurveControlPoint1[mFirstCurveStartPoint.x + CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4] =
            mFirstCurveStartPoint.y
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mFirstCurveControlPoint2[mFirstCurveEndPoint.x - CURVE_CIRCLE_RADIUS * 2 + CURVE_CIRCLE_RADIUS] =
            mFirstCurveEndPoint.y
        mSecondCurveControlPoint1[mSecondCurveStartPoint.x + CURVE_CIRCLE_RADIUS * 2 - CURVE_CIRCLE_RADIUS] =
            mSecondCurveStartPoint.y
        mSecondCurveControlPoint2[mSecondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4)] =
            mSecondCurveEndPoint.y
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                draw(0)

            }
            R.id.navigation_notification -> {
                draw(1)

            }
            R.id.navigation_profile -> {
                draw(2)

            }
            R.id.navigation_records -> {
                draw(3)
            }
        }
        return true
    }
}