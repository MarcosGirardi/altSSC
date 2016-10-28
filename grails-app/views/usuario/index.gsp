<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-usuario" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-usuario" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <h1><g:message code="${session.usuario.nombre}" /></h1>
            <h1>
                <g:if test="${session.roles.contains('ROLE_USER')}">USUARIO</g:if><br>
                <g:if test="${session.roles.contains('ROLE_ADMIN')}">ADMINISTRADOR</g:if>
            </h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:if test="${flash.error}">
                <div class="errors" role="status">${flash.error}</div>
            </g:if>
            <f:table collection="${usuarioList}" />

            <div class="pagination">
                <g:paginate total="${usuarioCount ?: 0}" />
            </div>
        </div>
    </body>
</html>