package altssc

import grails.transaction.Transactional

@Transactional
class InitService {

    def init() {

        log.println("creando roles")
        def userRole = Rol.findByAutoridad("ROLE_USER") ?: new Rol(autoridad: "ROLE_USER").save(failOnError: true, flush:true)
        def adminRole = Rol.findByAutoridad("ROLE_ADMIN") ?: new Rol(autoridad: "ROLE_ADMIN").save(failOnError: true, flush:true)

//----------------------------------------------------------------------------------------------------------------------

        log.println("creando Admin")
        def adminUser = Usuario.findByNombre('admin') ?: new Usuario(
                nombre: 'admin',
                apellido: 'admin',
                mail: 'admin@admin').save(failOnError: true, flush:true)

        if (!UsuarioRol.findAllByUsuarioAndRol(adminUser, adminRole)){
            adminUser.addToRoles(rol: adminRole)
        }

        log.println("creando User")
        def userUser = Usuario.findByNombre('user') ?: new Usuario(
                nombre: 'user',
                apellido: 'user',
                mail: 'user@user').save(failOnError: true)
        if (!UsuarioRol.findAllByUsuarioAndRol(userUser, userRole)){
            userUser.addToRoles(rol: userRole)
        }

        log.println("creando Sudo")
        def sudoUser = Usuario.findByNombre('sudo') ?: new Usuario(
                nombre: 'sudo',
                apellido: 'sudo',
                mail: 'sudo@sudo').save(failOnError: true)
        if (!UsuarioRol.findAllByUsuarioAndRol(sudoUser, adminRole)){
            sudoUser.addToRoles(rol: adminRole)
        }
        if (!UsuarioRol.findAllByUsuarioAndRol(sudoUser, userRole)){
            sudoUser.addToRoles(rol: userRole)
        }

        log.println("creando Marcos")
        def marcos = Usuario.findByNombre('marcos') ?: new Usuario(
                nombre: 'marcos',
                apellido: 'marcos',
                mail: 'marcos.girardi@uccuyosl.edu.ar').save(failOnError: true)
        if (!UsuarioRol.findAllByUsuarioAndRol(marcos, userRole)){
            marcos.addToRoles(rol: userRole)
        }

//----------------------------------------------------------------------------------------------------------------------

        log.println("Usuarios y roles creados")

    }
}
