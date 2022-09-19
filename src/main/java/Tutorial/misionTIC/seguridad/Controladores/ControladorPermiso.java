package Tutorial.misionTIC.seguridad.Controladores;
import Tutorial.misionTIC.seguridad.Modelos.Permiso;
import Tutorial.misionTIC.seguridad.Modelos.Rol;
import Tutorial.misionTIC.seguridad.Repositorios.RepositorioPermiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
        Permiso permisoActual=this.miRepositorioPermiso
                .findById(id)
                .orElse(null);
        return permisoActual;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Permiso create(@RequestBody Permiso infoPermiso){
        return this.miRepositorioPermiso.save(infoPermiso);
    }
    @PutMapping("{id}")
    public Permiso update(@PathVariable String id,@RequestBody Permiso infoPermiso){
        Permiso permisoActual=this.miRepositorioPermiso
                .findById(id)
                .orElse(null);
        if (permisoActual!=null){
            permisoActual.setUrl(infoPermiso.getUrl());
            permisoActual.setMetodo(infoPermiso.getMetodo());
            return this.miRepositorioPermiso.save(permisoActual);
        }else{
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
