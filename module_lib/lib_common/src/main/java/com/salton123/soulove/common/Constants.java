package com.salton123.soulove.common;

import com.salton123.soulove.sdk.ConstantsThird;

/**
 * Author: Thomas.
 * <br/>Date: 2019/9/18 13:58
 * <br/>Email: 1071931588@qq.com
 * <br/>Description:App常量
 */
public interface Constants extends ConstantsThird {
    interface Router {

        interface Xmly {
            String MAIN = "/xmly/main";
            String HOT = "/xmly/hot";
            String SEARCH = "/xmly/search";
            String SEARCH_RESULT = "/xmly/search/result";
            String F_SEARCH_SUGGEST = "/xmly/search/suggest";
            String F_ALBUM_LIST = "/xmly/album/list";
            String F_TRACK_LIST = "/xmly/track/list";
            String F_RADIO_LIST = "/xmly/radio/list";
            String F_ALBUM_DETAIL = "/xmly/album/detail";
            String F_PLAY_TRACK = "/xmly/play/track";
            String F_PLAY_RADIIO = "/xmly/play/radio";
            String F_ANNOUNCER_DETAIL = "/xmly/announcer/detail";
            String F_BATCH_DOWNLOAD = "/xmly/batch/download";
        }

        interface User {
            String LOGIN = "/user/login";
            String REGISTER = "/user/register";
            String PROFILE = "/user/profile";
            String ABOUT = "/user/about";
            String PERSONAL_DATA = "/user/personal_data";
            String SETTING = "/user/setting";
        }

        interface Listen {
            String F_MAIN = "/listen/main";
            String F_DOWNLOAD = "/listen/download";
            String F_DOWNLOAD_DELETE = "/listen/download/delete";
            String F_DOWNLOAD_SORT = "/listen/download/sort";
            String F_DOWNLOAD_ALBUM = "/listen/download/album";
            String F_HISTORY = "/listen/history";
            String F_FAVORITE = "/listen/favorite";
        }

        interface Test {
            String F_SPAN = "/test/span";
        }

        interface Voice {
            String MAIN = "/voice/main";

        }

        interface Projection {
            String MAIN = "/projection/main";
            String SETTING = "/projection/setting";
        }

        interface Record {
            String MAIN = "/record/main";
            String SETTING = "/record/setting";
        }

        interface Fundation {
            String MAIN = "/fundation/main";
            String DATA_COLLECT = "/fundation/data_collect";
            String SETTING = "/record/setting";
        }
    }

    interface Provider {
        String USER = "/provider/user";
        String RECORD = "/provider/record";
    }

    interface SP {
        String USER = "user";
        String TOKEN = "token";
        String HOST = "host";
        String CITY_CODE = "city_code";
        String CITY_NAME = "city_name";
        String PROVINCE_CODE = "province_code";
        String PROVINCE_NAME = "province_name";
        String PLAY_SCHEDULE_TYPE = "play_schedule_type";
        String PLAY_SCHEDULE_TIME = "play_schedule_time";
    }

    interface Default {
        String CITY_CODE = "4301";
        String CITY_NAME = "长沙";
        String PROVINCE_CODE = "430000";
        String PROVINCE_NAME = "湖南";
        String AD_NAME = "/ad.jpg";
    }

}
