package altssc

class Rol {

    String  autoridad

    static hasMany = [usuarios:UsuarioRol]

    static constraints = {
        autoridad(nullable:false)
    }

    String toString() {"${this.autoridad}"}
}
