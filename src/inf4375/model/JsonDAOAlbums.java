/*
 * Copyright 2015 Alexandre Terrasa <alexandre@moz-code.org>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package inf4375.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * An implementation of a DAOAlbums using JSON files.
 *
 * Catalog is stored as a single JSON file.
 */
public class JsonDAOAlbums implements DAOAlbums {

    String catalogFile = "json/catalog.json";

    // Load the JsonArray Catalog from the catalogFile.
    private JsonArray loadCatalog() {
        File jsonFile = new File(catalogFile);
        try (FileInputStream inputStream = new FileInputStream(jsonFile)) {
            try (JsonReader reader = Json.createReader(inputStream)) {
                return reader.readArray();
            }
        } catch (IOException ex) {
            System.err.println("Error: unable to load catalog at " + jsonFile);
            System.err.println(ex.getMessage());
            System.exit(1);
            return null;
        }
    }

    // Save the JsonArray Catalog into the catalogFile.
    private void saveCatalog(JsonArray catalog) {
        try (FileWriter fw = new FileWriter(catalogFile)) {
            fw.write(catalog.toString());
        } catch (IOException ex) {
            System.err.println("Error: unable to write catalog at " + catalogFile);
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    @Override
    public Album createAlbum(String title, String artist, Boolean inStock, Double price, Integer year) {
        JsonArray catalog = loadCatalog();
        JsonArrayBuilder newcat = Json.createArrayBuilder();
        for (JsonObject album : catalog.getValuesAs(JsonObject.class)) {
            newcat.add(album);
        }
        Integer id = catalog.size();
        Album album = new Album(id, title, artist, inStock, price, year);
        newcat.add(album.toJson());
        saveCatalog(newcat.build());
        return album;
    }

    @Override
    public Album findAlbum(Integer id) {
        JsonArray catalog = loadCatalog();
        if (id < 0 || id >= catalog.size()) {
            return null;
        }
        JsonObject obj = catalog.getJsonObject(id);
        return new Album(obj);
    }

    @Override
    public Album editAlbum(Integer id, Album album) {
        JsonArray catalog = loadCatalog();
        JsonArrayBuilder newcat = Json.createArrayBuilder();
        Album orig = null;
        for (JsonObject oalbum : catalog.getValuesAs(JsonObject.class)) {
            if (oalbum.getString("id").equals(id.toString())) {
                newcat.add(album.toJson());
                orig = album;
                continue;
            }
            newcat.add(oalbum);
        }
        saveCatalog(newcat.build());
        return orig;
    }

    @Override
    public Album removeAlbum(Integer id) {
        JsonArray catalog = loadCatalog();
        JsonArrayBuilder newcat = Json.createArrayBuilder();
        Album orig = null;
        for (JsonObject album : catalog.getValuesAs(JsonObject.class)) {
            if (album.getString("id").equals(id.toString())) {
                orig = new Album(album);
                continue;
            }
            newcat.add(album);
        }
        saveCatalog(newcat.build());
        return orig;
    }

    @Override
    public List<Album> allAlbums() {
        JsonArray catalog = loadCatalog();
        ArrayList<Album> albums = new ArrayList<>();
        for (JsonObject album : catalog.getValuesAs(JsonObject.class)) {
            albums.add(new Album(album));
        }
        return albums;
    }
}
