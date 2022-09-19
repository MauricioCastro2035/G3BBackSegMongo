package Tutorial.misionTIC.seguridad.Repositorios;


import Tutorial.misionTIC.seguridad.Modelos.PermisosRoles;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioPermisosRoles extends MongoRepository<PermisosRoles,String> {
}
