package altssc

class AuthController {

    def index() { }

    def login(params){
        println params
        println "comprobando"

        if (params.nombre == "marcos" && params.pass == "321"){
            session.usuario = Usuario.findByNombre('user')
            session.roles = session.usuario.roles.rol.toString()
            println (session.usuario.nombre)
            println (session.roles)
            redirect(uri: "/")
        } else {
            redirect(uri: "/")
        }
    }
}
