package com.zhuzichu.android.widget.toolbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.TextViewCompat
import com.zhuzichu.android.widget.R

class NiceToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val titleView: TextView by lazy { findViewById<TextView>(R.id.title) }

    private val leftLayout by lazy { findViewById<View>(R.id.left_layout) }
    private val leftTextView: TextView by lazy { findViewById<TextView>(R.id.left_text) }
    private val leftIconView: ImageView by lazy { findViewById<ImageView>(R.id.left_icon) }

    private val rightLayout by lazy { findViewById<View>(R.id.right_layout) }
    private val rightTextView: TextView by lazy { findViewById<TextView>(R.id.right_text) }
    private val rightIconView: ImageView by lazy { findViewById<ImageView>(R.id.right_icon) }

    var titleText: String? = null
        set(value) {
            value?.let {
                field = value
                titleView.text = it
            }
        }

    var titleTextAppearance: Int? = null
        set(value) {
            value?.let {
                field = value
                TextViewCompat.setTextAppearance(titleView, it)
            }
        }

    var leftText: String? = null
        set(value) {
            value?.let {
                field = value
                leftTextView.text = it
                leftLayout.visibility = View.VISIBLE
            }
        }

    private var leftDrawable: Drawable? = null
        set(value) {
            value?.let {
                field = value
                leftIconView.setImageDrawable(it)
                leftLayout.visibility = View.VISIBLE
            }
        }

    var leftIcon: Int? = null
        set(value) {
            value?.let {
                field = value
                leftDrawable = AppCompatResources.getDrawable(context, it)
            }
        }

    var onClickLeftListener: OnClickListener? = null
        set(value) {
            value?.let {
                field = value
                leftLayout.setOnClickListener(it)
            }
        }

    var rightText: String? = null
        set(value) {
            value?.let {
                field = value
                rightTextView.text = it
                rightLayout.visibility = View.VISIBLE
            }
        }

    private var rightDrawable: Drawable? = null
        set(value) {
            value?.let {
                field = value
                rightIconView.setImageDrawable(it)
                rightLayout.visibility = View.VISIBLE
            }
        }

    var rightIcon: Int? = null
        set(value) {
            value?.let {
                field = value
                rightDrawable = AppCompatResources.getDrawable(context, it)
            }
        }

    var onClickRightListener: OnClickListener? = null
        set(value) {
            value?.let {
                field = value
                rightLayout.setOnClickListener(it)
            }
        }

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.NiceToolbar, 0, 0)

        arr.getString(R.styleable.NiceToolbar_toolbarTitle)?.let {
            titleText = it
        }

        titleTextAppearance = arr.getResourceId(
            R.styleable.NiceToolbar_toolbarTitleTextAppearance,
            R.attr.titleTextAppearance
        )

        arr.getString(R.styleable.NiceToolbar_toolbarLeftText)?.let {
            leftText = it
        }
        arr.getDrawable(R.styleable.NiceToolbar_toolbarLeftIcon)?.let {
            leftDrawable = it
        }

        arr.getString(R.styleable.NiceToolbar_toolbarRightText)?.let {
            rightText = it
        }
        arr.getDrawable(R.styleable.NiceToolbar_toolbarRightIcon)?.let {
            rightDrawable = it
        }

        arr.recycle()
    }
}