package com.google.mlkit.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import androidx.annotation.NonNull
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Circle
import com.google.mlkit.vision.demo.R
import kotlinx.android.synthetic.main.progress_bar.view.*


class CustomProgressDialog {
    lateinit var dialog: Dialog

    fun show(context: Context) {
        show(context, null)
    }

    private fun show(context: Context, title: CharSequence?) {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.progress_bar, null)

        if (title != null) {
            view.cp_title.text = title
        }

        val circle: Sprite = Circle()
        view.spin_kit.setIndeterminateDrawable(circle)

        view.cp_title.setTextColor(Color.WHITE) //Text Color

        dialog = Dialog(context, R.style.CustomProgressBarTheme)

        dialog.setContentView(view)

        dialog.setCancelable(false)

        if (!(context as Activity).isFinishing) {
            context.runOnUiThread {
                dialog.show()
            }
        }


    }

    fun dismiss() {
        if (::dialog.isInitialized && dialog != null)
            dialog.dismiss()
    }

    fun setColorFilter(@NonNull drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }

    }

}