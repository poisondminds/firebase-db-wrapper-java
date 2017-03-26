package com.poisondminds.firebase_db_wrapper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class FIRWritableModel extends FIRModel
{
    protected abstract String getCollectionName();

    public void saveChanges()
    {
        saveChanges(null);
    }

    public void saveChanges(DatabaseReference.CompletionListener completionListener)
    {
        if (this.key == null)
        {
            throw new RuntimeException("Key cannot be null when updating FIRModel");
        }

        FirebaseDatabase.getInstance()
                .getReference(getCollectionName())
                .child(this.getKey())
                .setValue(this, completionListener);
    }

    public void create()
    {
        create(null);
    }

    public void create(DatabaseReference.CompletionListener completionListener)
    {
        if (this.key != null)
        {
            throw new RuntimeException("Cannot create a FIRModel that already has a key!");
        }

        this.key = FirebaseDatabase.getInstance().getReference(getCollectionName()).push().getKey();
        saveChanges(completionListener);
    }

    public void delete(DatabaseReference.CompletionListener completionListener)
    {
        if (this.key == null)
        {
            throw new RuntimeException("Key cannot be null when updating FIRModel");
        }

        FirebaseDatabase.getInstance()
                .getReference(getCollectionName())
                .child(this.getKey())
                .removeValue(completionListener);
    }

    public void delete()
    {
        this.delete(null);
    }
}
