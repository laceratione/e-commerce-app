package com.example.effectivemobiletest.presentation.ui.homepage

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.widget.AppCompatImageButton
import com.example.effectivemobiletest.R

//окно настроек фильтра
class FilterSettings() {

    fun showPopUpWindow(view: View) {
        val myView: View = LayoutInflater.from(view.context)
            .inflate(R.layout.filter, null)

        val width: Int = LinearLayout.LayoutParams.MATCH_PARENT
        val height: Int = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true

        val popupWindow = PopupWindow(myView, width, height, focusable)
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0)

        //закрыть окно
        val btnClose: AppCompatImageButton = myView.findViewById(R.id.btnFilterClose)
        btnClose.setOnClickListener {
            popupWindow.dismiss()
        }

        //применить фильтры
        val btnDone: Button = myView.findViewById(R.id.btnFilterDone)
        btnDone.setOnClickListener {
            popupWindow.dismiss()
        }
    }

}