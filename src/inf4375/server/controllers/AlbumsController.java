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
package inf4375.server.controllers;

import inf4375.model.Album;
import inf4375.model.Catalog;
import inf4375.server.Request;
import inf4375.server.Router;
import inf4375.server.UriMatchController;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * A Controller that displays the catalog in JSON format.
 */
public class AlbumsController extends UriMatchController {

    // Data Access Object to album list.
    Catalog catalog;

    public AlbumsController(Catalog catalog) {
        this.uriMatch = "^/albums(/?$|/.*)";
        this.catalog = catalog;
    }

    @Override
    public void action(Router router, Request request) {
        Integer id = extractID(request.uri);
        switch (request.method) {
            case "GET":
                if (id == null) {
                    router.sendJsonError(400, "Bad request");
                    return;
                }
                actionGetAlbum(router, request, id);
                return;
            case "POST":
                actionPostAlbum(router, request);
                return;
            case "PUT":
                if (id == null) {
                    router.sendJsonError(400, "Bad request");
                    return;
                }
                actionPutAlbum(router, request, id);
                return;
            case "DELETE":
                if (id == null) {
                    router.sendJsonError(400, "Bad request");
                    return;
                }
                actionDeleteAlbum(router, request, id);
                return;
        }
        router.sendJsonError(400, "Bad request");
    }

    // Extravct the id from an Request::uri.
    private Integer extractID(String uri) {
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(uri);
        if (!matcher.find()) {
            return null;
        }
        return Integer.parseInt(matcher.group(1));
    }

    // Returns the JSON Object representing the album having `id`.
    private void actionGetAlbum(Router router, Request request, Integer id) {
        Album album = catalog.findAlbum(id);
        if (album == null) {
            router.sendJsonError(404, "Not found");
            return;
        }
        router.sendJsonResponse(200, "OK", album.toJson());
    }

    // Create a new album from a JsonRequest.
    //
    // Display the received album.
    private void actionPostAlbum(Router router, Request request) {
        JsonReader reader = Json.createReader(new StringReader(request.body));
        JsonObject obj = reader.readObject();
        String title = obj.getString("title");
        String artist = obj.getString("artist");
        Boolean inStock = obj.getBoolean("instock");
        Double price = obj.getJsonNumber("price").doubleValue();
        Integer year = obj.getInt("year");
        Album album = catalog.createAlbum(title, artist, inStock, price, year);
        router.sendJsonResponse(201, "Created", album.toJson());
    }

    // Update the existing album with `id`.
    //
    // Display the modified album.
    private void actionPutAlbum(Router router, Request request, Integer id) {
        JsonReader reader = Json.createReader(new StringReader(request.body));
        JsonObject obj = reader.readObject();
        Album album = catalog.findAlbum(id);
        if (album == null) {
            // No album with this ID found, return error
            router.sendJsonError(404, "Not found");
            return;
        }
        if (obj.containsKey("title")) {
            album.setTitle(obj.getString("title"));
        }
        if (obj.containsKey("artist")) {
            album.setArtist(obj.getString("artist"));
        }
        if (obj.containsKey("instock")) {
            album.setInStock(obj.getBoolean("instock"));
        }
        if (obj.containsKey("price")) {
            album.setPrice(obj.getJsonNumber("price").doubleValue());
        }
        if (obj.containsKey("year")) {
            album.setYear(obj.getInt("year"));
        }
        reader.close();
        Album edited = catalog.editAlbum(id, album);
        router.sendJsonResponse(200, "OK", edited.toJson());
    }

    // Update the existing album with `id`.
    //
    // Display the deleted album.
    private void actionDeleteAlbum(Router router, Request request, Integer id) {
        Album album = catalog.removeAlbum(id);
        if (album == null) {
            router.sendJsonError(404, "Not found");
            return;
        }
        router.sendJsonResponse(200, "OK", album.toJson());
    }
}
