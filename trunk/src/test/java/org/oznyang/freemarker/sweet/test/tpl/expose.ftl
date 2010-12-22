<#macro outer name="cici">
<@expose/>
<#nested>
</#macro>

<#macro inner parent="">
<@expose parent="parent"/>
${parent.name}
</#macro>

<@outer><@inner/></@>