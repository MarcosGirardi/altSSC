package altssc

import grails.transaction.Transactional
import java.security.*                      //para hashear con salt
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;

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

//----------------------------------------------------------------------------------------------------------------------

        def source = "Hello. This is my secret message. That should be hashed."
        def secret = "super-secret-string"

        String.metaClass.toSHA1 = { salt = "" ->
            def messageDigest = MessageDigest.getInstance("SHA1")

            messageDigest.update(salt.getBytes())
            messageDigest.update(delegate.getBytes())

        /*
         * Why pad up to 40 characters? Because SHA-1 has an output
         * size of 160 bits. Each hexadecimal character is 4-bits.
         * 160 / 4 = 40
         */
            new BigInteger(1, messageDigest.digest()).toString(16).padLeft(40, '0')
        }

        Mac mac = Mac.getInstance("HmacSHA256")
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256")
        mac.init(secretKeySpec)
        byte[] digest = mac.doFinal(source.getBytes())
        def encodedData = digest.encodeBase64().toString()

        println "Source =       ${source}"
        println "SHA1 =         ${source.encodeAsSHA1()}"
        println "SHA256 =       ${source.encodeAsSHA256()}"
        println "SHA1-salt =    ${source.toSHA1(secret)}"
        println "HmacSHA256 =   ${encodedData}"

    }
}
