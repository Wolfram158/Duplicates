// IDeleteDuplicatesInterface.aidl
package ru.yadro.contacts_core;

import ru.yadro.contacts_core.DeleteDuplicatesResult;
import ru.yadro.contacts_core.IDeleteDuplicatesCallback;

interface IDeleteDuplicatesInterface {
    void deleteDuplicates(IDeleteDuplicatesCallback callback);
}