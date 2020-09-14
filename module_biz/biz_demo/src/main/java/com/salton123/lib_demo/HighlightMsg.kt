package com.salton123.lib_demo

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/7 12:01
 * ModifyTime: 12:01
 * Description:
 */

class HighlightMsgBody(var highlightText: String, var skipLink: String)

class HighlightMsg(var normalText: String, var highlightReplaceText: String, var highlightMsgBodys: Array<HighlightMsgBody>)