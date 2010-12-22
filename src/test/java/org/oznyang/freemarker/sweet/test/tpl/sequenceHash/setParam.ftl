<#macro hello name={"ozn":"ozn","cici":"cici"}>
<@expose/>
<#nested>
${name.me}
</#macro>
<@hello><@param name="name.me" value="ozn"/></@>