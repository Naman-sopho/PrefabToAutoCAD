package com.prefabToAutoCad;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();

        File structurePrefab = new File(args[0]);
        BufferedReader reader = new BufferedReader(new FileReader(structurePrefab));

        StringBuilder json = new StringBuilder();
        String input = reader.readLine();
        while (input != null) {
            json.append(input);
            input = reader.readLine();
        }

        SpawnBlockRegions spawnBlockRegions = gson.fromJson(json.toString(), SpawnBlockRegions.class);
        List<BlocksToFill> regions = spawnBlockRegions.regionsToFill;

        PrefabToAutoCad prefabToAutoCad = new PrefabToAutoCad(regions);
    }
}
