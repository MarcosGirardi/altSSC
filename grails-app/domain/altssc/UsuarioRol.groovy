package altssc

class UsuarioRol {

    static belongsTo = [usuario:Usuario, rol:Rol]

    static constraints = {
        usuario (nullable:false)
        rol     (nullable:false)
    }

    String toString() {"${this.usuario}; ${this. rol}"}
}
