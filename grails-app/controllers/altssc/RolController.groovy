package altssc

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RolController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {

        if (session.usuario) {session.invalidate()} else {println "sin sesion"}

        params.max = Math.min(max ?: 10, 100)
        respond Rol.list(params), model:[rolCount: Rol.count()]
    }

    def show(Rol rol) {
        respond rol
    }

    def create() {
        respond new Rol(params)
    }

    @Transactional
    def save(Rol rol) {
        if (rol == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (rol.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond rol.errors, view:'create'
            return
        }

        rol.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'rol.label', default: 'Rol'), rol.id])
                redirect rol
            }
            '*' { respond rol, [status: CREATED] }
        }
    }

    def edit(Rol rol) {
        respond rol
    }

    @Transactional
    def update(Rol rol) {
        if (rol == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (rol.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond rol.errors, view:'edit'
            return
        }

        rol.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'rol.label', default: 'Rol'), rol.id])
                redirect rol
            }
            '*'{ respond rol, [status: OK] }
        }
    }

    @Transactional
    def delete(Rol rol) {

        if (rol == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        rol.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'rol.label', default: 'Rol'), rol.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'rol.label', default: 'Rol'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
