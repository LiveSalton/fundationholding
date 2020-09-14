package com.salton123.soulove.common.net.service;

import com.salton123.soulove.common.net.Constans;
import com.salton123.soulove.common.net.dto.XmTokenDTO;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Author: Thomas.<br/>
 * Date: 2019/10/12 11:16<br/>
 * Email: 1071931588@qq.com<br/>
 * Description:公用Api
 */
public interface CommonService {

    /**
     * 刷新喜马拉雅token
     *
     * @param body
     * @return
     */
    @POST(Constans.REFRESH_TOKEN_URL)
    Observable<XmTokenDTO> refreshToken(@Body RequestBody body);

    /**
     * 从网络中获取ResponseBody
     *
     * @param url
     * @return
     */
    @GET
    Observable<ResponseBody> getCommonBody(@Url String url);
}
