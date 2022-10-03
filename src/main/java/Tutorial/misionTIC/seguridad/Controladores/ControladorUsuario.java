package Tutorial.misionTIC.seguridad.Controladores;
import Tutorial.misionTIC.seguridad.Exceptions.AlreadyExistingObjectException;
import Tutorial.misionTIC.seguridad.Modelos.Usuario;
import Tutorial.misionTIC.seguridad.Modelos.Rol;
import Tutorial.misionTIC.seguridad.Repositorios.RepositorioRol;
import Tutorial.misionTIC.seguridad.Repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.validation.Valid;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class ControladorUsuario {


    @Autowired
    private RepositorioUsuario miRepositorioUsuario;
    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("")
    public List<Usuario> index(){
        return this.miRepositorioUsuario.findAll();
    }


    @GetMapping("{id}")
    public Usuario index2(@PathVariable String id){
        return this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Usuario create(@RequestBody @Valid Usuario infoUsuario){
        List<Usuario> seudonimoUser = this.miRepositorioUsuario.findBySeudonimo(infoUsuario.getSeudonimo());
        if (seudonimoUser.size() > 0){
            throw new AlreadyExistingObjectException("Ya existe un usuario con este seudonimo");
        }

        List<Usuario> correoUser = this.miRepositorioUsuario.findByCorreo(infoUsuario.getCorreo());
        if (correoUser.size() > 0){
            throw new AlreadyExistingObjectException("Ya existe un usuario con este correo");
        }

        infoUsuario.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
        return this.miRepositorioUsuario.save(infoUsuario);
    }

    @PostMapping("/validar")
    public Usuario validate(@RequestBody Usuario infoUsuario,final HttpServletResponse response) throws IOException {
        Usuario usuarioActual=this.miRepositorioUsuario.getUserByCorreo(infoUsuario.getCorreo());
        if (usuarioActual!=null &&
                usuarioActual.getContrasena().equals(convertirSHA256(infoUsuario.getContrasena()))) {
            usuarioActual.setContrasena("");
            return usuarioActual;
        }else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }

    @PutMapping("{id}")
    public Usuario update(@PathVariable String id,@RequestBody Usuario infoUsuario){
        Usuario usuarioAActualizar=this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
        List<Usuario> seudonimoUser = this.miRepositorioUsuario.findBySeudonimo(infoUsuario.getSeudonimo());
        for (Usuario usuario:seudonimoUser){
            if (!usuario.get_id().equals(id)){
                throw new AlreadyExistingObjectException("Ya existe un usuario con este seudonimo");
            }
        }

        List<Usuario> correoUser = this.miRepositorioUsuario.findByCorreo(infoUsuario.getCorreo());
        for (Usuario usuario:correoUser){
            if (!usuario.get_id().equals(id)){
                throw new AlreadyExistingObjectException("Ya existe un usuario con este correo");
            }
        }

        if (usuarioAActualizar!=null){
            usuarioAActualizar.setSeudonimo(infoUsuario.getSeudonimo());
            usuarioAActualizar.setCorreo(infoUsuario.getCorreo());
            usuarioAActualizar.setContrasena(convertirSHA256(infoUsuario.getContrasena()));
            return this.miRepositorioUsuario.save(usuarioAActualizar);
        }else{
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Usuario usuarioAEliminar=this.miRepositorioUsuario
                .findById(id)
                .orElse(null);
        if (usuarioAEliminar!=null){
            this.miRepositorioUsuario.delete(usuarioAEliminar);
        }
    }

    /*Relacion 1 a n entre rol y usuario*/

    @PutMapping("{id}/rol/{id_rol}")
    public Usuario asignarRolAUsuario(@PathVariable String id,@PathVariable String id_rol){
        Usuario usuarioAAsignarRol=this.miRepositorioUsuario.findById(id).orElseThrow(RuntimeException::new);
        Rol rolActual=this.miRepositorioRol.findById(id_rol).orElseThrow(RuntimeException::new);
        usuarioAAsignarRol.setRol(rolActual);
        return this.miRepositorioUsuario.save(usuarioAAsignarRol);
    }

    public String convertirSHA256(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }
}
