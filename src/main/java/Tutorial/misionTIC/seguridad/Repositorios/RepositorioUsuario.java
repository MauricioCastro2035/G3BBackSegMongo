package Tutorial.misionTIC.seguridad.Repositorios;

import Tutorial.misionTIC.seguridad.Modelos.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RepositorioUsuario extends MongoRepository<Usuario,String>{

    List<Usuario> findBySeudonimo(String seudonimo);

    List<Usuario> findByCorreo(String correo);

    Usuario getUserByCorreo(String correo);
}
