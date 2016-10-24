package altssc

class Usuario {

    String  nombre
    String  apellido
    String  mail

    static hasMany = [roles:UsuarioRol]

    static constraints = {
        nombre  (nullable:false)
        apellido(nullable:false)
        mail    (nullable:false)
    }

    String toString(){"${this.apellido}, ${this.nombre}"}

}
