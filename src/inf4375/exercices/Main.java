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
package inf4375.exercices;

import inf4375.model.Catalog;
import inf4375.model.JsonDAOAlbums;
import inf4375.server.controllers.AlbumsController;
import inf4375.server.Router;

/**
 * Example of REST service based on JSON.
 */
public class Main {

    public static void main(String[] args) {
        JsonDAOAlbums dao = new JsonDAOAlbums();
        Catalog catalog = new Catalog(dao);
        Router router = new Router(8080);
        router.controllers.add(new AlbumsController(catalog));
        router.start();
    }
}
