<#macro hello name="ozn">
<@expose/>
<#nested>
${name}
</#macro>

<@hello><@param name="name" value="cici"/></@>