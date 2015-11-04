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

import java.util.List;

/**
 * Business representation of a Catalog.
 *
 * This abstraction works with any kind of DAOAlbums.
 */
public class Catalog {

    // DAO to albums
    private DAOAlbums albums;

    public Catalog(DAOAlbums albums) {
        this.albums = albums;
    }

    // Create a new Album.
    public Album createAlbum(String title, String artist, Boolean inStock, Double price, Integer year) {
        return albums.createAlbum(title, artist, inStock, price, year);
    }

    // Find an Album by its id.
    public Album findAlbum(Integer id) {
        return albums.findAlbum(id);
    }

    // Replace the album with `id` using the content of `album`.
    public Album editAlbum(Integer id, Album album) {
        return albums.editAlbum(id, album);
    }

    // Remove the album with `id`.
    public Album removeAlbum(Integer id) {
        return albums.removeAlbum(id);
    }

    // Find all albums.
    public List<Album> allAlbums() {
        return albums.allAlbums();
    }
}
