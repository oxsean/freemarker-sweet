<#macro hello name=["ozn","cici","me","who"]>
<@expose/>
<#nested>
${name?size}
</#macro>
<@hello><@param name="name[2..]" action="remove"/></@>