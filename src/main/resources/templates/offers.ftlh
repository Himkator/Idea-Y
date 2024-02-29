<#import "temp.ftlh" as t>
<@t.templ user>
    <h1><a href="/companyOffers">Company offer</a></h1>
    <ul>
            <#list companyOffer as cf>
                <li>${cf.title}</li>
            </#list>
    </ul>
    <h1><a href="/peopleOffers">People offer</a></h1>
    <ul>
            <#list peopleOffer as pf>
                <li>${pf.name}</li>
            </#list>
    </ul>
</@t.templ>