package com.salton123.xmly.mvp

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.salton123.C
import com.salton123.arch.mvp.BindPresenter
import com.salton123.log.XLog
import com.salton123.soulove.common.net.RxAdapter
import com.salton123.soulove.common.net.exception.CustException
import com.salton123.util.FileUtils
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack
import com.ximalaya.ting.android.opensdk.model.album.AlbumList
import com.ximalaya.ting.android.opensdk.model.album.AnnouncerListByIds
import com.ximalaya.ting.android.opensdk.model.album.BatchAlbumList
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbumsList
import com.ximalaya.ting.android.opensdk.model.album.DiscoveryRecommendAlbumsList
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList
import com.ximalaya.ting.android.opensdk.model.album.RelativeAlbums
import com.ximalaya.ting.android.opensdk.model.album.UpdateBatchList
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2List
import com.ximalaya.ting.android.opensdk.model.category.CategoryList
import com.ximalaya.ting.android.opensdk.model.coldboot.GenreList
import com.ximalaya.ting.android.opensdk.model.metadata.MetaDataList
import com.ximalaya.ting.android.opensdk.model.tag.TagList
import com.ximalaya.ting.android.opensdk.model.track.BatchTrackList
import com.ximalaya.ting.android.opensdk.model.track.CommonTrackList
import com.ximalaya.ting.android.opensdk.model.track.LastPlayTrackList
import com.ximalaya.ting.android.opensdk.model.track.SearchTrackList
import com.ximalaya.ting.android.opensdk.model.track.SearchTrackListV2
import com.ximalaya.ting.android.opensdk.model.track.TrackBaseInfo
import com.ximalaya.ting.android.opensdk.model.track.TrackHotList
import com.ximalaya.ting.android.opensdk.model.track.TrackList
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.disposables.Disposable
import java.io.File
import java.util.HashMap

/**
 * User: newSalton@outlook.com
 * Date: 2018/5/23 下午6:36
 * ModifyTime: 下午6:36
 * Description:
 */
@SuppressLint("CheckResult")
class RequestPresenter : BindPresenter<RequestContract.IRequestView>() {

