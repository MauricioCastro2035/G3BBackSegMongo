package Tutorial.misionTIC.seguridad.Repositorios;

import Tutorial.misionTIC.seguridad.Modelos.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioUsuario extends MongoRepository<Usuario,String>{
}
