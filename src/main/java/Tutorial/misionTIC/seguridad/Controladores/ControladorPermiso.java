package Tutorial.misionTIC.seguridad.Controladores;
import Tutorial.misionTIC.seguridad.Exceptions.AlreadyExistingObjectException;
import Tutorial.misionTIC.seguridad.Exceptions.NotValidMethodException;
import Tutorial.misionTIC.seguridad.Modelos.Permiso;
import Tutorial.misionTIC.seguridad.Repositorios.RepositorioPermiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/permiso")
public class ControladorPermiso {
    @Autowired
    private RepositorioPermiso miRepositorioPermiso;
    @GetMapping("")
    public List<Permiso> index(){
        return this.miRepositorioPermiso.findAll();
    }

    @GetMapping("{id}")
    public Permiso index2(@PathVariable String id){
        return this.miRepositorioPermiso
                .findById(id)
                .orElse(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Permiso create(@RequestBody @Valid Permiso infoPermiso){
        String metodo = infoPermiso.getMetodo();
        if (Arrays.stream(Permiso.allowedMethods).noneMatch(metodo::equals)) {

            throw new NotValidMethodException("Los metodos permitidos son los siguientes: " + Arrays.toString(Permiso.allowedMethods));
        }

        List<Permiso> permisoDuplicado = this.miRepositorioPermiso.findByUrlAndMetodo(infoPermiso.getUrl(), infoPermiso.getMetodo());
        if (permisoDuplicado.size() > 0){
            throw new AlreadyExistingObjectException("Ya existe un permiso igual a este");
        }

        return this.miRepositorioPermiso.save(infoPermiso);
    }
    @PutMapping("{id}")
    public Permiso update(@PathVariable String id,@RequestBody Permiso infoPermiso){

        String metodo = infoPermiso.getMetodo();
        if (Arrays.stream(Permiso.allowedMethods).noneMatch(metodo::equals)) {

            throw new NotValidMethodException("Los metodos permitidos son los siguientes: " + Arrays.toString(Permiso.allowedMethods));
        }

        List<Permiso> permisoDuplicado = this.miRepositorioPermiso.findByUrlAndMetodo(infoPermiso.getUrl(), infoPermiso.getMetodo());
        for (Permiso permiso:permisoDuplicado) {
            if (!permiso.get_id().equals(id)){
                throw new AlreadyExistingObjectException("Ya existe un permiso igual a este");
            }
        }

        Permiso permisoAModificar=this.miRepositorioPermiso
                .findById(id)
                .orElse(null);
        if (permisoAModificar!=null){
            permisoAModificar.setUrl(infoPermiso.getUrl());
            permisoAModificar.setMetodo(infoPermiso.getMetodo());
            return this.miRepositorioPermiso.save(permisoAModificar);
        }
        else{
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Permiso usuarioActual=this.miRepositorioPermiso
                .findById(id)
                .orElse(null);
        if (usuarioActual!=null){
            this.miRepositorioPermiso.delete(usuarioActual);
        }
    }

}
