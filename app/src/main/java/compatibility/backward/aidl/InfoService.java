package compatibility.backward.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class InfoService extends Service {
    private Binder binder = new IInfoService.Stub() {
        @Override
        public int getVersion() throws RemoteException {
            return 1;
        }

        @Override
        public void requestInfo(int clientVersion, String text, IInfoServiceCallback callback)
                throws RemoteException {
            Info info = new Info();
            info.title = text;
            SubInfo subInfo = new SubInfo();
            subInfo.title = text + "_sub";
            info.infos.add(subInfo);
            callback.onComplete(info);
        }
    };

    public InfoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}