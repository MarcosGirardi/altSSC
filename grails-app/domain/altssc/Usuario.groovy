package altssc

class Usuario {

    String  nombre
    String  password
    String  mail

    static hasMany = [roles:UsuarioRol]

    static constraints = {
        nombre  (nullable:false)
        password(nullable:false)
        mail    (nullable:false)
    }

    String toString(){"${this.nombre}"}

}
