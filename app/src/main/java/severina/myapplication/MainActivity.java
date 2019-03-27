package severina.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean binded = false;
    private ProgressBarService progressBarService;

    private TextView textView;

    ServiceConnection progressBarServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ProgressBarService.ProgressBarBinder binder = (ProgressBarService.ProgressBarBinder) service;
            progressBarService = binder.getService();
            binded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binded = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ProgressBarService.class);
        this.bindService(intent, progressBarServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (binded) {
            this.unbindService(progressBarServiceConnection);
            binded = false;
        }
    }

    private void AddProgress(ProgressBar progressBar) {
        int progress = this.progressBarService.setProgress(progressBar);
        Log.d(TAG, " progress = " + progress);
        progressBar.setProgress(progress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBar.getProgress();
      AddProgress(progressBar);
    }
}
