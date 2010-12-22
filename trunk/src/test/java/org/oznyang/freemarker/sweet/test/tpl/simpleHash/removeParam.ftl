<#macro outer name="ozn" age="27" sex="boy">
<@expose/>
    <#nested>
</#macro>

<#macro inner parent={}>
<@expose/>
<@param name="parent.name" action="remove"/>
${parent?keys?size}
</#macro>

<@outer><@inner/></@>