package Tutorial.misionTIC.seguridad.Repositorios;

import Tutorial.misionTIC.seguridad.Modelos.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioRol extends MongoRepository<Rol,String>{
}
