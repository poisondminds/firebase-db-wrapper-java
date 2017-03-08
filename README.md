# firebase-db-wrapper-java
An easy-to-use wrapper for Firebase's Realtime Database

**Dependencies:** FirebaseDatabase, FirebaseStorage

For demonstration purposes, we'll use the database structure defined below, comprised of murals and artists:
```json
{
  "murals" : 
  {
    "-KaQYfs3kbt4XgDY0ftb" : 
    {
      "artists" : 
      {
        "-KbJbPknFNECn07m1yzy" : true,
        "-KbJXK4aoXc6NZ6VwD7W" : true
      },
      "description" : "A beautiful mural in Orange, CA",
      "images" : 
      {
        "m1" : 
        {
          "location" : "/murals/m1.jpg"
        },
        "m2" : 
        {
          "location" : "/murals/m2.jpg"
        }
      },
      "name" : "A really great mural"
    }
  },
  "artists" : 
  {
    "-KbJXK4aoXc6NZ6VwD7W" : 
    {
      "country" : "US",
      "firstName" : "Mary",
      "lastName" : "Smith"
    },
    "-KbJbPknFNECn07m1yzy" : 
    {
      "country" : "US",
      "firstName" : "Kerry",
      "lastName" : "Winston"
    }
}
```
## `FIRModel` Usage
Use `FIRModel` for your already Firebase-matching models to additionally include the `key` of the snapshot.
```java
public class MuralModel extends FIRModel
{
    private String name;
    private String description;
    private HashMap<String, Boolean> artists;
    private HashMap<String, ImageModel> images;
    
    public static MuralModel FromSnapshot(DataSnapshot snapshot)
    {
        MuralModel mural = snapshot.getValue(MuralModel.class);
        mural.setKey(snapshot.getKey());
        return mural;
    }
    
    ...
}
```
The most important thing to note here is how a `MuralModel` should be created from a `DataSnapshot`.
```java
MuralModel mural = MuralModel.FromSnapshot(snapshot);
```

## `FIRListHelper` Usage
For ease of use interfacing with `HashMap` objects deserialized from `DataSnapshot` objects, use `FIRListHelper` to convert them into key lists or value lists.
```java
public class MuralModel extends FIRModel
{
    ...
    
    private HashMap<String, Boolean> artists;
    private HashMap<String, ImageModel> images;
    
    ...
    
    public List<ImageModel> getImages()
    {
        return FIRListHelper.ValueListFromMap(this.images);
    }
    
    public List<String> getArtists()
    {
        return FIRListHelper.KeyListFromMap(this.artists);
    }
}
```

## `FIRModelEventListener` Usage
`FIRModelEventListener` is meant to wrap Firebase's `ValueEventListener` in order to retrieve a list of strongly typed `FIRModel` objects. Create your own `FIRModelEventListener` like shown below:
```java
public abstract class MuralValueEventListener extends ModelValueEventListener<MuralModel>
{
    public MuralValueEventListener()
    {
        super(MuralModel.class);
    }
}
```
And use your listener like so:
```java
DatabaseReference muralsReference = FirebaseDatabase.getInstance().getReference("murals");
muralsReference.addValueEventListener(new MuralValueEventListener()
{
    @Override
    public void onDataChange(List<MuralModel> models)
    {
       // Do stuff here
    }
    @Override
    public void onCancelled(DatabaseError databaseError)
    {
        // Uh oh
    }
});
```
Obviously this only makes sense if your collection items actually match your `FIRModel` in your `FIRModelEventListener`.
**Note**: `MuralEventListener` will also set the `key` of each `FIRModel` it returns.

## `FIRQueryHelper` Usage
`FIRQueryHelper` wraps a few basic queries, like the most recent example, making them much simpler:
```java
FIRQueryHelper.All("murals", new MuralValueEventListener()
{
    @Override
    public void onDataChange(List<MuralModel> models)
    {
       // Do stuff here
    }
    @Override
    public void onCancelled(DatabaseError databaseError)
    {
        // Uh oh
    }
});
```
I personally like to take it a step further and do this:
```java
public class MuralModel extends FIRModel
{
    private static final String COLLECTION_NAME = "murals";
    
    ...
    
    public static void All(MuralValueEventListener valueEventListener)
    {
        FIRQueryHelper.All(COLLECTION_NAME, valueEventListener);
    }
    
    public static void FromKey(String key, MuralValueEventListener valueEventListener)
    {
        FIRQueryHelper.FromKey(COLLECTION_NAME, key, valueEventListener);
    }
    
    ...
}
```
Then later ...
```java
MuralModel.All(new MuralValueEventListener()
{
    @Override
    public void onDataChange(List<MuralModel> models)
    {
       // Do stuff here
    }
    @Override
    public void onCancelled(DatabaseError databaseError)
    {
        // Uh oh
    }
});
```

## `FIRWritableModel` Usage
```java
public class ArtistModel extends FIRWritableModel
{
    private String firstName;
    private String lastName;
    private String country;
    
    public ArtistModel(String first, String last, String country)
    {
        this.firstName = first;
        this.lastName = last;
        this.country = country;
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    @Override
    protected String getCollectionName()
    {
        return "artists";
    }
}
```
For saving changes to existing database objects with `key != null` (created with `FromSnapshot()` or using a `FIRModelEventListener`):
```java
ArtistModel artist = ArtistModel.FromSnapshot(snapshot);
artist.setFirstName("John");
artist.saveChanges();
```
For creating new items, use a public constructor and `create()`:
```java
ArtistModel artist = new ArtistModel("John", "Doe", "US");
artist.create();
```
Both `saveChanges()` and `create()` additionally have overloads that take completion listeners.

Enjoy!
