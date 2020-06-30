package com.limapps.base.support

import com.google.gson.JsonObject
import com.limapps.base.models.MissingProduct
import com.limapps.base.support.history.OrderHistoryItem
import com.limapps.base.support.model.ArticlesItem
import com.limapps.base.support.model.ConversationV2
import com.limapps.base.support.model.NewSection
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

interface SupportCenterController {


    fun createMissingProductsTicket(subject: String, description: String, orderId: String, products: List<MissingProduct>): Completable

    fun getCustomerProfile(): Single<List<JsonObject>>

    fun getFullLastOrder(page: Int = 1): Single<Pair<List<OrderHistoryItem>, Int>>

    fun getSectionsAndArticles(orderId: String): Single<List<NewSection>>

    fun getSectionsAndArticlesByScreen(appScreenId: String): Single<List<NewSection>>

    fun getSupportTicketsByOrder(orderId: String): Single<ConversationV2>
}
