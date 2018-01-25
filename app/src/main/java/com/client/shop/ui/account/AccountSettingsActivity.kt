package com.client.shop.ui.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.client.shop.R
import com.client.shop.ShopApplication
import com.client.shop.ext.checkedChanges
import com.client.shop.ui.account.contract.AccountSettingsPresenter
import com.client.shop.ui.account.contract.AccountSettingsView
import com.client.shop.ui.account.di.AuthModule
import com.ui.base.lce.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_account_settings.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AccountSettingsActivity :
    BaseActivity<Boolean, AccountSettingsView, AccountSettingsPresenter>(),
    AccountSettingsView {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, AccountSettingsActivity::class.java)
    }

    @Inject
    lateinit var accountSettingsPresenter: AccountSettingsPresenter

    //ANDROID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(getString(R.string.set_new_password))
        loadData()
    }

    //INIT

    override fun inject() {
        ShopApplication.appComponent.attachAuthComponent(AuthModule()).inject(this)
    }

    override fun getContentView() = R.layout.activity_account_settings

    override fun createPresenter() = accountSettingsPresenter

    //SETUP

    private fun setupChangeListener() {
        acceptPromotionSwitch
            .checkedChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { presenter.updateSettings(it) }
    }

    //LCE

    override fun loadData(pullToRefresh: Boolean) {
        super.loadData(pullToRefresh)
        presenter.getCustomer()
    }

    override fun showContent(data: Boolean) {
        super.showContent(data)
        acceptPromotionSwitch.isChecked = data
        setupChangeListener()
    }
}