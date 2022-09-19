package Tutorial.misionTIC.seguridad.Controladores;
import Tutorial.misionTIC.seguridad.Modelos.Rol;
import Tutorial.misionTIC.seguridad.Modelos.Usuario;
import Tutorial.misionTIC.seguridad.Repositorios.RepositorioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
        Rol rolActual=this.miRepositorioRol
                .findById(id)
                .orElse(null);
        return rolActual;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Rol create(@RequestBody Rol infoRol){

        return this.miRepositorioRol.save(infoRol);
    }
    @PutMapping("{id}")
    public Rol update(@PathVariable String id,@RequestBody Rol infoRol){
        Rol rolActual=this.miRepositorioRol
                .findById(id)
                .orElse(null);
        if (rolActual!=null){
            rolActual.setNombre(infoRol.getNombre());
            rolActual.setDescripcion(infoRol.getDescripcion());
            return this.miRepositorioRol.save(rolActual);
        }else{
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Rol rolActual=this.miRepositorioRol
                .findById(id)
                .orElse(null);
        if (rolActual!=null){
            this.miRepositorioRol.delete(rolActual);
        }
    }

}

