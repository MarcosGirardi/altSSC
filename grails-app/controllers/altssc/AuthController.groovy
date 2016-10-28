package altssc

class AuthController {

    def index() { }

    def login(params){
        println params
        println "comprobando"

        if (params.nombre && params.pass){
            println "están seteados"
            session.usuario = Usuario.findByNombreAndPassword(params.nombre, params.pass)
            if (session.usuario){
                session.roles = session.usuario.roles.rol.toString()
                println (session.usuario.nombre)
                println (session.roles)
                redirect(uri: "/")
            } else{
                println "no se encotró"
                redirect(uri: "/")
            }
        } else{
            println "no estaba seteado"
            redirect(uri: "/")
        }
    }
}
