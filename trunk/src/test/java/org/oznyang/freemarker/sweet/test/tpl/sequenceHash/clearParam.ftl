<#macro hello name={"ozn":"ozn","cici":"cici"}>
<@expose/>
<#nested>
${name?keys?size}
</#macro>
<@hello><@param name="name" action="clear"/></@>