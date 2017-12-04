package com.shopify

import android.content.Context
import com.domain.ShopWrapper
import com.domain.database.Dao
import com.shopify.api.ShopifyApi
import com.shopify.di.DaggerShopComponent
import com.shopify.di.ShopComponent
import com.shopify.di.ShopifyModule
import com.shopify.router.ShopifyRouter

class ShopifyWrapper(context: Context, dao: Dao) : ShopWrapper {

    companion object {
        const val BASE_URL = "lubitax.myshopify.com"
        const val ANDROID_PAY_PUBLIC_KEY = "BFzGr2NExzZ49C01KldmyMg9zYE0s0EOY2zf4f+lTMp3uB8wES5oUu6WJSdsthXHWJuIhrSEvDpXugILc1ZZV3E="
        private const val ACCESS_TOKEN = "98fb98180b7f5987f1fff84416d3a697"

        lateinit var component: ShopComponent
    }

    override val api = ShopifyApi(context, BASE_URL, ACCESS_TOKEN)
    override val router = ShopifyRouter()

    init {
        component = DaggerShopComponent.builder().shopifyModule(ShopifyModule(api, dao)).build()
    }
}