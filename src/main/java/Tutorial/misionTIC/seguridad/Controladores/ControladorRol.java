package Tutorial.misionTIC.seguridad.Controladores;
import Tutorial.misionTIC.seguridad.Exceptions.AlreadyExistingObjectException;
import Tutorial.misionTIC.seguridad.Modelos.Rol;
import Tutorial.misionTIC.seguridad.Repositorios.RepositorioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/roles")
public class ControladorRol {
    @Autowired
    private RepositorioRol miRepositorioRol;
    @GetMapping("")
    public List<Rol> index(){
        return this.miRepositorioRol.findAll();
    }

    @GetMapping("{id}")
    public Rol index2(@PathVariable String id){
        return this.miRepositorioRol
                .findById(id)
                .orElse(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Rol create(@RequestBody @Valid Rol infoRol){
        List<Rol> rolNombre = this.miRepositorioRol.findByNombre(infoRol.getNombre());
        if (rolNombre.size() > 0){
            throw new AlreadyExistingObjectException("Ya existe un rol con este nombre");
        }

        return this.miRepositorioRol.save(infoRol);
    }
    @PutMapping("{id}")
    public Rol update(@PathVariable String id,@RequestBody Rol infoRol){
        List<Rol> rolNombre = this.miRepositorioRol.findByNombre(infoRol.getNombre());
        for (Rol rol:rolNombre){
            if (!rol.get_id().equals(id)){
                throw new AlreadyExistingObjectException("Ya existe un rol con este nombre");
            }
        }

        Rol rolAActualizar=this.miRepositorioRol
                .findById(id)
                .orElse(null);
        if (rolAActualizar!=null){
            rolAActualizar.setNombre(infoRol.getNombre());
            rolAActualizar.setDescripcion(infoRol.getDescripcion());
            return this.miRepositorioRol.save(rolAActualizar);
        }else{
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Rol rolAEliminar=this.miRepositorioRol
                .findById(id)
                .orElse(null);
        if (rolAEliminar!=null){
            this.miRepositorioRol.delete(rolAEliminar);
        }
    }

}

