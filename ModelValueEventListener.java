package edu.chapman.cpsc370.muraltest.firebase_db_wrapper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelValueEventListener<T extends FIRModel> implements ValueEventListener
{
    private Class<T> type;

    public abstract void onDataChange(List<T> models);

    public ModelValueEventListener(Class<T> type)
    {
        this.type = type;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot)
    {
        List<T> models = new ArrayList<>();

        for (DataSnapshot snapshot : dataSnapshot.getChildren())
        {
            T model = snapshot.getValue(this.type);
            model.setKey(snapshot.getKey());

            models.add(model);
        }

        onDataChange(models);
    }
}
