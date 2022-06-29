package compatibility.backward.aidl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler = null;
    private boolean bound = false;
    private IInfoService mInfoService = null;
    private TextView mEditText = null;
    private TextView mInfoText = null;
    private TextView mSubInfoText = null;
    private IInfoServiceCallback mCallback = new IInfoServiceCallback.Stub() {
        @Override
        public void onComplete(Info info) {
            updateTextView(info.title, info.infos.get(0).title);
        }
    };

    private void updateTextView(String info, String subInfo) {
        mHandler.post(() -> {
            mInfoText.setText(info);
            mSubInfoText.setText(subInfo);
        });
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mInfoService = IInfoService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mInfoService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        mEditText = ActivityCompat.requireViewById(this, R.id.edit_text);
        View bindButton = ActivityCompat.requireViewById(this, R.id.bind);
        View unBindButton = ActivityCompat.requireViewById(this, R.id.unbind);
        View requestButton = ActivityCompat.requireViewById(this, R.id.request);

        mInfoText = ActivityCompat.requireViewById(this, R.id.info);
        mSubInfoText = ActivityCompat.requireViewById(this, R.id.sub_info);

        updateTextView("init", "");

        bindButton.setOnClickListener(view -> {
            if (!bound) {
                Intent intent = new Intent(this, InfoService.class);
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                bound = true;
                updateTextView("bind", "");
            }
        });

        unBindButton.setOnClickListener(view -> {
            if (bound) {
                unbindService(mConnection);
                updateTextView("unbind", "");
            }
        });

        requestButton.setOnClickListener(view -> {
            if (bound) {
                try {
                    mInfoService.requestInfo(0, mEditText.getText().toString(), mCallback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}