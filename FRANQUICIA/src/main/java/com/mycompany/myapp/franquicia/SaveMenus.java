package com.mycompany.myapp.franquicia;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.myapp.domain.Menu;
import com.mycompany.myapp.repository.MenuRepository;

//If the JSON data points to an existing object in the database and some properties of that object are changed,
// calling the menuRepository.save(newMenu) method will update the existing object's properties with the new values
// provided in newMenu.

//The save method performs an INSERT operation if the object with the specified id value does not exist in the
// database, and an UPDATE operation if it does. Therefore, if the id of the newMenu object already exists 
//in the database, the save method will update the corresponding object with the new property values.

//In summary, calling menuRepository.save(newMenu) with an object that has the same id as an 
//existing object in the database will result in an update of the existing object's properties.

@Component
public class SaveMenus {

    @Autowired
    private UtilsJulia utilsJulia;

    @Autowired
    private MenuRepository menuRepository;

    public void saveMenus() throws Exception {

        try {

            String jsonString = utilsJulia.sendHttpPostRequest();
            // Assuming the JSON string is stored in the 'jsonString' variable
            JSONObject jsonObj = new JSONObject(jsonString);
            JSONArray menus = jsonObj.getJSONArray("menus");
            for (int i = 0; i < menus.length(); i++) {
                JSONObject menu = menus.getJSONObject(i);

                Menu newMenu = new Menu();

                int id = menu.getInt("id");
                newMenu.setId(String.valueOf(id));

                String nombre = menu.getString("nombre");
                newMenu.setNombre(nombre);

                String descripcion = menu.getString("descripcion");
                newMenu.setDescripcion(descripcion);

                double precio = menu.getDouble("precio");
                newMenu.setPrecio(precio);

                String urlImagen = menu.getString("urlImagen");
                newMenu.setUrlImagen(urlImagen);

                Boolean activo = menu.getBoolean("activo");
                newMenu.setActivo(activo);

                String creadoStr = menu.getString("creado");
                Instant creado = Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse(creadoStr));
                newMenu.setCreado(creado);

                String actualizadoStr = menu.getString("actualizado");
                Instant actualizado = Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse(actualizadoStr));
                newMenu.setActualizado(actualizado);

                try {
                    Menu savedMenu = menuRepository.save(newMenu);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        //The end result will be that the database will have the latest version of all menus with their properties 
        // updated to the latest values provided in the new JSON.
        List<Menu> menus1= menuRepository.findAll();
        //menus1.forEach(menu -> System.out.println(menu));
        System.out.println("FROM SaveMenus.java");
        menus1.forEach(menu -> System.out.println("Nombre: " + menu.getNombre() + ", Precio: " + menu.getPrecio()));

    }
}
