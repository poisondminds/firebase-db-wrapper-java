package com.poisondminds.firebase_db_wrapper;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public abstract class FIRStorageDownloadable
{
    public abstract String getLocation();

    public void getDownloadUrl(OnSuccessListener<Uri> onSuccess)
    {
        StorageReference ref = FirebaseStorage.getInstance().getReference(getLocation());
        ref.getDownloadUrl().addOnSuccessListener(onSuccess);
    }
}
