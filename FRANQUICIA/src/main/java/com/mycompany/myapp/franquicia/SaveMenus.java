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
                    System.out.println(savedMenu);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        List<Menu> menus1= menuRepository.findAll();
        menus1.forEach(menu -> System.out.println(menu));
    }
}
