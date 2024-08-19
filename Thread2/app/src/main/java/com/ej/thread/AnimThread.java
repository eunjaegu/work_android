package com.ej.thread;

import android.graphics.drawable.Drawable;

class AnimThread extends Thread {
    private final MainActivity mainActivity;

    public AnimThread(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void run() {
        int index = 0;
        for (int i = 0; i < 100; i++) {
            final Drawable drawable = mainActivity.drawableList.get(index);
            index += 1;
            if (index > 4) {
                index = 0;
            }

            mainActivity.handler.post(new Runnable() {
                public void run() {
                    mainActivity.imageView.setImageDrawable(drawable);
                }
            });

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
