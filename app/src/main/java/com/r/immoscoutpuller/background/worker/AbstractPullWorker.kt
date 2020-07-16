package com.r.immoscoutpuller.background.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.r.immoscoutpuller.background.ImmoListDiffer
import com.r.immoscoutpuller.background.NotificationHelperItem
import com.r.immoscoutpuller.background.NotificationHelperSummary
import com.r.immoscoutpuller.model.ImmoItem
import com.roman.basearch.utility.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 16.07.20
 */

abstract class AbstractPullWorker<Type: ImmoItem>(context: Context, params: WorkerParameters)
    : CoroutineWorker(context, params), KoinComponent {


    abstract val keyImmoList: String
    abstract val notificationTitlePrefixRes: Int

    abstract fun getFreshImmoItems(): Flow<List<Type>>


    private val localRepository: LocalRepository by inject()
    private lateinit var notificationHelperItem: NotificationHelperItem<Type>
    private lateinit var notificationHelperSummary: NotificationHelperSummary<Type>


    override val coroutineContext: CoroutineDispatcher get() = Dispatchers.IO


    override suspend fun doWork(): Result = coroutineScope {

        notificationHelperItem = NotificationHelperItem(notificationTitlePrefixRes)
        notificationHelperSummary = NotificationHelperSummary(notificationTitlePrefixRes)

        val differ = ImmoListDiffer<Type>()

        val job = getLastItems()
            .flatMapConcat { differ.lastItems = it; getFreshImmoItems() }
            .flatMapConcat { saveFreshItems(it) }
            .flatMapConcat { differ.freshItems = it; getDiff(differ) }
            .flatMapConcat { showNotificationsForEach(it) }
            .onStart { notificationHelperSummary.onStart() }
            .launchIn(this)

        job.join()

        Result.success()
    }


    private fun getDiff(differ: ImmoListDiffer<Type>)
            : Flow<ImmoListDiffer.Diff<Type>> = flow {

        emit(differ.createDiff())
    }

    private fun getLastItems(): Flow<List<Type>> {
        return localRepository
            .readFile<List<Type>>(keyImmoList)
            .catch { emit(listOf()) }
    }

    private fun showNotificationsForEach(diff: ImmoListDiffer.Diff<Type>) = flow {
        notificationHelperItem.onDone(diff)
        notificationHelperSummary.onDone(diff)
        emit(diff)
    }

    private fun saveFreshItems(items: List<Type>): Flow<List<Type>> {
        return localRepository.saveFile(keyImmoList, items)
    }

}
