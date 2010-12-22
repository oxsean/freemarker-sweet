<#macro hello name=["ozn","cici"]>
<@expose/>
<#nested>
${name[1]}
</#macro>
<@hello><@param name="name[1]" value="me"/></@>