package com.dreamstep.moaipay.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dreamstep.moaipay.R
import com.dreamstep.moaipay.data.model.MoaiDetail
import com.dreamstep.moaipay.utils.BaseAdapter
import com.dreamstep.moaipay.utils.BaseViewHolder
import com.dreamstep.moaipay.utils.ViewUtils
import java.text.SimpleDateFormat

class MoaiDetailAdapter: BaseAdapter<MoaiDetail>() {

    lateinit var context: Context

    // FUNCTIONS
    // ====================================================
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_moai_detail, parent, false)
        )
    }

    // VIEW HOLDERS
    // ====================================================
    inner class ViewHolder(itemView: View): BaseViewHolder(itemView) {

        // ====================================================
        override fun onBind(position: Int) {
            super.onBind(position)

            // Object ========> POST
            val moaiDetail = mItemList[position]

            setView(moaiDetail)

            // CLICKS LISTENERS
            // ====================================================
            itemView.setOnClickListener {
            }
        }

        private fun setView(moaiDetail: MoaiDetail){
            val dfDate = SimpleDateFormat("M/d")
            val date = moaiDetail.eventDate
            val check = moaiDetail.check
            val attend = check[context.getString(R.string.check_attend_key)]
            val recieveMoai = check[context.getString(R.string.check_recieve_moai_key)]
            val recievePayment = check["recievePayment"]

//            ViewUtils.putText(itemView.lblDate, dfDate.format(date))
//            ViewUtils.putText(itemView.lblNum, num.toString())
        }

    }
}

