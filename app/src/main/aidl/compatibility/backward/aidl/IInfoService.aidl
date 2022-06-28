// IInfoService.aidl
package compatibility.backward.aidl;

import compatibility.backward.aidl.IInfoServiceCallback;

interface IInfoService {
    int getVersion();
    void requestInfo(int clientVersion, String text, IInfoServiceCallback callback);
}