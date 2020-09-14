// /*
//  * Copyright (C) 2016 MarkZhai (http://zhaiyifan.cn).
//  *
//  * Licensed under the Apache License, Version 2.0 (the "License");
//  * you may not use this file except in compliance with the License.
//  * You may obtain a copy of the License at
//  *
//  *     http://www.apache.org/licenses/LICENSE-2.0
//  *
//  * Unless required by applicable law or agreed to in writing, software
//  * distributed under the License is distributed on an "AS IS" BASIS,
//  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  * See the License for the specific language governing permissions and
//  * limitations under the License.
//  */
// package com.salton123.qa.kit.block;
//
// import android.content.Context;
//
// /**
//  * User should provide a real implementation of this class to use BlockCanary.
//  */
// public class BlockCanaryContext {
//
//     private static BlockCanaryContext sInstance = null;
//
//     public static BlockCanaryContext get() {
//         if (sInstance == null) {
//             throw new RuntimeException("BlockCanaryContext null");
//         } else {
//             return sInstance;
//         }
//     }
//
//     /**
//      * Provide application context.
//      */
//     public Context provideContext() {
//         return sApplicationContext;
//     }
//
//     /**
//      * Implement in your project.
//      *
//      * @return Qualifier which can specify this installation, like version + flavor.
//      */
//     public String provideQualifier() {
//         return "unknown";
//     }
//
//     /**
//      * Config block threshold (in millis), dispatch over this duration is regarded as a BLOCK. You may set it
//      * from performance of device.
//      *
//      * @return threshold in mills
//      */
//     public int provideBlockThreshold() {
//         return 1000;
//     }
//
//     /**
//      * Thread stack dump interval, use when block happens, BlockCanary will dump on main thread
//      * stack according to current sample cycle.
//      * <p>
//      * Because the implementation mechanism of Looper, real dump interval would be longer than
//      * the period specified here (especially when cpu is busier).
//      * </p>
//      *
//      * @return dump interval (in millis)
//      */
//     public int provideDumpInterval() {
//         return provideBlockThreshold();
//     }
//
//     /**
//      * Path to save log, like "/blockcanary/", will save to sdcard if can.
//      *
//      * @return path of log files
//      */
//     public String providePath() {
//         return "/blockcanary/";
//     }
//
//     /**
//      * Whether to stop monitoring when in debug mode.
//      *
//      * @return true if stop, false otherwise
//      */
//     public boolean stopWhenDebugging() {
//         return true;
//     }
// }