    private val TAG = "RequestPresenter"
    private val DEFAULT_SAVE_PATH = C.BASE_PATH + File.separator
    fun getMetadataList(categoryId: String) {
        val map = HashMap<String, String>()
        map[DTransferConstants.CATEGORY_ID] = categoryId
        CommonRequest.getMetadataList(map, object : IDataCallBack<MetaDataList> {
            override fun onSuccess(categoryList: MetaDataList?) {
                mView?.let { it.onRequestSucceed(categoryList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getCategories() {
        val map = HashMap<String, String>()
        CommonRequest.getCategories(map, object : IDataCallBack<CategoryList> {
            override fun onSuccess(categoryList: CategoryList?) {
                mView?.let { it.onRequestSucceed(categoryList) }
                FileUtils.writeFile(DEFAULT_SAVE_PATH + "category.txt", Gson().toJson(categoryList))
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getTags(categoryId: String, type: String) {
        val map = HashMap<String, String>()
        map[DTransferConstants.CATEGORY_ID] = categoryId
        map[DTransferConstants.TYPE] = type
        CommonRequest.getTags(map, object : IDataCallBack<TagList> {
            override fun onSuccess(tagList: TagList?) {
                mView?.let { it.onRequestSucceed(tagList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getAlbumList(categoryId: String, tagName: String, calcDimension: String, page: String) {
        val map = HashMap<String, String>()
        map[DTransferConstants.CATEGORY_ID] = categoryId
        map[DTransferConstants.TAG_NAME] = tagName
        map[DTransferConstants.CALC_DIMENSION] = calcDimension
        map[DTransferConstants.PAGE] = page
        CommonRequest.getAlbumList(map, object : IDataCallBack<AlbumList> {
            override fun onSuccess(albumList: AlbumList?) {
                mView?.let { it.onRequestSucceed(albumList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getTracks(albumId: Long, sort: String, page: String) {
        val map = HashMap<String, String>()
        map[DTransferConstants.ALBUM_ID] = albumId.toString()
        map[DTransferConstants.SORT] = sort
        map[DTransferConstants.PAGE] = page
        CommonRequest.getTracks(map, object : IDataCallBack<TrackList> {
            override fun onSuccess(trackList: TrackList?) {
                mView?.let { it.onRequestSucceed(trackList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getBatch(ids: String) {
        val map = HashMap<String, String>()
        map[DTransferConstants.ALBUM_IDS] = ids
        CommonRequest.getBatch(map, object : IDataCallBack<BatchAlbumList> {
            override fun onSuccess(batchAlbumList: BatchAlbumList?) {
                mView?.let { it.onRequestSucceed(batchAlbumList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getUpdateBatch(ids: String) {
        val map = HashMap<String, String>()

        map[DTransferConstants.ALBUM_IDS] = ids

        CommonRequest.getUpdateBatch(map, object : IDataCallBack<UpdateBatchList> {
            override fun onSuccess(updateBatchList: UpdateBatchList?) {
                mView?.let { it.onRequestSucceed(updateBatchList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getHotTracks(categoryId: String, tagName: String, page: String) {
        val map = HashMap<String, String>()

        map[DTransferConstants.CATEGORY_ID] = categoryId

        map[DTransferConstants.TAG_NAME] = tagName

        map[DTransferConstants.PAGE] = page

        CommonRequest.getHotTracks(map, object : IDataCallBack<TrackHotList> {
            override fun onSuccess(trackHotList: TrackHotList?) {
                mView?.let { it.onRequestSucceed(trackHotList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getBatchTracks(vararg ids: Long) {
        val map = HashMap<String, String>()
        val tempStr = StringBuffer()
        ids.forEachIndexed { index, l ->
            tempStr.append(l).append(",")
        }
        val trackIds = tempStr.removeSuffix(",").toString()
        map[DTransferConstants.TRACK_IDS] = trackIds
        CommonRequest.getBatchTracks(map, object : IDataCallBack<BatchTrackList> {
            override fun onSuccess(batchTrackList: BatchTrackList?) {
                mView?.let { it.onRequestSucceed(batchTrackList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getLastPlayTracks(albumId: String, trackId: String, containsPaid: Boolean) {
        val map = HashMap<String, String>()
        map[DTransferConstants.ALBUM_ID] = albumId
        map[DTransferConstants.TRACK_ID] = trackId
        map[DTransferConstants.CONTAINS_PAID] = containsPaid.toString() + ""
        CommonRequest.getLastPlayTracks(map, object : IDataCallBack<LastPlayTrackList> {
            override fun onSuccess(lastPlayTrackList: LastPlayTrackList?) {
                mView?.let { it.onRequestSucceed(lastPlayTrackList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getCategoryRecommendAlbums(categoryId: String, count: String) {
        val map = HashMap<String, String>()
        map[DTransferConstants.CATEGORY_ID] = categoryId
        map[DTransferConstants.DISPLAY_COUNT] = count
        CommonRequest.getCategoryRecommendAlbums(map,
                object : IDataCallBack<CategoryRecommendAlbumsList> {
                    override fun onSuccess(lastPlayTrackList: CategoryRecommendAlbumsList?) {
                        mView?.let { it.onRequestSucceed(lastPlayTrackList) }
                    }

                    override fun onError(i: Int, s: String) {
                        mView?.let { it.onRequestError(i, s) }
                    }
                })
    }

    fun getDiscoveryRecommendAlbums(count: String) {
        val map = HashMap<String, String>()
        map[DTransferConstants.DISPLAY_COUNT] = count
        CommonRequest.getDiscoveryRecommendAlbums(map,
                object : IDataCallBack<DiscoveryRecommendAlbumsList> {
                    override fun onSuccess(lastPlayTrackList: DiscoveryRecommendAlbumsList?) {
                        mView?.let { it.onRequestSucceed(lastPlayTrackList) }
                    }

                    override fun onError(i: Int, s: String) {
                        mView?.let { it.onRequestError(i, s) }
                    }
                })
    }

    fun getCategoryBannersV2(categoryId: String) {
        val map = HashMap<String, String>()
        map[DTransferConstants.CATEGORY_ID] = categoryId
        map[DTransferConstants.IMAGE_SCALE] = "2"
        CommonRequest.getCategoryBannersV2(map, object : IDataCallBack<BannerV2List> {
            override fun onSuccess(bannerV2List: BannerV2List?) {
                mView?.let { it.onRequestSucceed(bannerV2List) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getGuessLikeAlbum(count: String) {
        val map = HashMap<String, String>()
        map[DTransferConstants.LIKE_COUNT] = count
        CommonRequest.getGuessLikeAlbum(map, object : IDataCallBack<GussLikeAlbumList> {
            override fun onSuccess(gussLikeAlbumList: GussLikeAlbumList?) {
                mView?.let { it.onRequestSucceed(gussLikeAlbumList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getColdbootGenres() {
        val map = HashMap<String, String>()
        CommonRequest.getColdbootGenres(map, object : IDataCallBack<GenreList> {
            override fun onSuccess(genreList: GenreList?) {
                mView?.let { it.onRequestSucceed(genreList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getSearchedTracks(keyword: String) {
        val map = HashMap<String, String>()
        map[DTransferConstants.SEARCH_KEY] = keyword
        CommonRequest.getSearchedTracks(map, object : IDataCallBack<SearchTrackList> {
            override fun onSuccess(genreList: SearchTrackList?) {
                mView?.let { it.onRequestSucceed(genreList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getSingleTrackInfo(trackId: Long) {
        val map = HashMap<String, String>()
        map[DTransferConstants.TRACK_ID] = trackId.toString()
        CommonRequest.getTrackInfo(map, object : IDataCallBack<TrackBaseInfo> {
            override fun onSuccess(genreList: TrackBaseInfo?) {
                mView?.let { it.onRequestSucceed(genreList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun searchTrackV2(trackId: Long) {
        val map = HashMap<String, String>()
        map[DTransferConstants.TRACK_ID] = trackId.toString()
        CommonRequest.searchTrackV2(map, object : IDataCallBack<SearchTrackListV2> {
            override fun onSuccess(genreList: SearchTrackListV2?) {
                mView?.let { it.onRequestSucceed(genreList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    fun getTrackList(trackId: Long) {
        val map = HashMap<String, String>()
        map[DTransferConstants.TRACK_ID] = "$trackId"
        CommonRequest.getTrackList(map, object : IDataCallBack<CommonTrackList<*>> {
            override fun onSuccess(commonTrackList: CommonTrackList<*>?) {
                XLog.i(this, "commonTrackList:" + Gson().toJson(commonTrackList))
            }

            override fun onError(i: Int, s: String) {
                XLog.i(this, s)
            }
        })
    }

    fun getCustomizeTrackList(trackId: Long) {
        val map = HashMap<String, String>()
        map[DTransferConstants.CUSTOMIZED_TRACKLIST_ID] = trackId.toString()
        CommonRequest.getCustomizeTrackList(map, object : IDataCallBack<TrackList> {
            override fun onSuccess(genreList: TrackList?) {
                mView?.let { it.onRequestSucceed(genreList) }
            }

            override fun onError(i: Int, s: String) {
                mView?.let { it.onRequestError(i, s) }
            }
        })
    }

    /**
     * 获取某个声音的相关推荐专辑
     *
     * @param specificParams
     * @return
     */
    fun getRelativeAlbumsUseTrackId(
            specificParams: Map<String?, String?>?
    ): Observable<RelativeAlbums?>? {
        return Observable.create { emitter: ObservableEmitter<RelativeAlbums?> ->
            CommonRequest.getRelativeAlbumsUseTrackId(specificParams,
                    object : IDataCallBack<RelativeAlbums?> {
                        override fun onSuccess(
                                relativeAlbums: RelativeAlbums?
                        ) {
                            emitter.onNext(relativeAlbums!!)
                            emitter.onComplete()
                        }

                        override fun onError(i: Int, s: String) {
                            emitter.onError(CustException(i.toString(), s))
                        }
                    })
        }.compose(RxAdapter.exceptionTransformer())
    }

    /**
     * 根据一批主播ID批量获取主播信息
     *
     * @param specificParams
     * @return
     */
    fun getAnnouncersBatch(
            specificParams: Map<String?, String?>?
    ): Observable<AnnouncerListByIds?>? {
        return Observable.create { emitter: ObservableEmitter<AnnouncerListByIds?> ->
            CommonRequest.getAnnouncersBatch(specificParams,
                    object : IDataCallBack<AnnouncerListByIds?> {
                        override fun onSuccess(
                                announcerListByIds: AnnouncerListByIds?
                        ) {
                            emitter.onNext(announcerListByIds!!)
                            emitter.onComplete()
                        }

                        override fun onError(i: Int, s: String) {
                            emitter.onError(CustException(i.toString(), s))
                        }
                    })
        }.compose(RxAdapter.exceptionTransformer())
    }

    private var mSubscriptions: MutableList<Disposable?> = mutableListOf()

    private fun add(disposable: Disposable?) {
        mSubscriptions.add(disposable)
    }

    override fun detachView() {
        super.detachView()
        mSubscriptions.forEach {
            it?.apply {
                if (!isDisposed) {
                    dispose()
                }
            }
        }
    }
}
