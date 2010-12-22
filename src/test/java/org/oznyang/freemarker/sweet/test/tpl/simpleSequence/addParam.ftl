<#macro hello name=["ozn","cici"]>
<@expose/>
<#nested>
${name?size}
</#macro>
<@hello><@param name="name" value="me" action="add"/></@>