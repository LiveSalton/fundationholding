// package com.salton123.qa.kit;
//
// import android.view.animation.LinearInterpolator;
//
// import com.lxj.xpopup.animator.PopupAnimator;
//
// /**
//  * User: newSalton@outlook.com
//  * Date: 2019/12/16 10:51
//  * ModifyTime: 10:51
//  * Description:
//  */
// public class AlphaAnimator extends PopupAnimator {
//     @Override
//     public void initAnimator() {
//         targetView.setAlpha(0);
//         targetView.setScaleX(1.5f);
//         targetView.setScaleY(1.5f);
//     }
//
//     @Override
//     public void animateShow() {
//         targetView.animate().alpha(1).scaleX(1f).scaleY(1f).
//                 setInterpolator(new LinearInterpolator()).setDuration(300).start();
//     }
//
//     @Override
//     public void animateDismiss() {
//         targetView.animate().alpha(0).scaleX(1.5f).scaleY(1.5f).
//                 setInterpolator(new LinearInterpolator()).setDuration(300).start();
//     }
// }
