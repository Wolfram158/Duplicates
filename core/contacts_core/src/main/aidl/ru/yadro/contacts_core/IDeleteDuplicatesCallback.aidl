// IDeleteDuplicatesCallback.aidl
package ru.yadro.contacts_core;

import ru.yadro.contacts_core.DeleteDuplicatesResult;

oneway interface IDeleteDuplicatesCallback {
    void onResult(DeleteDuplicatesResult result);
}