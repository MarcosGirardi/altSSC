package altssc

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UsuarioRolController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {

        if (session.usuario){println (session.usuario.nombre)} else {println "sin sesion"}

        params.max = Math.min(max ?: 10, 100)
        respond UsuarioRol.list(params), model:[usuarioRolCount: UsuarioRol.count()]
    }

    def show(UsuarioRol usuarioRol) {
        respond usuarioRol
    }

    def create() {
        respond new UsuarioRol(params)
    }

    @Transactional
    def save(UsuarioRol usuarioRol) {
        if (usuarioRol == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (usuarioRol.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond usuarioRol.errors, view:'create'
            return
        }

        usuarioRol.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuarioRol.label', default: 'UsuarioRol'), usuarioRol.id])
                redirect usuarioRol
            }
            '*' { respond usuarioRol, [status: CREATED] }
        }
    }

    def edit(UsuarioRol usuarioRol) {
        respond usuarioRol
    }

    @Transactional
    def update(UsuarioRol usuarioRol) {
        if (usuarioRol == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (usuarioRol.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond usuarioRol.errors, view:'edit'
            return
        }

        usuarioRol.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'usuarioRol.label', default: 'UsuarioRol'), usuarioRol.id])
                redirect usuarioRol
            }
            '*'{ respond usuarioRol, [status: OK] }
        }
    }

    @Transactional
    def delete(UsuarioRol usuarioRol) {

        if (usuarioRol == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        usuarioRol.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuarioRol.label', default: 'UsuarioRol'), usuarioRol.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuarioRol.label', default: 'UsuarioRol'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
