<#macro hello name={"ozn":"ozn","cici":"cici"}>
<@expose/>
<#nested>
${name?keys?size}
</#macro>
<@hello><@param name="name" value={"me":"me","who":"who"} action="addAll"/></@>