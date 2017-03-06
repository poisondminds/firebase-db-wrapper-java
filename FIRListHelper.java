package com.poisondminds.firebase_db_wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FIRListHelper
{
    public static <T> List<T> ValueListFromMap(HashMap<String, T> map)
    {
        List<T> thingList = new ArrayList<>();

        for (Map.Entry<String, T> entry : map.entrySet())
        {
            thingList.add(entry.getValue());
        }

        return thingList;
    }

    public static List<String> KeyListFromMap(HashMap<String, Boolean> map)
    {
        List<String> keyList = new ArrayList<>();

        for (Map.Entry<String, Boolean> entry : map.entrySet())
        {
            keyList.add(entry.getKey());
        }

        return keyList;
    }
}
