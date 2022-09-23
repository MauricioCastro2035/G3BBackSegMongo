package Tutorial.misionTIC.seguridad.Repositorios;

import Tutorial.misionTIC.seguridad.Modelos.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RepositorioUsuario extends MongoRepository<Usuario,String>{
    @Query("{'correo': ?0}")
    public Usuario getUserByEmail(String correo);
    List<Usuario> findBySeudonimo(String seudonimo);

    List<Usuario> findByCorreo(String correo);
}
