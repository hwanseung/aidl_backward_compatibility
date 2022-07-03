package compatibility.backward.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLException;
import com.yausername.youtubedl_android.YoutubeDLRequest;
import com.yausername.youtubedl_android.mapper.VideoInfo;

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
            info.title = getInfo(text);
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

    private String getInfo(String url) {
        try {
            YoutubeDLRequest request = new YoutubeDLRequest(url);
            request.addOption("-f", "best");
            VideoInfo streamInfo = YoutubeDL.getInstance().getInfo(request);
            Log.i("youtube_dl", "url : " + streamInfo.getFormats().get(0).getUrl());
            Log.i("youtube_dl", "url : " + streamInfo.getUrl());
            Log.i("youtube_dl", "format size : " + streamInfo.getFormats().size());
            return streamInfo.getUrl();
        } catch (YoutubeDLException | InterruptedException e) {
            Log.i("Test", e.toString());
        }
        return "";
    }

    @Override
    public void onCreate() {
        try {
            YoutubeDL.getInstance().init(getApplication());
        } catch (YoutubeDLException e) {
            Log.e("Test", "failed to initialize youtubedl-android", e);
        }
    }
}