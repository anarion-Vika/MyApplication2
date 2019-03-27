package severina.myapplication;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.ProgressBar;

public class ProgressBarService extends Service {

    private String TAG = ProgressBarService.class.getSimpleName();

    private final IBinder binder = new ProgressBarBinder();

    public  class ProgressBarBinder extends Binder{
        public ProgressBarService getService() {
            return ProgressBarService.this;
        }
    }


    public ProgressBarService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return this.binder;
    }

    public int setProgress(ProgressBar progressBar) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        int progress = progressBar.getProgress();
        if (progress == 100) {
            progress = 5;
            return progress;
        }else
            progress = progress +5;

        return progress;

    }


}
