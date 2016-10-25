<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div>
    <form action="login" method="post">
        nombre: <input type="text" name="nombre"><br>
        contraseña: <input name="pass" type="password">
        <g:submitButton tipe="submit" name="login" value="autenticar"/>
    </form>
</div>
</body>
</html>