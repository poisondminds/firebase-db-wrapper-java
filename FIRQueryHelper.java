package com.poisondminds.firebase_db_wrapper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FIRQueryHelper
{
    public static void All(String collectionName, ValueEventListener valueEventListener)
    {
        DatabaseReference artistsRef = FirebaseDatabase.getInstance().getReference(collectionName);
        artistsRef.addValueEventListener(valueEventListener);
    }

    public static void FromKey(String collectionName, String key, ValueEventListener valueEventListener)
    {
        DatabaseReference artistsRef = FirebaseDatabase.getInstance().getReference(collectionName);
        Query q = artistsRef.orderByKey().equalTo(key);
        q.addListenerForSingleValueEvent(valueEventListener);
    }

    public static void WhereFieldEquals(String collectionName, String field, String value, ValueEventListener valueEventListener)
    {
        DatabaseReference artistsRef = FirebaseDatabase.getInstance().getReference(collectionName);
        Query q = artistsRef.orderByChild(field).equalTo(value);
        q.addValueEventListener(valueEventListener);
    }

    public static void WhereFieldEquals(String collectionName, String field, boolean value, ValueEventListener valueEventListener)
    {
        DatabaseReference artistsRef = FirebaseDatabase.getInstance().getReference(collectionName);
        Query q = artistsRef.orderByChild(field).equalTo(value);
        q.addValueEventListener(valueEventListener);
    }

    public static void WhereFieldEquals(String collectionName, String field, double value, ValueEventListener valueEventListener)
    {
        DatabaseReference artistsRef = FirebaseDatabase.getInstance().getReference(collectionName);
        Query q = artistsRef.orderByChild(field).equalTo(value);
        q.addValueEventListener(valueEventListener);
    }
}
