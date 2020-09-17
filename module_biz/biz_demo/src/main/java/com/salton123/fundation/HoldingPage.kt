package com.salton123.fundation

import android.text.TextUtils
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bin.david.form.core.TableConfig
import com.bin.david.form.data.CellInfo
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat
import com.bin.david.form.data.format.draw.FastTextDrawFormat
import com.bin.david.form.data.style.LineStyle
import com.bin.david.form.data.table.ArrayTableData
import com.salton123.fundation.mvvm.FundationViewModel
import com.salton123.fundation.poller.chicang.FundStock
import com.salton123.soulove.lib_demo.R
import com.salton123.ui.base.BaseActivity
import kotlinx.android.synthetic.main.page_fundation.*

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/9/16 10:11
 * ModifyTime: 10:11
 * Description:
 */
class HoldingPage : BaseActivity() {
    var code: String? = ""
    var shortName: String? = ""
    lateinit var mFundViewModel: FundationViewModel
    override fun initViewAndData() {
        code = intent.getStringExtra("code")
        if (TextUtils.isEmpty(code)) {
            finish()
        }
        shortName = intent.getStringExtra("shortName")
        mFundViewModel = ViewModelProviders.of(this).get(FundationViewModel::class.java)
        mFundViewModel.mFundStocksRet.observe(this, Observer {
            fillDataToTable(it)
        })
        mFundViewModel.searchFundHoldings(code!!)
    }

    private fun fillDataToTable(codeStacks: MutableList<FundStock>) {
        if (codeStacks.isEmpty()) {
            return
        }
        if (smartTable.tableData != null) {
            smartTable.tableData.clear()
        }
        smartTable.config.horizontalPadding = 20
        smartTable.config.verticalPadding = 20
        smartTable.config.sequenceHorizontalPadding = 50
        smartTable.config.contentGridStyle = LineStyle()
        smartTable.config.contentCellBackgroundFormat = object : BaseCellBackgroundFormat<CellInfo<*>>() {
            override fun getBackGroundColor(cellInfo: CellInfo<*>): Int {
                return if (cellInfo.row % 2 == 0) {
                    ContextCompat.getColor(activity(), R.color.default_background)
                } else TableConfig.INVALID_COLOR
            }
        }
        val tableName = "$shortName - $code"
        val titleName = arrayOf("股票名称", "股票代码")
        val data: Array<Array<String>> = arrayOf(
                codeStacks.map { it.gpjc }.toTypedArray(),
                codeStacks.map { it.gpdm }.toTypedArray()
        )
        val tableData = ArrayTableData.create(tableName, titleName, data, FastTextDrawFormat<String>())
        smartTable.tableData = tableData
        smartTable.matrixHelper.reset()
        smartTable.visibility = View.VISIBLE
    }

    override fun getLayout(): Int = R.layout.page_holding
}
