<#macro hello name=["ozn","cici"]>
<@expose/>
<#nested>
${name?size}
</#macro>
<@hello><@param name="name" action="clear"/></@>