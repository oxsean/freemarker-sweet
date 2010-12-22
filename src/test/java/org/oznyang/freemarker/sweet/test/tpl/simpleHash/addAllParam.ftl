<#macro outer name="ozn" age="27" sex="boy">
<@expose/>
<#nested>
</#macro>

<#macro inner parent={}>
<@expose/>
<@param name="parent" value={"me":"me","who":"who"} action="addAll"/>
${parent?keys?size}
</#macro>

<@outer><@inner/></@>