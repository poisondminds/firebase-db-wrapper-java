package com.poisondminds.firebase_db_wrapper;

import com.google.firebase.database.Exclude;

public abstract class FIRModel
{
    @Exclude
    String key;

    public String getKey()
    {
        return key;
    }

    protected void setKey(String key)
    {
        this.key = key;
    }
}
