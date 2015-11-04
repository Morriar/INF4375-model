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

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * A Business representation of an Album.
 */
public class Album {

    private Integer id;
    private String title;
    private String artist;
    private Boolean inStock;
    private Double price;
    private Integer year;

    public Album(Integer id, String title, String artist, Boolean inStock, Double price, Integer year) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.inStock = inStock;
        this.price = price;
        this.year = year;
    }

    // Build self from a JsonObject.
    //
    // Since albums are oftern manipulated with JSON through the API, this
    // constructor provide a useful service to build an album from a json object.
    public Album(JsonObject obj) {
        this.id = Integer.parseInt(obj.getString("id"));
        this.title = obj.getString("title");
        this.artist = obj.getString("artist");
        this.inStock = obj.getBoolean("instock");
        this.price = obj.getJsonNumber("price").doubleValue();
        this.year = obj.getInt("year");
    }

    // Translate `self` as a JsonObject.
    public JsonObject toJson() {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        obj.add("id", this.getId().toString());
        obj.add("title", this.getTitle());
        obj.add("artist", this.getArtist());
        obj.add("instock", this.getInStock());
        obj.add("price", this.getPrice());
        obj.add("year", this.getYear());
        return obj.build();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
