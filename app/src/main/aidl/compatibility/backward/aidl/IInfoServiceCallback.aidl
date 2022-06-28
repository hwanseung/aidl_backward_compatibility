// IInfoServiceCallback.aidl
package compatibility.backward.aidl;

import compatibility.backward.aidl.Info;

interface IInfoServiceCallback {
     void onComplete(in Info info);
}