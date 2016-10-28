package altssc

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UsuarioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {

        if (session.usuario){println (session.usuario.nombre)} else {println "sin sesion"}

        params.max = Math.min(max ?: 10, 100)
        request.withFormat {
            form multipartForm {
                flash.message = message(code: "logeado con permisos: ${session.roles}")
            }
            '*'{ respond Usuario.list(params), model:[usuarioCount: Usuario.count()]}
        }

    }

    def show(Usuario usuario) {

        println session.roles
        def autorizado
        if (session.usuario){
            if (session.roles.contains("ROLE_USER")){
                autorizado = true
            } else {
                autorizado = false
            }
        } else {
            autorizado = false
        }

        if (!autorizado) {
            def msg = "no posee los permisos para ver esto."
            request.withFormat {
                form multipartForm {
                    flash.error = message(code: "${msg}")
                    redirect (controller: "usuario", action: "index")
                }
            }
        } else {
            respond usuario
        }


    }

    def create() {

        def autorizado
        if (session.usuario){
            if (session.roles.contains("ROLE_ADMIN")){
                autorizado = true
            } else {
                autorizado = false
            }
        } else {
            autorizado = false
        }

        if (!autorizado) {
            def msg = "no posee los permisos para ver esto."
            request.withFormat {
                form multipartForm {
                    flash.error = message(code: "${msg}")
                    redirect (controller: "usuario", action: "index")
                }
            }
        } else {
            respond new Usuario(params)
        }

    }

    @Transactional
    def save(Usuario usuario) {
        if (usuario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (usuario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond usuario.errors, view:'create'
            return
        }

        usuario.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.id])
                redirect usuario
            }
            '*' { respond usuario, [status: CREATED] }
        }
    }

    def edit(Usuario usuario) {
        respond usuario
    }

    @Transactional
    def update(Usuario usuario) {
        if (usuario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (usuario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond usuario.errors, view:'edit'
            return
        }

        usuario.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.id])
                redirect usuario
            }
            '*'{ respond usuario, [status: OK] }
        }
    }

    @Transactional
    def delete(Usuario usuario) {

        if (usuario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        usuario.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
