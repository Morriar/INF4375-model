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
 * Abstract representation of a Data Access Object for albums.
 * 
 * Concrete representation are used to handle files, databases or other
 * data storage systems.
 */
public interface DAOAlbums {
    
    // Create a new Album.
    public abstract Album createAlbum(String title, String artist, Boolean inStock, Double price, Integer year);
    
    // Find an Album by its id.
    public abstract Album findAlbum(Integer id);
    
    // Replace the album with `id` using the content of `album`.
    public abstract Album editAlbum(Integer id, Album album);
    
    // Remove the album with `id`.
    public abstract Album removeAlbum(Integer id);
    
    // Find all albums.
    public abstract List<Album> allAlbums();
}
